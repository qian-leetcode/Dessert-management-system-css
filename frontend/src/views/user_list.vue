<script setup>
// 员工查询表
import {onMounted, reactive, ref} from "vue";
import {get_user_information_list, get_user_role_List} from "@/api/user.js";

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

// 职业列表
const role_list = ref([]);

// 获取职业列表
async function get_role_list() {
  role_list.value = await (await get_user_role_List()).data;
}

// 用户信息表
const user_information_list = ref([])
const page_num = ref(1)
const page_size = ref(5)
const total = ref(0)

async function get_information_list() {
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
  console.log(res.data);
  user_information_list.value = res.data.rows
  total.value = res.data.total
  console.log(user_information_list)
}

onMounted(() => {
  get_role_list();
  get_information_list();
})

</script>

<template>
  <div>
    <el-form>
      <el-form-item label="姓名">
        <el-input v-model="user_list.name" placeholder="请输入员工姓名" clearable/>
      </el-form-item>
      <el-form-item label="性别">
        <el-select v-model="user_list.gender">
          <el-option label="男" value="男" />
          <el-option label="女" value="女" />
        </el-select>
      </el-form-item>
      <el-form-item label="联系电话">
         <el-input v-model="user_list.phone" placeholder="请输入联系电话" clearable />
      </el-form-item>
      <el-form-item label="用户名">
        <el-input v-model="user_list.username" placeholder="请输入用户名" clearable />
      </el-form-item>
      <el-form-item label="职位">
        <el-select v-model="user_list.position">
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
            v-model="user_list.hire_date"
            type="date"
            placeholder="请选择入职日期"
            format="YYYY-MM-DD"
            value-format="YYYY-MM-DD"
        />
      </el-form-item>
      <el-form-item label="上班班次">
        <el-select v-model="user_list.shift">
          <el-option label="早班" value="早班" />
          <el-option label="中班" value="早班" />
          <el-option label="晚班" value="晚班" />
          <el-option label="全天" value="全天" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button @click=""> 查询 </el-button>
        <el-button @click=""> 重置 </el-button>
      </el-form-item>
    </el-form>
  </div>
  <div>
    <el-button>批量删除</el-button>
    <el-button>新增</el-button>
  </div>
  <div>
    <el-table :data="user_information_list" stripe @selection-change="val => selected = val.map(v=>v.id)" >
      <el-table-column type="selection" width="50" />
      <el-table-column prop="name" label="姓名" />
      <el-table-column prop="gender" label="性别" />
      <el-table-column prop="phone" label="联系电话" />
      <el-table-column prop="username" label="用户名" />
      <el-table-column prop="position" label="岗位职称" />
      <el-table-column prop="active" label="账号是否有管理权限" />
      <el-table-column prop="hire_date" label="入职日期"/>
      <el-table-column prop="shift" label="上班班次" />
      <el-table-column label="操作" width="150">
        <template @default="{rows}">
          <el-button size="small" type="primary" > 修改 </el-button>
          <el-button size="small" type="danger" > 删除 </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--  设置页码-->
    <el-pagination
        v-model:current-page="page_num"
        v-model:page-size="page_size"
        :total="total"
        :page-sizes="[5, 10, 20]"
        layout="total, sizes, prev, pager, next, jumper"
        style="margin-top: 15px; justify-content: center;"
        @size-change="get_information_list"
        @current-change="get_information_list"
    />
  </div>
</template>

<style scoped>

</style>