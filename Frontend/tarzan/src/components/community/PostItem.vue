<template>
  <div class="post-item">
    <div class="post-header">
      <h3>{{ title }}</h3>
    </div>
    <div class="post-body">
      <p>{{ content }}</p>
    </div>
    <div class="post-footer">
      <span>{{ timeAgo }}</span>
      <span>{{ commentsCount }} comments</span>
    </div>
  </div>
</template>

<script>
export default {
  name: 'PostItem',
  props: {
    title: {
      type: String,
      required: true
    },
    content: {
      type: String,
      required: true
    },
    timeAgo: {
      type: String,
      required: true
    },
    commentsCount: {
      type: Number,
      required: true
    }
  }
}
</script>

<style scoped>
.post-item {
  border: 1px solid #ddd;
  padding: 16px;
  margin: 16px 0;
  border-radius: 8px;
  background-color: #fff;
}

.post-header h3 {
  margin: 0;
  font-size: 1.5em;
}

.post-body p {
  margin: 8px 0;
  font-size: 1em;
}

.post-footer {
  display: flex;
  justify-content: space-between;
  font-size: 0.875em;
  color: #666;
}
</style>
