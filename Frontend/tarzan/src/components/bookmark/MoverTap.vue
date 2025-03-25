<template>
  <div class="accordion-wrapper tab-content">
    <BasicAccordion accordionTitle="이사 D-30">
      <CheckListItem
        v-for="checkItem in checkListData.move_day_before_30days" 
        :key="checkItem.idx"
        :checkListItem="checkItem"
        @change="updateCheckItem('move_day_before_30days', checkItem.idx)"
      ></CheckListItem>
    </BasicAccordion>
    <BasicAccordion accordionTitle="이사 D-7">
      <CheckListItem
        v-for="checkItem in checkListData.move_day_before_7days"
        :key="checkItem.idx"
        :checkListItem="checkItem"
      ></CheckListItem>
    </BasicAccordion>
    <BasicAccordion accordionTitle="이사 D-1">
      <CheckListItem
        v-for="checkItem in checkListData.move_day_before_1days"
        :key="checkItem.idx"
        :checkListItem="checkItem"
      ></CheckListItem>
    </BasicAccordion>
    <BasicAccordion accordionTitle="이사 D-DAY">
      <CheckListItem
        v-for="checkItem in checkListData.move_day_before_ddays"
        :key="checkItem.idx"
        :checkListItem="checkItem"
      ></CheckListItem>
    </BasicAccordion>

    <div>
      <div class="button-in-tab">
        <p>전입 신고 하기</p>
      </div>

      <div class="button-in-tab">
        <p>우편물 이전 신청하기</p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
// import { Check } from "@/data/check";
import { onMounted, reactive } from "vue";
import { axiosInstance } from "@/plugins/axiosPlugin";
import BasicAccordion from "@/components/common/BasicAccordion.vue";
import CheckListItem from "@/components/common/CheckListItem.vue";

// const checkListBefore30Days: Check[] = [
//   { idx: 1, name: "이사 방식 선택??", value: true },
//   { idx: 2, name: "이사 업체 선정 후 계약", value: false },
//   { idx: 3, name: "불필요한 물품 정리", value: false },
//   { idx: 4, name: "입주 청소 예약", value: false },
//   { idx: 5, name: "냉장고 파먹기", value: false },
//   { idx: 6, name: "정기 구독 서비스 확인", value: false },
//   { idx: 7, name: "인터넷 이전 신청", value: false },
// ];

// const checkListBefore7Days: Check[] = [
//   { idx: 1, name: "가구 배치 시뮬레이션", value: false },
//   { idx: 2, name: "관리사무소 이사 일정 통보", value: false },
//   { idx: 3, name: "은행 이체 한도 확인", value: false },
//   { idx: 4, name: "도시 가스 요금 정산", value: false },
// ];

// const checkListBefore1Days: Check[] = [
//   { idx: 1, name: "귀중품 점검", value: false },
//   { idx: 2, name: "냉장고, 세탁기 정리", value: false },
// ];

// const checkListDdays: Check[] = [
//   { idx: 1, name: "수도와 전기 요금 정산", value: false },
//   { idx: 2, name: "새 집 상태 점검", value: false },
//   { idx: 3, name: "쓰레기 봉투 구매", value: false },
//   { idx: 4, name: "이사 요금 지불", value: false },
//   { idx: 5, name: "전입신고와 확정일자", value: false },
//   { idx: 6, name: "우편물 이전 신청", value: false },
// ];

// 상태 변수 : 체크리스트
const checkListData = reactive({
  move_day_before_30days: [],
  move_day_before_7days: [],
  move_day_before_ddays: [],
  move_day_before_1days: [],
});

// API: 체크리스트 API 호출
const fetchCheckMoverList = async () => {
  try {
    const response = await axiosInstance.get(`/v1/checklist/mover?`);

    if (response.data.success) {
      console.log("이사 체크리스트 가져오기 성공!");
      const data = response.data.data;
      console.log("API 응답 데이터:", data);

      // checkListData.move_day_before_30days = data.move_day_before_30days.name_list.map(
      //   (name, index) => ({
      //     idx: data.move_day_before_30days.id_list[index], 
      //     name, 
      //     value: data.move_day_before_30days.value_list[index], 
      //   })
      // );
      // checkListData.move_day_before_7days = data.move_day_before_7days.name_list.map(
      //   (name, index) => ({
      //     idx: data.move_day_before_7days.id_list[index],
      //     name,
      //     value: data.move_day_before_7days.value_list[index],
      //   })
      // );
      // checkListData.move_day_before_1days = data.move_day_before_1days.name_list.map(
      //   (name, index) => ({
      //     idx: data.move_day_before_1days.id_list[index],
      //     name,
      //     value: data.move_day_before_1days.value_list[index],
      //   })
      // );
      // checkListData.move_day_before_ddays = data.move_day_before_ddays.name_list.map(
      //   (name, index) => ({
      //     idx: data.move_day_before_ddays.id_list[index],
      //     name,
      //     value: data.move_day_before_ddays.value_list[index],
      //   })
      // );
      // 🔥 모든 카테고리를 한 번에 업데이트
      Object.keys(checkListData).forEach((key) => {
        if (data[key]) {
          checkListData[key].splice( 
            0, 
            checkListData[key].length, // 기존 값 전부 제거
            ...data[key].name_list.map((name, index) => ({
              idx: data[key].id_list[index],
              name,
              value: data[key].value_list[index],
            }))
          );
        }
      });
  
    } else {
      console.error("체크리스트 데이터 없음", response.data.message);

    }
  } catch (error) {
    console.error("API 요청 오류: ", error);
  }
};

const updateCheckItem = async (category: string, itemIdx: number) => {
  try {
    const item = checkListData[category].find((item) => item.idx === itemIdx);
    if (!item) return;

    const response = await axiosInstance.post(`/v1/checklist/mover`, {
      itemIdx,
      value: !item.value,  // 현재 값의 반대로 변경 요청
    });

    if (response.data.success) {
      item.value = !item.value; // ✅ 요청이 성공한 경우에만 상태 변경
      console.log("체크 상태 저장 성공!", response.data);
    } else {
      console.error("체크 상태 저장 실패", response.data.message);
    }
  } catch (error) {
    console.error("체크 상태 저장 중 오류 발생: ", error);
  }
};


// 컴포넌트가 로드되었을 때 체크리스트 불러오기
onMounted(() => {
  fetchCheckMoverList();
});
</script>

<style lang="scss" scoped>
// 공통
.tab-content {
  margin-top: 0;
}
.button-in-tab {
  @include custom-button-style(
    $bg-color: #f2f2f2,
    $icon-size: 18px,
    $height: 52px
  );
  margin-bottom: $padding-small;
}
// scoped
.accordion-wrapper {
  display: flex;
  flex-direction: column;
  gap: $padding-small;
}
</style>
