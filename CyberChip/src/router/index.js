import { createRouter, createWebHashHistory } from 'vue-router'

const router = createRouter({
  history: createWebHashHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'cyberchiproomlist',
      component: () => import('../views/cyberchiproomlist.vue')
    }, {
      path: '/cyberchip',
      name: 'cyberchip',
      component: () => import('../views/cyberchip.vue'),
      beforeEnter: (to, from) => {
        if (sessionStorage.getItem('token') === null) {
          return { name: 'cyberchiproomlist' }
        }
      }
    }
  ]
})

export default router
