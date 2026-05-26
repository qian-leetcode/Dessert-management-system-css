<script setup>

import {onMounted, reactive, ref, watch} from "vue";
import {
  add_purchase_record_form_,
  delete_purchase_record_form_,
  get_purchase_record_form,
  update_purchase_record_form_
} from "@/api/purchase_record.js";
import {get_user_list_} from "@/api/user.js";
import {get_material_list_} from "@/api/material.js";

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
  user_name: '',
})

const purchase_record_information_form = ref([])

async function get_purchase_record_information_form() {
  const params = {
    page_size: page_size.value,
    page_num: page_num.value,
    purchase_order_number: query_record_form.purchase_order_number,
    purchase_date: query_record_form.purchase_date,
    material_id: query_record_form.material_id,
    supplier_name: query_record_form.supplier_name,
    production_batch: query_record_form.production_batch,
    production_date: query_record_form.production_date,
    payment_status: query_record_form.payment_status,
    remark: query_record_form.remark,
    create_time: query_record_form.create_time,
    procuring_entity:query_record_form.procuring_entity,
    user_name:query_record_form.user_name,
  }
  console.log(params)
  const res = await get_purchase_record_form(params);
  // console.log(res)
  purchase_record_information_form.value = res.data.rows
  // console.log(purchase_record_information_form)
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
  query_record_form.procuring_entity= ''
  query_record_form.user_name = ''
}

// 采购弹窗
const purchase_visible = ref(false)

// 采购表单
const add_purchase_form = reactive({
  purchase_id: '',
  purchase_order_number: '',
  purchase_date: '',
  material_id: '',
  purchase_quantity: '',
  purchase_price: '',
  purchase_amount: '',
  supplier_name: '',
  production_batch: '',
  production_date: '',
  payment_status: 0,
  user_id: '',
  procuring_entity: '',
  remark: '',
  create_time:''
})

// 清空采购表
async function clear_add_purchase_form() {
  add_purchase_form.purchase_id= '',
  add_purchase_form.purchase_order_number= '',
  add_purchase_form.purchase_date= '',
  add_purchase_form.material_id= '',
  add_purchase_form.purchase_quantity= '',
  add_purchase_form.purchase_price= '',
  add_purchase_form.purchase_amount= '',
  add_purchase_form.supplier_name= '',
  add_purchase_form.production_batch= '',
  add_purchase_form.production_date= '',
  add_purchase_form.payment_status= 0,
  add_purchase_form.user_id= '',
  add_purchase_form.procuring_entity= '',
  add_purchase_form.remark= ''
  add_purchase_form.create_time = ''
}


watch(purchase_visible, (newVal, oldVal) => {
  // console.log("变量变了！新值：", newVal)
  if(newVal === false) {
    clear_add_purchase_form();
  }
})

// 新增
async function add_purchase_information(){
  try {
    const params = {
      purchase_order_number: add_purchase_form.purchase_order_number,
      purchase_date: add_purchase_form.purchase_date,
      material_id: add_purchase_form.material_id,
      purchase_quantity: add_purchase_form.purchase_quantity,
      purchase_price: add_purchase_form.purchase_price,
      purchase_amount: add_purchase_form.purchase_amount,
      supplier_name: add_purchase_form.supplier_name,
      production_batch: add_purchase_form.production_batch,
      production_date: add_purchase_form.production_date,
      payment_status: add_purchase_form.payment_status,
      user_id: add_purchase_form.user_id,
      procuring_entity: add_purchase_form.procuring_entity,
      remark: add_purchase_form.remark,
      create_time : add_purchase_form.create_time,
    }
    // console.log(add_purchase_form)
    await add_purchase_record_form_(add_purchase_form)
    await get_purchase_record_information_form();
    purchase_visible.value = false
  }
  catch (error) {
    console.log(error);
  }
}

