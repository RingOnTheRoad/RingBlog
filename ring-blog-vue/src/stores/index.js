import { createPinia } from 'pinia'
import persist from 'pinia-plugin-persistedstate'

// 单独管理pinia
const pinia = createPinia()
pinia.use(persist)

export default pinia
export * from './modules/user'
