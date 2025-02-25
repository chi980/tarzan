// Composable/useInfiniteScroll.js
import { ref, onMounted, onUnmounted } from "vue";

export function useInfiniteScroll(fetchFunction) {
  const items = ref([]);  // 게시글 데이터를 저장할 변수
  const page = ref(0);    // 현재 페이지 번호
  const isLoading = ref(false);  // 데이터 로딩 상태

  const fetchItems = async () => {
    if (isLoading.value) return;  // 중복 요청 방지
    isLoading.value = true;

    try {
      const newItems = await fetchFunction(page.value);
      if (newItems.length > 0) {
        items.value.push(...newItems);
        page.value++;
      }
    } catch (error) {
      console.error("데이터 불러오기 오류:", error);
    } finally {
      isLoading.value = false;
    }
  };

  const handleScroll = () => {
    const { scrollTop, clientHeight, scrollHeight } = document.documentElement;
    if (scrollTop + clientHeight >= scrollHeight - 10) {
      fetchItems();  // 스크롤이 끝에 도달하면 새로운 데이터 요청
    }
  };

  onMounted(() => {
    fetchItems();
    window.addEventListener("scroll", handleScroll);
  });

  onUnmounted(() => {
    window.removeEventListener("scroll", handleScroll);
  });

  return { items, fetchItems };
}