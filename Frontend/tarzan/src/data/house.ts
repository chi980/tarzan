export interface House {
  idx: number;
  name: string;
  type: string;
  address: string;
}

export interface CompareHouse extends House {
  score: number;

  moneyType: string;
  deposit: number;
  rent: number;
  utilities: number;

  pet: string;
  parking: string;
  roomCnt: number;
  bathroomCnt: number;
  movingDay: Date;
  floor: number;
  direction: string;

  water: number;
  window: number;
  bathroom: number;
  surrounding: number;
  option: number;
  detail: number;
  security: number;
  etc: number;
  totalScore: number;
}
