<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>用户管理</el-breadcrumb-item>
      <el-breadcrumb-item>用户列表</el-breadcrumb-item>
    </el-breadcrumb>

    <!--卡片视图-->
    <el-card>
      <el-row :gutter="20">
        <el-col :span="7">
          <el-input placeholder="请按姓名进行搜索" v-model="searchName" clearable >
            <el-button slot="append" icon="el-icon-search" @click="onSearch" ></el-button>
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button type="info" @click="reset">重置搜索</el-button>
          <el-button type="primary" @click="dialogFormVisible = true" >添加用户</el-button>
          <el-dialog title="修改信息" :visible.sync="dialogFormVisible">
            <el-form :model="form">
              <el-form-item label="学号" :label-width="formLabelWidth" >
                <el-input v-model="form.stuId" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="姓名" :label-width="formLabelWidth">
                <el-input v-model="form.name" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="专业" :label-width="formLabelWidth">
                <el-input v-model="form.major" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="卡号" :label-width="formLabelWidth">
                <el-input v-model="form.cardNum" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="团队" :label-width="formLabelWidth">
                <el-select v-model="form.teamName" placeholder="请选择团队">
                  <el-option label="物联网实验室" value="1"></el-option>
                  <el-option label="凡路实验室" value="2"></el-option>
                  <el-option label="华云实验室" value="3"></el-option>
                  <el-option label="嵌入式实验室" value="4"></el-option>
                </el-select>
              </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
              <el-button @click="dialogFormVisible = false">取 消</el-button>
              <el-button type="primary" @click="addUser">确 定</el-button>
            </div>
          </el-dialog>
 </el-col>
      </el-row>
      <el-table :data="userlist" border stripe height="600px">
        <el-table-column type="index"></el-table-column>
        <el-table-column label="姓名" prop="name"></el-table-column>
        <el-table-column label="学号" prop="stuId" sortable></el-table-column>
        <el-table-column label="专业" prop="major"></el-table-column>
        <el-table-column label="状态">
          <template slot-scope="scope">
          <el-switch v-model="scope.row.activity"></el-switch>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-tooltip effect="dark" content="修改" placement="top">
              <el-button type="primary" icon="el-icon-edit" size="mini" @click="onEdit(scope.row)"></el-button>
            </el-tooltip>
            <el-tooltip effect="dark" content="删除" placement="top">
              <el-button type="danger" icon="el-icon-delete" size="mini" @click="delUser(scope.row)"></el-button>
            </el-tooltip>
            <el-tooltip effect="dark" content="分配角色" placement="top">
              <el-button type="warning" icon="el-icon-setting" size="mini"></el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="queryInfo.page" :page-sizes="[5,10,20,25]" :page-size="queryInfo.size"
      layout="total,sizes,prev,pager,next,jumper" :total="total"></el-pagination>
    </el-card>
  </div>
</template>

<script>
export default {
  data () {
    return {
      queryInfo: {
        page: 1,
        size: 20
      },
      dialogFormVisible: false,
      userlist: [],
      total: 0,
      searchName: '',
      form: {
        stuId: '',
        teamName: '',
        major: '',
        name: '',
        cardNum: ''
      },
      formLabelWidth: '120px'
    }
  },
  created () {
    this.getUserList()
  },
  methods: {
    async getUserList () {
      const { data: res } = await this.$http.get('admin/getUsers', {
        params: {
          page: this.queryInfo.page,
          size: this.queryInfo.size
        }
      })
      console.log(res)
      if (res.errorCode !== 0) { return this.$message.error('获取用户列表失败') }
      this.userlist = res.data.users
      this.total = res.data.total
    },
    handleSizeChange (newSize) {
      this.queryInfo.size = newSize
      this.getUserList()
    },
    handleCurrentChange (newPage) {
      this.queryInfo.page = newPage
      this.getUserList()
    },
    onSearch () {
      const _this = this
      this.$http.post('admin/search', { name: this.searchName }).then(res => {
        console.log(res)
        _this.userlist = res.data.data
        _this.total = _this.userlist.length
        console.log(_this.total)
      })
    },
    onEdit (row) {
      sessionStorage.setItem('stuId', row.stuId)
      sessionStorage.setItem('major', row.major)
      sessionStorage.setItem('name', row.name)
      sessionStorage.setItem('activity', row.activity)
      sessionStorage.setItem('avatar', row.avatar)
      this.$router.push('/edituserinfo')
    },
    reset () {
      this.searchName = ''
      this.getUserList()
    },
    delUser (row) {
      const _this = this
      this.$confirm('此操作将永久删除该用户, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        _this.$http.post('admin/delete', { stu_id: row.stuId })
          .then(res => {
            if (res.data.errorCode !== 0) this.$message.error('删除失败！')
            this.$message.success('删除成功！')
            this.getUserList()
            this.$router.push('/users')
          })
          .catch(() => {
            this.$message({
              type: 'info',
              message: '已取消删除'
            })
          })
      })
    },
    addUser () {
      const _this = this
      const user = {
        stuId: this.form.stuId,
        teamName: this.form.teamName,
        major: this.form.major,
        name: this.form.name,
        cardNum: this.form.cardNum,
        activity: true
      }
      this.dialogFormVisible = false
      this.$http.post('admin/addUser', {
        stu_id: this.form.stuId,
        name: this.form.name,
        major: this.form.major,
        u_id: this.form.cardNum,
        g_id: this.form.teamName
      }).then(res => {
        console.log(res)
        if (res.data.errorCode !== 0) this.$message.error('添加失败！')
        this.$message.success('添加成功')
        _this.userlist.push(user)
      })
      this.$refs[this.form].resetFields()
    }
  }
}
</script>

<style lang="less" scoped>

</style>
