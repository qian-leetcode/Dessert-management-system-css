<script setup>
import {onMounted, reactive, ref, watch} from "vue";
import {add_material_list_, delete_material_list_, get_query_list, update_material_list_} from "@/api/material.js";
import {ElMessage, ElMessageBox} from "element-plus";

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
  material_shelf_life_days_min: '',
  material_shelf_life_days_max:'',
  material_storage_condition:'',
  material_remark:''
})


// 发送原材料查询表
async function get_material_form() {
  loading.value = true
  try {
    const params = {
      // 原材料查询属性
      page_num: page_num.value,
      page_size: page_size.value,
      material_code: query_material_form.material_code,
      material_name: query_material_form.material_name,
      material_category: query_material_form.material_category,
      material_shelf_life_days_min: Number(query_material_form.material_shelf_life_days_min || 0),
      material_shelf_life_days_max: Number(query_material_form.material_shelf_life_days_max) || Number.MAX_VALUE,
      material_storage_condition: query_material_form.material_storage_condition,
      material_remark: query_material_form.material_remark,
    }
    const res = await get_query_list(params);
    // console.log(res)
    material_information_form.value = res.data.rows
    // console.log(material_information_form)
    total.value = res.data.total
  }
  catch (error){
    ElMessage.error("数据获取失败， 请联系工作人员");
  }
  finally {
    loading.value = false
  }
}

// 清除查询表
async function clear_material_form() {
  query_material_form.material_code = ''
  query_material_form.material_name = ''
  query_material_form.material_category = ''
  query_material_form.material_shelf_life_days_min = ''
  query_material_form.material_shelf_life_days_max = ''
  query_material_form.material_storage_condition = ''
  query_material_form.material_remark = ''
  page_num.value = 1
  await get_material_form();
}

const material_information_form = ref([])
const total = ref(0)
const loading = ref(false)

// 增改
const material_visible = ref(false) // 控制弹窗显示
const add_material_form = reactive({
  id: '', // 修改时必须用
  material_code: '',
  material_name: '',
  material_category: '',
  material_specification: '',
  material_unit: '',
  material_shelf_life_days: '',
  material_storage_condition: '',
  material_remark: ''
})

// 清空数据
async function clear_add_material_form() {
  add_material_form.id = ''
  add_material_form.material_code = ''
  add_material_form.material_name = ''
  add_material_form.material_category = ''
  add_material_form.material_specification = ''
  add_material_form.material_unit = ''
  add_material_form.material_shelf_life_days = ''
  add_material_form.material_storage_condition = ''
  add_material_form.material_remark = ''
}

// 新增
async function add_material_information(){
  try{
    if (
        add_material_form.material_code === '' ||
        add_material_form.material_name === '' ||
        add_material_form.material_category === '' ||
        add_material_form.material_specification === '' ||
        add_material_form.material_unit === '' ||
        add_material_form.material_shelf_life_days === '' ||
        add_material_form.material_storage_condition === '' ||
        add_material_form.material_remark === ''
    ) {
      ElMessage.error("请完善信息")
      return
    }
    const res = await add_material_list_(add_material_form)
    // console.log(res)
    if(res.data.code === 200){
      add_material_form.material_code = ''
      add_material_form.material_name = ''
      add_material_form.material_category = ''
      add_material_form.material_specification = ''
      add_material_form.material_unit = ''
      add_material_form.material_shelf_life_days = ''
      add_material_form.material_storage_condition = ''
      add_material_form.material_remark = ''
      material_visible.value = false
      ElMessage.success("原料添加成功")
    }
    else if(res.data.code === 400){
      ElMessage.error("原料添加失败")
    }
  }
  catch(err){
    ElMessage.error("新增异常， 请联系工作人员")
    // console.log(err)
  }
  await get_material_form();
}

// 更新 -- 回填数据
async function update_material_form(row){
  // console.log(row)
  material_visible.value = true
  add_material_form.id = row.material_id
  add_material_form.material_code = row.material_code
  add_material_form.material_name = row.material_name
  add_material_form.material_category = row.material_category
  add_material_form.material_specification = row.material_specification
  add_material_form.material_unit = row.material_unit
  add_material_form.material_shelf_life_days = row.material_shelf_life_days
  add_material_form.material_storage_condition = row.material_storage_condition
  add_material_form.material_remark = row.material_remark
}

// 修改
async function update_material(){
  try{
    if (
        add_material_form.id === '' ||
        add_material_form.material_code === '' ||
        add_material_form.material_name === '' ||
        add_material_form.material_category === '' ||
        add_material_form.material_specification === '' ||
        add_material_form.material_unit === '' ||
        add_material_form.material_shelf_life_days === '' ||
        add_material_form.material_storage_condition === '' ||
        add_material_form.material_remark === ''
    ) {
      ElMessage.error("请完善信息")
      return
    }
    const res = await update_material_list_(add_material_form)
    if(res.data.code === 200){
      ElMessage.success("修改成功")
      material_visible.value = false
    }
    else if(res.data.code === 400){
      ElMessage.error("修改失败")
    }
    else {
      ElMessage.error("修改异常， 请联系工作人员")
    }
    await get_material_form();
  }
  catch(err){
    ElMessage.error("修改异常， 请联系工作人员")
    // console.log(err)
  }
}

//删除
async function delete_material_form(id){
  try {
    const res = await delete_material_list_(id)
    if(res.data.code === 200){
      ElMessage.success("删除成功")
    }
    else if(res.data.code === 400){
      ElMessage.error("删除失败")
    }
    else {
      ElMessage.error("删除异常， 请联系工作人员")
    }
  }
  catch(err){
    // console.log(err)
    ElMessage.error("删除异常， 请联系工作人员")
  }
  await get_material_form();
}

