export type Tag =
  | "NONE"
  | "BUG"
  | "BUILDING_MANAGEMENT"
  | "PARKING"
  | "SOUND_PROOF"
  | "SECURITY"
  | "TRAIL"
  | "SECURITY_GUARD"
  | "VENTILATION"
  | "FLAT"
  | "QUITE"
  | "PREVENT_HEAT"
  | "PET"
  | "ELEVATOR"
  | "MOLD";

export interface Review {
  review_id: number;
  review_img_url: string;
  review_score: number;
  review_lease_type: string; // LeaseType enum 형태면 string 또는 enum으로
  review_deposit: number;
  review_management_fee: number;
  review_residence_period: number;
  review_floor: number;
  review_advantage: string;
  review_advantage_tags: Tag[];
  review_disadvantage: string;
  review_disadvantage_tags: Tag[];
  review_writer_nickname: string;
  review_is_writer: boolean;
}
export type LeaseType = "MONTHLY" | "KEY_MONEY";

export interface ReviewRequest {
  review_house_id: number;
  review_img_url: string;
  review_score: number;
  review_lease_type: LeaseType;
  review_rent: number;
  review_deposit: number;
  review_management_fee: number;
  review_residence_period: number;
  review_floor: number;
  review_advantage: string;
  review_advantage_tags: Tag[];
  review_disadvantage: string;
  review_disadvantage_tags: Tag[];
}
