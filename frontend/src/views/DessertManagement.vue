<script setup>

//功能查询
import {reactive} from "vue";

//查询表
const query_form = reactive({
  name:'',
  description:'',
})

// 查询

// 清空查询
function clear_query_form(){
  query_form.name  =''
  query_form.description  =''
}

</script>

<template>
  <div>
    <el-form :model="query_form">
      <el-form-item label="分类名称">
        <el-input v-model="query_form.name" placeholder="输入名称" clearable/>
      </el-form-item>
      <el-form-item label="描述">
        <el-input >
          <el-input v-model="query_form.description" placeholder="输入描述" clearable />
        </el-input>
      </el-form-item>
      <el-form-item>
        <el-button > 查询 </el-button>
        <el-button @click="clear_query_form"> 重置 </el-button>
      </el-form-item>
    </el-form>
    <div>
      <el-button>批量删除</el-button>
      <el-button>新增</el-button>
    </div>
    <div>
      <el-table>
        
      </el-table>
    </div>
  </div>
</template>

<style scoped>

</style>