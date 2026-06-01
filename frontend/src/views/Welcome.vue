<script setup>
import {ref, onMounted} from "vue";

const stats = ref([
  { label: '分类管理', icon: '🔖', desc: '管理甜品分类', index: '/category', color: '#6c5ce7' },
  { label: '甜点管理', icon: '🍰', desc: '管理甜品信息', index: '/dessert', color: '#fd79a8' },
  { label: '员工管理', icon: '👥', desc: '管理员工信息', index: '/user_list', color: '#00cec9' },
  { label: '原材料', icon: '📦', desc: '管理原料信息', index: '/material', color: '#fdcb6e' },
  { label: '库存管理', icon: '📊', desc: '查看库存状态', index: '/inventory', color: '#e17055' },
  { label: '采购记录', icon: '📋', desc: '管理采购单据', index: '/purchase_record', color: '#0984e3' },
])

const currentTime = ref('')
const greeting = ref('')

function updateTime() {
  const now = new Date()
  currentTime.value = now.toLocaleString('zh-CN', {
    year: 'numeric', month: 'long', day: 'numeric',
    hour: '2-digit', minute: '2-digit', second: '2-digit'
  })
  const h = now.getHours()
  if (h < 6) greeting.value = '夜深了，注意休息'
  else if (h < 9) greeting.value = '早上好'
  else if (h < 12) greeting.value = '上午好'
  else if (h < 14) greeting.value = '中午好'
  else if (h < 18) greeting.value = '下午好'
  else greeting.value = '晚上好'
}

onMounted(() => {
  updateTime()
  setInterval(updateTime, 1000)
})
</script>

<template>
  <div class="welcome-page">
    <!-- 欢迎横幅 -->
    <div class="welcome-banner">
      <div class="banner-content">
        <div class="banner-icon">🍰</div>
        <div class="banner-text">
          <h1>{{ greeting }}！</h1>
          <p class="banner-time">{{ currentTime }}</p>
          <p class="banner-sub">欢迎使用甜品管理系统，轻松管理你的甜品业务</p>
        </div>
      </div>
    </div>

    <!-- 快捷入口卡片 -->
    <h2 class="section-title">📌 功能快捷入口</h2>
    <div class="card-grid">
      <router-link v-for="card in stats" :key="card.label" :to="card.index" class="stat-card" :style="{ '--card-color': card.color }">
        <div class="card-icon">{{ card.icon }}</div>
        <div class="card-info">
          <div class="card-label">{{ card.label }}</div>
          <div class="card-desc">{{ card.desc }}</div>
        </div>
        <div class="card-arrow">→</div>
      </router-link>
    </div>

    <!-- 底部提示 -->
    <div class="welcome-footer">
      <p>💡 左侧导航栏可随时切换功能模块</p>
    </div>
  </div>
</template>

<style scoped>
.welcome-page {
  max-width: 900px;
  margin: 0 auto;
}

/* 欢迎横幅 */
.welcome-banner {
  background: linear-gradient(135deg, #6c5ce7, #a29bfe, #fd79a8);
  border-radius: 14px;
  padding: 32px 36px;
  color: #fff;
  margin-bottom: 28px;
  position: relative;
  overflow: hidden;
}
.welcome-banner::after {
  content: '';
  position: absolute;
  top: -50%; right: -20%;
  width: 300px; height: 300px;
  background: radial-gradient(circle, rgba(255,255,255,0.1) 0%, transparent 60%);
  border-radius: 50%;
}
.banner-content {
  display: flex;
  align-items: center;
  gap: 20px;
  position: relative;
  z-index: 1;
}
.banner-icon { font-size: 48px; }
.banner-text h1 {
  margin: 0 0 4px;
  font-size: 24px;
  font-weight: 700;
}
.banner-time {
  margin: 0 0 4px;
  font-size: 13px;
  opacity: 0.85;
}
.banner-sub {
  margin: 0;
  font-size: 14px;
  opacity: 0.75;
}

/* 标题 */
.section-title {
  font-size: 18px;
  font-weight: 600;
  color: #333;
  margin: 0 0 16px;
}

/* 卡片网格 */
.card-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(260px, 1fr));
  gap: 14px;
  margin-bottom: 24px;
}
.stat-card {
  display: flex;
  align-items: center;
  gap: 14px;
  padding: 18px 20px;
  border-radius: 12px;
  background: #fafbff;
  border: 1px solid #f0f0f5;
  cursor: pointer;
  transition: all 0.25s;
  text-decoration: none;
  color: inherit;
}
.stat-card:hover {
  transform: translateY(-3px);
  box-shadow: 0 8px 24px rgba(108,92,231,0.12);
  border-color: var(--card-color);
}
.card-icon { font-size: 32px; }
.card-info { flex: 1; }
.card-label {
  font-size: 15px;
  font-weight: 600;
  color: #333;
}
.card-desc {
  font-size: 12px;
  color: #999;
  margin-top: 2px;
}
.card-arrow {
  font-size: 18px;
  color: #ccc;
  transition: transform 0.2s;
}
.stat-card:hover .card-arrow {
  transform: translateX(4px);
  color: var(--card-color);
}

/* 底部 */
.welcome-footer {
  text-align: center;
  padding: 12px;
  background: #fafafa;
  border-radius: 10px;
  color: #999;
  font-size: 13px;
}
</style>
