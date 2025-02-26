import { ref, watchEffect, onMounted, onUnmounted } from "vue";

export function useInfiniteScroll(fetchMore) {
  const target = ref(null);
  const isLoading = ref(false);

  const observer = new IntersectionObserver(
    async ([entry]) => {
      if (entry.isIntersecting && !isLoading.value) {
        isLoading.value = true;
        await fetchMore();
        isLoading.value = false;
      }
    },
    { rootMargin: "100px" }
  );

  watchEffect(() => {
    if (target.value) observer.observe(target.value);
  });

  onUnmounted(() => {
    if (target.value) observer.unobserve(target.value);
  });

  return { target, isLoading };
}
