<template>
  <el-container>
    <el-aside :width="asideWidth" style="min-height: 200vh; background-color:#001529">
      <div style="height: 60px;line-height: 60px;display: flex;justify-content: center; color: cyan;">
        <transition name="el-fade-in-linear">
          <span style="font-size: 13px;margin-left: 5px" v-show="!this.isCollapse">高校财务报账智能会计监督系统</span>
        </transition>
      </div>

      <el-menu router background-color="#001529" text-color="rgba(255,255,255,0.65)" style="border: none;"
               :default-active="activeMenu" active-text-color="#1890ff" :collapse="this.isCollapse"
               collapse-transition="false">

        <el-menu-item index="/home">
          <i class="el-icon-s-home"></i>
          <span>系统首页</span>
        </el-menu-item>

        <!-- 新增的“预约审批表”菜单项 -->
        <el-menu-item index="/approvalForm">
          <i class="el-icon-document"></i>
          <span>填表</span>
        </el-menu-item>

<!--        后期设置管理权限时可以加上 v-if = "localStorageUser.role === '用户'" , 让管理员看不到这玩意, 其它权限控制也同理-->
        <el-menu-item index="/userHistory">
          <i class="el-icon-document"></i>
          <span>历史</span>
        </el-menu-item>

        <el-menu-item index="/adminHistory" >
          <i class="el-icon-document"></i>
          <span>所有用户的单子</span>
        </el-menu-item>

        <el-menu-item index="/change" >
          <i class="el-icon-document"></i>
          <span>修改</span>
        </el-menu-item>

      </el-menu>
    </el-aside>

    <el-container>
      <el-header style="display: flex; justify-content: space-between; align-items: center; background-color: cornflowerblue; padding: 0 20px;">
        <el-button class="el-icon-s-operation" @click="handleCollapse" style="margin-right: 20px;"></el-button>
        <el-breadcrumb style="margin-right: 20px;">
          <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item :to="{ path: $route.path }">{{ $route.meta.title }}</el-breadcrumb-item>
        </el-breadcrumb>

        <div style="display: flex; align-items: center;">
          <el-dropdown trigger="hover" placement="bottom">
            <div style="display: flex; align-items: center;">
              <div style="font-size: medium; color: black">{{ localStorageUser.username }}</div>
            </div>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item @click.native="handlePersonal">个人信息</el-dropdown-item>
              <el-dropdown-item @click.native="handlePassword">修改密码</el-dropdown-item>
              <el-dropdown-item @click.native="handleExit">退出系统</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
      </el-header>

      <el-main>
        <router-view @update:user="updateUser"></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import router from "@/router";

export default {
  name: 'HomeView',
  data() {
    return {
      isCollapse: false,
      asideWidth: "200px",
      localStorageUser: JSON.parse(localStorage.getItem("localStorageUser") || "{}"),
      activeMenu: this.$route.path // 设置当前激活的菜单项
    }
  },
  watch: {
    '$route.path': function(newPath) {
      this.activeMenu = newPath; // 路由变化时更新活动菜单
    }
  },
  methods: {
    handleTable() {
      router.push("/tableManager");
    },
    handleApprovalForm() {
      router.push("/approvalForm");
    },
    handleCollapse() {
      this.isCollapse = !this.isCollapse;
      this.asideWidth = this.isCollapse ? "48px" : "200px";
    },
    handleExit() {
      this.$confirm('是否退出系统?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        localStorage.removeItem("localStorageUser");
        this.$router.push("/login");
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消退出'
        });
      });
    },
    handlePersonal() {
      router.push("/person");
    },
    updateUser() {
      this.localStorageUser = JSON.parse(localStorage.getItem("localStorageUser") || "{}");
    },
    handlePassword() {
      router.push("/password");
    }
  }
}
</script>

<style scoped>
/* 样式代码保持不变 */
</style>
