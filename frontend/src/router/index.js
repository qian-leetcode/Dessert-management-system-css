import {createRouter, createWebHistory} from "vue-router";

const routes = [
    {
        path: '/',
        name : 'Login',
        component: () => import( '@/views/Login.vue'),
    },
    {
        path :'/home',
        name : 'Home',
        component: () => import( '@/views/Home.vue'),
        redirect: '/home/home',
        children: [
            {
                path:'/category',
                name : 'Category',
                component: () => import( '@/views/CategoryManagement.vue')
            },{
                path:'/dessert',
                name : 'Dessert',
                component: () => import( '@/views/DessertManagement.vue')
            }
        ]
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes,
})

export default router