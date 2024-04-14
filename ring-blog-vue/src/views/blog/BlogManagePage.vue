<script setup>
import { onMounted, ref } from 'vue'
import CardContainer from './components/CardContainer.vue'
import SelectionPart from './components/SelectionPart.vue'
import DrawerPart from './components/DrawerPart.vue'
import { Edit, Delete } from '@element-plus/icons-vue'
import { blogListService, blogDeleteService } from '@/api/blogs'

// mounted生命周期获取博客数据
onMounted(() => {
  getBlogData()
})

// 获取数据&处理数据
const getBlogData = async () => {
  const res = await blogListService(params.value)
  total.value = res.data.data.total
  BlogList.value = res.data.data.blogs
  for (let i = 0; i < BlogList.value.length; i++) {
    if (BlogList.value[i].state === '0') {
      BlogList.value[i].state = '草稿'
    } else {
      BlogList.value[i].state = '已发布'
    }
  }
}

const loading = ref(false)
const drawerRef = ref()
const total = ref(100)
const BlogList = ref([
  {
    id: 1,
    title: 'title',
    date: 'date',
    state: '已发布',
    tagName: '测试分类'
  }
])
const params = ref({
  pageNum: 1,
  pageSize: 10,
  tagName: '',
  state: ''
})

// 博客成功上传回调
const onSuccess = async () => {
  getBlogData()
}

// 添加按钮
const onAdd = () => {
  drawerRef.value.open({})
}

// 编辑按钮
const onEdit = (row) => {
  drawerRef.value.open(row)
}

// 删除按钮
const onDelete = async (row) => {
  await ElMessageBox.confirm('master确定要删除这篇博客吗?', '提示瞄', {
    type: 'warning',
    confirmButtonText: '确认',
    cancelBuutonText: '取消'
  })
  const res = await blogDeleteService(row.id)
  if (res.data.data === '删除博客成功') {
    ElMessage({ type: 'success', message: '删除成功' })
    getBlogData()
  }
}

// 分页大小
const onSizeChange = async (size) => {
  params.value.pageNum = 1
  params.value.pageSize = size
  getBlogData()
}

// 当前页数据
const onCurrentChange = async (page) => {
  params.value.pageNum = page
  getBlogData()
}

// 搜索
const onSearch = async () => {
  params.value.pageNum = 1
  getBlogData()
}

// 重置按钮
const onReset = async () => {
  params.value.pageNum = 1
  params.value.tagName = ''
  params.value.state = ''
  getBlogData()
}
</script>

<template>
  <card-container title="博客管理">
    <template #extra>
      <el-button type="primary" @click="onAdd">发布博客</el-button>
    </template>
    <el-form inline>
      <el-form-item label="博客分类" style="width: 200px">
        <selection-part v-model="params.tagName"></selection-part>
      </el-form-item>
      <el-form-item label="发布状态" style="width: 200px">
        <el-select v-model="params.state">
          <el-option label="已发布" value="1"></el-option>
          <el-option label="草稿" value="0"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSearch">搜索</el-button>
        <el-button @click="onReset">重置</el-button>
      </el-form-item>
    </el-form>
    <el-table :data="BlogList" v-loading="loading" style="width: 100%">
      <el-table-column label="博客标题" style="width: 100%">
        <template #default="{ row }">
          <el-link
            type="primary"
            :underline="false"
            target="_blank"
            :href="`${row.id}`"
            >{{ row.title }}</el-link
          >
        </template>
      </el-table-column>
      <el-table-column label="分类" prop="tagName"></el-table-column>
      <el-table-column label="发布时间" prop="date"></el-table-column>
      <el-table-column label="状态" prop="state"></el-table-column>
      <el-table-column label="操作" width="100">
        <template #default="{ row }">
          <el-button
            :icon="Edit"
            circle
            plain
            type="primary"
            @click="onEdit(row)"
          >
          </el-button>
          <el-button
            :icon="Delete"
            circle
            plain
            type="danger"
            @click="onDelete(row)"
          >
          </el-button>
        </template>
      </el-table-column>
      <template #empty>
        <el-empty description="没有数据捏"></el-empty>
      </template>
    </el-table>
    <el-pagination
      :current-page="params.pageNum"
      :page-size="params.pageSize"
      :page-sizes="[10, 20, 50, 100]"
      layout="total, sizes, prev, pager, next"
      background
      :total="total"
      @size-change="onSizeChange"
      @current-change="onCurrentChange"
      style="margin-top: 20px; justify-content: flex-end"
    ></el-pagination>
  </card-container>
  <drawer-part ref="drawerRef" @success="onSuccess"></drawer-part>
</template>

<style lang="scss" scoped></style>
