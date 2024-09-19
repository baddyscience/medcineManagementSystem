<template>
  <div>
    <div style="display: flex; justify-content: center; align-items: center; margin-top: 20px;">
      <div>
        <el-button size="large" class="large-button" @click="drawer = true" type="primary">
          点我打开
        </el-button>
      </div>
    </div>
    <el-drawer
        title="选择需要修改的表单"
        :visible.sync="drawer"
        :direction="direction"
        :before-close="handleClose">
      <el-row :gutter="20">
        <el-col :span="24" v-for="(item, index) in expenseList" :key="item.id">
          <el-radio v-model="selectedId" :label="expenseList[index].id" @change="goTo">
            {{ expenseList[index].expensecontent }}
          </el-radio>
        </el-col>
      </el-row>
    </el-drawer>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  data() {
    return {
      drawer: false,
      direction: 'rtl',
      selectedId: null,
      expenseList: []
    };
  },
  mounted() {
    request.get("/zquRecl/getAllInfo").then(res => {
      this.expenseList = res.data;
    }).catch(error => {
      console.error("请求失败", error);
    });
  },
  methods: {
    goTo() {
      let formId = null;
      this.expenseList.forEach(item => {
        if (item.id === this.selectedId) {
          formId = Math.floor(item.numberforsearch);
        }
      });
      if (formId !== null) {
        const url = '/table' + formId;
        console.log(url);
        this.$router.push(url);
      }
    }
  }
};
</script>

<style scoped>
/* 自定义样式 */
.large-button {
  font-size: 24px; /* 字体更大 */
  padding: 15px 40px; /* 调整按钮的内边距，让它显得更大 */
  height: 60px; /* 高度调整 */
  width: 200px; /* 宽度调整 */
}

/* 表格标题样式 */
.el-table th {
  background-color: #f5f7fa;
  font-weight: bold;
  text-align: center;
}

/* 表格行样式，添加行高和边距 */
.table-row {
  height: 50px;
}

/* 表格单元格样式 */
.el-table td {
  padding: 12px 8px;
  text-align: center;
  border-bottom: 1px solid #ebeef5;
}

/* 左对齐的描述列 */
.el-table-column--left td {
  text-align: left;
}

/* 鼠标悬停效果 */
.el-table .el-table__body-wrapper tbody tr:hover {
  background-color: #f9f9f9;
}

/* 单选框的间距调整 */
.el-radio {
  margin-left: 5px;
}

/* 当前选中行的高亮效果 */
.el-radio__input.is-checked .el-radio__inner {
  border-color: #409EFF;
  background-color: #409EFF;
}
</style>