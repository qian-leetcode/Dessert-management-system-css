<script setup>
import {add_category_list_, delete_category_list_, get_category_List_, update_category_list_} from '@/api/category.js'
import {onMounted, reactive, ref, watch} from "vue";
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

// 获取种类列表
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
    const res = await get_category_List_(params)
    dessert_form.value = res.data.rows
    // console.log(dessert_form)
    // console.log(res)
    total.value = res.data.total
  }
  catch(err){
    ElMessage.error(err)
  }
}

const selected = ref([])
// 新增
const category_visible = ref(false);
const add_category = reactive({
  name:'',
  description:'',
  id:''
})

async function clear_add_category(){
  add_category.name = ''
  add_category.description = ''
  add_category.id= ''
}

// 新增内容
async function add_category_list(){
  try{
    if(add_category.name === '' || add_category.description === ''){
      ElMessage.error("请完善信息")
      return
    }
    const res = await add_category_list_(add_category)
    console.log(res)
    category_visible.value = false
  }
  catch(err){
    ElMessage.error(err)
    console.log(err)
  }
}

// 删除分类
async function handle_delete(id){
  try{
    const res = await delete_category_list_(id)
    console.log(res)
    await fetch_data()
  }
  catch(err){
    ElMessage.error(err)
  }

}

// 更新分类
async function update_category_list(row){
    category_visible.value = true
    add_category.name = row.name
    add_category.description = row.description
    add_category.id = row.id
    // category_visible.value = false
}

// 删除种类
async function update_category(){
  try {
    if (!add_category.name || !add_category.description) {
      ElMessage.error("请完善信息")
      return
    }
    await update_category_list_(add_category)
    ElMessage.success("修改成功")
    category_visible.value = false
    await fetch_data()
  } catch (err) {
    ElMessage.error("修改失败")
  }
}


//批量删除
async function Batch_delete(){
  try {
    for (const value of selected.value){
      await delete_category_list_(value)
    }
    ElMessage.success("批量删除成功")
    selected.value = []
    await fetch_data()
  }
  catch(err){
    ElMessage.error(err)
  }
}


watch(category_visible, (newVal, oldVal) => {
  console.log("变量变了！新值：", newVal)
  if(newVal === false) {
    clear_add_category()
  }
})


onMounted(() => {
  fetch_data();
})

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
    <el-button type="danger" @click="Batch_delete">批量删除</el-button>
    <el-button type="primary" @click="category_visible = true"> 新增 </el-button>
    <!--  设置新增弹窗-->
    <el-dialog
      :model-value="category_visible"
      @close="category_visible = false"
      title="新增分类"
      width="500px"
    >
      <el-form label-width="120px">
        <el-form-item label="分类名称" required>
          <el-input v-model="add_category.name" placeholder="请输入分类名称" clearable />
        </el-form-item>
        <el-form-item label="分类描述" required>
          <el-input v-model="add_category.description" placeholder="请输入分类描述" clearable />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="category_visible = false"> 取消 </el-button>
        <el-button type="primary" @click="add_category.id ? update_category():add_category_list()"> {{ add_category.id ? '修改' : '添加' }} </el-button>
      </template>
    </el-dialog>
  </div>

  <el-table :data="dessert_form" stripe style="width: 100%" @selection-change="val => selected = val.map(v => v.id)">
    <el-table-column type="selection" width="50" />
    <el-table-column prop="name" label="分类名称" />
    <el-table-column prop="description" label="描述" />
    <el-table-column label="操作" width="150">
      <template #default="{ row }">
        <el-button size="small" type="primary" @click="update_category_list(row)"> 修改 </el-button>
        <el-button size="small" type="danger" @click="handle_delete(row.id)"> 删除 </el-button>
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