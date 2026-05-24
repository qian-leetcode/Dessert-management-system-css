<script setup>

import {onMounted, reactive, ref} from "vue";
import {get_purchase_record_form} from "@/api/purchase_record.js";

const page_num = ref(1)
const page_size = ref(10)
const total = ref(0)

const query_record_form = reactive({
  purchase_order_number: '',
  purchase_date: '',
  material_id: '',
  supplier_name: '',
  production_batch: '',
  production_date: '',
  payment_status: '',
  procuring_entity: '',
  remark: '',
  create_time: '',
})

const purchase_record_information_form = ref([])

async function get_purchase_record_information_form() {
  const params = {
    page_size: page_size.value,
    page_num: page_num.value,
    purchase_order_number: '',
    purchase_date: '',
    material_id: '',
    supplier_name: '',
    production_batch: '',
    production_date: '',
    payment_status: '',
    remark: '',
    create_time: ''
  }
  const res = await get_purchase_record_form(params);
  console.log(res)
  purchase_record_information_form.value = res.data.rows
  console.log(purchase_record_information_form.value)
  total.value = res.data.total
}

function clear_query_record_form() {
  query_record_form.purchase_order_number = ''
  query_record_form.purchase_date = ''
  query_record_form.material_id = ''
  query_record_form.supplier_name = ''
  query_record_form.production_batch = ''
  query_record_form.production_date = ''
  query_record_form.payment_status = ''
  query_record_form.procuring_entity = ''
  query_record_form.remark = ''
  query_record_form.create_time = ''
}

onMounted(() => {
  get_purchase_record_information_form();
})

</script>

<template>
  <div>
    <!-- 采购记录查询表单（和你下面的格式完全一致） -->
    <el-form :model="query_record_form">
      <el-form-item label="采购订单号">
        <el-input v-model="query_record_form.purchase_order_number" placeholder="请输入采购订单号" clearable/>
      </el-form-item>
      <el-form-item label="采购日期">
        <!-- 日期选择器，默认选择日期格式 -->
        <el-date-picker
            v-model="query_record_form.purchase_date"
            type="date"
            placeholder="请选择采购日期"
            format="YYYY-MM-DD"
            value-format="YYYY-MM-DD"
        />
      </el-form-item>
      <el-form-item label="原材料ID">
        <el-input v-model="query_record_form.material_id" placeholder="请输入原材料ID" clearable/>
      </el-form-item>
      <el-form-item label="供应商名称">
        <el-input v-model="query_record_form.supplier_name" placeholder="请输入供应商名称" clearable/>
      </el-form-item>
      <el-form-item label="生产批次">
        <el-input v-model="query_record_form.production_batch" placeholder="请输入生产批次" clearable/>
      </el-form-item>
      <el-form-item label="生产日期">
        <el-input v-model="query_record_form.production_date" placeholder="请选择生产日期" clearable/>
      </el-form-item>
      <el-form-item label="付款状态">
        <el-input v-model="query_record_form.payment_status" placeholder="请输入付款状态" clearable/>
      </el-form-item>
      <el-form-item label="采购单位">
        <el-input v-model="query_record_form.procuring_entity" placeholder="请输入采购单位" clearable/>
      </el-form-item>
      <el-form-item label="备注">
        <el-input v-model="query_record_form.remark" placeholder="输入额外的备注" clearable/>
      </el-form-item>
      <el-form-item label="创建时间">
        <el-input v-model="query_record_form.create_time" placeholder="请选择创建时间" clearable/>
      </el-form-item>
      <el-form-item>
        <el-button @click="get_purchase_record_information_form"> 查询 </el-button>
        <el-button @click="clear_query_record_form"> 重置 </el-button>
      </el-form-item>
    </el-form>
  </div>
  <div>
    <el-button>批量删除</el-button>
    <el-button>新增</el-button>
  </div>
  <div>
    <el-table :data="purchase_record_information_form" stripe style="width: 100%">
      <el-table-column type="selection" width="50" />
      <el-table-column prop="purchase_id" label="采购ID" />
      <el-table-column prop="purchase_order_number" label="采购订单编号" />
      <el-table-column prop="purchase_date" label="采购日期" />
      <el-table-column prop="material_id" label="物料ID" />
      <el-table-column prop="material_name" label="物料名称" />
      <el-table-column prop="purchase_quantity" label="采购数量" />
      <el-table-column prop="purchase_price" label="采购单价" />
      <el-table-column prop="purchase_amount" label="采购金额" />
      <el-table-column prop="supplier_name" label="供应商名称" />
      <el-table-column prop="production_batch" label="生产批次号" />
      <el-table-column prop="production_date" label="生产日期" />
      <el-table-column prop="payment_status" label="付款状态" />
      <el-table-column prop="procuring_name" label="采购人" />
      <el-table-column prop="remark" label="备注" />
      <el-table-column prop="create_time" label="创建时间" />

      <!-- 操作按钮 -->
      <el-table-column label="操作" width="150">
        <template #default="{ rows }">
          <el-button size="small" type="primary">修改</el-button>
          <el-button size="small" type="danger">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>

</template>

<style scoped>

</style>