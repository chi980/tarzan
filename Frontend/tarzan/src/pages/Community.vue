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

      <div class="tag-button-container">
        <TagButtonGroup />
      </div>

      <div class="result-bar-container">
        <ResultBar resultTitle="결과" :sortOptions="sortOptions" />
      </div>

      <div class="post-llist-container">
        <PostList :posts="posts" />
      </div>

      <div class="write-button" @click="goToPostCreate">
        <img id="post-write-icon" src="@/assets/icons/Filter/post-write-icon.png" />
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
import ResultBar from "@/components/common/ResultBar.vue";
import TagButtonGroup from "@/components/common/TagButtonGroup.vue"; 
import PostList from "@/components/post/PostList.vue"

export default {
  components: {
    TopBar,
    BottomBar,
    SearchBar,
    DescriptionComponent,
    ResultBar,
    TagButtonGroup,
    PostList,
  },
  methods: {
    goToPostCreate() {
      this.$router.push({ name: 'PostCreate' });
    },
  },
  data() {
    return {
      sortOptions: [
        {idx: 1, value: 'date', name: '날짜순' },
        { idx: 2, value: 'popularity', name: '인기순' },
        { idx: 3, value: 'rating', name: '평점순' },
      ],
      posts: [
        { id: 1, tag: '교통', title: 'First Post', content: 'This is the first post.', comments: '3'},
        { id: 2, tag: '정보', title: '오늘 중구 시청역더플라자호텔 버스정류장  앞에서 사고 났어요!', content: 'This is the second post. XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX', comments: '5'},
      ],
    }
  }
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

  .tag-button-container {
    padding: 8px;
  }

  :deep(.tag-button-container) {
    overflow-x: auto;
  }

  .result-bar-container {
    @include custom-margin-x;
  }

  .post-llist-container {
    @include custom-margin-x;
  }

  .center-container .write-button {
    @include custom-text($font-color: white, $font-weight: 800, $font-size: 14px);
    @include custom-none-select-basic;
    position: absolute;
    bottom: calc(#{$height-bottom-bar} + #{$padding-default});
    right: $padding-default;
    z-index: $z-index-button;

    display: flex;
    justify-content: center;
    align-items: center; 
    padding: 12px 14px 12px 12px;
    gap: $padding-small;

    border-radius: 20px;
    background-color: $primary-color-400;

    box-shadow: 0px 0px 10px rgba(166, 166, 166, 0.3);
  }

  #post-write-icon {
      @include custom-icon-style;
    }
</style>