<script setup>
import {add_category_list_, delete_category_list_, get_category_List_, update_category_list_} from '@/api/category.js'
import {onMounted, reactive, ref, watch} from "vue";
import {ElMessage, ElMessageBox} from "element-plus";

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
async function clear_query_form(){
  query_form.name = '';
  query_form.description = '';
  page_num.value = 1;
  await fetch_data();
}

// 种类显示
const dessert_form = ref([])
const page_num = ref(1)
const page_size = ref(10)
const total = ref(0)
const loading = ref(false)

// 获取种类列表
async function fetch_data(){
  loading.value = true
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
    total.value = res.data.total
    if(res.data.code !== 200) {
      ElMessage.info("请联系工作人员")
    }
  }
  catch(err){
    ElMessage.error(err)
  }
  finally {
    loading.value = false
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

//  清空查询表
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
    if(res.data.code === 200 ){
      ElMessage.success("新增成功")
    }
    else if(res.data.code === 500){
      ElMessage.error("添加失败")
    }
    else {
      ElMessage.error("请联系工作人员")
    }
    category_visible.value = false
  }
  catch(err){
    ElMessage.error(err)
    console.log(err)
  }
  await fetch_data();
}

// 删除分类
async function handle_delete(id){
  try{
    const res = await delete_category_list_(id)
    // console.log(res)
    if (res.data.code === 200) {
      ElMessage.success("删除成功");
    }
    else if(res.data.code === 400) {
      ElMessage.error("删除失败")
    }
    else {
      ElMessage.error("请联系工作人员")
    }
  }
  catch(err){
    ElMessage.error(err)
  }
  await fetch_data()
}

// 更新分类
async function update_category_list(row){
    add_category.name = row.name
    add_category.description = row.description
    add_category.id = row.id
    category_visible.value = true
    // category_visible.value = false
}

// 更新种类
async function update_category(){
  try {
    if (!add_category.name || !add_category.description) {
      ElMessage.error("请完善信息")
      return
    }
    const res = await update_category_list_(add_category)
    if(res.data.code === 200){
      ElMessage.success("修改成功")
    }
    else if(res.data.code === 400){
      ElMessage.error("修改失败")
    }
    else {
      ElMessage.error("请联系工作人员")
    }

    category_visible.value = false
    await fetch_data()
  } catch (err) {
    console.log(err)
    ElMessage.error("修改异常，请联系工作人员")
  }
}


//批量删除
async function Batch_delete(){
  try {
    await ElMessageBox.confirm(
        '确定要删除选中的分类吗？',
        '删除确认',
        { confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning' }
    )
    for (const value of selected.value){
      const temp_res = await delete_category_list_(value)
      if (temp_res.data.code === 200) {
        ElMessage.success("删除成功");
      }
      else if(temp_res.data.code === 400) {
        ElMessage.error("删除失败,批量删除终止")
        return
      }
      else {
        ElMessage.error("批量删除终止 ， 请联系工作人员")
        return
      }
    }
    ElMessage.success("批量删除成功")
    selected.value = []
    await fetch_data()
  }
  catch(err){
    ElMessage.error("批量删除失败， 请联系工作人人员")
    console.log(err.message)
  }
}


watch(category_visible, (newVal, oldVal) => {
  // console.log("变量变了！新值：", newVal)
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
    <div class="query-form">
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

    <div class="toolbar">
      <el-button type="danger" @click="Batch_delete">批量删除</el-button>
      <el-button type="primary" @click="category_visible = true"> 新增 </el-button>
    </div>

    <el-dialog
      class="dialog-form"
      :model-value="category_visible"
      @close="category_visible = false"
      :title="add_category.id ? '修改分类' : '新增分类'"
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

    <div class="table-container">
      <el-table :data="dessert_form" v-loading="loading" stripe style="width: 100%" @selection-change="val => selected = val.map(v => v.id)">
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
    </div>

    <div class="pagination-wrap">
      <el-pagination
          v-model:current-page="page_num"
          v-model:page-size="page_size"
          :total="total"
          :page-sizes="[5, 10, 20]"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="fetch_data"
          @current-change="fetch_data"
      />
    </div>
  </div>
</template>

<style scoped>
/* 分类管理页面专属样式（共享样式在 shared.css 中） */
</style>