<script setup>
import {onMounted, reactive, ref, watch} from "vue";
import {ElMessage} from "element-plus";
import {get_category_list_name_, get_category_lists} from "@/api/category.js";
import {add_dessert_list_, delete_dessert_list_, get_dessert_list_, update_dessert_list_} from "@/api/dessert.js";

//功能查询

//查询表
const query_form = reactive({
  dessert_name:'',
  dessert_description:'',
  dessert_category:'',
  dessert_min_price:'',
  dessert_max_price:'',
})

// 分类页管理
const category_list = ref([])
// const dessert_list = ref([])

// 获取种类名称列表
async function fetch_category_list() {
  try{
    const res = await get_category_list_name_()
    category_list.value = res.data
  }
  catch(error){
    ElMessage.error(error.message);
  }
}

// 页码管理
const page_num = ref(1)
const page_size = ref(5)
const total =  ref(0)

// 甜品信息表
const dessert_form = ref([])


// 查询甜品信息
async function query_form_dessert() {
  try{
    const params = {
      dessert_name: query_form.dessert_name,
      dessert_description: query_form.dessert_description,
      dessert_category: query_form.dessert_category,
      dessert_min_price: query_form.dessert_min_price === '' ? 0.0 : parseFloat(query_form.dessert_min_price),
      dessert_max_price: query_form.dessert_max_price === '' ? 999999.0 : parseFloat(query_form.dessert_max_price),
      page_num: page_num.value,
      page_size: page_size.value,
    }
    const res = await get_dessert_list_(params)
    dessert_form.value = res.data.rows
    // console.log(dessert_form)
    total.value = res.data.total
  }
  catch(error){
    ElMessage.error(error.message);
  }
}


// 清空查询条件
async function clear_query_form(){
  query_form.dessert_name  =''
  query_form.dessert_description  =''
  query_form.dessert_category  = ''
  query_form.dessert_min_price  =''
  query_form.dessert_max_price  =''
  await query_form_dessert();
}

// 新增
const dessert_visible = ref(false)

//删除
const selected = ref([])
const add_dessert_form = reactive({
  id:'',
  name:'',
  photo_url:'',
  price:'',
  description:'',
  release_date:'',
  cat_id:'',
  dessert_status:'',
  dessert_number:''
})

async function clear_add_dessert_form(){
  add_dessert_form.id = ''
  add_dessert_form.name = ''
  add_dessert_form.photo_url = ''
  add_dessert_form.price = ''
  add_dessert_form.description = ''
  add_dessert_form.release_date = ''
  add_dessert_form.cat_id = ''
  add_dessert_form.dessert_status = ''
  add_dessert_form.dessert_number = ''
}

//添加
async function add_dessert_information(){
  try{
    if (
        add_dessert_form.name === '' ||
        add_dessert_form.photo_url === '' ||
        add_dessert_form.price === '' ||
        add_dessert_form.description === '' ||
        add_dessert_form.release_date === '' ||
        add_dessert_form.cat_id === '' ||
        add_dessert_form.dessert_status === '' ||
        add_dessert_form.dessert_number === ''
    ) {
      return; // 有空值，直接返回
    }
    await add_dessert_list_(add_dessert_form);
    // console.log(res)
    await fetch_category_list()
    await query_form_dessert();
    dessert_visible.value = false
  }
  catch (e){
    ElMessage.error(e);
  }
}

// 写入表格
async function update_dessert_information(row){
  // console.log(row)
  dessert_visible.value = true
  add_dessert_form.id = row.id
  add_dessert_form.name =  row.name
  add_dessert_form.photo_url = row.photo_url
  add_dessert_form.price = row.price
  add_dessert_form.description = row.description
  add_dessert_form.release_date = row.release_date
  for (const it of category_cat_id.value) {
    if (it.name === row.dessert_category) {
      add_dessert_form.cat_id = it.id;
      break;
    }
  }
  add_dessert_form.dessert_status = row.dessert_status
  add_dessert_form.dessert_number = row.dessert_number
  console.log(add_dessert_form)
}


// 更新代码
async function update_dessert_(){
  try{
    if (
        add_dessert_form.id === '' ||
        add_dessert_form.name === '' ||
        add_dessert_form.photo_url === '' ||
        add_dessert_form.price === '' ||
        add_dessert_form.description === '' ||
        add_dessert_form.release_date === '' ||
        add_dessert_form.cat_id === '' ||
        add_dessert_form.dessert_status === '' ||
        add_dessert_form.dessert_number === ''
    ) {
      return; // 有空值，直接返回
    }
    await update_dessert_list_(add_dessert_form);
    ElMessage.success("修改成功")
    dessert_visible.value = false
    await fetch_category_list()
    await query_form_dessert();
  }
  catch(error){
    dessert_visible.value = false
    ElMessage.error(error.message);
  }
}

