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
  await fetch_data();
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
    total.value = res.data.total
    if(res.data.code !== 200) {
      ElMessage.info("请联系工作人员")
    }
    // console.log(res)
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
</template>

<style scoped>
.query-form { margin-bottom: 20px; }
.query-form :deep(.el-form-item) { margin-bottom: 0; }
.query-form :deep(.el-input__wrapper) {
  border-radius: 8px;
  transition: box-shadow 0.3s;
}
.query-form :deep(.el-input__wrapper:hover) {
  box-shadow: 0 0 0 1px #6c5ce7 inset;
}

.toolbar {
  display: flex;
  gap: 12px;
  margin-bottom: 16px;
}
.toolbar .el-button { border-radius: 8px; transition: all 0.25s; }
.toolbar .el-button:hover { transform: translateY(-1px); }
.toolbar .el-button--primary {
  background: linear-gradient(135deg, #6c5ce7, #a29bfe);
  border: none;
}
.toolbar .el-button--primary:hover {
  box-shadow: 0 4px 12px rgba(108,92,231,0.3);
}

.table-container :deep(.el-table) {
  border-radius: 10px;
  overflow: hidden;
  box-shadow: 0 1px 6px rgba(0,0,0,0.04);
}
.table-container :deep(.el-table th.el-table__cell) {
  background: linear-gradient(135deg, #f8f7ff, #f0eeff);
  color: #6c5ce7;
  font-weight: 600;
  font-size: 13px;
}
.table-container :deep(.el-table__row) {
  transition: background 0.2s;
}
.table-container :deep(.el-table__row:hover) {
  background: #faf9ff !important;
}
.table-container :deep(.el-table--striped .el-table__body tr.el-table__row--striped) {
  background: #fafafa;
}
.table-container :deep(.el-button--small) {
  border-radius: 6px;
  transition: all 0.2s;
}
.table-container :deep(.el-button--small:hover) { transform: translateY(-1px); }

.pagination-wrap {
  margin-top: 18px;
  display: flex;
  justify-content: center;
}
.pagination-wrap :deep(.el-pagination) { font-weight: 500; }
.pagination-wrap :deep(.el-pager li) {
  border-radius: 6px;
  margin: 0 2px;
  transition: all 0.2s;
}
.pagination-wrap :deep(.el-pager li.active) {
  background: linear-gradient(135deg, #6c5ce7, #a29bfe);
  color: #fff;
}
.pagination-wrap :deep(.el-pager li:not(.active):hover) {
  color: #6c5ce7;
}

.dialog-form :deep(.el-dialog__header) {
  background: linear-gradient(135deg, #6c5ce7, #a29bfe);
  margin: 0;
  padding: 18px 20px;
  border-radius: 4px 4px 0 0;
}
.dialog-form :deep(.el-dialog__title) { color: #fff; font-weight: 600; }
.dialog-form :deep(.el-dialog__headerbtn .el-dialog__close) { color: rgba(255,255,255,0.7); }
.dialog-form :deep(.el-dialog__headerbtn:hover .el-dialog__close) { color: #fff; }
.dialog-form :deep(.el-dialog__body) { padding: 24px 20px; }
.dialog-form :deep(.el-input__wrapper) { border-radius: 8px; }
</style>