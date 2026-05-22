<script setup>

</script>

<template>
  <el-container>
    <el-header > 甜品系统管理 </el-header>
    <el-aside>
      <el-menu :router="true" default-active="/" >
        <el-menu-item index="/category">
          <span> 分类管理 </span>
        </el-menu-item>
        <el-menu-item index="/dessert">
          <span> 甜点管理 </span>
        </el-menu-item>
        <el-menu-item index="/usr_list">
          <span> 员工列表 </span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <el-main>
      <router-view />
    </el-main>
    <el-footer> Copyright@2026 </el-footer>
  </el-container>
</template>

<style scoped>

</style>