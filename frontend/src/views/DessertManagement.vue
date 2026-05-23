<script setup>
import {onMounted, reactive, ref} from "vue";
import {ElMessage} from "element-plus";
import {get_dessert_list_name} from "@/api/category.js";
import {get_dessert_list} from "@/api/dessert.js";

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

// 获取种类名称列表
async function fetch_category_list() {
  try{
    const res = await get_dessert_list_name()
    // console.log(res)
    category_list.value = res.data
    // console.log(category_list)
  }
  catch(error){
    ElMessage.error(error.message);
  }
}

// 页码管理
const page_num = ref(0)
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
      dessert_min_price: query_form.dessert_min_price,
      dessert_max_price: query_form.dessert_max_price,
      page_num: page_num.value,
      page_size: page_size.value,
    }
    const res = await get_dessert_list(params)
    console.log(res)
    dessert_form.value = res.data.rows
    console.log(dessert_form.value)
    total.value = res.data.total
  }
  catch(error){
    ElMessage.error(error.message);
  }
}


// 清空查询条件
function clear_query_form(){
  query_form.dessert_name  =''
  query_form.dessert_description  =''
  query_form.dessert_category  = ''
  query_form.dessert_min_price  =''
  query_form.dessert_max_price  =''
}

onMounted(() => {
  fetch_category_list();
  query_form_dessert();
})

</script>

<template>
  <div>
    <el-form :model="query_form">
      <el-form-item label="分类名称">
        <el-input v-model="query_form.dessert_name" placeholder="输入名称" clearable/>
      </el-form-item>
      <el-form-item label="描述">
        <el-input v-model="query_form.dessert_description" placeholder="请输入描述" clearable />
      </el-form-item>
      <el-form-item label="分类">
        <el-select v-model="query_form.dessert_category" placeholder="选择分类">
          <el-option
              v-for="item in category_list"
              :key="item"
              :label="item"
              :value="item"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="价格区间">
        <el-input v-model="query_form.dessert_min_price" placeholder="最低价" />
        ~
        <el-input v-model="query_form.dessert_max_price" placeholder="最高价" />
      </el-form-item>
      <el-form-item>
        <el-button @click="query_form_dessert"> 查询 </el-button>
        <el-button @click="clear_query_form"> 重置 </el-button>
      </el-form-item>
    </el-form>
    <div>
      <el-button>批量删除</el-button>
      <el-button>新增</el-button>
    </div>
    <div>
      <el-table :data="dessert_form" stripe style="width: 100%" @selection-change="val => selected = val.map(v=>v.id)">
        <el-table-column type="selection" width="50" />
        <el-table-column prop="photo_url" label="图片" >
          <template #default="{ row }">
            <el-image v-if="row.photo_url" :src="row.photo_url" style="width: 60px; height: 60px;" fit="cover" />
            <span v-else>无图片</span>
          </template>
        </el-table-column>
        <el-table-column prop="name" label="名称" />
        <el-table-column prop="dessert_category" label="所属分类" />
        <el-table-column prop="price" label="单价(单位: ￥)" />
        <el-table-column prop="description" label="描述" />
        <el-table-column prop="release_date" label="发布日" />
        <el-table-column label="操作" width="150">
          <template @default="{row}">
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
        @size-change="query_form_dessert"
        @current-change="query_form_dessert"
    />
  </div>
</template>

<style scoped>

</style>