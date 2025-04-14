export interface House {
  house_id: number;
  house_latitude: number;
  house_longitude: number;
}

export interface HouseOverview {
  house_name: string;
  house_address: string;
}
// 체크리스트 항목 타입
export interface HouseChecks {
  CHECK_WINDOW: number;
  CHECK_WATER: number;
  CHECK_DETAIL: number;
  CHECK_BATHROOM: number;
  CHECK_OPTION: number;
  [key: string]: number; // 추후 체크리스트 항목이 더 늘어날 수 있으므로
}

// 인덱스 항목 타입
export interface HouseIndexes {
  AMENITY: number;
  CLINIC: number;
  SECURITY: number;
  SHOPPING: number;
  TRANSPORTATION: number;
  [key: string]: number; // 확장성 고려
}

// 전체 주택 정보 타입
export interface HouseCompareInfo {
  house_id: number;
  house_name: string;
  house_address: string;
  house_category: string;
  house_score: number;
  house_checks: HouseChecks;
  house_indexes: HouseIndexes;
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
