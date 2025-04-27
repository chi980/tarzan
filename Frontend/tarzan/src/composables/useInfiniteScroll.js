import { ref, onUnmounted } from "vue";

export function useInfiniteScroll(fetchCallback, options = { threshold: 0.5 }) {
  const target = ref(null);
  const observer = ref(null);
  const isLoading = ref(false);

  const setupObserver = () => {
    observer.value = new IntersectionObserver(async ([entry]) => {
      if (entry.isIntersecting && !isLoading.value) {
        isLoading.value = true;
        await fetchCallback();
        isLoading.value = false;
      }
    }, options);

    if (target.value) {
      observer.value.observe(target.value);
    }
  };

  const stopObserver = () => {
    if (observer.value) {
      observer.value.disconnect();
    }
  };

  onUnmounted(() => {
    stopObserver();
  });

  return { target, setupObserver, stopObserver };
}
