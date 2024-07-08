import { createApp } from "vue";
import "./style.css";

import { createPinia } from "pinia";
import "bootstrap-icons/font/bootstrap-icons.css";
import router from "@/router/index.js";
import App from "./App.vue";

import { library } from "@fortawesome/fontawesome-svg-core"; 
import { fas } from "@fortawesome/free-solid-svg-icons"; 
import { far } from "@fortawesome/free-regular-svg-icons"; 
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';

library.add (fas) 
library.add (far)

const app = createApp(App);
app.component('font-awesome-icon', FontAwesomeIcon)
app.use(router);
app.use(createPinia()); // createPinia를 호출하여 Pinia를 초기화합니다.
app.mount("#app");
