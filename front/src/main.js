import { createApp } from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify'
import vueLodash from 'vue-lodash'
import router from './router'
import store from './store'
import { loadFonts } from './plugins/webfontloader'

loadFonts()

const app = createApp(App)
  .use(vuetify)
  .use(router)
  .use(store)
  // .use(vueLodash)
  .mount('#app')
