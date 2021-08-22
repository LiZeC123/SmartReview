import Vue from 'vue'
import App from './App.vue'
import axios from "axios";
import router from "@/router";
import store from "@/store";

Vue.config.productionTip = false

Vue.prototype.$axios = axios

axios.defaults.baseURL = '/api'
axios.interceptors.request.use(config => {
    // 这是一个函数, 因此并不会在定义时立即执行, 而是在每次发送请求时执行此操作
    if(store.state.token) {
        config.headers.common['Authorization'] = store.state.token;
    }
    return config;
});

axios.interceptors.response.use(res => {
    if (res.data.success) {
        return res;
    } else {
        store.commit('del_token')
        router.push({path: '/login'}).then(() => {
        });
    }
});

// 页面刷新时，重新赋值token
if (localStorage.getItem('token')) {
    store.commit('set_token', localStorage.getItem('token'))
}

router.beforeEach((to, from, next) => {
    const isLogin = store.state.token;  // 是否登录

    if (!isLogin && to.path !== "/login") {
        // 未登录状态；跳转至login
        router.push({path: '/login'}).then(()=>{});
    }

    if (to.path === '/login') {
        // 已登录状态；当路由到login时，跳转至home
        if (isLogin) {
            router.push({path: '/home/recent'}).then(()=>{}) ;
        }
    }
    next();
});


new Vue({
    router,
    store,
    render: h => h(App),
}).$mount('#app')
