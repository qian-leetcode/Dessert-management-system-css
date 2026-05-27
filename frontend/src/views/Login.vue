<script setup>
import { onMounted, reactive, ref} from 'vue'
import {useRouter} from "vue-router";
import {ElMessage} from "element-plus";
import {user_login , user_register} from "@/api/login.js";
import {get_user_role_List , get_user_username_list} from "@/api/user.js";
import {useLoginStore} from "@/stores/useLoginStore.js";

const regVisible = ref(false)

const router = useRouter()
const loginStore = useLoginStore()
const rememberMe = ref(false)

// 登录信息表
const user_login_information = reactive({
  username: '',
  password: '',
})

// 登录请求
const loginUser = async () => {
  if (!user_login_information.username || !user_login_information.password) {
    ElMessage.warning('请输入用户名和密码')
    return
  }
  try {
      const res = await user_login(user_login_information.username, user_login_information.password)
      const data = res.data

      // ✅ 只改这里：token 在 data.data 里面
      if (data.data?.token) {
        const token = data.data.token
        loginStore.setToken(token)
        localStorage.setItem('token', token)
        ElMessage.success('登录成功')
        await router.push('/home')
      } else {
        ElMessage.error(data.msg || '登录失败')
      }
    } catch (e) {
      console.error(e)
      ElMessage.error('登录失败，请检查网络连接')
    }
}


// 注册表
const user_from_register = reactive({
  username : '',
  password: '',
  re_password: '',
  name :'',
  gender:'',
  phone:'',
  position:'',
  hire_date:'',
  shift:''
})

// 打开弹窗
const open_el_dialog = () =>{
  regVisible.value = true
}

// 发送注册请求
const push_from_register_data = async () => {
  if (!user_from_register.username || !user_from_register.password) {
    ElMessage.warning('请输入用户名和密码')
    return
  }
  if (!user_from_register.re_password && user_from_register.re_password !== user_from_register.password) {
    ElMessage.warning('请再次确认密码')
    return
  }
  if (!user_from_register.name || !user_from_register.gender || !user_from_register.phone || !user_from_register.name || !user_from_register.hire_date) {
    ElMessage.warning('请完善信息')
    return
  }
  const len = username_list.length
  let val = false;
  for (let i = 0; i < len; i++) {
    if (user_from_register.username && user_from_register.username === username_list[i]) {
      val = true;
      break;
    }
  }
  if (val) {
    ElMessage.warning(' 用户名重复');
    return
  }
  try {
    console.log(user_from_register)
    const res = await user_register(user_from_register.username ,
        user_from_register.password,
        user_from_register.name,
        user_from_register.gender,
        user_from_register.phone,
        user_from_register.position,
        user_from_register.hire_date,
        user_from_register.shift,)
    // for (const v in res){
    //   console.log(v + "-----" + res[v])
    //   // console.log(res[v])
    // }
    if (res["status"]=== 200) {
      // ElMessage.info('注册成功')
      ElMessage.success('注册成功')
    } else {
      ElMessage.warning('注册失败')
      console.log(res.msg)
    }
  } catch (err) {
    ElMessage.warning('注册失败')
  }

  // console.log(user_from.value)
  regVisible.value = false
}

// 获取职位列表
const role_list = ref([])
const username_list = ref([])

const fetch_user_role_list = async () => {
  try{
    const res = await get_user_role_List()
    // console.log("后端返回的数据：", res.data)
    role_list.value = res.data
  }
  catch(error){
    console.error("获取列表失败",error)
  }
}

const fetch_username_list = async () => {
  try{
    const res = await get_user_username_list()
    username_list.value = res.data
  }
  catch(error){
    console.error("获取列表失败",error)
  }
}

onMounted(() => {
  fetch_user_role_list();
  fetch_username_list();
})

</script>