// 修改
async function update_purchase_information(row) {
  purchase_visible.value = true
  // console.log(row)
  add_purchase_form.purchase_id = row.purchase_id
  add_purchase_form.purchase_order_number = row.purchase_order_number
  add_purchase_form.purchase_date = row.purchase_date
  add_purchase_form.material_id = row.material_id
  add_purchase_form.purchase_quantity = row.purchase_quantity
  add_purchase_form.purchase_price = row.purchase_price
  add_purchase_form.purchase_amount = row.purchase_amount
  add_purchase_form.supplier_name = row.supplier_name
  add_purchase_form.production_batch = row.production_batch
  add_purchase_form.production_date = row.production_date
  add_purchase_form.payment_status = row.payment_status
  add_purchase_form.user_id = row.user_id
  add_purchase_form.procuring_entity = row.procuring_entity
  add_purchase_form.remark = row.remark
  add_purchase_form.create_time = row.create_time
}

//修改
async function update_purchase() {
  try {
    await update_purchase_record_form_(add_purchase_form)
    await get_purchase_record_information_form()
    purchase_visible.value = false
  }
  catch (error) {
    console.log(error);
  }
}

// 删除
async function delete_purchase(id) {
  try {
    await delete_purchase_record_form_(id)

  }
  catch (error) {
    console.log(error);
  }
  await get_purchase_record_information_form()
}

const selected = ref([])

// 批量删除
async function batch_delete() {
  try {
    for (const value of selected.value) {
      await delete_purchase_record_form_(value)
    }
    selected.value = []
  }
  catch (error) {
    console.log(error);
  }
  await get_purchase_record_information_form()
}

// 原料id
const material_list_ = ref([])
async function get_material_list_id() {
  try {
    const res = await get_material_list_()
    material_list_.value = res.data
    // console.log(material_list_)
  }
  catch (error) {
    console.log(error);
  }
}

// 用户 id
const user_list_ = ref([])
async function get_user_list_id() {
  try {
    const res =await get_user_list_();
    // console.log(res)
    user_list_.value = res.data
  }
  catch (error) {
    console.log(error);
  }
}

onMounted(() => {
  get_purchase_record_information_form();
  get_material_list_id();
  get_user_list_id();
})

</script>

<template>
  <div>
    <!-- 采购记录查询表单（和你下面的格式完全一致） -->
    <el-form inline :model="query_record_form">
      <el-form-item label="采购订单号">
        <el-input v-model="query_record_form.purchase_order_number" placeholder="请输入采购订单号" style="width: 250px;" clearable/>
      </el-form-item>
      <el-form-item label="采购日期">
        <!-- 日期选择器，默认选择日期格式 -->
        <el-date-picker
            v-model="query_record_form.purchase_date"
            type="date"
            placeholder="请选择采购日期"
            format="YYYY-MM-DD"
            value-format="YYYY-MM-DD"
            style="width: 250px;"
        />
      </el-form-item>
      <el-form-item label="原材料ID" >
        <el-input v-model="query_record_form.material_id" placeholder="请输入原材料ID" style="width: 250px;" clearable/>
      </el-form-item>
      <el-form-item label="供应商名称">
        <el-input v-model="query_record_form.supplier_name" placeholder="请输入供应商名称" style="width: 250px;" clearable/>
      </el-form-item>
      <el-form-item label="生产批次">
        <el-input v-model="query_record_form.production_batch" placeholder="请输入生产批次" style="width: 250px;" clearable/>
      </el-form-item>
      <el-form-item label="生产日期">
        <!-- 日期选择器，默认选择日期格式 -->
        <el-date-picker
            v-model="query_record_form.purchase_date"
            type="date"
            placeholder="请选择生产日期"
            format="YYYY-MM-DD"
            value-format="YYYY-MM-DD"
            style="width: 250px;"
        />
<!--        <el-input v-model="query_record_form.production_date" placeholder="请选择生产日期" style="width: 250px;" clearable/>-->
      </el-form-item>
      <el-form-item label="付款状态">
        <el-input v-model="query_record_form.payment_status" placeholder="请输入付款状态" style="width: 250px;" clearable/>
      </el-form-item>
