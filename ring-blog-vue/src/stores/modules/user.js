import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useUserStore = defineStore(
  'user',
  () => {
    // 用户信息仓库
    const token = ref('')
    const account = ref('')
    const desc = ref('')
    const avatar = ref('')
    const setToken = (newToken) => {
      token.value = newToken
    }
    const setAccount = (accountName) => {
      account.value = accountName
    }
    const setDesc = (desc) => {
      desc.value = desc
    }
    const setAvatar = (avatar) => {
      avatar.value = avatar
    }
    const remove = () => {
      token.value = ''
      account.value = ''
    }

    return {
      token,
      account,
      desc,
      avatar,
      setToken,
      setAccount,
      setAvatar,
      setDesc,
      remove
    }
  },
  // 持久化存储
  { persist: true }
)
