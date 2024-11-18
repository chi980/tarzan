import { createApp } from "vue";
import "./style.css";

import { library } from '@fortawesome/fontawesome-svg-core';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { faMagnifyingGlass } from '@fortawesome/free-solid-svg-icons';

import { createPinia } from "pinia";
import "bootstrap-icons/font/bootstrap-icons.css";
import router from "@/router/index.js";
import App from "./App.vue";
import axiosPlugin from "@/plugins/axiosPlugin";

library.add(faMagnifyingGlass);
const app = createApp(App);
app.component('font-awesome-icon', FontAwesomeIcon);
app.use(router);
app.use(createPinia()); // createPinia를 호출하여 Pinia를 초기화합니다.
app.use(axiosPlugin);
app.mount("#app");
