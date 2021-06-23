<template>
<div>
  <el-breadcrumb separator-class="el-icon-arrow-right">
    <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
    <el-breadcrumb-item>出勤管理</el-breadcrumb-item>
    <el-breadcrumb-item>出勤排行</el-breadcrumb-item>
  </el-breadcrumb>
  <el-table :data="ranklist" border stripe>
    <el-table-column label="学号" prop="stu_id"></el-table-column>
    <el-table-column label="卡号" prop="u_id"></el-table-column>
    <el-table-column label="专业" prop="major"></el-table-column>
    <el-table-column label="姓名" prop="name"></el-table-column>
    <el-table-column label="近期出入次数" prop="number" sortable></el-table-column>
  </el-table>
</div>
</template>

<script>
export default {
  data () {
    return {
      day: 10,
      ranklist: []
    }
  },
  created () {
    this.getUserRank()
  },
  methods: {
    async getUserRank () {
      const { data: res } = await this.$http.post('user/rank', { day: this.day })
      console.log(res)
      if (res.errorCode !== 0) { return this.$message.error('获取用户列表失败') }
      this.ranklist = res.data
    }
  }
}
</script>

<style scoped>

</style>
