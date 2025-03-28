<script setup lang="ts">
/** load */
import { ref, defineEmits } from 'vue';

import TopBarBack from "@/components/common/TopBarBack.vue";
import AddressSearchResult from './AddressSearchResult.vue';
import {Building }from "@/data/Building";

const KAKAO_API_KEY = import.meta.env.VITE_KAKAO_API_KEY; // 여기에 본인의 카카오 REST API 키를 입력하세요.

const emit = defineEmits(['close']);

/** emits */
const closeModal = () => {
  emit('close'); // 부모 컴포넌트에 모달 닫기 이벤트 전달
};

// 검색어와 결과를 저장할 상태 변수
const searchQuery = ref(""); // 사용자가 입력한 검색어
interface SearchResult {
  place_name: string;
  road_address_name?: string;
  address_name?: string;
  x: string; // 경도
  y: string; // 위도
}

const searchResults = ref<SearchResult[]>([]); // 검색 결과를 저장하는 배열

// 위도와 경도를 저장할 상태 변수
const selectedLocation = ref<{
  latitude: string | null;
  longitude: string | null;
}>({
  latitude: null,
  longitude: null,
});


// 주소 검색 함수
const searchAddress = async () => {
  if (!searchQuery.value) {
    searchResults.value = [];
    return;
  }

  try {
    // 키워드 검색 API 호출
    const response = await axios.get(
      "https://dapi.kakao.com/v2/local/search/keyword.json",
      {
        params: { query: searchQuery.value },
        headers: { Authorization: `KakaoAK ${KAKAO_API_KEY}` },
      }
    );
    searchResults.value = response.data.documents; // 검색 결과 저장
  } catch (error) {
    console.error("주소 검색 중 오류 발생: ", error);
    searchResults.value = [];
  }
};

// 주소 선택 함수
const selectAddress = (selectedPlace: SearchResult) => {
  searchQuery.value = `${selectedPlace.place_name} - ${
    selectedPlace.road_address_name || selectedPlace.address_name
  }`;
  searchResults.value = []; // 검색 결과 목록 초기화

  // 위도와 경도 저장
  selectedLocation.value.latitude = selectedPlace.y; // 위도
  selectedLocation.value.longitude = selectedPlace.x; // 경도
};

</script>

<template>
<div class="modal-container" @click.self="closeModal">
    <div class="modal-wrapper">
        <div class="modal-title" style="background-color: aqua;display: flex;flex-direction: row;"> 
            <div style="width: 64px;height: 64px;background-color: black;" @click="closeModal"><-</div>
            <h1>주소 검색</h1>
        </div>
        <div class="modal-content">
            <!-- <input type="text" v-model="searchQuery" placeholder="검색어를 입력하세요" />
            <div v-if="searchResults.length > 0">
                <ul>
                    <li v-for="result in searchResults" :key="result.place_name" @click="selectAddress(result)">
                        {{ result.place_name }} - {{ result.road_address_name || result.address_name }}
                    </li>
                </ul>
            </div> -->


    <input v-model="searchQuery" type="text" placeholder="검색할 주소명을 입력해주세요" class="search-input"/>
<AddressSearchResult></AddressSearchResult>
        </div>
    </div>

    <div class="button-wrapper">
        <button class="button-default" @click="closeModal">검색</button>
    </div>
</div>
</template>

<style scoped lang="scss">
.modal-wrapper{
    display: flex;
    flex-direction: column;

    height: 100%;

    .modal-title{
        height: $height-top-bar;
        background-color: aqua;
    }

    .modal-content{

@include custom-padding-x($padding-default);
@include custom-padding-y($padding-big);

display: flex;
flex-direction: column;

height: 100%;;
overflow-y: auto; /* 세로 스크롤을 추가 */
@include custom-scrollbar-style; /* 스크롤바 스타일 적용 */

    
}
}
.modal-container{
    @include custom-modal;
}

.search-input{
    @include custom-input-style;
    // @include custom-shadow-style;
}

.button-wrapper{
    width: 100%;position: absolute; bottom: $padding-default;display: flex; flex-direction: row;
}

.button-default{
  @include custom-button-style($bg-color: $secondary-color-default,$font-color: white);
  @include custom-margin-x;
}
</style>