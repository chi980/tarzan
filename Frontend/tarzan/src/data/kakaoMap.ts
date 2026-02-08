// 레벨에 따른 축척 비율을 저장한 객체
export interface ScaleRatio {
  distance: number; // 실제 거리 (단위: 미터)
  scale: number; // 축척 비율 (1:scale)
}

// 레벨별 축척 비율 정의 (distance: m, scale: 1:xxx)
export const scaleRatios: Record<number, ScaleRatio> = {
  // 0: { distance: 10, scale: 475 },
  // 1: { distance: 20, scale: 950 },
  // 2: { distance: 30, scale: 1900 },
  // 3: { distance: 50, scale: 3800 },
  // 4: { distance: 100, scale: 7600 },
  // 5: { distance: 250, scale: 15200 },
  // 6: { distance: 500, scale: 30400 },
  // 7: { distance: 1000, scale: 60800 },
  // 8: { distance: 2000, scale: 121600 },
  // 9: { distance: 4000, scale: 243200 },
  // 10: { distance: 8000, scale: 486400 },
  // 11: { distance: 16000, scale: 972800 },
  // 12: { distance: 32000, scale: 1945600 },
  // 13: { distance: 64000, scale: 3891200 },
  // 14: { distance: 128000, scale: 7782400 },

  0: { distance: 10, scale: 1945600 },
  1: { distance: 20, scale: 1945600 },
  2: { distance: 30, scale: 1945600 },
  3: { distance: 50, scale: 1945600 },
  4: { distance: 100, scale: 1945600 },
  5: { distance: 250, scale: 1945600 },
  6: { distance: 500, scale: 1945600 },
  7: { distance: 1000, scale: 1945600 },
  8: { distance: 2000, scale: 1945600 },
  9: { distance: 4000, scale: 1945600 },
  10: { distance: 8000, scale: 1945600 },
  11: { distance: 16000, scale: 1945600 },
  12: { distance: 32000, scale: 3891200 },
  13: { distance: 64000, scale: 7782400 },
  14: { distance: 128000, scale: 7782400 },
};

// 특정 레벨에 대한 축척 비율을 조회하는 함수
export const getScaleRatio = (level: number): ScaleRatio | null => {
  return scaleRatios[level] || null; // 레벨에 해당하는 축척 비율 반환, 없으면 null 반환
};
