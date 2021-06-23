<template>
<div>
  <el-breadcrumb separator-class="el-icon-arrow-right">
    <el-breadcrumb-item :to="{ path: '/homeforusers' }">首页</el-breadcrumb-item>
    <el-breadcrumb-item>出勤管理</el-breadcrumb-item>
    <el-breadcrumb-item>出勤记录</el-breadcrumb-item>
  </el-breadcrumb>
  <el-card>
    <el-table :data="ioInfolist" border stripe height="600px">
      <el-table-column label="时间" prop="time" :formatter="dateFormat"></el-table-column>
      <el-table-column label="学号" prop="stu_id"></el-table-column>
      <el-table-column label="姓名" prop="name"></el-table-column>
      <el-table-column label="专业" prop="major"></el-table-column>
      <el-table-column label="卡号" prop="u_id"></el-table-column>
      <el-table-column label="所属团队" prop="team_name"></el-table-column>
    </el-table>
  </el-card>
</div>
</template>

<script>
export default {
  data () {
    return {
      day: 10,
      ioInfolist: []
    }
  },
  created () {
    this.getUserioinfo()
  },
  methods: {
    async getUserioinfo () {
      const { data: res } = await this.$http.post('user/ioInfo', { day: this.day })
      if (res.errorCode !== 0) return this.$message.error('获取数据失败')
      this.ioInfolist = res.data
    },
    dateFormat (row, colum) {
      const dateee = new Date(row.time).toJSON()
      row.reg_time = new Date(+new Date(dateee) + 8 * 3600 * 1000).toISOString().replace(/T/g, ' ').replace(/\.[\d]{3}Z/, '')
      return row.reg_time
    }
  }
}
</script>

<style scoped>

</style>
