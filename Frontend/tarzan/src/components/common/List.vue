<!-- api 요청을 통해 데이터를 가져오고, 스크롤 이벤트를 감지하여 무한 스크롤을 구현하는 Vue 컴포넌트입니다. -->
<template>
  <div>
    <Transition name="fade">
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
              if (props.isItemChangable) {
                Object.assign(item, itemArg); // item 객체 내부를 itemArg로 덮어쓰기
                props.onClick?.(items);
              } else {
                props.onClick?.(item);
              }
            },
          }" />

        <InfiniteScrollTrigger @trigger="onScrollBottom" />
      </div>
    </Transition>
  </div>
</template>
<script setup lang="ts">
import { onMounted, ref, watch, defineEmits, defineExpose } from "vue";
import SkeletonList from "@/components/common/SkeletonList.vue";
import NonContent from "@/components/common/NonContent.vue";
import InfiniteScrollTrigger from "@/components/common/InfiniteScrollTrigger.vue";

/**
 * @fetchItems: 데이터를 받아올 함수, 데이터와 다음 페이지 여부를 반환
 * @params: API 요청에 필요한 파라미터
 * @onDelete: 삭제 이벤트[선택적]
 */
const props = withDefaults(
  defineProps<{
    fetchItems: (
      page: number,
      params: any
    ) => Promise<{ items: any[]; isNext: boolean }>;
    params: any;
    onDelete?: (id: number) => Promise<void>; // 삭제 후 비동기 처리 예상[선택적]
    onClick?: (item: any) => void; // 클릭 이벤트[선택적]
    isItemChangable?: boolean; // 아이템 수정 가능 여부
  }>(),
  {
    isItemChangable: false,
  }
);

// 1) 리스트 앞에 아이템을 추가하는 메서드
function addItem(item: any) {
  items.value.unshift(item);
}

// 2) 부모가 호출할 수 있도록 공개
defineExpose({ addItem });

//화면에 그릴 데이터 리스트를 저장할 반응형 배열
const items = ref<any[]>([]);
//현재 몇 페이지까지 불러왔는지 저장 (무한스크롤에 사용)
const page = ref(0);
const loading = ref(false);
const isEnd = ref(false);

// params 바뀔 때마다 API 재요청 하도록 감지
// 파라미터가 바뀌면 페이지 초기화하고 데이터 새로 불러옴
watch(
  () => props.params,
  async () => {
    page.value = 0;
    items.value = [];
    isEnd.value = false;
    await loadItems();
  },
  {
    deep: true, // ← 객체 내부 속성 변경까지 감지
    immediate: true, // ← 마운트 직후에도 실행하고 싶다면
  }
);

// async function loadItems() {
//   // 이미 로딩 중이거나 마지막 페이지라면 요청 안 함
//   if (loading.value || isEnd.value) return;

//   //로딩 상태 true로 바꾸고, API 요청
//   loading.value = true;

//   //받아온 데이터가 없으면 마지막 페이지로 간주, 아니면 리스트에 추가
//   const newItems = await props.fetchItems(page.value, props.params);
//   if (newItems.length === 0) {
//     isEnd.value = true;
//   } else {
//     items.value.push(...newItems);
//     page.value++;
//   }
//   loading.value = false;
// }
async function loadItems() {
  if (loading.value || isEnd.value) return;

  loading.value = true;

  try {
    const { items: newItems, isNext } = await props.fetchItems(
      page.value,
      props.params
    );

    items.value.push(...newItems);
    page.value++;

    if (!isNext) {
      isEnd.value = true;
    }
  } catch (error) {
    console.error("데이터 로딩 실패:", error);
  } finally {
    loading.value = false;
  }
}

// 삭제 이벤트 핸들러
function handleDelete(item: any, index: number) {
  console.log("삭제 요청된 index:", index);
  console.log("삭제 요청된 item.id:", item.id);
  console.log(
    "현재 items:",
    items.value.map((i) => i.id)
  ); // 또는 i.review_id
  items.value.splice(index, 1);
  console.log("아이템 삭제됨:", item);
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
