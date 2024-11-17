import { createApp } from "vue";
import "./style.css";

import { createPinia } from "pinia";
import "bootstrap-icons/font/bootstrap-icons.css";
import router from "@/router/index.js";
import App from "./App.vue";
import axiosPlugin from "@/plugins/axiosPlugin";

const app = createApp(App);
// app.component('font-awesome-icon', FontAwesomeIcon)
app.use(router);
app.use(createPinia()); // createPinia를 호출하여 Pinia를 초기화합니다.
app.use(axiosPlugin);
app.mount("#app");
