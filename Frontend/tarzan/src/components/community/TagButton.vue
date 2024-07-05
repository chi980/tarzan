<template>
  <button class="tag-button"
    :class = "['tag-button', { active: isActive }]"
    @click = "handleClick" >
    {{ label }}
  </button>
</template>


<script>
export default {
  props: {
    label: {
      type: String, // label 속성의 타입을 문자열로 설정합니다
      required: true // label 속성은 반드시 제공되어야 합니다
    },
    isActive: {
      type: Boolean, // isActive 속성의 타입을 부울 값으로 설정합니다 (true 또는 false)
      default: false // isActive 속성의 기본값을 false로 설정합니다
    }
  },
  methods: {
    handleClick() {
      this.$emit('click'); // 버튼을 클릭하면 click 이벤트를 발생시킵니다
    }
  }
}
</script>


<style lang="scss" scoped>
  .tag-button {
    background: none;
    border-radius: 20px;
    border: 1px solid #E5E5E5;
    padding: 13px 14px;
    @include custom-text($font-weight: 600);
    font-size: 12px;
    white-space: nowrap; /* 버튼 텍스트 줄바꿈 방지 */
  }

  .tag-button.active {
    background-color: $primary-color-light;
    border-color: $primary-color-default;
    color: $primary-color-default;
  }
</style>
