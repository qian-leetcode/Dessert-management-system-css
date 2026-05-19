<script setup>
import { ref } from 'vue'

const regVisible = ref(false)

const user_from_register = ref({
  username : '',
  password: '',
  re_password: ''
})

const open_el_dialog = () =>{
  regVisible.value = true
}

const push_from_register_data = () =>{
  // console.log(user_from.value)
  regVisible.value = false
}

</script>

<template>
    <el-container class="login-page">
      <el-header class="login-header">
        甜品管理系统
      </el-header>
      <el-main class="login-main">
        <el-container style="font-size:32px">
          账号登录
        </el-container>
        <el-container>
          <el-input type="text" name="userName" placeholder="请输入用户名" class="input"/>
        </el-container>
        <el-container>
          <el-input type="text" name="password" placeholder="请输入密码" class="input"/>
        </el-container>
        <el-container style="padding-top: 12px;justify-content: center; align-items: center">
          <el-checkbox name="remember" /> 记住账号
          <el-container style="justify-content: flex-end">
          <span @click="open_el_dialog" class="clickable-text"> 没有账号？点击注册 </span>
            <!-- 注册弹窗 -->
            <el-dialog
              :model-value="regVisible"
              @update:model-value="regVisible = $event"
              title="账号注册"
              width="500px"

            >
              <el-form label-width="80px">
                <el-form-item label="账号">
                  <el-input v-model="user_from_register.username" />
                </el-form-item>
                <el-form-item label="密码">
                  <el-input v-model="user_from_register.password" show-password/>
                </el-form-item>
                <el-form-item label="确认密码">
                  <el-input v-model="user_from_register.re_password" show-password/>
                </el-form-item>
              </el-form>
              <template #footer>
                <el-button @click="regVisible = false"> 取消 </el-button>
                <el-button type="primary" @click="push_from_register_data"> 立即注册 </el-button>
              </template>

            </el-dialog>

          </el-container>
        </el-container>
        <el-button class="login-button">登录</el-button>
      </el-main>
      <el-footer >Copyright@2026</el-footer>
    </el-container>
</template>

<style scoped>
.login-page {
  width: 100vw;
  height: 100vh;
  margin: 0 auto;
  padding: 0;
}

.login-header {
  background: rgb(150,200,255);
  font-size: 72px;
  height: 100px;
}

.login-main {
  margin: 0 auto;
  padding-top: 100px;
}

.input {
  width: 450px;
  height: 78px;
  padding-top: 24px;
}

.clickable-text {
  color: #409eff;           /* 蓝色，看起来像链接 */
  cursor: pointer;          /* 鼠标变成手型 */
  user-select: none;        /* 防止选中文字 */
}

.login-button {
  width: 450px;
  height: 48px;
  background: #409eff;
}
</style>