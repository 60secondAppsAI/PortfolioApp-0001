import { createRouter, createWebHashHistory } from 'vue-router';
import { useAuth } from '../stores/auth';
import SignIn from '../components/SignIn.vue';
import Settings from '../components/Settings.vue';
import MyDashboard from '../components/MyDashboard.vue';

// Dynamic imports for module components
import Portfolios from '../components/Portfolios.vue';
import PortfolioDetail from '../components/PortfolioDetail.vue';
import PortfolioAccounts from '../components/PortfolioAccounts.vue';
import PortfolioAccountDetail from '../components/PortfolioAccountDetail.vue';
import PortfolioBalances from '../components/PortfolioBalances.vue';
import PortfolioBalanceDetail from '../components/PortfolioBalanceDetail.vue';
import PortfolioStrategys from '../components/PortfolioStrategys.vue';
import PortfolioStrategyDetail from '../components/PortfolioStrategyDetail.vue';

const routes = [
  {
    path: '/',
    name: 'home',
    redirect: '/dashboard'
  },
  {
    path: '/dashboard',
    name: 'Dashboard',
    component: MyDashboard
  },
  
  // Dynamic module routes
  {
    path: '/portfolios',
    name: 'Portfolios',
    component: Portfolios
  },
  {
    path: '/portfolio/:id',
    name: 'PortfolioDetail',
    component: PortfolioDetail,
    props: true
  },
  {
    path: '/portfolioAccounts',
    name: 'PortfolioAccounts',
    component: PortfolioAccounts
  },
  {
    path: '/portfolioAccount/:id',
    name: 'PortfolioAccountDetail',
    component: PortfolioAccountDetail,
    props: true
  },
  {
    path: '/portfolioBalances',
    name: 'PortfolioBalances',
    component: PortfolioBalances
  },
  {
    path: '/portfolioBalance/:id',
    name: 'PortfolioBalanceDetail',
    component: PortfolioBalanceDetail,
    props: true
  },
  {
    path: '/portfolioStrategys',
    name: 'PortfolioStrategys',
    component: PortfolioStrategys
  },
  {
    path: '/portfolioStrategy/:id',
    name: 'PortfolioStrategyDetail',
    component: PortfolioStrategyDetail,
    props: true
  },

  // Core application routes
  {
    path: '/signin',
    name: 'SignIn',
    component: SignIn
  },
//  {
//    path: '/users',
//    name: 'Users',
//    component: Users,
//    meta: { requiresAuth: true }
//  },
  {
    path: '/settings',
    name: 'Settings',
    component: Settings,
    meta: { requiresAuth: true }
  },
  {
    path: '/:pathMatch(.*)*',
    redirect: '/dashboard'
  }
];

const router = createRouter({
  history: createWebHashHistory(import.meta.env.BASE_URL),
  routes,
  scrollBehavior(to, from, savedPosition) {
    return savedPosition || { top: 0 };
  }
});

// Navigation guard for authentication
router.beforeEach((to, from, next) => {
  const auth = useAuth();
  
  if (to.meta.requiresAuth && !auth.isAuthenticated) {
    next({ name: 'SignIn', query: { redirect: to.fullPath } });
  } else if (to.name === 'SignIn' && auth.isAuthenticated) {
    next({ name: 'Dashboard' });
  } else {
    next();
  }
});

export default router;
