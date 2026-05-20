<script setup>

</script>

<template>
  <el-container>
    <el-header> 甜品系统管理 </el-header>
    <el-aside>
      <el-menu :router="true" default-active="/" >
        <el-menu-item index="/">
          <span> 分类管理 </span>
        </el-menu-item>
        <el-menu-item index="/about">
          <span> 甜点管理 </span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <el-main>
      <router-view />
    </el-main>
    <el-flooter> Copyright@2026 </el-flooter>
  </el-container>
</template>

<style scoped>

</style>