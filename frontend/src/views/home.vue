<script setup>
import {ref, onMounted, computed} from "vue";
import {useRouter} from "vue-router";
import {ElMessage, ElMessageBox} from "element-plus";
import {useLoginStore} from "@/stores/useLoginStore.js";

const router = useRouter()
const loginStore = useLoginStore()
const isCollapse = ref(false)

// 从 localStorage 读取（登录时存的）
const username = ref(localStorage.getItem('username') || '管理员')

function toggleSidebar() {
  isCollapse.value = !isCollapse.value
}

async function handleLogout() {
  try {
    await ElMessageBox.confirm('确定要退出登录吗？', '退出确认', {
      confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'
    })
    loginStore.setToken('')
    localStorage.removeItem('token')
    localStorage.removeItem('username')
    ElMessage.success('已退出')
    await router.push('/')
  } catch (_) { /* 取消 */ }
}

const menuItems = [
  { index: '/welcome',         icon: '🏠', label: '首页概览' },
  { index: '/category',        icon: '🔖', label: '分类管理' },
  { index: '/dessert',         icon: '🍰', label: '甜点管理' },
  { index: '/user_list',       icon: '👥', label: '员工列表' },
  { index: '/material',        icon: '📦', label: '原材料列表' },
  { index: '/inventory',       icon: '📊', label: '库存列表' },
  { index: '/purchase_record', icon: '📋', label: '采购记录' },
]
</script>

<template>
  <el-container class="home-container">
    <!-- 顶栏 -->
    <el-header class="home-header">
      <div class="header-left">
        <span class="sidebar-toggle" @click="toggleSidebar">☰</span>
        <span class="header-logo">🍰</span>
        <span class="header-title">甜品管理系统</span>
      </div>
      <div class="header-right">
        <span class="header-user">
          <span class="user-avatar">👤</span>
          {{ username }}
        </span>
        <el-button size="small" class="logout-btn" @click="handleLogout">退出登录</el-button>
      </div>
    </el-header>

    <el-container class="home-body">
      <!-- 侧栏 -->
      <el-aside :width="isCollapse ? '56px' : '200px'" class="home-aside">
        <el-menu
          :router="true"
          :default-active="$route.path"
          :collapse="isCollapse"
          class="side-menu"
        >
          <el-menu-item v-for="item in menuItems" :key="item.index" :index="item.index">
            <span class="menu-icon">{{ item.icon }}</span>
            <template #title>
              <span>{{ item.label }}</span>
            </template>
          </el-menu-item>
        </el-menu>
      </el-aside>

      <!-- 主内容 -->
      <el-main class="home-main">
        <div class="main-content">
          <router-view v-slot="{ Component }">
            <transition name="page-fade" mode="out-in">
              <component :is="Component" />
            </transition>
          </router-view>
        </div>
      </el-main>
    </el-container>

    <el-footer class="home-footer">
      Copyright &copy; 2026 甜品管理系统 &nbsp;·&nbsp; Sweet Management System
    </el-footer>
  </el-container>
</template>

<style scoped>
.home-container {
  height: 100vh;
  display: flex;
  flex-direction: column;
  background: #f0f2f5;
}

/* 顶栏 */
.home-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  background: linear-gradient(135deg, #1a1a2e, #16213e, #0f3460);
  color: #fff;
  height: 56px;
  padding: 0 20px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.15);
  z-index: 100;
  user-select: none;
}
.header-left { display: flex; align-items: center; gap: 8px; }
.sidebar-toggle {
  font-size: 20px;
  cursor: pointer;
  padding: 4px 8px;
  border-radius: 6px;
  transition: background 0.2s;
  line-height: 1;
}
.sidebar-toggle:hover { background: rgba(255,255,255,0.15); }
.header-logo { font-size: 24px; animation: bounce 2s ease-in-out infinite; }
@keyframes bounce {
  0%, 100% { transform: translateY(0); }
  50% { transform: translateY(-4px); }
}
.header-title { font-size: 18px; font-weight: 700; letter-spacing: 2px; }

.header-right {
  display: flex;
  align-items: center;
  gap: 16px;
}
.header-user {
  display: flex;
  align-items: center;
  gap: 6px;
  font-size: 14px;
  color: rgba(255,255,255,0.85);
}
.user-avatar { font-size: 18px; }
.logout-btn {
  background: rgba(255,255,255,0.12);
  border: 1px solid rgba(255,255,255,0.2);
  color: rgba(255,255,255,0.85);
  border-radius: 6px;
  font-size: 12px;
}
.logout-btn:hover {
  background: rgba(255,255,255,0.2);
  color: #fff;
  border-color: rgba(255,255,255,0.3);
}

/* 主体 */
.home-body { flex: 1; display: flex; overflow: hidden; }

/* 侧栏 */
.home-aside {
  background: #fff;
  border-right: 1px solid #e8e8e8;
  overflow-y: auto;
  overflow-x: hidden;
  transition: width 0.3s ease;
}
.home-aside::-webkit-scrollbar { width: 4px; }
.home-aside::-webkit-scrollbar-thumb { background: #ddd; border-radius: 4px; }

.side-menu {
  border-right: none;
  transition: all 0.3s;
}
.side-menu .el-menu-item {
  height: 44px;
  line-height: 44px;
  margin: 3px 8px;
  border-radius: 8px;
  transition: all 0.25s;
  font-size: 14px;
}
.side-menu .el-menu-item:hover { background: #f5f3ff; color: #6c5ce7; }
.side-menu .el-menu-item.is-active {
  background: linear-gradient(135deg, #6c5ce7, #a29bfe);
  color: #fff;
  box-shadow: 0 4px 12px rgba(108,92,231,0.3);
}
.menu-icon { margin-right: 10px; font-size: 16px; }

/* 主区域 */
.home-main {
  flex: 1;
  padding: 20px;
  overflow-y: auto;
  background: #f0f2f5;
}
.home-main::-webkit-scrollbar { width: 6px; }
.home-main::-webkit-scrollbar-thumb { background: #ddd; border-radius: 4px; }

.main-content {
  background: #fff;
  border-radius: 12px;
  padding: 24px;
  box-shadow: 0 2px 12px rgba(0,0,0,0.06);
  min-height: calc(100% - 40px);
}

/* 页面过渡动画 */
.page-fade-enter-active, .page-fade-leave-active {
  transition: opacity 0.25s ease, transform 0.25s ease;
}
.page-fade-enter-from { opacity: 0; transform: translateY(12px); }
.page-fade-leave-to { opacity: 0; transform: translateY(-8px); }

/* 底栏 */
.home-footer {
  height: 36px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #fff;
  border-top: 1px solid #e8e8e8;
  color: #999;
  font-size: 12px;
}
</style>