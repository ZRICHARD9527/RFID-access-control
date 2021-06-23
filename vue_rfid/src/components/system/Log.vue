<template>
<div>
  <el-breadcrumb separator-class="el-icon-arrow-right">
  <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
  <el-breadcrumb-item>系统管理</el-breadcrumb-item>
  <el-breadcrumb-item>管理日志</el-breadcrumb-item>
</el-breadcrumb>
  <el-card>
  <el-table :data="loglist" border stripe height="600px">
    <el-table-column label="日志ID" prop="id" sortable></el-table-column>
    <el-table-column label="管理员姓名" prop="aname"></el-table-column>
    <el-table-column label="管理员ID" prop="aid"></el-table-column>
    <el-table-column label="操作" prop="event"></el-table-column>
    <el-table-column label="对象信息" prop="obj"></el-table-column>
  </el-table>
</el-card></div>
</template>

<script>
export default {
  data () {
    return {
      log: {
        id: 1,
        event: '',
        obj: '',
        aid: '1',
        aname: ''
      },
      loglist: []
    }
  },
  created () {
    this.getLoglist()
  },
  methods: {
    async getLoglist () {
      const { data: res } = await this.$http.get('admin/getLog')
      console.log(res)
      if (res.errorCode !== 0) { return this.$message.error('获取日志失败') }
      this.loglist = res.data
    }
  }
}
</script>

<style scoped>

</style>
