import { createApp } from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify'
import router from './router'
import { loadFonts } from './plugins/webfontloader'
import { createPinia } from 'pinia'
import { Skeletor } from "vue-skeletor";
import "vue-skeletor/dist/vue-skeletor.css";

loadFonts()

const pinia = createPinia()

const app = createApp(App)
  .use(vuetify)
  .use(router)
  .use(pinia)
  .component('Skeletor', Skeletor)
  .mount('#app')
