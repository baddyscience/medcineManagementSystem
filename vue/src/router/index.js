import Vue from 'vue'
import VueRouter from 'vue-router'
import tableRoutes from "@/utils/tableRoutes";

const originPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
    return originPush.call(this, location).catch(err => err)
}

Vue.use(VueRouter)

const routes = [
    {
        path: '/login',
        name: 'Login',
        component: () => import('../views/Login.vue')
    },
    {
        path: '/register',
        name: 'Register',
        component: () => import('../views/Register.vue')
    },
    {
        path: '/',
        name: 'Manager',
        component: () => import('../views/Manager.vue'),
        children: [
            {path: 'home', name: 'Home', meta: {title: '首页'}, component: () => import('../views/Manager/Home.vue')},
            {
                path: 'approvalForm',
                name: 'ApprovalForm',
                component: () => import('../views/ApprovalForm.vue'),
                meta: {title: '预约审批表'},
                children: [
                    {path: 'projectManager', name: 'ProjectManager', meta: {title: 'ProjectManager'}, component: () => import('../views/Manager/ProjectManeger.vue')},
                ]
            },
            {
                path: 'person',
                name: 'Person',
                meta: {title: '个人信息'},
                component: () => import('../views/Manager/Person.vue')
            },
            {
                path: 'approvalTable',
                name: 'ApprovalTable',
                component: () => import('../views/ApprovalTable.vue'),
                meta: {title: '填报材料'}
            },
            {path: '403', name: 'Auth', meta: {title: '无权限'}, component: () => import('../views/Manager/Auth.vue')},
            {
                path: 'password',
                name: 'password',
                meta: {title: '修改密码'},
                component: () => import('../views/Manager/Password.vue')
            },
            {
                path: 'adminHistory',
                name: 'AdminHistory',
                meta: {title: '管理员的页面'},
                component: () => import('../views/Manager/adminHistory.vue'),
                children: [
                    {path: 'test', name: 'Test', meta: {title: 'Test'}, component: () => import('../views/Test.vue')},
                ]
            },
            {
                path: 'userHistory',
                name: 'UserHistory',
                meta: {title: '用户历史记录'},
                component: () => import('../views/Manager/userHistory.vue'),
                children: [
                    {path: 'test', name: 'Test', meta: {title: 'Test'}, component: () => import('../views/Test.vue')},
                ]
            },
            {
                path: 'change',
                name: 'change',
                meta: {title: '修改'},
                component: () => import('../views/Manager/Change.vue'),
            },
            ...tableRoutes

        ]

    },
    {path: '*', name: '404', meta: {title: '404'}, component: () => import('../views/404.vue')},
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

router.beforeEach((to, from, next) => {
    // console.log(to)
    // console.log(from)
    // console.log(next);
    // next()
    let adminPaths = ['/user']
    let localStorageUser = JSON.parse(localStorage.getItem('wxd-user') || '{}')

    if (localStorageUser.role !== '管理员' && adminPaths.includes(to.path)) {
        next('/403')
    } else {
        next()
    }
    next()
})

export default router