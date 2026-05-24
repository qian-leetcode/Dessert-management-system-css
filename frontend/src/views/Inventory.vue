<script setup>

import {onMounted, reactive, ref} from "vue";
import {get_inventory_information} from "@/api/inventory.js";

const page_num = ref(1)
const page_size = ref(5)
const total = ref(0)

// 查询库存表
const query_inventory_form = reactive({
  inventory_id: "",
  material_id: "",
  latest_purchase_date: "",

})

const inventory_information_form = ref([])

async function query_inventory_information() {
  const params = {
    page_size: page_size.value,
    page_num: page_num.value,
    inventory_id: query_inventory_form.inventory_id,
    material_id: query_inventory_form.material_id,
    latest_purchase_date: query_inventory_form.latest_purchase_date,
  }
  console.log(params.page_num)
  console.log(params.page_size)
  const res = await get_inventory_information(params);
  console.log(res)
  inventory_information_form.value = res.data.rows
  total.value = res.data.total
}

onMounted(() => {
  query_inventory_information();
})

</script>

<template>
  <div>
    <el-form  :model="query_inventory_form">

      <el-form-item label="库存ID" >
        <el-input v-model="query_inventory_form.inventory_id" placeholder="请输入库存ID" clearable/>
      </el-form-item>
      <el-form-item label="原料ID" >
        <el-input v-model="query_inventory_form.inventory_id" placeholder="请输入原料ID" clearable/>
      </el-form-item>
      <el-form-item label="最后采购时间">
        <!-- 日期选择器，默认选择日期格式 -->
        <el-date-picker
            v-model="query_inventory_form.latest_purchase_date"
            type="date"
            placeholder="请选择最后采购时间"
            format="YYYY-MM-DD"
            value-format="YYYY-MM-DD"
        />
      </el-form-item>
      <el-form-item>
        <el-button @click="query_inventory_information"> 查询 </el-button>
        <el-button @click="clear_material_form"> 重置 </el-button>
      </el-form-item>
    </el-form>
  </div>
  <div>
    <el-button>批量删除</el-button>
    <el-button>新增</el-button>
  </div>
  <div>
    <el-table :data="inventory_information_form">
      <el-table-column type="selection" />
      <el-table-column prop="inventory_id" label="库存ID" />
      <el-table-column prop="material_id" label="物料ID" />
      <el-table-column prop="material_name" label="原材料名称" />
      <el-table-column prop="current_inventory_level" label="当前库存数量" />
      <el-table-column prop="safety_stock_quantity" label="安全库存数量" />
      <el-table-column prop="last_purchase_time" label="最后采购时间" />
      <el-table-column label="操作" width="150">
        <template #default="{rows}">
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
        @size-change="query_inventory_information"
        @current-change="query_inventory_information"
    />
  </div>
</template>

<style scoped>

</style>