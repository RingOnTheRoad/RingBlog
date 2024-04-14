<script setup>
import { tagListService } from '@/api/tags'
import { onMounted, ref } from 'vue'

// mounted生命周期获取标签数据
onMounted(async () => {
  const res = await tagListService()
  blogList.value = res.data.data
})

const blogList = ref([
  {
    id: 1,
    title: 'title',
    date: 'date',
    state: '已发布',
    tagName: '测试分类'
  }
])

// 更新触发事件
const emit = defineEmits(['update:modelValue'])
defineProps({
  // v-model数据
  modelValue: {
    type: [Number, String]
  },
  width: {
    type: String
  }
})
</script>

<template>
  <el-select
    :model-value="modelValue"
    @update:modelValue="emit('update:modelValue', $event)"
    :style="{ width }"
  >
    <el-option
      v-for="blog in blogList"
      :key="blog.id"
      :label="blog.tagName"
      :value="blog.tagName"
    >
    </el-option>
  </el-select>
</template>
