<script setup>

import {onMounted, reactive, ref, watch} from "vue";
import {
  add_inventory_information_,
  delete_inventory_information_, get_inventory_information, update_inventory_information_
} from "@/api/inventory.js";
import {get_material_list_} from "@/api/material.js";

const page_num = ref(1)
const page_size = ref(5)
const total = ref(0)

// 查询库存表
const query_inventory_form = reactive({
  inventory_id: "",
  material_id: "",
  latest_purchase_date: "",
  material_name: "",
})

// 获取原料相关信息
const material_category = ref([])

async function get_material_categories() {
  try {
    const res = await get_material_list_()
    // console.log(res)
    material_category.value = res.data
    // console.log(material_category)
  }
  catch (error) {
    console.log(error)
  }
}

const inventory_information_form = ref([])

// 查询
async function query_inventory_information() {
  const params = {
    page_size: page_size.value,
    page_num: page_num.value,
    inventory_id: query_inventory_form.inventory_id,
    material_name: query_inventory_form.material_name,
    latest_purchase_date: query_inventory_form.latest_purchase_date,
  }
  // console.log(params.page_num)
  // console.log(params.page_size)
  const res = await get_inventory_information(params);
  console.log(res)
  inventory_information_form.value = res.data.rows
  // console.log(inventory_information_form)
  total.value = res.data.total
}

// 清空查询表
async function clear_inventory_information() {
  query_inventory_form.inventory_id = "";
  query_inventory_form.material_id = "";
  query_inventory_form.latest_purchase_date = "";
  query_inventory_form.material_name = "";
  await query_inventory_information();
}

// 库存弹窗显示
const inventory_visible = ref(false)

// 库存表单
const add_inventory_form = reactive({
  inventory_id: '',
  material_id: '',
  current_inventory_level: '',
  safety_stock_quantity: '',
  last_purchase_time: ''
})

async function clear_add_inventory_form() {
  add_inventory_form.inventory_id = ''
  add_inventory_form.material_id = ''
  add_inventory_form.current_inventory_level = ''
  add_inventory_form.safety_stock_quantity = ''
  add_inventory_form.last_purchase_time = ''
}

// 添加
async function add_inventory_information(){
  try {
    if (
        add_inventory_form.material_id === '' ||
        add_inventory_form.current_inventory_level === '' ||
        add_inventory_form.safety_stock_quantity === '' ||
        add_inventory_form.last_purchase_time === ''
    ) {
      return
    }
    await add_inventory_information_(add_inventory_form)
    console.log(add_inventory_form)
    await query_inventory_information()
    inventory_visible.value = false
    add_inventory_form.inventory_id = ''
    add_inventory_form.material_id = ''
    add_inventory_form.last_purchase_time = ''
    add_inventory_form.safety_stock_quantity = ''
    add_inventory_form.current_inventory_level = ''
  }
  catch (error) {
    console.log(error);
  }
}

// 修改
async function update_inventory_information(row){
  // console.log(row)
  inventory_visible.value = true
  add_inventory_form.inventory_id = row.inventory_id
  add_inventory_form.material_id = row.material_id
  add_inventory_form.current_inventory_level = row.current_inventory_level
  add_inventory_form.safety_stock_quantity = row.safety_stock_quantity
  add_inventory_form.last_purchase_time = row.last_purchase_time
}

// 更新
async function update_inventory(){
  try {
    if (
        add_inventory_form.inventory_id === '' ||
        add_inventory_form.material_id === '' ||
        add_inventory_form.current_inventory_level === '' ||
        add_inventory_form.safety_stock_quantity === '' ||
        add_inventory_form.last_purchase_time === ''
    ) {
      return
    }
    await update_inventory_information_(add_inventory_form)

    inventory_visible.value = false
  }
  catch (error) {
    console.log(error);
  }
  await query_inventory_information();
}

