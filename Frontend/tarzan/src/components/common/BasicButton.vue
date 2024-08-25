<template>
  <div class="basic-button">
    <button @click="navigate">{{ label }}</button>
  </div>
</template>
<script>
export default {
  name: 'BasicButton',
  props: {
    to: {
      type: String,
      required: true
    },
    label: {
      type: String,
      required: true
    }
  },
  methods: {
    navigate() {
      this.$router.push(this.to);
    }
  }
};
</script>
<style lang="scss" scoped>
  button {
    width: 100%;
    height: 48px;
    background-color: black;
    @include custom-text-bold($font-color: $small-button-background-color-unactive, $font-size: 14px);
  }
</style>