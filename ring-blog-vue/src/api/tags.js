import request from '@/utils/request'

// 获得标签列表
export const tagListService = () => request.get('/tags')

// 添加标签
export const tagAddService = ({ tagName }) =>
  request.post('/tags', {
    tagName: tagName
  })

// 更新标签内容
export const tagUpdateService = (id, newTagName) =>
  request.put('/tags', {
    id: id,
    tagName: newTagName
  })

// 删除标签
export const tagDeleteService = (id) =>
  request.delete('/tags', { data: { id: id } })