<template>
  <div class="login-wrapper">
    <div class="bg-shapes">
      <div class="shape s1"></div>
      <div class="shape s2"></div>
      <div class="shape s3"></div>
      <div class="shape s4"></div>
    </div>
    <div class="login-card">
      <div class="login-brand">
        <div class="brand-icon">🍰</div>
        <h1 class="brand-title">甜品管理系统</h1>
        <p class="brand-subtitle">Sweet Management, Sweet Life</p>
      </div>
      <div class="login-form">
        <h2 class="form-title">账号登录</h2>
        <el-input
          type="text"
          v-model="user_login_information.username"
          placeholder="请输入用户名"
          class="login-input"
          prefix-icon="User"
        />
        <el-input
          type="password"
          v-model="user_login_information.password"
          placeholder="请输入密码"
          class="login-input"
          prefix-icon="Lock"
          show-password
        />
        <div class="login-options">
          <el-checkbox name="remember" v-model="rememberMe" /> 记住账号
          <span @click="open_el_dialog" class="register-link">没有账号？点击注册</span>
        </div>
        <el-button class="login-btn" @click="loginUser">登 录</el-button>
      </div>
      <div class="login-footer">Copyright &copy; 2026 甜品管理系统</div>
    </div>

    <el-dialog
      :model-value="regVisible"
      @update:model-value="regVisible = $event"
      title="账号注册"
      width="500px"
      top="5vh"
      class="register-dialog"
    >
      <el-form label-width="80px">
        <el-form-item label="账号" required>
          <el-input v-model="user_from_register.username" />
        </el-form-item>
        <el-form-item label="密码" required>
          <el-input v-model="user_from_register.password" show-password/>
        </el-form-item>
        <el-form-item label="确认密码" required>
          <el-input v-model="user_from_register.re_password" show-password/>
        </el-form-item>
        <el-form-item label="姓名" required>
          <el-input v-model="user_from_register.name" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="性别" required>
          <el-select v-model="user_from_register.gender" placeholder="请选择性别">
            <el-option label="男" value="男"/>
            <el-option label="女" value="女"/>
          </el-select>
        </el-form-item>
        <el-form-item label="电话" required>
          <el-input v-model="user_from_register.phone" placeholder="请输入电话号码" />
        </el-form-item>
        <el-form-item label="职位" required>
          <el-select v-model="user_from_register.position">
            <el-option
            v-for="item in role_list"
            :key ="item"
            :label="item"
            :value="item"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="入职日期" required>
          <el-date-picker
              v-model="user_from_register.hire_date"
              type="date"
              placeholder="请选择入职日期"
              format="YYYY-MM-DD"
              value-format="YYYY-MM-DD"
          />
        </el-form-item>
        <el-form-item label="班次" required>
          <el-select v-model="user_from_register.shift" placeholder="选择班次">
            <el-option label="早班" value="早班"></el-option>
            <el-option label="中班" value="中班"></el-option>
            <el-option label="晚班" value="晚班"></el-option>
            <el-option label="全天" value="全天"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="regVisible = false"> 取消 </el-button>
        <el-button type="primary" @click="push_from_register_data"> 立即注册 </el-button>
      </template>
    </el-dialog>
  </div>
</template>

<style scoped>
.login-wrapper {
  width: 100vw;
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #1a1a2e 0%, #16213e 50%, #0f3460 100%);
  position: relative;
  overflow: hidden;
}

