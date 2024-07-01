<template>
  <div class="comment-container">
        <div class="comment-writer">
          <span>{{ comment.writer }}</span>
        </div>
        <div class="comment-content">
          <p>{{ comment.content }}</p>
        </div>
        <div class="comment-time">
          <span>{{ comment.time }}</span>
        </div>
        <div class="comment-delete">
          <button @click="deleteComment" class="delete-button">
            <i class="fas fa-trash-alt"></i>
          </button>
        </div>
      </div>
</template>

<script>
export default {
  name: 'Comment',
  props: {
    comment: Object,
    index: Number,
  },

  methods: {
    deleteComment() {
      this.$emit('delete-comment', this.index);
    },
  },
}
</script>
<style lang="">
  
</style>