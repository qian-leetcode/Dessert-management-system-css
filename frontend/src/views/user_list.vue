<script setup>
// 员工查询表
import {onMounted, reactive, ref, watch} from "vue";
import {
  delete_user_information_,
  get_user_information_list,
  get_user_role_List,
  update_user_information_
} from "@/api/user.js";
import {ElMessage, ElMessageBox} from "element-plus";
import {user_register} from "@/api/login.js";

// 用户查询表
const user_list = reactive({
  name: "",
  gender:"",
  phone:"",
  username:"",
  position:"",
  hire_date:"",
  shift:""
})

async function clear_user_list(){
  user_list.name = ''
  user_list.gender = ''
  user_list.phone = ''
  user_list.username = ''
  user_list.position = ''
  user_list.hire_date = ''
  user_list.shift = ''
  page_num.value = 1
  await get_information_list()
}

// 职业列表
const role_list = ref([]);

// 获取职业列表
async function get_role_list() {
  try {
    role_list.value = await (await get_user_role_List()).data;
  }
  catch(error){
    ElMessage.error("获取职位列表失败，请联系工作人员")
  }
}

// 用户信息表
const user_information_list = ref([])
const page_num = ref(1)
const page_size = ref(5)
const total = ref(0)
const loading = ref(false)

//获取列表
async function get_information_list() {
  loading.value = true
  try {
    const params = {
      page_size: page_size.value,
      page_num: page_num.value,
      name: user_list.name,
      gender: user_list.gender,
      phone: user_list.phone,
      username: user_list.username,
      position: user_list.position,
      hire_date: user_list.hire_date,
      shift: user_list.shift,
    }
    const res = await get_user_information_list(params);
    // console.log(res)
    // console.log(res.data);
    if(res.data.code === 200){
      user_information_list.value = res.data.rows
      total.value = res.data.total
    }
    else {
      ElMessage.error("获取用户列表失败，请联系工作人员")
    }
  }
  catch(error){
    ElMessage.error(error)
  }
  finally {
    loading.value = false
  }
}