const category_cat_id = ref([])

// 解析cat_id
async function get_set_cat_id(){
  try{
    const res = await get_category_lists()
    category_cat_id.value = res.data
    // console.log(category_cat_id)
  }
  catch(error){
    ElMessage.error(error.message);
  }
}

// 删除甜品
async function delete_dessert_information(id){
  try {
    const res = await delete_dessert_list_(id);
    console.log(res)
    await fetch_category_list();
    await query_form_dessert();
  }
  catch(error){
    ElMessage.error(error.message);
  }
}

// 批量删除
async function Batch_delete(){
  try {
    for (const value of selected.value){
      await delete_dessert_information(value);
    }
    ElMessage.success("批量删除成功")
    selected.value = []
    await query_form_dessert();
    // await fetch_data()
  }
  catch(error){
    ElMessage.error(error.message);
  }
}

watch(dessert_visible, (newVal, oldVal) => {
  console.log("变量变了！新值：", newVal)
  if(newVal === false) {
    clear_add_dessert_form()
  }
})


onMounted(() => {
  fetch_category_list();
  query_form_dessert();
  get_set_cat_id();
})

</script>

<template>
  <div>
    <div class="query-form">
      <el-form :inline="true" :model="query_form">
          <el-form-item label="分类名称">
            <el-input v-model="query_form.dessert_name" placeholder="输入名称" clearable style="width: 250px;"/>
          </el-form-item>
          <el-form-item label="描述">
            <el-input v-model="query_form.dessert_description" placeholder="请输入描述" clearable style="width: 250px;"/>
          </el-form-item>
          <el-form-item label="分类">
            <el-select v-model="query_form.dessert_category" placeholder="选择分类" style="width: 250px;">
              <el-option
                  v-for="item in category_cat_id"
                  :key="item.name"
                  :label="item.name"
                  :value="item.name"
              />
            </el-select>
          </el-form-item>
        <br style="margin: 10px 0;">
        <el-form-item label="价格区间">
          <el-input type="number" v-model="query_form.dessert_min_price" placeholder="最低价" style="width: 150px;"/>
          <span style="margin: 0 5px;">~</span>
          <el-input type="number" v-model="query_form.dessert_max_price" placeholder="最高价" style="width: 150px;"/>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="query_form_dessert"> 查询 </el-button>
          <el-button type="primary" @click="clear_query_form"> 重置 </el-button>
        </el-form-item>
      </el-form>
    </div>

    <div class="toolbar">
      <el-button type="danger" @click="Batch_delete">批量删除</el-button>
      <el-button type="primary" @click="dessert_visible = true">新增</el-button>
    </div>

    <el-dialog
        class="dialog-form"
        :model-value="dessert_visible"
        @close="dessert_visible = false"
        :title="add_dessert_form.id ? '修改甜品' : '新增甜品'"
        width="600px"
    >
      <el-form
          :model="add_dessert_form"
          label-width="100px"
          label-position="right"
      >
        <el-form-item label="甜品名称" required>
          <el-input v-model="add_dessert_form.name" placeholder="请输入甜品名称" clearable />
        </el-form-item>
        <el-form-item label="图片地址" required>
          <el-input v-model="add_dessert_form.photo_url" placeholder="请输入图片URL" clearable />
        </el-form-item>
        <el-form-item label="价格" required>
          <el-input v-model.number="add_dessert_form.price" type="number" placeholder="请输入价格" clearable />
        </el-form-item>
        <el-form-item label="描述" required>
          <el-input v-model="add_dessert_form.description" type="textarea" :rows="3" placeholder="请输入甜品描述" clearable />
        </el-form-item>
        <el-form-item label="上架日期" required>
          <el-date-picker v-model="add_dessert_form.release_date" type="date" placeholder="选择上架日期" style="width: 100%" />
        </el-form-item>
        <el-form-item label="所属分类" required>
          <el-select v-model="add_dessert_form.cat_id" placeholder="请选择分类" clearable style="width: 100%">
            <el-option v-for="item in category_cat_id" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="状态" required>
          <el-radio-group v-model="add_dessert_form.dessert_status">
            <el-radio :value="1">上架</el-radio>
            <el-radio :value="0">下架</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="库存数量" required>
          <el-input v-model.number="add_dessert_form.dessert_number" type="number" placeholder="请输入库存数量" clearable />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dessert_visible = false">取消</el-button>
        <el-button type="primary" @click="add_dessert_form.id ? update_dessert_() : add_dessert_information()">
          {{ add_dessert_form.id ? '修改' : '添加' }}
        </el-button>
      </template>
    </el-dialog>

    <div class="table-container">
      <el-table :data="dessert_form" stripe style="width: 100%" @selection-change="val => selected = val.map(v=>v.id)">
        <el-table-column type="selection" width="50" />
        <el-table-column prop="photo_url" label="图片" >
          <template #default="{ row }">
            <el-image v-if="row.photo_url" :src="row.photo_url" style="width: 60px; height: 60px; border-radius: 8px;" fit="cover" />
            <span v-else>无图片</span>
          </template>
        </el-table-column>
        <el-table-column prop="name" label="名称" />
        <el-table-column prop="dessert_category" label="所属分类" />
        <el-table-column prop="price" label="单价(￥)" />
        <el-table-column prop="description" label="描述" />
        <el-table-column prop="release_date" label="发布日" />
        <el-table-column label="上架状态" >
          <template #default="scope">
            <el-tag :type="scope.row.dessert_status === 1 ? 'success' : 'info'">
              {{ scope.row.dessert_status === 1 ? "已上架" : "已下架" }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="dessert_number" label="商品数量" />
        <el-table-column label="操作" width="200">
          <template #default="{row}">
            <el-button size="small" type="primary" @click="update_dessert_information(row)"> 修改 </el-button>
            <el-button size="small" type="danger" @click="delete_dessert_information(row.id)"> 删除 </el-button>
            <el-button size="small" :type="row.dessert_status === 1 ? 'warning' : 'success'" @click="row.dessert_status = row.dessert_status === 1 ? 0 : 1">
              {{row.dessert_status == 0 ? '上架' : '下架'}} </el-button>
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
          @size-change="query_form_dessert"
          @current-change="query_form_dessert"
      />
    </div>
  </div>
</template>

<style scoped>
.query-form { margin-bottom: 20px; }
.query-form :deep(.el-form-item) { margin-bottom: 0; }
.query-form :deep(.el-input__wrapper),
.query-form :deep(.el-select .el-input__wrapper) {
  border-radius: 8px; transition: box-shadow 0.3s;
}
.query-form :deep(.el-input__wrapper:hover),
.query-form :deep(.el-select .el-input__wrapper:hover) {
  box-shadow: 0 0 0 1px #6c5ce7 inset;
}

.toolbar {
  display: flex; gap: 12px; margin-bottom: 16px;
}
.toolbar .el-button { border-radius: 8px; transition: all 0.25s; }
.toolbar .el-button:hover { transform: translateY(-1px); }
.toolbar .el-button--primary {
  background: linear-gradient(135deg, #6c5ce7, #a29bfe); border: none;
}
.toolbar .el-button--primary:hover { box-shadow: 0 4px 12px rgba(108,92,231,0.3); }

.table-container :deep(.el-table) {
  border-radius: 10px; overflow: hidden; box-shadow: 0 1px 6px rgba(0,0,0,0.04);
}
.table-container :deep(.el-table th.el-table__cell) {
  background: linear-gradient(135deg, #f8f7ff, #f0eeff);
  color: #6c5ce7; font-weight: 600; font-size: 13px;
}
.table-container :deep(.el-table__row) { transition: background 0.2s; }
.table-container :deep(.el-table__row:hover) { background: #faf9ff !important; }
.table-container :deep(.el-table--striped .el-table__body tr.el-table__row--striped) { background: #fafafa; }
.table-container :deep(.el-button--small) { border-radius: 6px; transition: all 0.2s; }
.table-container :deep(.el-button--small:hover) { transform: translateY(-1px); }

.pagination-wrap {
  margin-top: 18px; display: flex; justify-content: center;
}
.pagination-wrap :deep(.el-pagination) { font-weight: 500; }
.pagination-wrap :deep(.el-pager li) {
  border-radius: 6px; margin: 0 2px; transition: all 0.2s;
}
.pagination-wrap :deep(.el-pager li.active) {
  background: linear-gradient(135deg, #6c5ce7, #a29bfe); color: #fff;
}
.pagination-wrap :deep(.el-pager li:not(.active):hover) { color: #6c5ce7; }

.dialog-form :deep(.el-dialog__header) {
  background: linear-gradient(135deg, #6c5ce7, #a29bfe);
  margin: 0; padding: 18px 20px; border-radius: 4px 4px 0 0;
}
.dialog-form :deep(.el-dialog__title) { color: #fff; font-weight: 600; }
.dialog-form :deep(.el-dialog__headerbtn .el-dialog__close) { color: rgba(255,255,255,0.7); }
.dialog-form :deep(.el-dialog__headerbtn:hover .el-dialog__close) { color: #fff; }
.dialog-form :deep(.el-dialog__body) { padding: 24px 20px; }
.dialog-form :deep(.el-input__wrapper) { border-radius: 8px; }
</style>