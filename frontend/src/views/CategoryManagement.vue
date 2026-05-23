<script setup>
import { get_dessert_List } from '@/api/category.js'
import {onMounted, reactive, ref} from "vue";
import {ElMessage} from "element-plus";

// 种类查询表
const query_form = reactive({
  name:'',
  description:'',
})

// 查询按钮

function query_form_button(){
  fetch_data()
}

// 清空种类查询表中的内容
function clear_query_form(){
  query_form.name = '';
  query_form.description = '';
}

// 种类显示
const dessert_form = ref([])
const page_num = ref(0)
const page_size = ref(10)
const total = ref(0)

async function fetch_data(){
  try {
    const params = {
      page_num: page_num.value,
      page_size: page_size.value,
      // 空值传入
      name : query_form.name,
      description : query_form.description,
      total : total.value
    }
    const res = await get_dessert_List(params)
    dessert_form.value = res.data.rows
    // console.log(dessert_form)
    // console.log(res)
    total.value = res.data.total
  }
  catch(err){
    ElMessage.error(err)
  }
}

onMounted(() => fetch_data())

</script>

<template>
  <div>
    <el-form :inline="true" :model="query_form">
      <el-form-item label="分类名称">
        <el-input v-model="query_form.name" placeholder="请输入分类名称" />
      </el-form-item>
      <el-form-item label="描述">
        <el-input v-model="query_form.description" placeholder="请输入描述"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="query_form_button">查询</el-button>
        <el-button type="primary" @click="clear_query_form">重置</el-button>
      </el-form-item>
    </el-form>
  </div>

  <div>
    <el-button type="danger">批量删除</el-button>
    <el-button type="primary"> 新增 </el-button>
    <!--  设置新增弹窗-->
  </div>
  <el-table :data="dessert_form" stripe style="width: 100%" @selection-change="val => selected = val.map(v => v.id)">
    <el-table-column type="selection" width="50" />
    <el-table-column prop="name" label="分类名称" />
    <el-table-column prop="description" label="描述" />
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
      @size-change="fetch_data"
      @current-change="fetch_data"
  />

</template>

<style scoped>

</style>