.bg-shapes { position: absolute; inset: 0; pointer-events: none; }
.shape {
  position: absolute;
  border-radius: 50%;
  filter: blur(80px);
  opacity: 0.15;
  animation: float 8s ease-in-out infinite;
}
.s1 {
  width: 500px; height: 500px;
  background: linear-gradient(135deg, #e17055, #fd79a8);
  top: -10%; left: -5%;
}
.s2 {
  width: 400px; height: 400px;
  background: linear-gradient(135deg, #00cec9, #81ecec);
  bottom: -10%; right: -5%;
  animation-delay: -3s;
}
.s3 {
  width: 300px; height: 300px;
  background: linear-gradient(135deg, #fdcb6e, #f39c12);
  top: 50%; left: 60%;
  animation-delay: -5s;
}
.s4 {
  width: 250px; height: 250px;
  background: linear-gradient(135deg, #6c5ce7, #a29bfe);
  top: 20%; right: 10%;
  animation-delay: -2s;
}
@keyframes float {
  0%, 100% { transform: translate(0, 0) scale(1); }
  33% { transform: translate(30px, -30px) scale(1.05); }
  66% { transform: translate(-20px, 20px) scale(0.95); }
}

.login-card {
  position: relative;
  z-index: 1;
  width: 420px;
  background: rgba(255, 255, 255, 0.95);
  border-radius: 20px;
  box-shadow: 0 25px 60px rgba(0, 0, 0, 0.3);
  overflow: hidden;
  animation: cardIn 0.6s ease-out;
}
@keyframes cardIn {
  from { opacity: 0; transform: translateY(40px) scale(0.96); }
  to { opacity: 1; transform: translateY(0) scale(1); }
}

.login-brand {
  background: linear-gradient(135deg, #6c5ce7, #a29bfe, #fd79a8);
  padding: 32px 0 28px;
  text-align: center;
  position: relative;
  overflow: hidden;
}
.login-brand::after {
  content: '';
  position: absolute;
  top: -50%; left: -50%;
  width: 200%; height: 200%;
  background: radial-gradient(circle, rgba(255,255,255,0.1) 0%, transparent 60%);
  animation: shimmer 6s ease-in-out infinite;
}
@keyframes shimmer {
  0%, 100% { transform: rotate(0deg); }
  50% { transform: rotate(180deg); }
}
.brand-icon { font-size: 48px; animation: bounce 2s ease-in-out infinite; }
@keyframes bounce {
  0%, 100% { transform: translateY(0); }
  50% { transform: translateY(-8px); }
}
.brand-title {
  color: #fff; font-size: 26px; font-weight: 700;
  margin-top: 8px; letter-spacing: 2px;
}
.brand-subtitle {
  color: rgba(255,255,255,0.8); font-size: 13px;
  margin-top: 4px; letter-spacing: 4px;
}

.login-form { padding: 32px 36px 20px; }
.form-title {
  text-align: center; font-size: 22px; font-weight: 600;
  color: #2d3436; margin-bottom: 24px;
  letter-spacing: 1px;
}

.login-input { margin-bottom: 18px; }
.login-input :deep(.el-input__wrapper) {
  border-radius: 10px;
  padding: 4px 16px;
  box-shadow: 0 0 0 1px #e0e0e0 inset;
  transition: box-shadow 0.3s, transform 0.3s;
}
.login-input :deep(.el-input__wrapper:hover) {
  box-shadow: 0 0 0 1px #a29bfe inset;
}
.login-input :deep(.el-input__wrapper.is-focus) {
  box-shadow: 0 0 0 2px #6c5ce7 inset, 0 4px 12px rgba(108,92,231,0.15);
  transform: translateY(-1px);
}
.login-input :deep(.el-input__inner) { height: 44px; font-size: 14px; }
.login-input :deep(.el-input__prefix-inner) { color: #b2bec3; }

.login-options {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
  font-size: 13px;
  color: #636e72;
}
.register-link {
  color: #6c5ce7;
  cursor: pointer;
  font-weight: 500;
  transition: color 0.3s;
}
.register-link:hover { color: #a29bfe; text-decoration: underline; }

.login-btn {
  width: 100%;
  height: 48px;
  font-size: 16px;
  font-weight: 600;
  letter-spacing: 4px;
  border: none;
  border-radius: 10px;
  background: linear-gradient(135deg, #6c5ce7, #a29bfe);
  color: #fff;
  cursor: pointer;
  transition: all 0.3s;
  box-shadow: 0 4px 15px rgba(108,92,231,0.3);
}
.login-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 25px rgba(108,92,231,0.4);
}
.login-btn:active { transform: translateY(0); }

.login-footer {
  text-align: center;
  padding: 16px 0;
  color: #b2bec3;
  font-size: 12px;
  border-top: 1px solid #f0f0f0;
}

.register-dialog :deep(.el-dialog__header) {
  background: linear-gradient(135deg, #6c5ce7, #a29bfe);
  color: #fff;
  margin: 0;
  padding: 18px 20px;
  border-radius: 4px 4px 0 0;
}
.register-dialog :deep(.el-dialog__title) { color: #fff; font-weight: 600; }
.register-dialog :deep(.el-dialog__headerbtn .el-dialog__close) { color: rgba(255,255,255,0.7); }
.register-dialog :deep(.el-dialog__headerbtn:hover .el-dialog__close) { color: #fff; }
.register-dialog :deep(.el-dialog__body) { padding: 24px 20px; }
</style>