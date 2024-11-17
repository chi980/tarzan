<template>
  <div class="photo-upload">
    <img id="photo-upload-icon" src="@/assets/icons/Filter/plus-icon.svg" alt="photo-upload-icon">
  </div>
</template>
<script>
export default {
  
}
</script>
<style lang="scss" scoped>
.photo-upload {
  background-color: black;
  width: 80px;
  height: 80px;
  border-radius: 16px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.photo-upload #photo-upload-icon {
  width: 20px;
}
</style>