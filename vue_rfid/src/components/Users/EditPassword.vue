<template>
  <div>
      <el-form class="user-account-key" ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="原密码" prop="password">
          <el-input type="password" placeholder="请输入原密码" v-model="form.password" size="medium"></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
          <el-input type="password" placeholder="请设置新密码" v-model="form.newPassword" size="medium"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="newPassword2">
          <el-input type="password" placeholder="请确认新密码" v-model="form.newPassword2" size="medium"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="editPassword">确认修改</el-button>
          <el-button @click="$refs['form'].resetFields()">重置</el-button>
        </el-form-item>
      </el-form>
  </div>
</template>

<script>
export default {
  data () {
    // 此处即表单发送之前验证
    const validateNewPassword = (rule, value, callback) => {
      if (value === this.form.password) {
        callback(new Error('新密码不能与原密码相同!'))
      } else {
        callback()
      }
    }
    const validateNewPassword2 = (rule, value, callback) => {
      if (value !== this.form.newPassword) {
        callback(new Error('与新密码不一致!'))
      } else {
        callback()
      }
    }
    return {
      form: {},
      rules: {
        password: [
          { required: true, message: '请输入原密码', trigger: 'blur' }
        ],
        newPassword: [
          { required: true, message: '请设置新密码', trigger: 'blur' },
          { validator: validateNewPassword, trigger: 'blur' }
        ],
        newPassword2: [
          { required: true, message: '请确认新密码', trigger: 'blur' },
          { validator: validateNewPassword2, trigger: 'blur' }
        ]
      }
    }
  },
  created () {
  },
  methods: {
    async editPassword () {
      const { data: res } = await this.$http.post('user/password', {
        password: this.form.newPassword
      })
      if (res.errorCode !== 0) return this.$message.error('修改失败！')
      this.$message.success('修改成功！')
      await this.$router.push('/dutyrank')
    }
  }
}
</script>

<style scoped>
.el-input {
  width: 500px;
}
</style>
