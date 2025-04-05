export interface House {
  house_id: number;
  house_latitude: number;
  house_longitude: number;
}

export interface HouseOverview {
  house_name: string;
  house_address: string;
}

export interface CompareHouse extends House {
  [key: string]: any; // 모든 string 타입 키에 대해 any 타입 값을 허용
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

  factor: {
    transportation: number;
    shopping: number;
    amenity: number;
    security: number;
    clinic: number;
  };
}

export interface HouseDetail {
  house_id: number;
  house_name: string;
  house_address: string;
  house_category: string;
  house_latitude: number;
  house_longitude: number;
  house_indexes: Index[];
  house_review_images: string[];
  house_reviews: Review[];
}

export interface Index {
  house_index_amenity: number;
  house_index_clinic: number;
  house_index_security: number;
  house_index_shopping: number;
  house_index_transportation: number;
}
