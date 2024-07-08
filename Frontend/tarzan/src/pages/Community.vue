<template>
  <div class="sub-container">
    <TopBar></TopBar>

    <div class="center-container">
      <div class="searchbar">
        <div class="input-icon-wrap">
          <font-awesome-icon :icon="['fas', 'magnifying-glass']" class="icon-search"/>
          <input
            v-model="searchQuery" 
            type="text"
            placeholder="찾고 싶은 글 제목을 입력해주세요." />
        </div>
      </div>

      <DescriptionComponent
          descriptionImgSrc="/src/assets/etc/Saly-25.png"
          descriptionTitle="동네주민과<br/>얘기해보세요!"
          descriptionContent="여러 정보를 얻어보세요<br/>모임도 참여할 수 있어요!"
          backgroundColor="#FFF7D9"/>

      <TagButtonGroup />
      <PostList />

      <div class="write-button" @click="goToPostCreate">
        <img :src="CompareImgSrc" />
        글쓰기
      </div>
    </div>

    <BottomBar></BottomBar>
  </div>
</template>

<script>
import TopBar from "@/components/common/TopBar.vue";
import BottomBar from "@/components/common/BottomBar.vue";
import PostList from "@/components/post/PostList.vue"
import DescriptionComponent from "@/components/common/Description.vue";
import TagButtonGroup from "@/components/community/TagButtonGroup.vue"; 


export default {
  components: {
    TopBar,
    BottomBar,
    PostList,
    DescriptionComponent,
    TagButtonGroup,
  },
  methods: {
    goToPostCreate() {
      this.$router.push({ name: 'PostCreate' });
    },
  },
}
</script>


<style lang="scss" scoped>
  .sub-container {
    justify-content: space-between;
  }

  .center-container {
    display: flex;
    flex-direction: column;
    width: 100%;
    height: 100%;
  }
  
  .center-container .searchbar {
    display: flex;
    padding-top: $margin-small;
    padding-bottom: $margin-default;
    @include custom-padding-x;
    box-sizing: border-box;
  }

  .center-container .searchbar .input-icon-wrap {
    display: flex;
    align-items: center;
    width: 100%;
    height: 48px;
    border-radius: 13px;
    background-color: $input-color;
    padding-right: $padding-default;
  }

  .center-container .searchbar .input-icon-wrap .icon-search {
    width: 16px;
    height: 16px;
    @include custom-margin-x;
    color: $input-placeholder-color;
  }

  .center-container .searchbar .input-icon-wrap input {
    width: 100%;
    appearance: none;
    border: none;
    outline: none;
    background: transparent;
    @include custom-text;
  }

  .center-container .write-button {
    @include custom-text($font-color: white, $font-weight: 800, $font-size: 14px);
    @include custom-none-select-basic;
    position: absolute;
    bottom: calc(#{$height-bottom-bar} + #{$padding-default});
    right: $padding-default;
    z-index: $z-index-button;

    display: flex;
    justify-content: center; /* 가로축 중앙 정렬 */
    align-items: center; /* 세로축 중앙 정렬 */
    padding: 12px 14px 12px 12px;
    gap: $padding-small;

    border-radius: 20px;
    background-color: $primary-color-400;

    box-shadow: 0px 0px 10px rgba(166, 166, 166, 0.3);

    img {
      @include custom-icon-style;
    }
  }
</style>