import { ref } from "vue";

export interface Check {
  idx: number;
  name: string;
  value: boolean;
}

export interface CategoryCheckList {
  BEFO_MOVE: Check[];
  AFTER_MOVE: Check[];
}

export interface FullCheckListData {
  homeAppliances: CategoryCheckList;
  fabrics: CategoryCheckList;
  bathroomSupplies: CategoryCheckList;
  ingredients: CategoryCheckList;
  kitchenUtensils: CategoryCheckList;
  householdGoods: CategoryCheckList;
}

export const checkListData: FullCheckListData = {
  homeAppliances: {
    BEFO_MOVE: [
      { idx: 1, name: "냄비", value: false },
      { idx: 2, name: "뒤집개", value: false },
    ],
    AFTER_MOVE: [
      { idx: 1, name: "전자레인지", value: false },
      { idx: 2, name: "에어컨", value: false },
    ],
  },
  fabrics: {
    BEFO_MOVE: [
      { idx: 1, name: "방석", value: false },
      { idx: 2, name: "커튼", value: false },
    ],
    AFTER_MOVE: [
      { idx: 1, name: "이불", value: false },
      { idx: 2, name: "카펫", value: false },
    ],
  },
  bathroomSupplies: {
    BEFO_MOVE: [
      { idx: 1, name: "방석", value: false },
      { idx: 2, name: "커튼", value: false },
    ],
    AFTER_MOVE: [
      { idx: 1, name: "이불", value: false },
      { idx: 2, name: "카펫", value: false },
    ],
  },
  ingredients: {
    BEFO_MOVE: [
      { idx: 1, name: "방석", value: false },
      { idx: 2, name: "커튼", value: false },
    ],
    AFTER_MOVE: [
      { idx: 1, name: "이불", value: false },
      { idx: 2, name: "카펫", value: false },
    ],
  },
  kitchenUtensils: {
    BEFO_MOVE: [
      { idx: 1, name: "방석", value: false },
      { idx: 2, name: "커튼", value: false },
    ],
    AFTER_MOVE: [
      { idx: 1, name: "이불", value: false },
      { idx: 2, name: "카펫", value: false },
    ],
  },
  householdGoods: {
    BEFO_MOVE: [
      { idx: 1, name: "방석", value: false },
      { idx: 2, name: "커튼", value: false },
    ],
    AFTER_MOVE: [
      { idx: 1, name: "이불", value: false },
      { idx: 2, name: "카펫", value: false },
    ],
  },
};
