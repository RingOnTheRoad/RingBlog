import axios from 'axios'

export async function imageUrlToFile(url, fileName) {
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
