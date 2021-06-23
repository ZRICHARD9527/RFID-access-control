<template>
  <div class="login_container">
    <div class="login_box">
      <!--头像-->
      <div class="avatar-box">
        <img src="../assets/nao.png" alt="">
      </div>
      <el-form ref="loginFormRef" :rules="loginFormRules" :model="loginForm" label-width="0px" class="login_form">
        <!--用户名-->
        <el-form-item prop="username">
          <el-input v-model="loginForm.name" prefix-icon="el-icon-user"></el-input>
        </el-form-item>
        <!--密码-->
        <el-form-item prop="password">
          <el-input type="password" v-model="loginForm.password" prefix-icon="el-icon-lock"></el-input>
        </el-form-item>
        <el-form-item class="btns">
          <el-button type="primary" @click="loginForUsers">用户登录</el-button>
          <el-button type="primary" @click="loginForAdmin">管理员登录</el-button>
          <el-button type="info" @click="resetLoginForm()">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {

      loginForm: {
        name: '',
        password: ''
      },
      loginFormRules: {
        name: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 10, message: '用户名需满足长度在3到10个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 3, max: 15, message: '密码需满足长度在3到10个字符', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    resetLoginForm () {
      this.$refs.loginFormRef.resetFields()
    },
    loginForAdmin: function () {
      this.$refs.loginFormRef.validate(
        async valid => {
          if (!valid) return false
          const { data: res } = await this.$http.post('admin/login', this.loginForm)
          console.log(res)
          if (res.errorCode !== 0) return this.$message.error('登录失败')
          this.$message.success('登录成功')
          window.sessionStorage.setItem('token', res.data.token)
          await this.$router.push('/home')
        })
    },
    loginForUsers: function () {
      this.$refs.loginFormRef.validate(
        async valid => {
          if (!valid) return false
          const { data: res } = await this.$http.post('user/login', {
            stu_id: this.loginForm.name,
            password: this.loginForm.password
          })
          console.log(res)
          if (res.errorCode !== 0) return this.$message.error('登录失败')
          this.$message.success('登录成功')
          if (this.loginForm.name === this.loginForm.password) {
            window.sessionStorage.setItem('token', res.data.token)
            this.getUserinfo()
            return await this.$router.push('/editpassword')
          }
          this.getUserinfo()
          window.sessionStorage.setItem('token', res.data.token)
          await this.$router.push('/homeforusers')
        })
    },
    async getUserinfo () {
      const { data: res } = await this.$http.get('user/userInfo')
      console.log(res)
      if (res.errorCode !== 0) this.$message.error('失败')
      sessionStorage.setItem('stuId', res.data.stuId)
      sessionStorage.setItem('name', res.data.name)
      sessionStorage.setItem('major', res.data.major)
      sessionStorage.setItem('avatar', res.data.avatar)
    }
  }
}
</script>

<style lang="less" scoped>
.login_container{
  background-color: #2b4b6b;
  height: 100%;
}

.login_box{
  width: 600px;
  height: 300px;
  background-color: #fff;
  border-radius: 3px;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%,-50%);
}
.avatar-box{
  height: 130px;
  width: 130px;
  border: 1px solid #eee;
  border-radius: 50%;
  padding: 10px;
  box-shadow: 0 0 10px #ddd;
  position: absolute;
  left: 50%;
  transform: translate(-50%,-50%);
  background-color: #fff;
  img{
    width: 100%;
    height: 100%;
    border-radius: 50%;
    background-color: #eee;
  }
}
.btns{
  display: flex;
  justify-content: flex-end;
}

.login_form{
  position: absolute;
  bottom: 0;
  width: 100%;
  padding: 0 20px;
  box-sizing: border-box;
}
</style>
