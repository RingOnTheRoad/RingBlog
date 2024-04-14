import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '@/stores/modules/user'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),

  // 路由跳转
  routes: [
    {
      path: '/login',
      component: () => import('@/views/login/LoginPage.vue')
    },
    {
      path: '/',
      component: () => import('@/views/index/IndexPage.vue'),
      redirect: '/blog/tags',
      children: [
        {
          path: '/index',
          component: () => import('@/views/blog/BlogTagsPage.vue')
        },
        {
          path: '/blog/manage',
          component: () => import('@/views/blog/BlogManagePage.vue')
        },
        {
          path: '/blog/tags',
          component: () => import('@/views/blog/BlogTagsPage.vue')
        },
        {
          path: '/user/my',
          component: () => import('@/views/user/UserMyPage.vue')
        },
        {
          path: '/user/password',
          component: () => import('@/views/user/UserPasswordPage.vue')
        }
      ]
    }
  ]
})

// 路由检查token
router.beforeEach((to) => {
  if (to.path != '/login' && !useUserStore().token) return '/login'
})

export default router
