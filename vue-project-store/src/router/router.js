import { createRouter, createWebHistory } from 'vue-router'

import StoreBack from '../views/stores/StoreBack.vue'

import StoreAllOrders from '../views/stores/StoreAllOrders.vue'
import StoreInsert from '../views/stores/StoreInsert.vue'


const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      name: 'back-link',
      component: StoreBack
    },
    {
      path: '/back/orders',
      name: 'stores-orders-link',
      component: StoreAllOrders
    },
    {
      path: '/back/insert',
      name: 'stores-insert-link',
      component: StoreInsert
    },
    
  ]
})

export default router
