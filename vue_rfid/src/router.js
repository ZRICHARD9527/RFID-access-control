import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '@/components/Login'
import Home from '@/components/Home'
import Welcome from '@/components/Welcome'
import Users from '@/components/Users/Users'
import Dutyrank from '@/components/dutymnge/Dutyrank'
import Export from '@/components/system/Export'
import Log from '@/components/system/Log'
import Lost from '@/components/system/Lost'
import HomeForUsers from '@/components/HomeForUsers'
import RecentDuty from '@/components/dutymnge/RecentDuty'
import EditUserInfo from '@/components/Users/EditUserInfo'
import WelcomeForUsers from '@/components/WelcomeForUsers'
import EditPassword from '@/components/Users/EditPassword'
import UserInfo from '@/components/Users/UserInfo'

Vue.use(VueRouter)

const routes = [
  { path: '/', redirect: '/login' },
  { path: '/login', component: Login },
  {
    path: '/homeforusers',
    component: HomeForUsers,
    redirect: '/dutyrank',
    children: [
      { path: '/welcomeforusers', component: WelcomeForUsers },
      { path: '/dutyrank', component: Dutyrank },
      { path: '/userinfo', component: UserInfo },
      { path: '/recentduty', component: RecentDuty },
      { path: '/editpassword', component: EditPassword }
    ]
  },
  {
    path: '/home',
    component: Home,
    redirect: '/users',
    children: [
      { path: '/welcome', component: Welcome },
      { path: '/users', component: Users },
      { path: '/dutysort', component: Dutyrank },
      { path: '/export', component: Export },
      { path: '/log', component: Log },
      { path: '/lost', component: Lost },
      { path: '/edituserinfo', component: EditUserInfo }
    ]
  }
]

const router = new VueRouter({
  routes
})

export default router
