// utils/formatTime.js 또는 formatTime.ts
import dayjs from "dayjs";
import relativeTime from "dayjs/plugin/relativeTime";
import "dayjs/locale/ko";

dayjs.extend(relativeTime);
dayjs.locale("ko");

export function formatSmartTime(dateString) {
  const now = dayjs();
  const target = dayjs(dateString);
  const diffDays = now.diff(target, "day");

  if (diffDays < 7) {
    return target.fromNow(); // "3일 전", "6시간 전" 등
  } else {
    // 기존 formatDateWithoutTime 함수와 같은 형식으로 출력
    return `${target.year()}.${String(target.month() + 1).padStart(
      2,
      "0"
    )}.${String(target.date()).padStart(2, "0")}`;
  }
}
