<!-- <template>
  <div class="photo-upload">
    <img id="photo-upload-icon" src="@/assets/icons/Filter/plus-icon.svg" alt="photo-upload-icon">
  </div>
</template>
<script setup>

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
</style> -->
<template>
  <div class="photo-upload-container">
    <!-- 업로드된 이미지 목록 표시 -->
    <div class="uploaded-images">
      <div v-for="(img, index) in imageList" :key="index" class="image-wrapper">
        <img :src="img" alt="Uploaded Image" class="preview-image">
        <button class="delete-btn" @click="removeImage(index)">X</button>
      </div>
    </div>

    <!-- 파일 업로드 버튼 (최대 10장 제한) -->
    <label v-if="imageList.length < 10" for="file-upload" class="upload-btn">
      <img id="photo-upload-icon" src="@/assets/icons/Filter/plus-icon.svg" alt="photo-upload-icon">
    </label>
    <input id="file-upload" type="file" @change="handleFileUpload" accept="image/*" multiple>
  </div>
</template>

<script setup>
import { ref } from "vue";

const imageList = ref([]); // 여러 개의 이미지 URL을 저장하는 배열

const handleFileUpload = (event) => {
  const files = Array.from(event.target.files); // 업로드한 파일들을 배열로 변환
  if (imageList.value.length + files.length > 10) {
    alert("최대 10장까지만 업로드할 수 있습니다.");
    return;
  }

  files.forEach((file) => {
    const reader = new FileReader();
    reader.onload = (e) => {
      if (imageList.value.length < 10) {
        imageList.value.push(e.target.result); // 변환된 이미지 URL을 배열에 추가
      }
    };
    reader.readAsDataURL(file); // 파일을 base64로 변환
  });
};

const removeImage = (index) => {
  imageList.value.splice(index, 1); // 선택한 이미지 삭제
};
</script>

<style lang="scss" scoped>
.photo-upload-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 10px;
}

.uploaded-images {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.image-wrapper {
  position: relative;
  width: 80px;
  height: 80px;
  border-radius: 16px;
  overflow: hidden;
}

.preview-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 16px;
}

.delete-btn {
  position: absolute;
  top: 5px;
  right: 5px;
  // background-color: red;
  color: white;
  border: none;
  cursor: pointer;
  border-radius: 50%;
  width: 20px;
  height: 20px;
}

.upload-btn {
  background-color: black;
  width: 80px;
  height: 80px;
  border-radius: 16px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
}

#photo-upload-icon {
  width: 24px;
}

input[type="file"] {
  display: none;
}
</style>