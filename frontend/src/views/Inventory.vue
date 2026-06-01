<script setup>

import {onMounted, reactive, ref, watch} from "vue";
import {
  add_inventory_information_,
  delete_inventory_information_, get_inventory_information, update_inventory_information_
} from "@/api/inventory.js";
import {get_material_list_} from "@/api/material.js";
import {ElMessage, ElMessageBox} from "element-plus";

const page_num = ref(1)
const page_size = ref(5)
const total = ref(0)
const loading = ref(false)

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
    material_category.value = res.data
  }
  catch (error) {
    ElMessage.error("获取原料列表失败，请联系工作人员");
  }
}

const inventory_information_form = ref([])

// 查询
async function query_inventory_information() {
  loading.value = true
  try {
    const params = {
      page_size: page_size.value,
      page_num: page_num.value,
      inventory_id: query_inventory_form.inventory_id,
      material_name: query_inventory_form.material_name,
      latest_purchase_date: query_inventory_form.latest_purchase_date,
    }
    const res = await get_inventory_information(params);
    if(res.data.code === 200){
      inventory_information_form.value = res.data.rows
      total.value = res.data.total
    }
    else {
      ElMessage.error("获取库存信息失败，请联系工作人员")
    }
  }
  catch (error) {
    ElMessage.error(error);
  }
  finally {
    loading.value = false
  }
}

// 清空查询表
async function clear_inventory_information() {
  query_inventory_form.inventory_id = "";
  query_inventory_form.material_id = "";
  query_inventory_form.latest_purchase_date = "";
  query_inventory_form.material_name = "";
  page_num.value = 1
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
      ElMessage.error("请完善信息")
      return
    }
    const res = await add_inventory_information_(add_inventory_form)
    if(res.data.code === 200){
      ElMessage.success("新增成功")
    }
    else if(res.data.code === 400){
      ElMessage.error("新增失败")
    }
    else {
      ElMessage.error("新增异常，请联系工作人员")
    }
    await query_inventory_information()
    inventory_visible.value = false
  }
  catch (error) {
    ElMessage.error("新增异常，请联系工作人员");
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
      ElMessage.error("请完善信息")
      return
    }
    const res = await update_inventory_information_(add_inventory_form)
    if(res.data.code === 200){
      ElMessage.success("修改成功")
    }
    else if(res.data.code === 400){
      ElMessage.error("修改失败")
    }
    else {
      ElMessage.error("修改异常，请联系工作人员")
    }
    inventory_visible.value = false
  }
  catch (error) {
    ElMessage.error("修改异常，请联系工作人员");
  }
  await query_inventory_information();
}

// 删除
async function delete_inventory(id) {
  try {
    const res = await delete_inventory_information_(id);
    if(res.data.code === 200){
      ElMessage.success("删除成功")
    }
    else if(res.data.code === 400){
      ElMessage.error("删除失败")
    }
    else {
      ElMessage.error("删除异常，请联系工作人员")
    }
  }
  catch (error) {
    ElMessage.error("删除异常，请联系工作人员");
  }
  await query_inventory_information()
}

// 批量删除
const selected = ref([])

async function batch_delete() {
  try {
    await ElMessageBox.confirm(
        '确定要删除选中的库存记录吗？',
        '删除确认',
        { confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning' }
    )
    for (const value of selected.value){
      const res = await delete_inventory_information_(value)
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
  }
  catch (error) {
    ElMessage.error("批量删除失败，请联系工作人员");
  }
  await query_inventory_information();
}

watch(inventory_visible, (newVal, oldVal) => {
  // console.log("变量变了！新值：", newVal)
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
      <el-table :data="inventory_information_form" v-loading="loading" stripe @selection-change="val => selected = val.map(v=>v.inventory_id)">
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
/* Inventory - 专属样式（共享样式在 shared.css） */
</style>