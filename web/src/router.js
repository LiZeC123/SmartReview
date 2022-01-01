import {createRouter, createWebHistory} from "vue-router"

import ReviewRecentCard from "./view/page/ReviewRecentPage";

import Login from "./view/Login";
import Main from "./view/Main";
import ManageKnowledgePage from "./view/page/ManageKnowledgePage";
import CreateKnowledgePage from "./view/page/CreateKnowledgePage";
import ReviewKnowledge from "./view/page/ReviewKnowledge";
import ManagerQuotePage from "./view/page/ManagerQuotePage";


const routes = [
    {path: '/', redirect: '/home/recent'},
    {path: '/login', component: Login},
    {
        path: '/home', component: Main,
        children: [
            {path: 'recent', component: ReviewRecentCard},
            {path: "review", component: ReviewKnowledge},
            {path: 'create', component: CreateKnowledgePage},
            {path: 'knowledge', component: ManageKnowledgePage},
            {path: 'quote', component: ManagerQuotePage}
        ]
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router