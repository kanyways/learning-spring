import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'index',
      component: () => import('@/views/index/index'),
      meta: {title: '首页'}
    },
    {
      path: '/editor',
      name: 'editor',
      component: () => import('@/views/editor/index'),
      meta: {title: '编辑器'}
    }
  ]
})
