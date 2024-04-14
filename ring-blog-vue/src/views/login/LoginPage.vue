<script setup>
import { onMounted, ref } from 'vue'
import { Lock, User } from '@element-plus/icons-vue'
import {
  userLoginService,
  userRegisterService,
  userTokenService
} from '@/api/users'
import { useUserStore } from '@/stores'
import { useRouter } from 'vue-router'

// mounted生命周期校验token
onMounted(async () => {
  const res = await userTokenService(userStore.token)
  if (res.data.data === 'Token已经过期') {
    userStore.removeToken()
    ElMessage.error('Token已经过期')
    router.push('/login')
  }
})

const userStore = useUserStore()
const router = useRouter()
const formModel = ref({
  username: '',
  password: '',
  repassword: ''
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
const form = ref()
const isRegister = ref(true)

// 登录按钮
const login = async () => {
  await form.value.validate()
  const res = await userLoginService(formModel.value)
  userStore.setToken(res.data.data)
  userStore.setAccount(formModel.value.username)
  ElMessage.success('登录成功')
  router.push('/index')
}

// 注册按钮
const register = async () => {
  await form.value.validate()
  const res = await userRegisterService(formModel.value)
  userStore.setToken(res.data.data)
  userStore.setAccount(formModel.value.username)
  ElMessage.success('注册成功')
  router.push('/index')
}
</script>

<template>
  <div class="cover">
    <el-row></el-row>
    <el-row class="login-page" justify="space-around">
      <el-col :span="10"></el-col>
      <el-col :span="4" :offset="0" class="form">
        <el-form
          :model="formModel"
          :rules="rules"
          ref="form"
          size="large"
          v-if="isRegister"
        >
          <el-form-item>
            <h1 class="title">登录</h1>
          </el-form-item>
          <el-form-item prop="username">
            <el-input
              placeholder="账号在这里输入捏"
              v-model="formModel.username"
              :prefix-icon="User"
            ></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input
              placeholder="密码在这里输入捏"
              v-model="formModel.password"
              :prefix-icon="Lock"
            ></el-input>
          </el-form-item>
          <el-form-item>
            <el-button
              @click="login"
              type="primary"
              auto-insert-space
              class="button"
            >
              登录
            </el-button>
          </el-form-item>
          <el-form-item>
            <el-link
              type="primary"
              @click="isRegister = false"
              :underline="false"
              class="backTitle"
              >注册</el-link
            >
          </el-form-item>
        </el-form>
        <el-form
          :model="formModel"
          :rules="rules"
          ref="form"
          size="large"
          v-else
        >
          <el-form-item>
            <h1 class="title">注册</h1>
          </el-form-item>
          <el-form-item prop="usernamae">
            <el-input
              placeholder="账号在这里输入捏"
              v-model="formModel.username"
              :prefix-icon="User"
            ></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input
              placeholder="密码在这里输入捏"
              v-model="formModel.password"
              :prefix-icon="Lock"
            ></el-input>
          </el-form-item>
          <el-form-item prop="repassword">
            <el-input
              placeholder="确认密码在这里输入捏"
              v-model="formModel.repassword"
              :prefix-icon="Lock"
            ></el-input>
          </el-form-item>
          <el-form-item>
            <el-button
              @click="register"
              type="primary"
              auto-insert-space
              class="button"
            >
              注册
            </el-button>
          </el-form-item>
          <el-form-item>
            <el-link
              type="primary"
              @click="isRegister = true"
              :underline="false"
              class="backTitle"
              >返回</el-link
            >
          </el-form-item>
        </el-form>
      </el-col>
      <el-col :span="10"></el-col>
    </el-row>
  </div>
</template>

<style lang="scss" scope>
.el-row {
  margin-bottom: 25vh;
}
.login-page {
  background: transparent;
  backdrop-filter: blur(10px);
  border: 2px solid rgba(255, 255, 255, 0.2);
  border-radius: 10px;
  box-shadow: 10px 5px 5px rgba(0, 0, 0, 0.2);
  .form {
    user-select: none;
    .button {
      width: 100%;
    }
    .title {
      color: white;
      text-shadow: 0 0 10px rgba(255, 255, 255, 0.5);
    }
    .backTitle {
      color: blueviolet;
      text-shadow: 0 0 10px rgba(231, 11, 213, 0.8);
    }
  }
}
.cover {
  background: url('@/assets/loginCover.jpg') no-repeat center / cover;
  height: 98vh;
  border-radius: 2vh;
}
</style>