<!--      <el-form-item label="采购单位">-->
<!--        <el-input v-model="query_record_form.procuring_entity" placeholder="请输入采购单位" style="width: 250px;" clearable/>-->
<!--      </el-form-item>-->
      <el-form-item label="采购人">
          <el-select v-model="query_record_form.user_name" placeholder="选择采购人" style="width: 250px;">
            <el-option
                v-for="item in user_list_"
                :key="item.name"
                :label="item.name"
                :value="item.name"
            />
          </el-select>
      </el-form-item>
      <el-form-item label="备注">
        <el-input v-model="query_record_form.remark" placeholder="输入额外的备注" style="width: 250px;" clearable/>
      </el-form-item>
      <el-form-item label="创建时间">
        <!-- 日期选择器，默认选择日期格式 -->
        <el-date-picker
            v-model="query_record_form.purchase_date"
            type="date"
            placeholder="请选择创建时间"
            format="YYYY-MM-DD"
            value-format="YYYY-MM-DD"
            style="width: 250px;"
        />
<!--        <el-input v-model="query_record_form.create_time" placeholder="请选择创建时间" style="width: 250px;" clearable/>-->
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="get_purchase_record_information_form"> 查询 </el-button>
        <el-button type="primary" @click="clear_query_record_form"> 重置 </el-button>
      </el-form-item>
    </el-form>
  </div>
  <div>
    <el-button type="danger" @click="batch_delete">批量删除</el-button>
    <el-button type="primary" @click="purchase_visible = true">新增</el-button>
    <el-dialog
        :model-value="purchase_visible"
        @close="purchase_visible = false"
        :title="add_purchase_form.purchase_id ? '修改采购记录' : '新增采购记录'"
        width="800px"
    >
      <el-form
          :model="add_purchase_form"
          label-width="140px"
          label-position="right"
      >
        <!-- 采购单号 -->
        <el-form-item label="采购单号" required>
          <el-input
              v-model="add_purchase_form.purchase_order_number"
              placeholder="请输入采购单号"
              clearable
          />
        </el-form-item>

        <!-- 采购日期 -->
        <el-form-item label="采购日期" required>
          <el-date-picker
              v-model="add_purchase_form.purchase_date"
              type="date"
              placeholder="选择采购日期"
              format="YYYY-MM-DD"
              value-format="YYYY-MM-DD"
              style="width: 100%"
          />
        </el-form-item>

        <!-- 物料ID -->
        <el-form-item label="物料ID" required>
          <el-select v-model="add_purchase_form.material_id" placeholder="请选择原材料">
            <el-option
                v-for="item in material_list_"
                :key="item.material_id"
                :label="item.material_name"
                :value="item.material_id"
            />
          </el-select>
        </el-form-item>

        <!-- 采购数量 -->
        <el-form-item label="采购数量" required>
          <el-input
              v-model.number="add_purchase_form.purchase_quantity"
              type="number"
              placeholder="请输入采购数量"
              clearable
          />
        </el-form-item>

        <!-- 采购单价 -->
        <el-form-item label="采购单价" required>
          <el-input
              v-model.number="add_purchase_form.purchase_price"
              type="number"
              placeholder="请输入采购单价"
              clearable
          />
        </el-form-item>

        <!-- 采购金额 -->
        <el-form-item label="采购金额" required>
          <el-input
              v-model.number="add_purchase_form.purchase_amount"
              type="number"
              placeholder="系统自动计算或手动输入"
              clearable
          />
        </el-form-item>

        <!-- 供应商名称 -->
        <el-form-item label="供应商名称" required>
          <el-input
              v-model="add_purchase_form.supplier_name"
              placeholder="请输入供应商名称"
              clearable
          />
        </el-form-item>

        <!-- 生产批次 -->
        <el-form-item label="生产批次" required>
          <el-input
              v-model="add_purchase_form.production_batch"
              placeholder="请输入生产批次"
              clearable
          />
        </el-form-item>

        <!-- 生产日期 -->
        <el-form-item label="生产日期" required>
          <el-date-picker
              v-model="add_purchase_form.production_date"
              type="date"
              placeholder="选择生产日期"
              format="YYYY-MM-DD"
              value-format="YYYY-MM-DD"
              style="width: 100%"
          />
        </el-form-item>

        <!-- 支付状态 -->
        <el-form-item label="支付状态" required>
          <el-radio-group v-model="add_purchase_form.payment_status">
            <el-radio :value="2">已支付</el-radio>
            <el-radio :value="1">部分支付</el-radio>
            <el-radio :value="0">未支付</el-radio>
          </el-radio-group>
        </el-form-item>

        <!-- 用户ID/采购人ID -->
        <el-form-item label="采购人" required>
