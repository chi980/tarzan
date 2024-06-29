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

const { emit } = defineEmits(["child-div-toggled"]);

const toggleChildDiv = () => {
  alert("child-vid click");
  emit("child-div-toggled", !props.showChildDiv);
};
</script>
