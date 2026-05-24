<script setup>
import {onMounted, reactive, ref} from "vue";
import {get_query_list} from "@/api/material.js";

const page_num = ref(1)
const page_size = ref(10)

// 原材料信息表
const query_material_form = reactive({
  page_num: page_num.value,
  page_size:page_size.value,
  material_code:'',
  material_name:'',
  material_category:'',
  material_specification:'',
  material_unit:'',
  material_shelf_life_days:'',
  material_storage_condition:'',
  material_remark:''
})


// 发送原材料查询表
async function get_material_form() {
  const params = {
    // 原材料查询属性
    page_num: page_num.value,
    page_size: page_size.value,
    material_code: query_material_form.material_code,
    material_name: query_material_form.material_name,
    material_category: query_material_form.material_category,
    material_shelf_life_days: query_material_form.material_shelf_life_days,
    material_storage_condition: query_material_form.material_storage_condition,
    material_remark: query_material_form.material_remark,
  }
  const res = await get_query_list(params);
  material_information_form.value = res.data.rows
  total.value = res.data.total
}

// 清除查询表
function clear_material_form() {
  query_material_form.material_code = ''
  query_material_form.material_name = ''
  query_material_form.material_category = ''
  query_material_form.material_shelf_life_days = ''
  query_material_form.material_storage_condition = ''
  query_material_form.material_remark = ''
}

const material_information_form = ref([])
const total = ref(0)

onMounted(() => {
  get_material_form();
})


</script>

<template>
  <div>
    <el-form :model="query_material_form">
      <el-form-item label="原材料编码">
        <el-input v-model="query_material_form.material_code" placeholder="请输入原材料编码" clearable/>
      </el-form-item>
      <el-form-item label="原材料名称">
        <el-input v-model="query_material_form.material_name" placeholder="请输入原材料名称" clearable/>
      </el-form-item>
      <el-form-item label="原材料的种类">
        <el-input v-model="query_material_form.material_category" placeholder="请输入原材料的种类" clearable/>
      </el-form-item>
      <el-form-item label="原材料的保质期">
        <el-input v-model="query_material_form.material_shelf_life_days" placeholder="请输入保质期时间(天)" clearable/>
      </el-form-item>
      <el-form-item label="原材料的存储条件">
        <el-input v-model="query_material_form.material_storage_condition" placeholder="请输入存储条件" clearable/>
      </el-form-item>
      <el-form-item label="备注">
        <el-input v-model="query_material_form.material_remark" placeholder="输入额外的备注" clearable/>
      </el-form-item>
      <el-form-item>
        <el-button @click="get_material_form"> 查询 </el-button>
        <el-button @click="clear_material_form"> 重置 </el-button>
      </el-form-item>
    </el-form>
    <div>
      <el-button>批量删除</el-button>
      <el-button>新增</el-button>
    </div>
    <div>
      <el-table :data="material_information_form" stripe  style="width: 100%">
        <el-table-column type="selection" width="50" />
        <el-table-column prop="material_code" label="原材料编码" />
        <el-table-column prop="material_name" label="原材料名称" />
        <el-table-column prop="material_category" label="分类" />
        <el-table-column prop="material_specification" label="规格型号" />
        <el-table-column prop="material_unit" label="单位" />
        <el-table-column prop="material_shelf_life_days" label="保质期(天)" />
        <el-table-column prop="material_storage_condition" label="存储条件" />
        <el-table-column prop="material_remark" label="备注" />
        <el-table-column label="操作" width="150">
          <template #default="{rows}">
            <el-button size="small" type="primary" > 修改 </el-button>
            <el-button size="small" type="danger" > 删除 </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!--  设置页码-->
    <el-pagination
        v-model:current-page="page_num"
        v-model:page-size="page_size"
        :total="total"
        :page-sizes="[5, 10, 20]"
        layout="total, sizes, prev, pager, next, jumper"
        style="margin-top: 15px; justify-content: center;"
        @size-change="get_material_form"
        @current-change="get_material_form"
    />
  </div>
</template>

<style scoped>

</style>