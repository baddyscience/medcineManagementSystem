<template>
  <el-container>
    <el-main>
      <div style="display: flex; flex-wrap: wrap; gap: 20px; justify-content: space-between;">
        <!-- 库存警报 -->
        <el-card class="dashboard-card" shadow="always">
          <div slot="header">库存警报</div>
          <p>显示库存不足的药品...</p>
        </el-card>

        <!-- 药品销售统计 -->
        <el-card class="dashboard-card" shadow="always">
          <div slot="header">药品销售统计</div>
          <p>显示销售数据...</p>
        </el-card>

        <!-- 预约记录 -->
        <el-card class="dashboard-card" shadow="always">
          <div slot="header">预约记录</div>
          <p>显示即将到期的预约...</p>
        </el-card>

        <!-- 用户任务提醒 -->
        <el-card class="dashboard-card" shadow="always">
          <div slot="header">用户任务提醒</div>
          <p>待处理的工作事项...</p>
        </el-card>
      </div>

      <!-- 快捷操作 -->
      <div style="margin-top: 20px;">
        <el-button type="primary" @click="handleAddMedicine">添加药品</el-button>
        <el-button type="success" @click="handleCreateOrder">创建订单</el-button>
        <el-button type="warning" @click="handleViewReports">查看报表</el-button>
        <el-button type="danger" @click="handleManageInventory">管理库存</el-button>
      </div>
    </el-main>
  </el-container>
</template>

<script>
import {defineAsyncComponent} from "vue";
import request from "@/utils/request";

export default {
  name: "Home",
  data() {
    return {
      localStorageUser: JSON.parse(localStorage.getItem("localStorageUser") || "{}")
    };
  },
  mounted() {
    if (Object.keys(this.localStorageUser).length === 0){
      this.$router.push("/login")
    }
  },
  methods: {
    handleAddMedicine() {
      this.$router.push("/medicine/add");
    },
    handleCreateOrder() {
      this.$router.push("/order/create");
    },
    handleViewReports() {
      this.$router.push("/reports");
    },
    handleManageInventory() {
      this.$router.push("/inventory/manage");
    },
    handlePersonal() {
      this.$router.push("/person");
    },
    handlePassword() {
      this.$router.push("/password");
    },
    handleExit() {
      localStorage.removeItem("localStorageUser");
      this.$router.push("/login");
    }
  }
};
</script>

<style scoped>
/* 仪表盘卡片样式 */
.dashboard-card {
  width: 23%;
  padding: 20px;
  background-color: #f5f5f5;
  border-radius: 10px;
  transition: box-shadow 0.3s ease;
}

.dashboard-card:hover {
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
}

.el-header {
  background-color: #004080;
}

.el-menu-item {
  font-size: 16px;
}

.el-menu-item:hover {
  background-color: #1a75ff;
}
</style>
