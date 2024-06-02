import { createApp } from "vue";
import "./style.css";

import { createPinia } from "pinia";
import router from "@/router/index.js";
import App from "./App.vue";

const app = createApp(App);
app.use(router);
app.use(createPinia()); // createPinia를 호출하여 Pinia를 초기화합니다.
app.mount("#app");
