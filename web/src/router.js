import {createRouter, createWebHistory} from "vue-router"

import ReviewRecentCard from "./view/page/ReviewRecentPage";

import Login from "./view/Login";
import Main from "./view/Main";
import ManageKnowledgePage from "./view/page/ManageKnowledgePage";
import CreateKnowledgePage from "./view/page/CreateKnowledgePage";
import ShowRelatedCoups from "./view/page/ShowRelatedCoups";


const routes = [
    {path: '/', redirect: '/home/recent'},
    {path: '/login', component: Login},
    {
        path: '/home', component: Main,
        children: [
            {path: 'recent', component: ReviewRecentCard},
            {path: "related", component: ShowRelatedCoups},
            {path: 'create', component: CreateKnowledgePage},
            {path: 'knowledge', component: ManageKnowledgePage},
        ]
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router