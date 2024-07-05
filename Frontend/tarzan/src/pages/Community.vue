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
          backgroundColor="#FFF7D9"
      />

      <TagButtonGroup />

      <div class="resultbar">
        <span class="result-text">결과</span>
        <span class="result-number">{{  result }}</span>
        <div class="sort-dropdown">
          <select v-model="selectedSort" @change="onSortChange">
            <option value="latest">최신순</option>
            <option value="views">조회수순</option>
            <option value="likes">좋아요순</option>
          </select>
        </div>
      </div>

      <PostList />

      <div class="write-button">
        <button @click="goToPostCreate">글쓰기</button>
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
  data() {
    return {
      tags: ['전체', '교통', '맛집', '생활팁', '질문', '모임'],
      activeTag: '전체',
      result: '1600',
      selectedSort: 'latest',
    };
  },
  methods: {
    setActiveTag(tag) {
      this.activeTag = tag;
    },
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

  
</style>