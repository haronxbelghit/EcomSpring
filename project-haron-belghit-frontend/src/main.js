import { createApp } from 'vue'
import App from './App.vue'
import router from './router/index.js'
import swal from 'sweetalert';



window.axios = require('axios')

createApp(App).use(router).mount('#app');
