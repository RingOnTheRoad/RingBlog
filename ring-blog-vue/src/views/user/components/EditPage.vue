<script setup>
import { ref } from 'vue'
import { Plus } from '@element-plus/icons-vue'
import { userUpdateMsg } from '@/api/users'
import { useUserStore } from '@/stores'

const emit = defineEmits(['success'])
const userStore = useUserStore()
const formRef = ref()
const dialogVisible = ref(false)
const imgUrl = ref()
const oldFlag = ref()
const formModel = ref({
  id: '',
  account: '',
  description: '',
  avatar: ''
})
const rules = {
  username: [
    { required: true, message: '请输入账号瞄', trigger: 'blur' },
    { min: 4, max: 10, message: '账号必须是4-10位字符瞄', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码瞄', trigger: 'blur' },
    {
      pattern: /^\S{8,16}$/,
      message: '密码必须是8-16位的非空字符瞄',
      trigger: 'blur'
    }
  ],
  repassword: [
    { required: true, message: '请再次输入以确认密码瞄', trigger: 'blur' },
    {
      pattern: /^\S{8,16}$/,
      message: '密码必须是8-16位的非空字符瞄',
      trigger: 'blur'
    },
    {
      validator: (rule, value, callback) => {
        if (value !== formModel.value.password) {
          callback(new Error('两次的密码居然不一样瞄!'))
        } else {
          callback()
        }
      },
      trigger: 'blur'
    }
  ]
}
const onUploadImg = (uploadFile) => {
  imgUrl.value = URL.createObjectURL(uploadFile.raw)
  formModel.value.avatar = uploadFile.raw
}
const onSubmit = async () => {
  await formRef.value.validate()
  if (oldFlag.value === formModel.value.avatar) {
    formModel.value.avatar = new File([], '')
  } else {
    userStore.avatar = formModel.value.avatar
  }
  const fd = new FormData()
  for (let key in formModel.value) {
    fd.append(key, formModel.value[key])
  }
  await userUpdateMsg(fd)
  userStore.account = formModel.value.account
  userStore.description = formModel.value.description
  emit('success', formModel.value.account)
  dialogVisible.value = false
}
const open = async (row) => {
  dialogVisible.value = true
  formModel.value = { ...row }
  imgUrl.value = formModel.value.avatar
  oldFlag.value = imgUrl.value
}
defineExpose({ open })
</script>

<template>
  <el-dialog v-model="dialogVisible" title="编辑信息" width="30%">
    <el-form
      ref="formRef"
      :model="formModel"
      :rules="rules"
      label-width="100px"
      style="padding-left: 30px"
    >
      <el-form-item label="新账号名称" prop="account">
        <el-input v-model="formModel.account"></el-input>
      </el-form-item>
      <el-form-item label="新签名" prop="description">
        <el-input v-model="formModel.description"></el-input>
      </el-form-item>
      <el-form-item label="新头像" prop="avatar"
        ><el-upload
          class="cover_uploader"
          :auto-upload="false"
          :show-file-list="false"
          :on-change="onUploadImg"
          ><img v-if="imgUrl" :src="imgUrl" class="avatar" /><el-icon
            v-else
            class="avatar-uploads"
            ><Plus /></el-icon></el-upload
      ></el-form-item>
    </el-form>
    <template #footer>
      <span>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="onSubmit">确认</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<style lang="scss" scoped>
.cover_uploader {
  :deep() {
    .avatar {
      width: 168px;
      height: 168px;
      display: block;
    }
    .el-upload {
      border: 1px dashed var(--el-border-color);
    }
    .el-icon.avatar-uploads {
      width: 168px;
      height: 168px;
    }
  }
}
</style>
