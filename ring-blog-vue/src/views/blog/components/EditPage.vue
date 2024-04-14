<script setup>
import { ref } from 'vue'
import { tagAddService, tagUpdateService } from '@/api/tags'

const emit = defineEmits(['success'])
const formRef = ref()
const dialogVisible = ref(false)
const formModel = ref({
  tagName: ''
})
const rules = {
  tagName: [
    { required: true, message: '请输入分类名称瞄', trigger: 'blur' },
    { pattern: /^\S{2,4}$/, message: '必须是2-4位字符捏', trigger: 'blur' }
  ]
}

// 上传标签
const onSubmit = async () => {
  await formRef.value.validate()

  // 修改标签
  if (formModel.value.id) {
    const res = await tagUpdateService(
      formModel.value.id,
      formModel.value.tagName
    )
    if (res.data.data === '已存在此Tag') {
      ElMessage({
        type: 'error',
        message: '已存在此Tag'
      })
    } else {
      ElMessage({
        type: 'success',
        message: formModel.value.id ? '添加成功' : '编辑成功'
      })
    }
    emit('success', 'add')
  } else {
    // 新标签
    const res = await tagAddService(formModel.value)
    if (res.data.data === '已存在此Tag') {
      ElMessage({
        type: 'error',
        message: '已存在此Tag'
      })
    } else {
      ElMessage({
        type: 'success',
        message: formModel.value.id ? '添加成功' : '编辑成功'
      })
    }
    emit('success', 'add')
  }

  dialogVisible.value = false
}

// 向外暴露方法
const open = async (row) => {
  dialogVisible.value = true
  formModel.value = { ...row }
}
defineExpose({ open })
</script>

<template>
  <el-dialog
    v-model="dialogVisible"
    :title="formModel.tagName ? '编辑分类' : '添加分类'"
    width="30%"
  >
    <el-form
      ref="formRef"
      :model="formModel"
      :rules="rules"
      label-width="100px"
      style="padding-left: 30px"
    >
      <el-form-item label="分类名称" prop="tagName">
        <el-input v-model="formModel.tagName" min="2" max="4"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <span>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="onSubmit">确认</el-button>
      </span>
    </template>
  </el-dialog>
</template>
