<template>
  <div>
    <button @click="toggleChildDiv">Toggle Child Div from Child</button>
    <div v-if="showChildDiv">Child Div Content</div>
  </div>
</template>

<script setup lang="ts">
// computed를 사용해보자
const props = defineProps({
  showChildDiv: Boolean,
});

// defineEmits를 호출하여 emit 함수를 직접 사용
const emit = defineEmits(["child-div-toggled"]);

const toggleChildDiv = () => {
  alert("child-div click");
  emit("child-div-toggled", !props.showChildDiv);
};
</script>
