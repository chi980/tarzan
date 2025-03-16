import { axiosInstance } from "@/plugins/axiosPlugin";

// 리뷰 생성 API 호출 함수
export const createReview = async (reviewData) => {
  try {
    const formattedData = {
      // buildIdx: reviewData.buildIdx || 1, // 기본값 1
      buildIdx: 1, // 기본값 1
      review_score: reviewData.review_score,
      review_lease_type: reviewData.review_lease_type,
      review_rent: reviewData.review_rent ? Number(reviewData.review_rent.toString().replace(/,/g, "")) : null,
      review_deposit: reviewData.review_deposit ? Number(reviewData.review_deposit.toString().replace(/,/g, "")) : null,
      review_management_fee: reviewData.review_management_fee ? Number(reviewData.review_management_fee.toString().replace(/,/g, "")) : null,
      review_residence_period: reviewData.review_residence_period,
      review_floor: reviewData.review_floor,
      review_advantage: reviewData.review_advantage,
      review_advantage_tag: reviewData.review_advantage_tag || [],
      review_disadvantage: reviewData.review_disadvantage,
      review_disadvantage_tag: reviewData.review_disadvantage_tag || []
    };
    console.log("📢 전송 데이터:", reviewData); 

    const response = await axiosInstance.post("/v1/reviews", formattedData);
    
    // 성공 시 응답 반환
    return response.data;
  } catch (error) {
    console.error("리뷰 생성 중 오류 발생:", error);
    throw error;
  }
};
