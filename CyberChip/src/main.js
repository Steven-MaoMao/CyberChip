import { createApp } from 'vue'
import App from './App.vue'
import router from './router'

// Vuetify
import 'vuetify/styles'
import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'

import axios from 'axios'

const vuetify = createVuetify({
    components,
    directives,
})

const app = createApp(App)

app.use(router).use(vuetify)

app.config.globalProperties.axios = axios
app.config.globalProperties.baseURL = 'http://localhost:8001'

app.mount('#app')
