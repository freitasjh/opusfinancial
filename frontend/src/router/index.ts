import AppLayout from '@/layout/AppLayout.vue';
import { createRouter, createWebHistory } from 'vue-router';

const router = createRouter({
    history: createWebHistory(),
    routes: [
        {
            path: '/',
            component: AppLayout,
            children: [
                {
                    path: '/',
                    name: 'dashboard',
                    component: () => import('@/views/Dashboard.vue')
                },
                {
                    path: '/category',
                    name: 'category',
                    component: () => import('@/financial/category/views/page/CategoryPage.vue')
                },
                {
                    path: '/account',
                    name: 'account',
                    component: () => import('@/financial/account/views/pages/AccountPage.vue')
                },
                {
                    path: '/transaction/incoming',
                    name: 'incoming',
                    component: () => import('@/financial/incoming/view/page/IncomingTransactionView.vue')
                },
                {
                    path: '/transaction/expense',
                    name: 'expense',
                    component: () => import('@/financial/expense/view/page/ExpenseView.vue')
                },
                {
                    path: '/uikit/input',
                    name: 'input',
                    component: () => import('@/views/uikit/InputDoc.vue')
                },
                {
                    path: '/uikit/button',
                    name: 'button',
                    component: () => import('@/views/uikit/ButtonDoc.vue')
                },
            ]
        },
        {
            path: '/pages/notfound',
            name: 'notfound',
            component: () => import('@/views/pages/NotFound.vue')
        },

        {
            path: '/auth/login',
            name: 'login',
            component: () => import('@/views/pages/auth/Login.vue')
        },
        {
            path: '/auth/access',
            name: 'accessDenied',
            component: () => import('@/views/pages/auth/Access.vue')
        },
        {
            path: '/auth/error',
            name: 'error',
            component: () => import('@/views/pages/auth/Error.vue')
        },
        {
            path: '/auth/register',
            name: 'register',
            component: () => import('@/views/pages/auth/Register.vue')
        },
        {
            path: '/:pathMatch(.*)*',
            redirect: '/pages/notfound' // Certifique-se que /pages/notfound existe
        }

    ]
});

router.beforeEach((to, from, next) => {
    const token = localStorage.getItem('accessToken');

    if (to.name === 'error') {
        next();
    }

    if (to.name === 'register') {
        next();
    } else if (to.name != 'login' && token === null) {
        next({ name: 'login' });
    } else {
        next();
    }
});

export default router;