// 删除
async function delete_inventory(id) {
  try {
    await delete_inventory_information_(id);
  }
  catch (error) {
    console.log(error);
  }
  await query_inventory_information()
}

// 批量删除
const selected = ref([])

async function batch_delete() {
  try {
    for (const value of selected.value){
      await delete_inventory_information_(value)
    }
    selected.value = []
  }
  catch (error) {
    console.log(error);
  }
  await query_inventory_information();
}

watch(inventory_visible, (newVal, oldVal) => {
  console.log("变量变了！新值：", newVal)
  if(newVal === false) {
    clear_add_inventory_form()
  }
})

onMounted(() => {
  query_inventory_information();
  get_material_categories();
})

</script>

<template>
  <div>
    <div class="query-form">
      <el-form inline :model="query_inventory_form">
        <el-form-item label="原料" >
          <el-select v-model="query_inventory_form.material_name" placeholder="请选择原材料" style="width: 250px;">
            <el-option v-for="item in material_category" :key="item.material_name" :label="item.material_name" :value="item.material_name" />
          </el-select>
        </el-form-item>
        <el-form-item label="最后采购时间">
          <el-date-picker v-model="query_inventory_form.latest_purchase_date" type="date" placeholder="请选择最后采购时间" format="YYYY-MM-DD" value-format="YYYY-MM-DD" style="width: 250px;" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="query_inventory_information"> 查询 </el-button>
          <el-button type="primary" @click="clear_inventory_information"> 重置 </el-button>
        </el-form-item>
      </el-form>
    </div>

    <div class="toolbar">
      <el-button type="danger" @click="batch_delete">批量删除</el-button>
      <el-button type="primary" @click="inventory_visible = true">新增</el-button>
    </div>

    <el-dialog
        class="dialog-form"
        :model-value="inventory_visible"
        @close="inventory_visible = false"
        :title="add_inventory_form.inventory_id ? '修改库存' : '新增库存'"
        width="700px"
    >
      <el-form :model="add_inventory_form" label-width="130px" label-position="right">
        <el-form-item label="原材料ID" required>
          <el-select v-model="add_inventory_form.material_id" placeholder="请选择原材料">
            <el-option v-for="item in material_category" :key="item.material_id" :label="item.material_name" :value="item.material_id" />
          </el-select>
        </el-form-item>
        <el-form-item label="当前库存" required>
          <el-input v-model.number="add_inventory_form.current_inventory_level" type="number" placeholder="请输入当前库存" clearable />
        </el-form-item>
        <el-form-item label="安全库存" required>
          <el-input v-model.number="add_inventory_form.safety_stock_quantity" type="number" placeholder="请输入安全库存" clearable />
        </el-form-item>
        <el-form-item label="最后采购时间" required>
          <el-date-picker v-model="add_inventory_form.last_purchase_time" type="date" placeholder="选择最后采购日期" style="width: 100%" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="inventory_visible = false">取消</el-button>
        <el-button type="primary" @click="add_inventory_form.inventory_id ? update_inventory() : add_inventory_information()">
          {{ add_inventory_form.inventory_id ? '修改' : '添加' }}
        </el-button>
      </template>
    </el-dialog>

    <div class="table-container">
      <el-table :data="inventory_information_form" stripe @selection-change="val => selected = val.map(v=>v.inventory_id)">
        <el-table-column type="selection" />
        <el-table-column prop="material_name" label="原材料名称" />
        <el-table-column prop="current_inventory_level" label="当前库存数量" />
        <el-table-column prop="safety_stock_quantity" label="安全库存数量" />
        <el-table-column prop="last_purchase_time" label="最后采购时间" />
        <el-table-column label="操作" width="150">
          <template #default="{row}">
            <el-button size="small" type="primary" @click="update_inventory_information(row)"> 修改 </el-button>
            <el-button size="small" type="danger" @click="delete_inventory(row.inventory_id)"> 删除 </el-button>
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
          @size-change="query_inventory_information"
          @current-change="query_inventory_information"
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