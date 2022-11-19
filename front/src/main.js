import { createApp } from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify'
import vueLodash from 'vue-lodash'
import router from './router'
import { loadFonts } from './plugins/webfontloader'
import { createPinia } from 'pinia'

loadFonts()

const pinia = createPinia()

const app = createApp(App)
  .use(vuetify)
  .use(router)
  .use(pinia)
  // .use(vueLodash)
  .mount('#app')