<!--          <el-input-->
<!--              v-model.number="add_purchase_form.user_id"-->
<!--              type="number"-->
<!--              placeholder="请输入采购人ID"-->
<!--              clearable-->
<!--          />-->
          <el-select v-model="add_purchase_form.user_id" placeholder="选择采购人">
            <el-option
                v-for="item in user_list_"
                :key="item.id"
                :label="item.name"
                :value="String(item.id)"
            />
          </el-select>
        </el-form-item>

        <!-- 采购单位 -->
        <el-form-item label="采购单位" required>
          <el-input
              v-model="add_purchase_form.supplier_name"
              placeholder="请输入采购单位"
              clearable
          />
        </el-form-item>

        <!-- 备注 -->
        <el-form-item label="备注" required>
          <el-input
              v-model="add_purchase_form.remark"
              type="textarea"
              :rows="3"
              placeholder="请输入备注信息"
              clearable
          />
        </el-form-item>
        <el-form-item label="创建时间" required>
          <el-date-picker
              v-model="add_purchase_form.create_time"
              type="date"
              placeholder="选择创建时间"
              format="YYYY-MM-DD"
              value-format="YYYY-MM-DD"
              style="width: 100%"
          />
        </el-form-item>
      </el-form>

      <template #footer>
        <el-button @click="purchase_visible = false">取消</el-button>
        <el-button
            type="primary"
            @click="add_purchase_form.purchase_id ? update_purchase() : add_purchase_information()"
        >
          {{ add_purchase_form.purchase_id ? '修改' : '添加' }}
        </el-button>
      </template>
    </el-dialog>
  </div>
  <div>
    <el-table :data="purchase_record_information_form" stripe style="width: 100%" @selection-change="val => selected = val.map(v=>v.purchase_id)">
      <el-table-column type="selection" width="50" />
      <el-table-column prop="purchase_id" label="采购ID" />
      <el-table-column prop="purchase_order_number" label="采购订单编号" />
      <el-table-column prop="purchase_date" label="采购日期" />
      <el-table-column prop="material_id" label="物料ID" />
      <el-table-column prop="purchase_quantity" label="采购数量" />
      <el-table-column prop="purchase_price" label="采购单价" />
      <el-table-column prop="purchase_amount" label="采购金额" />
      <el-table-column prop="supplier_name" label="供应商名称" />
      <el-table-column prop="production_batch" label="生产批次号" />
      <el-table-column prop="production_date" label="生产日期" />
      <el-table-column label="付款状态" width="100px">
        <template #default="scope">
          <el-tag
              :type="
              scope.row.payment_status == 1 ? 'warning' :
              scope.row.payment_status == 2 ? 'success' : 'danger'
      "
          >
            <template v-if="scope.row.payment_status == 1">部分支付</template>
            <template v-else-if="scope.row.payment_status == 2">已支付</template>
            <template v-else>未支付</template>
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="procuring_name" label="采购人" />

      <el-table-column prop="remark" label="备注" />
      <el-table-column prop="create_time" label="创建时间" />

      <!-- 操作按钮 -->
      <el-table-column label="操作" width="150">
        <template #default="{ row }">
          <el-button size="small" type="primary" @click="update_purchase_information(row)">修改</el-button>
          <el-button size="small" type="danger" @click="delete_purchase(row.purchase_id)">删除</el-button>
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
        @size-change="get_purchase_record_information_form"
        @current-change="get_purchase_record_information_form"
    />
  </div>

</template>

<style scoped>

</style>