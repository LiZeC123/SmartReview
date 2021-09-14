import {createRouter, createWebHistory} from "vue-router"

import ReviewRecentCard from "./components/ReviewRecentCard";

import Login from "./view/Login";
import Main from "./view/Main";
import ManageKnowledgePage from "./view/page/ManageKnowledgePage";
import ExportPage from "./view/page/ExportPage";
import TagPage from "./view/page/TagPage";
import CreateKnowledgePage from "./view/page/CreateKnowledgePage";
import ModifyKnowledgePage from "./view/page/ModifyKnowledgePage";


const routes = [
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
];

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router