<template>
  <div class="sub-container">
    <TopBar />
    
    <div class="center-container">
      <SearchBar />
      <DescriptionComponent
          descriptionImgSrc="/src/assets/etc/Saly-25.png"
          descriptionTitle="동네주민과<br/>얘기해보세요!"
          descriptionContent="여러 정보를 얻어보세요<br/>모임도 참여할 수 있어요!"
          backgroundColor="#FFF7D9"/>
      <TagButtonGroup />
      <PostList />
      <div class="write-button" @click="goToPostCreate">
        <img src="@/assets/icons/Filter/post-write-icon.png" />
        글쓰기
      </div>
    </div>
    
    <BottomBar />
  </div>
</template>

<script>
import TopBar from "@/components/common/TopBar.vue";
import BottomBar from "@/components/common/BottomBar.vue";
import SearchBar from "../components/common/SearchBar.vue";
import DescriptionComponent from "@/components/common/Description.vue";
import TagButtonGroup from "@/components/common/TagButtonGroup.vue"; 
import PostList from "@/components/post/PostList.vue"

export default {
  components: {
    TopBar,
    BottomBar,
    SearchBar,
    DescriptionComponent,
    TagButtonGroup,
    PostList,
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