const selected = ref([])

// 批量删除
async function Batch_delete(){
  try {
    await ElMessageBox.confirm(
        '确定要删除选中的原料吗？',
        '删除确认',
        { confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning' }
    )
    const val = ref(false)
    for (const value of selected.value){
      const res = await delete_material_list_(value)
      if(res.data.code === 200){
        ElMessage.success("删除成功")
      }
      else if(res.data.code === 400){
        ElMessage.error("删除失败")
        val.value = true;
      }
      else {
        ElMessage.error("删除异常， 请联系工作人员")
        val.value = true;
      }
    }
    if(val.value === false){
      ElMessage.success("批量删除成功")
    }
    selected.value = []
    if(val.value === true){
      ElMessage.warning("删除存在异常， 请核查数据")
    }
  }
  catch(err){
    ElMessage.error("删除异常， 请联系工作人员")
  }
  await get_material_form();
}

watch(material_visible, (newVal, oldVal) => {
  // console.log("变量变了！新值：", newVal)
  if(newVal === false) {
    clear_add_material_form()
  }
})

onMounted(() => {
  get_material_form();
})


</script>

<template>
  <div>
    <div class="query-form">
      <el-form :model="query_material_form" inline>
        <el-form-item label="原材料编码">
          <el-input v-model="query_material_form.material_code" placeholder="请输入原材料编码" style="width: 250px;" clearable/>
        </el-form-item>
        <el-form-item label="原材料名称">
          <el-input v-model="query_material_form.material_name" placeholder="请输入原材料名称" style="width: 250px;" clearable/>
        </el-form-item>
        <el-form-item label="原材料的种类">
          <el-input v-model="query_material_form.material_category" placeholder="请输入原材料的种类"  style="width: 250px;" clearable/>
        </el-form-item>
        <el-form-item label="原材料的保质期">
          <el-input type="number" v-model="query_material_form.material_shelf_life_days_min" placeholder="请输入保质期时间(天)最小值" style="width: 250px;" clearable/>
          <span style="margin: 0 5px;">~</span>
          <el-input type="number" v-model="query_material_form.material_shelf_life_days_max" placeholder="请输入保质期时间(天)最大值" style="width: 250px" clearable></el-input>
        </el-form-item>
        <el-form-item label="原材料的存储条件">
          <el-input v-model="query_material_form.material_storage_condition" placeholder="请输入存储条件" style="width: 250px;" clearable/>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="query_material_form.material_remark" placeholder="输入额外的备注" style="width: 250px;" clearable/>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="get_material_form"> 查询 </el-button>
          <el-button type="primary" @click="clear_material_form"> 重置 </el-button>
        </el-form-item>
      </el-form>
    </div>

    <div class="toolbar">
      <el-button type="danger" @click="Batch_delete">批量删除</el-button>
      <el-button type="primary" @click="material_visible = true">新增</el-button>
    </div>

    <el-dialog
        class="dialog-form"
        :model-value="material_visible"
        @close="material_visible = false"
        :title="add_material_form.id ? '修改物料' : '新增物料'"
        width="700px"
    >
      <el-form
          :model="add_material_form"
          label-width="130px"
          label-position="right"
      >
        <el-form-item label="物料编码" required>
          <el-input v-model="add_material_form.material_code" placeholder="请输入物料编码" clearable />
        </el-form-item>
        <el-form-item label="物料名称" required>
          <el-input v-model="add_material_form.material_name" placeholder="请输入物料名称" clearable />
        </el-form-item>
        <el-form-item label="物料分类" required>
          <el-input v-model="add_material_form.material_category" placeholder="请输入物料分类" clearable />
        </el-form-item>
        <el-form-item label="物料规格" required>
          <el-input v-model="add_material_form.material_specification" placeholder="请输入物料规格" clearable />
        </el-form-item>
        <el-form-item label="物料单位" required>
          <el-input v-model="add_material_form.material_unit" placeholder="kg/个/袋/瓶等" clearable />
        </el-form-item>
        <el-form-item label="保质期(天)"required>
          <el-input v-model.number="add_material_form.material_shelf_life_days" type="number" placeholder="请输入数字" clearable />
        </el-form-item>
        <el-form-item label="储存条件" required>
          <el-input v-model="add_material_form.material_storage_condition" placeholder="常温/冷藏/冷冻等" clearable />
        </el-form-item>
        <el-form-item label="备注" required>
          <el-input v-model="add_material_form.material_remark" type="textarea" :rows="3" placeholder="请输入备注信息" clearable />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="material_visible = false">取消</el-button>
        <el-button type="primary" @click="add_material_form.id ? update_material() : add_material_information()">
          {{ add_material_form.id ? '修改' : '添加' }}
        </el-button>
      </template>
    </el-dialog>

    <div class="table-container">
      <el-table :data="material_information_form" v-loading="loading" stripe  style="width: 100%" @selection-change="val => selected = val.map(v=>v.material_id)">
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
          <template #default="{row}">
            <el-button size="small" type="primary" @click="update_material_form(row)"> 修改 </el-button>
            <el-button size="small" type="danger" @click="delete_material_form(row.material_id)"> 删除 </el-button>
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
          @size-change="get_material_form"
          @current-change="get_material_form"
      />
    </div>
  </div>
</template>

<style scoped>
/* Material - 专属样式（共享样式在 shared.css） */
</style>