import request from '@/utils/request'

const system = {

  // 用户删除
  deleteStaff(data) {
    return request.post('/funeral-manage/drive-task/staff/delete', data)
  },
  // 编辑员工
  editStaff(data) {
    return request.post('/funeral-manage/drive-task/staff/old', data)
  },
  // 新增员工
  addStaff(data) {
    return request.post('/funeral-manage/drive-task/staff/add', data)
  },
  // 员工列表
  queryStaff(data) {
    return request.get('/funeral-manage/drive-task/staff/list', { params: data })
  },

  // 车辆删除
  deleteCar(data) {
    return request.post('/funeral-manage/drive-task/car/delete', data)
  },
  // 编辑车辆
  editCar(data) {
    return request.post('/funeral-manage/drive-task/car/old', data)
  },
  // 新增车辆
  addCar(data) {
    return request.post('/funeral-manage/drive-task/car/add', data)
  },
  // 车辆列表
  queryCar(data) {
    return request.get('/funeral-manage/drive-task/car/list', { params: data })
  },
  // 获取车辆及排班顺序
  getCarScheduling(data) {
    return request.get('/funeral-manage/drive-task/car/scheduling', { params: data })
  },

  // 遗体接运删除
  deleteDriveTask(data) {
    return request.post('/funeral-manage/drive-task/task/delete', data)
  },
  // 回车 完成遗体接运
  finishDriveTask(data) {
    return request.post('/funeral-manage/drive-task/task/finish', data)
  },
  // 遗体接运修改
  editDriveTask(data) {
    return request.post('/funeral-manage/drive-task/task/old', data)
  },
  // 遗体接运新增
  addDriveTask(data) {
    return request.post('/funeral-manage/drive-task/task/add', data)
  },
  // 遗体接运列表
  queryDriveTask(data) {
    return request.get('/funeral-manage/drive-task/task/list', { params: data })
  },
  // 任务查询统计
  taskStatisticsQuery(data) {
    return request.get('/funeral-manage/drive-task/task/statistics-query', { params: data })
  },
  // 接运任务状态查询
  statusQuery(data) {
    return request.get('/funeral-manage/drive-task/task/status-query', { params: data })
  },
  // pdf
  pdfDriveTask(data, type) {
    switch (type) {
      case 1:window.open('/funeral-manage/drive-task/task/business-pdf?id=' + data.id) // 派车业务单
        break
      case 2:window.open('/funeral-manage/drive-task/task/dead-pdf?id=' + data.id) // 死亡人员情况登记表
        break
      case 3:window.open('/funeral-manage/drive-task/task/task-pdf?startTime=' + data.startTime + '&endTime=' + data.endTime) // 接尸登记表
        break
    }
  }

}

export default system
