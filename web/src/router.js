import Vue from "vue";
import VueRouter from "vue-router"

import ReviewRecentCard from "@/components/ReviewRecentCard";

import Login from "@/view/Login";
import Main from "@/view/Main";
import ManageKnowledgePage from "@/view/page/ManageKnowledgePage";
import ExportPage from "@/view/page/ExportPage";
import TagPage from "@/view/page/TagPage";
import CreateKnowledgePage from "@/view/page/CreateKnowledgePage";
import ModifyKnowledgePage from "@/view/page/ModifyKnowledgePage";


Vue.use(VueRouter)

export default new VueRouter({
    mode: 'history',
    routes: [
        {path: '/', redirect: '/home/recent'},
        {path: '/login', component: Login},
        {
            path: '/home', component: Main,
            children: [
                {path: 'recent', component: ReviewRecentCard},
                {path: 'create', component: CreateKnowledgePage},
                {path: 'knowledge', component: ManageKnowledgePage},
                {path: 'tag', component: TagPage},
                {path: 'export', component: ExportPage},
                {path: 'modify', component: ModifyKnowledgePage, name: "ModifyKnowledgePage"},
            ]
        }
    ]
})