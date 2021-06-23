<template>

  <el-container class="home-container">
    <el-header>
      <div class="styleA">
        <img src="../assets/nao.png" alt="" class="avatar-box">
          <span>
        </span>
        </div>
        <el-button type="info" @click="logout">退出</el-button>
    </el-header>
    <el-container>
      <!-- 侧边栏菜单-->
      <el-aside :width="isCollapse? '64px' : '200px'">
        <div class="toggle-button" @click="toggleCollapse">|||</div>
        <el-menu
          background-color="#333744"
          text-color="#fff"
          active-text-color="#409eef"
          :collapse="isCollapse"
          :collapse-transition="false" router
          :default-active="activePath"
        >
          <!--一级菜单-->
          <el-submenu :index="item.id + ''" v-for="item in menulist" :key="item.id">
            <template slot="title">
              <i :class="iconsObj[item.id]"></i>
              <span>{{item.authName}}</span>
            </template>
            <!--二级菜单-->
            <el-menu-item :index="'/' + subItem.path" v-for="subItem in item.children" :key="subItem.id"
                          @click="saveNavState('/' + subItem.path)">
              <template slot="title">
                <i class="el-icon-menu"></i>
                <span>{{subItem.authName}}</span>
              </template>
            </el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>
      <el-main>
        <router-view>
        </router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  data: function () {
    return {
      menulist: [
        {
          authName: '用户管理',
          id: 125,
          path: 'users',
          children: [{
            authName: '用户列表',
            id: 110,
            path: 'users'
          }]
        },
        {
          authName: '出勤管理',
          id: 103,
          path: 'dutymnge',
          children: [{
            authName: '出勤排行',
            id: 111,
            path: 'dutysort'
          }]
        },
        {
          authName: '系统管理',
          id: 145,
          path: 'system',
          children: [{
            authName: '管理日志',
            id: 113,
            path: 'log'
          }]
        }
      ],
      iconsObj: {
        125: 'el-icon el-icon-user-solid',
        103: 'el-icon el-icon-s-tools',
        145: 'el-icon el-icon-s-claim'
      },
      isCollapse: false,
      activePath: ''
    }
  },
  created () {
    this.activePath = window.sessionStorage.getItem('activePath')
  },
  methods: {
    logout () {
      window.sessionStorage.clear()
      this.$router.push('/login')
    },
    toggleCollapse () {
      this.isCollapse = !this.isCollapse
    },
    saveNavState (activePath) {
      window.sessionStorage.setItem('activePath', activePath)
      this.activePath = activePath
    }
  }
}

</script>

<style lang="less" scoped>

.home-container {
  height: 100%;
}
.el-header {
  background-color: #373d41;
  display: flex;
  justify-content: space-between;
  padding-left: 0;
  span {
    color: #EFEFEF;
    margin-left: 20px;
    font-weight: 500;
  }
}

.el-aside {
  background-color: #333744;
  .el-menu{
    border-right: 0;
  }
}

.el-main {
  background-color: #eaedf1;
}

.el-icon {
  margin-right: 10px;
}

.toggle-button {
  background-color: #4a5064;
  font-size: 10px;
  line-height: 24px;
  color: #fff;
  text-align: center;
  letter-spacing: 0.2em;
  cursor: pointer;
}

.avatar-box{
  height: 50px;
  width: 50px;
  border: 1px solid #eee;
  border-radius: 50%;
  box-shadow: 0 0 10px #ddd;
  background-color: #fff;
  img{
    width: 100%;
    height: 100%;
    border-radius: 50%;
    background-color: #eee;
  }
}
</style>
