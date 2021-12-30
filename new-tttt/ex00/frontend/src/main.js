import Vue from 'vue'
import App from './App.vue'

// ===========================================================
// 전역에서 element-ui를 사용할 수 있도록 
// main.js에 element-ui에 import하여 Vue.use(Element-ui)를 추가
// Custom theme와 언어설정 포함
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import locale from 'element-ui/lib/locale/lang/ko'

Vue.use(ElementUI, { locale });
// ===========================================================

Vue.config.productionTip = false

new Vue({
  render: h => h(App),
}).$mount('#app')
