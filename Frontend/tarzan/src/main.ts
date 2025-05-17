// 외부 라이브러리 import
import { createApp } from "vue";
import { createPinia } from "pinia";
import { library } from "@fortawesome/fontawesome-svg-core";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { faMagnifyingGlass } from "@fortawesome/free-solid-svg-icons";
import "bootstrap-icons/font/bootstrap-icons.css";

// 내부 파일 import
import App from "./App.vue";
import router from "@/router/index.js";
import { axiosInstance } from "@/plugins/axiosPlugin"; // 항상 pinia를 등록 후 axios를 등록해야 합니다.
import "./style.css";

// 아이콘 등록 (외부 설정)
library.add(faMagnifyingGlass);

// 앱 초기화 및 설정
const app = createApp(App);
app.component("font-awesome-icon", FontAwesomeIcon);
app.use(router);
app.use(createPinia()); // createPinia를 호출하여 Pinia를 초기화합니다.
// app.use(axiosInstance);
app.config.globalProperties.$axios = axiosInstance;
app.mount("#app");
