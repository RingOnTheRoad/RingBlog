import request from '@/utils/request'

// 获取博客列表
export const blogListService = (params) => request.get('/blog', { params })

// 添加（更新）博客
export const blogAddService = (data) => request.post('/blog', data)

// 删除博客
export const blogDeleteService = (id) => request.delete('/blog', { data: id })

// 获得博客详情
export const blogGetDetail = (id) => request.get(`/blog/${id}`)
