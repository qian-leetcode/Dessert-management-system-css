<script setup>

</script>

<template>
  <el-container style="height: 100vh; flex-direction: column;">
    <el-header> 甜品系统管理 </el-header>
    <el-container style="flex:1;">
    <el-aside width="200px">
      <el-menu :router="true" default-active="/" style="height: 100%">
        <el-menu-item index="/category">
          <span> 分类管理 </span>
        </el-menu-item>
        <el-menu-item index="/dessert">
          <span> 甜点管理 </span>
        </el-menu-item>
        <el-menu-item index="/user_list">
          <span> 员工列表 </span>
        </el-menu-item>
        <el-menu-item index="/material">
          <span>原材料列表</span>
        </el-menu-item>
        <el-menu-item index="/inventory">
          <span>库存列表</span>
        </el-menu-item>
        <el-menu-item index="/purchase_record">
          <span>采购记录</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <el-main>
      <router-view />
    </el-main>
    </el-container>
    <el-footer> Copyright@2026 </el-footer>
  </el-container>
</template>

<style scoped>

</style>