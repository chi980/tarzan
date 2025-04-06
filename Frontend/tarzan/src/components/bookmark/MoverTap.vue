<template>
  <div>
    <div class="tag-button-wrapper">
      <TagButtonGroup
        :buttons="tagOptions"
        v-model:selectedButton="selectedTag"
        :multiple="false" />
    </div>
    <CheckListItem
      v-for="checkItem in filteredCheckList"
      :key="checkItem.idx + checkItem.category"
      :checkListItem="checkItem"
      @change="updateCheckItem(checkItem.category, checkItem.idx)" />
  </div>
</template>

<script setup lang="ts">
// import { Check } from "@/data/check";
import { ref, onMounted, reactive, computed } from "vue";
import { axiosInstance } from "@/plugins/axiosPlugin";
import CheckListItem from "@/components/common/CheckListItem.vue";
import TagButtonGroup from "@/components/common/TagButtonGroup.vue";

const tagOptions = ref([
  { label: "전체", value: "ALL" },
  { label: "d-day", value: "D-DAY" },
  { label: "d-1", value: "D-1" },
  { label: "d-7", value: "D-7" },
  { label: "d-30", value: "D-30" },
]);

const selectedTag = ref("ALL"); // 단일 선택용

// 상태 변수 : 체크리스트
const checkListData = reactive({
  move_day_before_30days: [
    { idx: 1, name: "이사 방식 선택??", value: true },
    { idx: 2, name: "이사 업체 선정 후 계약", value: false },
    { idx: 3, name: "불필요한 물품 정리", value: false },
    { idx: 4, name: "입주 청소 예약", value: false },
    { idx: 5, name: "냉장고 파먹기", value: false },
    { idx: 6, name: "정기 구독 서비스 확인", value: false },
    { idx: 7, name: "인터넷 이전 신청", value: false },
  ],
  move_day_before_7days: [
    { idx: 1, name: "가구 배치 시뮬레이션", value: false },
    { idx: 2, name: "관리사무소 이사 일정 통보", value: false },
    { idx: 3, name: "은행 이체 한도 확인", value: false },
    { idx: 4, name: "도시 가스 요금 정산", value: false },
  ],
  move_day_before_1days: [
    { idx: 1, name: "귀중품 점검", value: false },
    { idx: 2, name: "냉장고, 세탁기 정리", value: false },
  ],
  move_day_before_ddays: [
    { idx: 1, name: "수도와 전기 요금 정산", value: false },
    { idx: 2, name: "새 집 상태 점검", value: false },
    { idx: 3, name: "쓰레기 봉투 구매", value: false },
    { idx: 4, name: "이사 요금 지불", value: false },
    { idx: 5, name: "전입신고와 확정일자", value: false },
    { idx: 6, name: "우편물 이전 신청", value: false },
  ],
});
const tagMap = {
  "D-30": "move_day_before_30days",
  "D-7": "move_day_before_7days",
  "D-1": "move_day_before_1days",
  "D-DAY": "move_day_before_ddays",
} as const;
const filteredCheckList = computed(() => {
  if (selectedTag.value === "ALL") {
    return [
      ...checkListData.move_day_before_30days.map((item) => ({
        ...item,
        category: "move_day_before_30days",
      })),
      ...checkListData.move_day_before_7days.map((item) => ({
        ...item,
        category: "move_day_before_7days",
      })),
      ...checkListData.move_day_before_1days.map((item) => ({
        ...item,
        category: "move_day_before_1days",
      })),
      ...checkListData.move_day_before_ddays.map((item) => ({
        ...item,
        category: "move_day_before_ddays",
      })),
    ];
  }

  const category = tagMap[selectedTag.value as keyof typeof tagMap];
  return category
    ? checkListData[category].map((item) => ({ ...item, category }))
    : [];
});

// 체크 상태 업데이트
const updateCheckItem = (category: string, idx: number) => {
  const list = checkListData[category as keyof typeof checkListData];
  const item = list.find((i) => i.idx === idx);
  if (item) {
    item.value = !item.value;
    saveToLocalStorage(); // ✅ 체크 상태 변경 시 저장
  }
};

