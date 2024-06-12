<template>
  <div class="result-container">
    <div class="result-text">
      결과 <span class="result-count">1,600</span>
    </div>
    <div class="sort-dropdown">
      최신순
      <select>
        <option value="latest">최신순</option>
        <option value="oldest">오래된순</option>
        <option value="popular">인기순</option>
      </select>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ResultComponent',
};
</script>

<style scoped>
.result-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px;
  border-bottom: 1px solid #ccc;
}

.result-text {
  font-size: 18px;
  font-weight: bold;
}

.result-count {
  color: green;
}

.sort-dropdown {
  position: relative;
  display: flex;
  align-items: center;
}

.sort-dropdown select {
  margin-left: 5px;
  border: none;
  background: transparent;
  font-size: 16px;
  cursor: pointer;
}
</style>
