<script setup>
import { onMounted, ref } from 'vue'
import CardContainer from './components/CardContainer.vue'
import EditPage from './components/EditPage.vue'
import { Edit, Delete } from '@element-plus/icons-vue'
import { tagDeleteService, tagListService } from '@/api//tags'

// mounted生命周期获取标签数据
onMounted(async () => {
  const res = await tagListService()
  tagList.value = res.data.data
})

const dialog = ref()
const tagList = ref([])
const isloading = ref(false)

// 获取标签数据
const getTagData = async () => {
  const res = await tagListService()
  tagList.value = res.data.data
}

// 成功回调
const onSuccess = async () => {
  getTagData()
}

// 添加按钮
const onAdd = () => {
  dialog.value.open({})
}

// 编辑按钮
const onEdit = async (row) => {
  dialog.value.open(row)
}

// 删除按钮
const onDelete = async (row) => {
  await ElMessageBox.confirm('master确认要删除该分类吗?', '提示瞄', {
    type: 'warning',
    confirmButtonText: '确认',
    cancelBuutonText: '取消'
  })
  const res = await tagDeleteService(row.id)
  if (res.data.data === '成功删除Tag') {
    ElMessage({ type: 'success', message: '删除成功' })
    getTagData()
  }
}
</script>

<template>
  <card-container title="分类管理">
    <template #extra>
      <el-button type="primary" @click="onAdd">添加分类</el-button>
    </template>
    <el-table :data="tagList" style="width: 100%" v-loading="isloading">
      <el-table-column label="序号" width="100" type="index"></el-table-column>
      <el-table-column label="分类名称" prop="tagName"></el-table-column>
      <el-table-column label="博客数量" prop="blogNum"></el-table-column>
      <el-table-column label="操作" width="100">
        <template #default="{ row }">
          <el-button
            :icon="Edit"
            circle
            plain
            type="primary"
            @click="onEdit(row)"
          ></el-button>
          <el-button
            :icon="Delete"
            circle
            plain
            type="danger"
            @click="onDelete(row)"
          ></el-button>
        </template>
      </el-table-column>

      <template #empty>
        <el-empty description="没有数据瞄~"></el-empty>
      </template>
    </el-table>
  </card-container>
  <edit-page ref="dialog" @success="onSuccess"></edit-page>
</template>

<style lang="scss" scoped></style>
