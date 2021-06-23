<template>
  <div style="background-color: #eaedf1">
    <!--:inline="true" class="demo-form-inline"-->
    <el-form style="width: 40%" :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px">
      <el-row :gutter="20" justify="">
        <el-col :span="2" >
          <el-form-item lable="头像" prop="img">
            <!--加冒号表示v-bind 绑定数据，为变量；否则为常量-->
            <el-avatar :src="ruleForm.picpath" :size="120"></el-avatar>
          </el-form-item>
        </el-col>
        <el-col :span="2" style="float: right;margin-right: 250px">
          <el-upload
            class="upload-demo"
            action="http://localhost:8183/addPic/"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            list-type="picture"
            :data="{p_id:ruleForm.p_id}"
            name="img">
            <el-button v-show="false" size="small" type="primary">选择图片</el-button>
          </el-upload>
        </el-col>
      </el-row>

      <el-form-item label="用户学号" prop="u_id">
        <el-input v-model="ruleForm.u_id" readonly=""></el-input>
      </el-form-item>
      <el-form-item label="专业" prop="major">
        <el-input v-model="ruleForm.major" readonly=""></el-input>
      </el-form-item>
      <el-form-item label="用户名" prop="name" >
        <el-input v-model="ruleForm.name" readonly=""></el-input>
      </el-form-item>
      <el-form-item label="挂失">
        <el-switch v-model="ruleForm.activeFlag"
                   active-color="#13ce66"
                   inactive-color="#999999">
        </el-switch>
      </el-form-item>
      <el-form-item >
        <el-button type="primary" @click="update">提交</el-button>
      </el-form-item>

      <!--                选择框显示-->
      <!--                <el-checkbox-group v-model="ruleForm.activeFlag">-->
      <!--                    <el-checkbox label="激活" name="activeFlag"></el-checkbox>-->
      <!--                </el-checkbox-group>-->
    </el-form>
  </div>
</template>
<script>

export default {
  data () {
    return {
      imageURL: '',
      ruleForm: {
        u_id: sessionStorage.getItem('stuId'),
        name: sessionStorage.getItem('name'),
        activeFlag: sessionStorage.getItem('activity'),
        major: sessionStorage.getItem('major'),
        picpath: 'http://192.168.43.43:8133/' + sessionStorage.getItem('avatar')
      },
      rules: {
        u_id: [
          { required: true, message: '不可修改', trigger: 'focus' }
        ]

        // img: [
        //     //{required: true, message: '请选择图片', trigger: 'change'},
        //     //{type: 'array', required: true, message: '请至少选择一个图片', trigger: 'change'}
        // ]
      }
    }
  },
  methods: {
    update () {
      const _this = this
      _this.$http.post('update', {
        stu_id: _this.ruleForm.u_id,
        name: _this.ruleForm.name,
        major: _this.ruleForm.major,
        activity: _this.ruleForm.activeFlag
      }).then(res => {
        console.log(res)
        if (res.data.errorCode !== 0) return this.$message.error('上传失败！')
        this.$message.success('上传成功！')
        sessionStorage.setItem('stuId', _this.ruleForm.u_id)
        sessionStorage.setItem('major', _this.ruleForm.major)
        sessionStorage.setItem('name', _this.ruleForm.name)
        sessionStorage.setItem('activity', _this.ruleForm.activeFlag)
      })
    },
    delUser () {
      const _this = this
      this.$confirm('此操作将永久删除该用户, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        _this.$http.post('delete', { stu_id: this.ruleForm.u_id })
          .then(res => {
            if (res.data.errorCode !== 0) this.$message.error('删除失败！')
            this.$message.success('删除成功！')
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
    handleRemove (file, fileList) {
      console.log(file, fileList)
    },
    handlePreview (file) {
      console.log(file)
    },
    submitUpload (file, filelist) {
      this.$ref.upload.submit()
    }
  },
  created () {
  }
}
</script>

<style>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 120px;
  height: 120px;
  line-height: 120px;
  text-align: center;
}
.avatar {
  width: 120px;
  height: 120px;
  display: block;
}
</style>
