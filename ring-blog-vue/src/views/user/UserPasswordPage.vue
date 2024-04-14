<script setup>
import { ref } from 'vue'
import { Lock } from '@element-plus/icons-vue'
import { useUserStore } from '@/stores'
import { userResetPassword } from '@/api/users'

const userStore = useUserStore()
const dataForm = ref({
  oldPassword: '',
  newPassword: '',
  reNewPassword: ''
})
const formRef = ref()
const rules = {
  oldPassword: [
    {
      required: true,
      message: 'master忘记输入原来的密码了瞄!',
      trigger: 'blur'
    },
    {
      pattern: /^\S{8,16}$/,
      message: '密码必须是8-16位的非空字符瞄',
      trigger: 'blur'
    }
  ],
  newPassword: [
    {
      required: true,
      message: 'master忘记输入新的密码了瞄!',
      trigger: 'blur'
    },
    {
      pattern: /^\S{8,16}$/,
      message: '密码必须是8-16位的非空字符瞄',
      trigger: 'blur'
    }
  ],
  reNewPassword: [
    {
      required: true,
      message: 'master忘记输入新的密码了瞄!',
      trigger: 'blur'
    },
    {
      pattern: /^\S{8,16}$/,
      message: '密码必须是8-16位的非空字符瞄',
      trigger: 'blur'
    },
    {
      validator: (rule, value, callback) => {
        if (value !== dataForm.value.newPassword) {
          callback(new Error('两次的密码居然不一样瞄!'))
        } else {
          callback()
        }
      },
      trigger: 'blur'
    }
  ]
}

// 重置密码按钮
const resetPwd = async () => {
  // 包装为FormData上传后端
  const fd = new FormData()
  fd.append('account', userStore.account)
  fd.append('old_password', dataForm.value.oldPassword)
  fd.append('new_password', dataForm.value.newPassword)
  const res = await userResetPassword(fd)
  if (res.data.data === '成功修改密码') {
    dataForm.value.oldPassword = ''
    dataForm.value.reNewPassword = ''
    dataForm.value.newPassword = ''
    ElMessage({
      message: '重置密码成功瞄~',
      type: 'success'
    })
  } else {
    ElMessage({
      message: '原密码输入错误',
      type: 'error'
    })
  }
}
</script>

<template>
  <div>
    <el-col>
      <el-form
        ref="formRef"
        style="max-width: 400px"
        :model="dataForm"
        status-icon
        :rules="rules"
        label-width="auto"
      >
        <el-form-item label="旧密码" prop="oldPassword">
          <el-input
            type="password"
            autocomplete="off"
            v-model="dataForm.oldPassword"
            :prefix-icon="Lock"
          ></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
          <el-input
            type="password"
            autocomplete="off"
            v-model="dataForm.newPassword"
            :prefix-icon="Lock"
          ></el-input>
        </el-form-item>
        <el-form-item label="重复密码" prop="reNewPassword">
          <el-input
            type="password"
            autocomplete="off"
            v-model="dataForm.reNewPassword"
            :prefix-icon="Lock"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="resetPwd">重置密码</el-button>
        </el-form-item>
      </el-form>
    </el-col>
  </div>
</template>

<style lang="scss" scoped></style>
