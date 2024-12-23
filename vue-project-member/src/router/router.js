import { createRouter, createWebHistory } from 'vue-router'

import MemberAllBook from '../views/users/MemberAllBook.vue'
import MemberAllOrder from '../views/users/MemberAllOrder.vue'
import MemberInsert from '../views/users/MemberInsert.vue'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/members/:id',
      name: 'members-books-link',
      component: MemberAllBook
    },
    {
      path: '/members/order/:id',
      name: 'members-orders-link',
      component: MemberAllOrder
    },
    {
      path: '/',
      name: 'member-insert-link',
      component: MemberInsert
    },
  ]
})

export default router
