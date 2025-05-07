// src/composables/useKakaoMarkerManager.ts
import { ref } from "vue";

export type MarkerClickHandler<T> = (item: T) => void;

export function useKakaoMarkerManager(mapInstance: kakao.maps.Map) {
  // internal markers & optional clusterer
  const markers = ref<kakao.maps.Marker[]>([]);
  let clusterer: kakao.maps.MarkerClusterer | null = null;

  /** Remove all existing markers and clear cluster */
  function clearAll() {
    markers.value.forEach((m) => m.setMap(null));
    markers.value = [];
    if (clusterer) {
      clusterer.clear();
    }
  }

  /**
   * 단일 마커 표시. 기존 마커는 모두 지우고 하나만 남깁니다.
   * @param latitude  – 위도
   * @param longitude – 경도
   * @param data      – 클릭 시 넘겨줄 데이터
   * @param onClick   – 클릭 핸들러
   */
  function addMarker<T>(
    latitude: number,
    longitude: number,
    data: T,
    onClick: MarkerClickHandler<T>
  ): kakao.maps.Marker {
    clearAll();
    const marker = new kakao.maps.Marker({
      map: mapInstance,
      position: new kakao.maps.LatLng(latitude, longitude),
    });
    kakao.maps.event.addListener(marker, "click", () => onClick(data));
    markers.value.push(marker);
    return marker;
  }

  /**
   * Generic marker renderer.
   * @param list        – 데이터 리스트
   * @param latKey      – 객체 내 위도 프로퍼티 키
   * @param lngKey      – 객체 내 경도 프로퍼티 키
   * @param onClick     – 마커 클릭 시 실행할 핸들러
   * @param useCluster  – true면 클러스터에, false면 개별 마커로
   */
  function addMarkers<T extends Record<string, any>>(
    list: T[],
    latKey: keyof T,
    lngKey: keyof T,
    onClick: MarkerClickHandler<T>,
    useCluster = true
  ) {
    clearAll();

    // 클러스터를 쓸 경우 한 번만 초기화
    if (useCluster && !clusterer) {
      clusterer = new kakao.maps.MarkerClusterer({
        map: mapInstance,
        averageCenter: true,
        minLevel: 5,
      });
    }

    list.forEach((item) => {
      const lat = Number(item[latKey]);
      const lng = Number(item[lngKey]);
      const marker = new kakao.maps.Marker({
        position: new kakao.maps.LatLng(lat, lng),
      });

      kakao.maps.event.addListener(marker, "click", () => onClick(item));

      if (useCluster && clusterer) {
        clusterer.addMarker(marker);
      } else {
        marker.setMap(mapInstance);
      }

      markers.value.push(marker);
    });
  }

  return {
    clearAll,
    addMarkers,
    addMarker,
  };
}
