<!-- api 요청을 통해 데이터를 가져오고, 스크롤 이벤트를 감지하여 무한 스크롤을 구현하는 Vue 컴포넌트입니다. -->
<template>
  <div>
    <SkeletonList v-if="loading && items.length === 0" />
    <NonContent
      v-else-if="!loading && items.length === 0"
      :value="'내용이 없습니다.'" />
    <div v-else>
      <slot
        name="item"
        v-for="(item, index) in items"
        :key="index"
        v-bind="{
          item,
          index,
          onDelete: (itemArg, indexArg) =>
            props.onDelete?.(itemArg).then(() => {
              handleDelete(itemArg, indexArg); // 혹은 indexArg 사용
            }),
          onClick: (itemArg) => {
            props.onClick?.(itemArg);
          },
        }" />

      <InfiniteScrollTrigger @trigger="onScrollBottom" />
    </div>
  </div>
</template>
<script setup lang="ts">
import { onMounted, ref, watch, defineEmits } from "vue";
import SkeletonList from "@/components/common/SkeletonList.vue";
import NonContent from "@/components/common/NonContent.vue";
import InfiniteScrollTrigger from "@/components/common/InfiniteScrollTrigger.vue";

/**
 * @fetchItems: 데이터를 받아올 함수, 데이터와 다음 페이지 여부를 반환
 * @params: API 요청에 필요한 파라미터
 * @onDelete: 삭제 이벤트[선택적]
 */
const props = defineProps<{
  fetchItems: (
    page: number,
    params: any
  ) => Promise<{ items: any[]; isNext: boolean }>;
  params: any;
  onDelete?: (id: number) => Promise<void>; // 삭제 후 비동기 처리 예상[선택적]
  onClick?: (item: any) => void; // 클릭 이벤트[선택적]
}>();

//화면에 그릴 데이터 리스트를 저장할 반응형 배열
const items = ref<any[]>([]);
//현재 몇 페이지까지 불러왔는지 저장 (무한스크롤에 사용)
const page = ref(1);
const loading = ref(false);
const isEnd = ref(false);

// params 바뀔 때마다 API 재요청 하도록 감지
// 파라미터가 바뀌면 페이지 초기화하고 데이터 새로 불러옴
watch(
  () => props.params,
  async () => {
    page.value = 1;
    items.value = [];
    isEnd.value = false;
    await loadItems();
  }
);

async function loadItems() {
  // 이미 로딩 중이거나 마지막 페이지라면 요청 안 함
  if (loading.value || isEnd.value) return;

  //로딩 상태 true로 바꾸고, API 요청
  loading.value = true;

  //받아온 데이터가 없으면 마지막 페이지로 간주, 아니면 리스트에 추가
  const newItems = await props.fetchItems(page.value, props.params);
  if (newItems.length === 0) {
    isEnd.value = true;
  } else {
    items.value.push(...newItems);
    page.value++;
  }
  loading.value = false;
}
// 삭제 이벤트 핸들러
function handleDelete(item: any, index: number) {
  items.value.splice(index, 1);
}

//스크롤 끝에 도달하면 다음 페이지 불러오는 함수
function onScrollBottom() {
  loadItems();
}
//컴포넌트 처음 로드될 때 첫 페이지 불러옴
onMounted(() => {
  loadItems();
});
</script>
