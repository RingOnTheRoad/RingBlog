<script setup>
import { ref } from 'vue'
import SelectionPart from './SelectionPart.vue'
import { Plus } from '@element-plus/icons-vue'
import { QuillEditor } from '@vueup/vue-quill'
import '@vueup/vue-quill/dist/vue-quill.snow.css'
import { blogAddService, blogGetDetail } from '@/api/blogs'
import { baseURL } from '@/utils/request'
import axios from 'axios'

const imgUrl = ref('')
const emit = defineEmits(['success'])
const defaultForm = {
  title: '',
  tagName: '',
  content: '',
  cover_img: ''
}
const formModel = ref({ ...defaultForm })
const formRef = ref()
const editorRef = ref()
const visibleDrawer = ref(false)

// 接收后端arraybuffer数据转blob/url/file
async function imageUrlToFile(url, fileName) {
  try {
    const response = await axios.get(url, { responseType: 'arraybuffer' })
    const imageData = response.data
    const blob = new Blob([imageData], {
      type: 'image/jpeg'
    })
    imgUrl.value = URL.createObjectURL(blob)
    const file = new File([blob], fileName, { type: blob.type })
    return file
  } catch (error) {
    console.error('图片转换错误:', error)
    throw error
  }
}

// 上传图片回显
const onUploadImg = (uploadFile) => {
  imgUrl.value = URL.createObjectURL(uploadFile.raw)
  formModel.value.cover_img = uploadFile.raw
}

// 上传博客
const onPublish = async (state) => {
  formModel.value.state = state

  // 数据塞进FormData进行上传
  const fd = new FormData()
  for (let key in formModel.value) {
    fd.append(key, formModel.value[key])
  }
  if (formModel.value.id) {
    await blogAddService(fd)
    ElMessage.success('编辑成功')
    emit('success', 'edit')
  } else {
    await blogAddService(fd)
    ElMessage.success('添加成功')
    emit('success', 'add')
  }
  visibleDrawer.value = false
}

// 向外暴露方法
const open = async (row) => {
  visibleDrawer.value = true

  // 编辑旧内容
  if (row.id) {
    const res = await blogGetDetail(row.id)
    formModel.value = res.data.data
    const reqUrl = baseURL + '/blog/img/' + row.id
    const file = await imageUrlToFile(reqUrl, row.id)
    formModel.value.cover_img = file
  } else {
    // 新添加
    formModel.value = { ...defaultForm }
    imgUrl.value = ''
    editorRef.value.setHTML('')
  }
}
defineExpose({ open })
</script>

<template>
  <el-drawer v-model="visibleDrawer" title="" direction="rtl" size="50%">
    <el-form :model="formModel" ref="formRef" label-width="100px">
      <el-form-item label="博客标题" prop="title">
        <el-input
          v-model="formModel.title"
          placeholder="标题在此填入捏"
        ></el-input>
      </el-form-item>
      <el-form-item label="博客分类" prop="tagName">
        <selection-part
          v-model="formModel.tagName"
          width="100%"
        ></selection-part>
      </el-form-item>
      <el-form-item label="博客封面" prop="cover_img"
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
      <el-form-item label="博客内容" prop="content">
        <div class="editor">
          <quill-editor
            ref="editorRef"
            theme="snow"
            v-model:content="formModel.content"
            content-type="html"
          ></quill-editor>
        </div>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onPublish('1')">发布</el-button>
        <el-button @click="onPublish('0')">存为草稿</el-button>
      </el-form-item>
    </el-form>
  </el-drawer>
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
.editor {
  width: 100%;
  :deep(.ql-editor) {
    min-height: 250px;
  }
}
</style>
