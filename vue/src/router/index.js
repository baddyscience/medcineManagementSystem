import Vue from 'vue'
import VueRouter from 'vue-router'

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
            {path: 'person', name: 'Person', meta: {title: '个人信息'}, component: () => import('../views/Manager/Person.vue')},
            {path: '403', name: 'Auth', meta: {title: '无权限'}, component: () => import('../views/Manager/Auth.vue')},
            {path: 'password', name: 'password', meta: {title: '修改密码'}, component: () => import('../views/Manager/Password.vue')},
            {path: 'medicine', name: 'medicine', meta: {title: '药品管理'}, component: () => import('../views/Manager/Medicine.vue')},

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
    let localStorageUser = JSON.parse(localStorage.getItem('user') || '{}')

    if (localStorageUser.role !== '管理员' && adminPaths.includes(to.path)) {
        next('/403')
    } else {
        next()
    }
    next()
})

export default router