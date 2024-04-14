<script setup>
import {
  CollectionTag,
  Tickets,
  User,
  Avatar,
  Key,
  CaretBottom,
  Right
} from '@element-plus/icons-vue'
import { userGetMsg, userTokenService } from '@/api/users'
import { onMounted, ref, watch } from 'vue'
import { useUserStore } from '@/stores'
import { useRouter } from 'vue-router'
import axios from 'axios'
import { baseURL } from '@/utils/request'

// 监视头像变更并更新
watch(
  () => useUserStore().avatar,
  (newVal) => {
    avatarUrl.value = URL.createObjectURL(newVal)
  },
  { deep: true }
)

// mounted生命周期校验token/获取用户数据
onMounted(async () => {
  console.log('欢迎Master~')
  var res = await userTokenService(userStore.token)
  if (res.data.data === 'Token已经过期') {
    userStore.remove()
    ElMessage.error('Token已经过期')
    router.push('/login')
  }
  res = await userGetMsg({ account: userStore.account })
  userData.value = res.data.data
  const reqUrl = baseURL + '/users/img/' + userData.value.id
  const file = imageUrlToFile(reqUrl, userData.value.id)
  avatarUrl.value = file
})

// 接收后端arraybuffer数据转blob/url/file
async function imageUrlToFile(url, fileName) {
  try {
    const response = await axios.get(url, { responseType: 'arraybuffer' })
    const imageData = response.data
    const blob = new Blob([imageData], {
      type: 'image/jpeg'
    })
    avatarUrl.value = URL.createObjectURL(blob)
    const file = new File([blob], fileName, { type: blob.type })
    return file
  } catch (error) {
    console.error('图片转换错误:', error)
    throw error
  }
}

const userData = ref()
const avatarUrl = ref()
const imgUrl = '../assets/avatar.jpg'
const userStore = useUserStore()
const router = useRouter()
const username = userStore.account

// 下拉菜单回调
const onCommand = async (command) => {
  if (command === 'logout') {
    await ElMessageBox.confirm('master你真的要退出吗?', '提示瞄', {
      type: 'warning',
      confirmButton: '确认',
      cancelButton: '取消'
    })
    userStore.remove()
    router.push('/login')
  } else {
    router.push(`/user/${command}`)
  }
}
</script>
<template>
  <el-container class="index-container">
    <el-aside width="233px">
      <el-menu
        active-text-color="#66CCFF"
        :default-active="router.currentRoute.value.path"
        router
      >
        <el-menu-item index="/blog/tags"
          ><el-icon><CollectionTag /></el-icon
          ><span>博客分类</span></el-menu-item
        >
        <el-menu-item index="/blog/manage"
          ><el-icon><Tickets /></el-icon><span>博客管理</span></el-menu-item
        >
        <el-sub-menu index="/user">
          <template #title>
            <el-icon><User /></el-icon><span>个人中心</span>
          </template>
          <el-menu-item index="/user/my"
            ><el-icon><Avatar /></el-icon><span>我的资料</span></el-menu-item
          >
          <el-menu-item index="/user/password"
            ><el-icon><Key /></el-icon><span>重置密码</span></el-menu-item
          >
        </el-sub-menu>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div>
          欢迎master: <strong>{{ username }}</strong>
        </div>
        <el-dropdown @command="onCommand">
          <span class="avatar_box">
            <el-avatar :src="avatarUrl ? avatarUrl : imgUrl" />
            <el-icon><CaretBottom /></el-icon>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item command="my" :icon="Avatar"
                >我的资料</el-dropdown-item
              >
              <el-dropdown-item command="password" :icon="Key"
                >重置密码</el-dropdown-item
              >
              <el-dropdown-item command="logout" :icon="Right"
                >退出登录</el-dropdown-item
              >
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </el-header>
      <el-main>
        <router-view></router-view>
      </el-main>
      <el-footer>RingBlog @2024 Create by ring</el-footer>
    </el-container>
  </el-container>
</template>

<style lang="scss" scoped>
.index-container {
  height: 98dvh;
  .el-menu {
    height: 100%;
  }

  .el-header {
    background-color: rgba(126, 180, 243, 0.2);
    border-radius: 30px;
    display: flex;
    align-items: center;
    justify-content: space-between;
    width: 95%;
    margin-top: 1%;
    margin-left: 2%;
    .avatar_box {
      display: flex;
      align-items: center;
      .el-icon {
        margin-left: 10px;
      }
      &:active,
      &:focus {
        outline: none;
      }
    }
  }
  .el-footer {
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 10px;
  }
}
</style>
