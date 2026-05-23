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
        children: [
            {
                path:'/category',
                name : 'Category',
                component: () => import( '@/views/CategoryManagement.vue')
            },{
                path:'/dessert',
                name : 'Dessert',
                component: () => import( '@/views/DessertManagement.vue')
            },{
                path:'/user_list',
                name : 'UserList',
                component:()=> import('@/views/user_list.vue')
            },{
                path:'/material',
                name : 'Material',
                component:() => import('@/views/Material.vue')
            }, {
                path:'/inventory',
                name : 'Inventory',
                component:() => import('@/views/Inventory.vue')
            }, {
                path:'/purchase_record',
                name : 'purchaseRecord',
                component:() => import('@/views/purchaseRecord.vue')
            }
        ]
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes,
})

export default router