const STORAGE_KEY = "checklist-storage-mover";
const saveToLocalStorage = () => {
  const dataToStore = JSON.parse(JSON.stringify(checkListData));
  localStorage.setItem(STORAGE_KEY, JSON.stringify(dataToStore));
};
const loadFromLocalStorage = () => {
  const stored = localStorage.getItem(STORAGE_KEY);
  if (stored) {
    const parsed = JSON.parse(stored);
    Object.keys(parsed).forEach((key) => {
      if (checkListData[key]) {
        checkListData[key].splice(0, checkListData[key].length, ...parsed[key]);
      }
    });
  }
};
onMounted(() => {
  loadFromLocalStorage();
  // fetchCheckMoverList(); // API로 덮어씌우는 로직이 있다면 필요 시 선택
});

// API: 체크리스트 API 호출
// const fetchCheckMoverList = async () => {
//   try {
//     const response = await axiosInstance.get(`/v1/checklist/mover?`);

//     if (response.data.success) {
//       console.log("이사 체크리스트 가져오기 성공!");
//       const data = response.data.data;
//       console.log("API 응답 데이터:", data);

//       // checkListData.move_day_before_30days = data.move_day_before_30days.name_list.map(
//       //   (name, index) => ({
//       //     idx: data.move_day_before_30days.id_list[index],
//       //     name,
//       //     value: data.move_day_before_30days.value_list[index],
//       //   })
//       // );
//       // checkListData.move_day_before_7days = data.move_day_before_7days.name_list.map(
//       //   (name, index) => ({
//       //     idx: data.move_day_before_7days.id_list[index],
//       //     name,
//       //     value: data.move_day_before_7days.value_list[index],
//       //   })
//       // );
//       // checkListData.move_day_before_1days = data.move_day_before_1days.name_list.map(
//       //   (name, index) => ({
//       //     idx: data.move_day_before_1days.id_list[index],
//       //     name,
//       //     value: data.move_day_before_1days.value_list[index],
//       //   })
//       // );
//       // checkListData.move_day_before_ddays = data.move_day_before_ddays.name_list.map(
//       //   (name, index) => ({
//       //     idx: data.move_day_before_ddays.id_list[index],
//       //     name,
//       //     value: data.move_day_before_ddays.value_list[index],
//       //   })
//       // );
//       // 🔥 모든 카테고리를 한 번에 업데이트
//       Object.keys(checkListData).forEach((key) => {
//         if (data[key]) {
//           checkListData[key].splice(
//             0,
//             checkListData[key].length, // 기존 값 전부 제거
//             ...data[key].name_list.map((name, index) => ({
//               idx: data[key].id_list[index],
//               name,
//               value: data[key].value_list[index],
//             }))
//           );
//         }
//       });
//     } else {
//       console.error("체크리스트 데이터 없음", response.data.message);
//     }
//   } catch (error) {
//     console.error("API 요청 오류: ", error);
//   }
// };

// // const updateCheckItem = async (category: string, itemIdx: number) => {
// //   try {
// //     const item = checkListData[category].find((item) => item.idx === itemIdx);
// //     if (!item) return;

// //     const response = await axiosInstance.post(`/v1/checklist/mover`, {
// //       itemIdx,
// //       value: !item.value, // 현재 값의 반대로 변경 요청
// //     });

// //     if (response.data.success) {
// //       item.value = !item.value; // ✅ 요청이 성공한 경우에만 상태 변경
// //       console.log("체크 상태 저장 성공!", response.data);
// //     } else {
// //       console.error("체크 상태 저장 실패", response.data.message);
// //     }
// //   } catch (error) {
// //     console.error("체크 상태 저장 중 오류 발생: ", error);
// //   }
// // };

// // 컴포넌트가 로드되었을 때 체크리스트 불러오기
// onMounted(() => {
//   fetchCheckMoverList();
// });
</script>

<style lang="scss" scoped>
// 공통
.tag-button-wrapper {
  @include custom-padding-x;
  @include custom-padding-y($padding-small);
}
// scoped
</style>
