import Vue from "vue";
import VueRouter from "vue-router"

import KnowledgeForm from "@/components/KnowledgeForm";
import ReviewCard from "@/components/ReviewCard";
import DefaultEmpty from "@/components/DefaultEmpty";

import Login from "@/view/Login";
import Main from "@/view/Main";


Vue.use(VueRouter)

export default new VueRouter({
    mode: 'history',
    routes: [
        { path: '/', redirect: '/home/recent'},
        {path: '/login', component: Login},
        {
            path: '/home', component: Main,
            children: [
                {path: 'recent', component: ReviewCard},
                {path: 'knowledge', component: KnowledgeForm},
                {path: 'tag', component: DefaultEmpty},
                {path: 'export', component: DefaultEmpty}
            ]
        }
    ]
})