// 新增
const regVisible = ref(false)
// 注册表
const user_from_register = reactive({
  id:'',
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

// 清空注册表
async function clear_user_from_register(){
  user_from_register.id=''
  user_from_register.username = ''
  user_from_register.password= ''
  user_from_register.re_password= ''
  user_from_register.name =''
  user_from_register.gender=''
  user_from_register.phone=''
  user_from_register.position=''
  user_from_register.hire_date=''
  user_from_register.shift=''
}

// 名称表
const username_list = ref([])

// 发送注册请求
const push_from_register_data = async () => {
  if (!user_from_register.username || !user_from_register.password) {
    ElMessage.warning('请输入用户名和密码')
    return
  }
  if (!user_from_register.re_password || user_from_register.re_password !== user_from_register.password) {
    ElMessage.warning('请再次确认密码')
    return
  }
  if (!user_from_register.name || !user_from_register.gender || !user_from_register.phone || !user_from_register.name || !user_from_register.hire_date) {
    ElMessage.warning('请完善信息')
    return
  }
  const len = username_list.value.length
  let val = false;
  for (let i = 0; i < len; i++) {
    if (user_from_register.username && user_from_register.username === username_list.value[i]) {
      val = true;
      break;
    }
  }
  if (val) {
    ElMessage.warning(' 用户名重复');
    return
  }
  try {
    // console.log(user_from_register)
    const res = await user_register(
        user_from_register.username ,
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
    // console.log(res)
    if (res.data.code === 200) {
      ElMessage.success('注册成功')
      await clear_user_from_register()
    } else {
      ElMessage.warning('注册失败')
    }
  } catch (err) {
    ElMessage.warning('注册失败')
  }

  regVisible.value = false
}

// 删除
const selected = ref([])

async function delete_user_information(id){
  try{
    const res = await delete_user_information_(id)
    if(res.data.code === 200){
      ElMessage.success("删除成功")
    }
    else if(res.data.code === 400){
      ElMessage.error("删除失败")
    }
    else {
      ElMessage.error("删除异常，请联系工作人员")
    }
    await get_information_list();
  }
  catch(err){
    ElMessage.error("删除异常，请联系工作人员")
  }
}

// 批量删除
async function Batch_delete(){
  try{
    await ElMessageBox.confirm(
        '确定要删除选中的员工吗？',
        '删除确认',
        { confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning' }
    )
    for (const value of selected.value){
      const res = await delete_user_information_(value)
      if(res.data.code === 200){
        ElMessage.success("删除成功")
      }
      else if(res.data.code === 400){
        ElMessage.error("删除失败，批量删除终止")
        return
      }
      else {
        ElMessage.error("批量删除终止，请联系工作人员")
        return
      }
    }
    ElMessage.success("批量删除成功")
    selected.value = []
    await get_information_list();
  }
  catch(err){
    ElMessage.error("批量删除失败，请联系工作人员")
  }
}

// 修改用户
async function update_user_information(row){
  regVisible.value = true
  user_from_register.id = row.id
  user_from_register.username = row.username
  user_from_register.password = ''
  user_from_register.re_password =''
  user_from_register.name = row.name
  user_from_register.gender = row.gender
  user_from_register.phone = row.phone
  user_from_register.position = row.position
  user_from_register.hire_date = row.hire_date
  user_from_register.shift = row.shift
  console.log(user_from_register)
}

// 更新用户
async function update_user(){
  // 如果填写了密码，需要校验确认密码
  if (user_from_register.password && (!user_from_register.re_password || user_from_register.re_password !== user_from_register.password)) {
    ElMessage.warning('两次输入的密码不一致')
    return
  }
  try{
    const res = await update_user_information_(user_from_register)
    if(res.data.code === 200){
      ElMessage.success("修改成功")
    }
    else if(res.data.code === 400){
      ElMessage.error("修改失败")
    }
    else {
      ElMessage.error("修改异常，请联系工作人员")
    }
    regVisible.value = false
    await get_information_list();
  }
  catch(err){
    ElMessage.error("修改异常，请联系工作人员")
  }
}

watch(regVisible, (newVal, oldVal) => {
  console.log("变量变了！新值：", newVal)
  if(newVal === false) {
    clear_user_from_register()
  }
})


onMounted(() => {
  get_role_list();
  get_information_list();
})

</script>

<template>
  <div>
    <div class="query-form">
      <el-form inline>
        <el-form-item label="姓名">
          <el-input v-model="user_list.name" placeholder="请输入员工姓名" clearable style="width: 250px;"/>
        </el-form-item>
        <el-form-item label="性别" style="width: 250px;">
          <el-select v-model="user_list.gender" placeholder="请选择性别">
            <el-option label="男" value="男" />
            <el-option label="女" value="女" />
          </el-select>
        </el-form-item>
        <el-form-item label="联系电话">
           <el-input type="number" v-model="user_list.phone" placeholder="请输入联系电话" clearable style="width: 250px;"/>
        </el-form-item>
        <el-form-item label="用户名">
          <el-input v-model="user_list.username" placeholder="请输入用户名" clearable style="width: 250px;"/>
        </el-form-item>
        <el-form-item label="职位">
          <el-select v-model="user_list.position" style="width: 250px;" placeholder="请选择职业">
            <el-option v-for="item in role_list" :key ="item" :label="item" :value="item" />
          </el-select>
        </el-form-item>
        <el-form-item label="入职日期">
          <el-date-picker v-model="user_list.hire_date" type="date" placeholder="请选择入职日期" format="YYYY-MM-DD" value-format="YYYY-MM-DD" style="width: 250px;" />
        </el-form-item>
        <el-form-item label="上班班次">
          <el-select v-model="user_list.shift" style="width: 250px;" placeholder="请选择上班班次">
            <el-option label="早班" value="早班" />
            <el-option label="中班" value="中班" />
            <el-option label="晚班" value="晚班" />
            <el-option label="全天" value="全天" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="get_information_list"> 查询 </el-button>
          <el-button type="primary" @click="clear_user_list"> 重置 </el-button>
        </el-form-item>
      </el-form>
    </div>

    <div class="toolbar">
      <el-button type="danger" @click="Batch_delete">批量删除</el-button>
      <el-button type="primary" @click="regVisible = true">新增</el-button>
    </div>

    <el-dialog
        class="dialog-form"
        :model-value="regVisible"
        @close="regVisible = false"
        :title="user_from_register.id ? '账户修改' :'账号注册'"
        width="500px"
    >
      <el-form label-width="120px">
        <el-form-item label="账号" required>
          <el-input v-model="user_from_register.username" placeholder="请输入用户名"/>
        </el-form-item>
        <el-form-item label="密码" required>
          <el-input v-model="user_from_register.password" placeholder="请输入密码" show-password/>
        </el-form-item>
        <el-form-item label="确认密码" required>
          <el-input v-model="user_from_register.re_password" placeholder="再次确认密码" show-password/>
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
          <el-input type="number" v-model="user_from_register.phone" placeholder="请输入电话号码" />
        </el-form-item>
        <el-form-item label="职位" required>
          <el-select v-model="user_from_register.position" placeholder="请选择职位">
            <el-option v-for="item in role_list" :key ="item" :label="item" :value="item" />
          </el-select>
        </el-form-item>
        <el-form-item label="入职日期" required>
          <el-date-picker v-model="user_from_register.hire_date" type="date" placeholder="请选择入职日期" format="YYYY-MM-DD" value-format="YYYY-MM-DD" />
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
        <el-button type="primary" @click="user_from_register.id ? update_user() : push_from_register_data()">{{user_from_register.id ? '保存修改' : '立即注册'}}</el-button>
      </template>
    </el-dialog>

    <div class="table-container">
      <el-table :data="user_information_list" v-loading="loading" stripe @selection-change="val => selected = val.map(v=>v.id)" >
        <el-table-column type="selection" width="50" />
        <el-table-column prop="name" label="姓名" />
        <el-table-column prop="gender" label="性别" />
        <el-table-column prop="phone" label="联系电话" />
        <el-table-column prop="username" label="用户名" />
        <el-table-column prop="position" label="岗位职称" />
        <el-table-column prop="hire_date" label="入职日期"/>
        <el-table-column prop="shift" label="上班班次" />
        <el-table-column label="操作" width="150">
          <template #default="{row}">
            <el-button size="small" type="primary" @click="update_user_information(row)"> 修改 </el-button>
            <el-button size="small" type="danger" @click="delete_user_information(row.id)"> 删除 </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div class="pagination-wrap">
      <el-pagination
          v-model:current-page="page_num"
          v-model:page-size="page_size"
          :total="total"
          :page-sizes="[5, 10, 20]"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="get_information_list"
          @current-change="get_information_list"
      />
    </div>
  </div>
</template>

<style scoped>
/* user_list - 专属样式（共享样式在 shared.css） */
</style>