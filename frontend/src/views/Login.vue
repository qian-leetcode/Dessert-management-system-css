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
  const val = false;
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
        user_from_register.hire_date,)
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
    <el-container class="login-page">
      <el-header class="login-header">
        甜品管理系统
      </el-header>
      <el-main class="login-main">
        <el-container style="font-size:32px">
          账号登录
        </el-container>
        <el-container>
          <el-input type="text" v-model="user_login_information.username" placeholder="请输入用户名" class="input"/>
        </el-container>
        <el-container>
          <el-input type="text" v-model="user_login_information.password" placeholder="请输入密码" class="input"/>
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
                <el-form-item label="姓名">
                  <el-input v-model="user_from_register.name" placeholder="请输入姓名" />
                </el-form-item>
                <el-form-item label="性别">
                  <el-select v-model="user_from_register.gender" placeholder="请选择性别">
                    <el-option label="男" value="男"/>
                    <el-option label="女" value="女"/>
                  </el-select>
                </el-form-item>
                <el-form-item label="电话">
                  <el-input v-model="user_from_register.phone" placeholder="请输入电话号码" />
                </el-form-item>
                <el-form-item label="职位">
                  <el-select v-model="user_from_register.position">
                    <el-option
                    v-for="item in role_list"
                    :key ="item"
                    :label="item"
                    :value="item"
                    />
                  </el-select>
                </el-form-item>
                <el-form-item label="入职日期">
                  <!-- 日期选择器，默认选择日期格式 -->
                  <el-date-picker
                      v-model="user_from_register.hire_date"
                      type="date"
                      placeholder="请选择入职日期"
                      format="YYYY-MM-DD"
                      value-format="YYYY-MM-DD"
                  />
                </el-form-item>
                <el-form-item label="班次">
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
          </el-container>
        </el-container>
        <el-button class="login-button" @click="loginUser"> 登录 </el-button>
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