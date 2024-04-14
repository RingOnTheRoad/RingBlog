<script setup>
import router from '@/router'
import { useUserStore } from '@/stores'
import { userGetMsg } from '@/api/users'
import { onMounted, ref } from 'vue'
import axios from 'axios'
import { baseURL } from '@/utils/request'
import EditPage from './components/EditPage.vue'

// mounted生命周期获取用户数据
onMounted(async () => {
  const res = await userGetMsg({ account: userStore.account })
  userData.value = res.data.data
  const reqUrl = baseURL + '/users/img/' + userData.value.id
  const file = imageUrlToFile(reqUrl, userData.value.id)
  avatarUrl.value = file
})

const userData = ref('')
const imgURL = '/src/assets/avatar.jpg'
const avatarUrl = ref('')
const userStore = useUserStore()
const editRef = ref()

// 退出按钮
const exit = () => {
  userStore.remove()
  router.push('/login')
}

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

// 修改资料按钮
const onChange = async () => {
  const user = await userGetMsg({ account: userStore.account })
  editRef.value.open({
    id: user.data.data.id,
    account: userData.value.account,
    description: userData.value.description,
    avatar: avatarUrl.value
  })
}

// 编辑页面成功回调
const onSuccess = async (newName) => {
  ElMessage.success('修改信息成功')
  const res = await userGetMsg({ account: newName })
  userData.value = res.data.data
  const reqUrl = baseURL + '/users/img/' + userData.value.id
  const file = imageUrlToFile(reqUrl, userData.value.id)
  avatarUrl.value = file
  userStore.setAvatar(file)
}

// 查看博客跳转
const onCheck = () => {
  router.push('/blog/manage')
}
</script>

<template>
  <div class="container">
    <div class="card">
      <el-row class="card_up">
        <el-col class="card_up_inside_avatar">
          <el-avatar
            :src="avatarUrl ? avatarUrl : imgURL"
            :size="150"
            class="avatar"
          ></el-avatar>
        </el-col>
        <el-col class="card_up_inside_name">
          <span class="name">{{ userStore.account }}</span>
        </el-col>
        <el-col class="card_up_inside_desc">
          <span>{{ userData.description }}</span>
        </el-col>
      </el-row>
      <el-row class="card_down"
        ><el-button type="primary" @click="onChange">修改资料</el-button
        ><el-button type="success" @click="onCheck">博客查看</el-button
        ><el-button type="danger" @click="exit">退出登录</el-button></el-row
      >
    </div>
  </div>
  <edit-page ref="editRef" @success="onSuccess"></edit-page>
</template>

<style lang="scss" scoped>
.container {
  background-color: rgb(131, 108, 165);
  height: 100%;
  border-radius: 5px;
  display: flex;
  justify-content: center;
  align-items: center;
  .card {
    background-color: aliceblue;
    width: 90%;
    height: 70vh;
    border-radius: 10px;
    box-shadow: 5px 5px 20px 10px rgba(1, 1, 1, 0.4);
    .card_up {
      background-color: antiquewhite;
      display: flex;
      justify-content: center;
      align-items: center;
      border-radius: 10px;
      .card_up_inside_avatar {
        display: flex;
        justify-content: center;
        align-items: start;
        margin-top: 1.5vh;
        .avatar {
          box-shadow: 1px 1px 50px -20px black;
        }
      }
      .card_up_inside_name {
        display: flex;
        justify-content: center;
        align-items: center;
        margin-top: 1vh;
        .name {
          display: flex;
          justify-content: center;
          background-image: linear-gradient(to top right, #66ccff, #ffcc66);
          border-radius: 10px;
          width: 7%;
          white-space: nowrap;
          border: 1px solid rgb(0, 0, 0);
          box-shadow: 0px 0px 12px -5px rgb(240, 196, 3);
          font-size: larger;
          font-weight: bold;
        }
      }
      .card_up_inside_desc {
        display: flex;
        justify-content: center;
        align-items: center;
        margin-top: 1vh;
        margin-bottom: 1vh;
      }
    }

    .card_down {
      display: flex;
      justify-content: center;
      align-content: space-between;
      margin-top: 1%;
      .el-button {
        box-shadow: 0 0 10px -2px rgb(221, 86, 181);
        margin-block: 1vh;
      }
    }
  }
}
</style>
