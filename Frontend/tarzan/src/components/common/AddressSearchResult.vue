<script setup lang="ts">
import { ref } from 'vue';

import locationMarkImg from "@/assets/icons/location_mark.png"
</script>

<template>
            <div class="location-wrapper">
                <div class="location-degree">
                    <img :src="locationMarkImg" alt="이미지">
                    <p>300m</p>
                </div>
                <div class="location-address-wrapper">
                    <p class="location-address-name">나의 원룸<span>고시원, 고시텔</span></p>
                    <p class="location-address">서울 중구 서소문로 11231(봉래동)</p>
                </div>
            </div>
</template>

<style scoped lang="scss">

.location-wrapper{
    @include custom-text;
    @include custom-padding-y($padding-default);
    @include custom-padding-x($padding-small);

    display: flex;
    flex-direction: row;
    gap: $padding-default;
    align-items: center; /* 아이템을 수직 중앙에 정렬 */

    .location-degree{
        img{
            @include custom-icon-style(24px);
        }
        p{
            
  font-family: "Ownglyph_ParkDaHyun", sans-serif;
  font-size: 12px;
  color: #969696;
        }
    }

    .location-address-wrapper{
        flex:1;
        display: flex;
        flex-direction: column;
        gap: $padding-small;
        
        @include custom-text($font-size: 12px, $font-color: $text-color-light);
        text-align: left;

        .location-address-name{
            @include custom-text($font-size: 14px);
            span{
        @include custom-text($font-size: 12px, $font-color: $text-color-light);
        padding-left: $padding-small;
            }
        }
    }

}

</style>