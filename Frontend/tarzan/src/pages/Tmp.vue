<template>
  <div>
    <button @click="toggleChildDiv">Toggle Child Div from Parent</button>
    <ChildComponent
      :showChildDiv="showChildDiv"
      @child-div-toggled="handleChildDivToggled"
    />
  </div>
</template>
<script setup lang="ts">
import { ref } from "vue";
import ChildComponent from "@/components/ChildComponent.vue";
const showChildDiv = ref(false);

const toggleChildDiv = () => {
  showChildDiv.value = !showChildDiv.value;
};

const handleChildDivToggled = (newState: Boolean) => {
  alert("handleChildDivToggled");
  alert(newState.value);
  showChildDiv.value = newState;
};
</script>

<style scoped lang="scss"></style>
