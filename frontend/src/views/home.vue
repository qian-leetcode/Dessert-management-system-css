<script setup>

</script>

<template>
  <el-container class="home-container">
    <el-header class="home-header">
      <div class="header-left">
        <span class="header-logo">🍰</span>
        <span class="header-title">甜品管理系统</span>
      </div>
      <div class="header-right">
        <span class="header-subtitle">Sweet Management System</span>
      </div>
    </el-header>
    <el-container class="home-body">
    <el-aside width="200px" class="home-aside">
      <el-menu
        :router="true"
        default-active="/category"
        class="side-menu"
      >
        <el-menu-item index="/category">
          <el-icon><svg viewBox="0 0 24 24" width="18" height="18" fill="none" stroke="currentColor" stroke-width="2"><rect x="3" y="3" width="7" height="7" rx="1"/><rect x="14" y="3" width="7" height="7" rx="1"/><rect x="3" y="14" width="7" height="7" rx="1"/><rect x="14" y="14" width="7" height="7" rx="1"/></svg></el-icon>
          <span> 分类管理 </span>
        </el-menu-item>
        <el-menu-item index="/dessert">
          <el-icon><svg viewBox="0 0 24 24" width="18" height="18" fill="none" stroke="currentColor" stroke-width="2"><circle cx="12" cy="12" r="10"/><path d="M12 6v6l4 2"/></svg></el-icon>
          <span> 甜点管理 </span>
        </el-menu-item>
        <el-menu-item index="/user_list">
          <el-icon><svg viewBox="0 0 24 24" width="18" height="18" fill="none" stroke="currentColor" stroke-width="2"><path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/><circle cx="9" cy="7" r="4"/><path d="M23 21v-2a4 4 0 0 0-3-3.87"/><path d="M16 3.13a4 4 0 0 1 0 7.75"/></svg></el-icon>
          <span> 员工列表 </span>
        </el-menu-item>
        <el-menu-item index="/material">
          <el-icon><svg viewBox="0 0 24 24" width="18" height="18" fill="none" stroke="currentColor" stroke-width="2"><path d="M21 16V8a2 2 0 0 0-1-1.73l-7-4a2 2 0 0 0-2 0l-7 4A2 2 0 0 0 3 8v8a2 2 0 0 0 1 1.73l7 4a2 2 0 0 0 2 0l7-4A2 2 0 0 0 21 16z"/><polyline points="3.27 6.96 12 12.01 20.73 6.96"/><line x1="12" y1="22.08" x2="12" y2="12"/></svg></el-icon>
          <span>原材料列表</span>
        </el-menu-item>
        <el-menu-item index="/inventory">
          <el-icon><svg viewBox="0 0 24 24" width="18" height="18" fill="none" stroke="currentColor" stroke-width="2"><path d="M22 12h-4l-3 9L9 3l-3 9H2"/></svg></el-icon>
          <span>库存列表</span>
        </el-menu-item>
        <el-menu-item index="/purchase_record">
          <el-icon><svg viewBox="0 0 24 24" width="18" height="18" fill="none" stroke="currentColor" stroke-width="2"><path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"/><polyline points="14 2 14 8 20 8"/><line x1="16" y1="13" x2="8" y2="13"/><line x1="16" y1="17" x2="8" y2="17"/></svg></el-icon>
          <span>采购记录</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <el-main class="home-main">
      <div class="main-content">
        <router-view />
      </div>
    </el-main>
    </el-container>
    <el-footer class="home-footer"> Copyright &copy; 2026 甜品管理系统 </el-footer>
  </el-container>
</template>

<style scoped>
.home-container {
  height: 100vh;
  display: flex;
  flex-direction: column;
  background: #f0f2f5;
}

.home-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  background: linear-gradient(135deg, #1a1a2e, #16213e, #0f3460);
  color: #fff;
  height: 56px;
  padding: 0 24px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.15);
  z-index: 10;
}
.header-left { display: flex; align-items: center; gap: 10px; }
.header-logo { font-size: 24px; animation: bounce 2s ease-in-out infinite; }
@keyframes bounce {
  0%, 100% { transform: translateY(0); }
  50% { transform: translateY(-4px); }
}
.header-title { font-size: 18px; font-weight: 700; letter-spacing: 2px; }
.header-subtitle { font-size: 12px; color: rgba(255,255,255,0.6); letter-spacing: 2px; }

.home-body { flex: 1; display: flex; overflow: hidden; }

.home-aside {
  background: #fff;
  border-right: 1px solid #e8e8e8;
  overflow-y: auto;
  animation: slideIn 0.4s ease-out;
}
@keyframes slideIn {
  from { opacity: 0; transform: translateX(-20px); }
  to { opacity: 1; transform: translateX(0); }
}

.side-menu { border-right: none; }
.side-menu .el-menu-item {
  height: 48px;
  line-height: 48px;
  margin: 4px 8px;
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
.side-menu .el-menu-item.is-active .el-icon { color: #fff; }
.side-menu .el-menu-item .el-icon { margin-right: 8px; }

.home-main {
  flex: 1;
  padding: 20px;
  overflow-y: auto;
  background: #f0f2f5;
}

.main-content {
  background: #fff;
  border-radius: 12px;
  padding: 24px;
  box-shadow: 0 2px 12px rgba(0,0,0,0.06);
  min-height: 100%;
  animation: fadeUp 0.4s ease-out;
}
@keyframes fadeUp {
  from { opacity: 0; transform: translateY(16px); }
  to { opacity: 1; transform: translateY(0); }
}

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