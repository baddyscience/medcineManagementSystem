<template>
  <div>
    <!-- 模式开关 -->
    <div style="text-align: right; margin-bottom: 20px;">
      <el-switch
          v-model="isDetailedMode"
          active-text="详情模式"
          inactive-text="简洁模式"
          active-color="#409EFF"
          inactive-color="#909399"
      ></el-switch>
    </div>

    <!-- 表格显示 -->
    <div v-if="isDetailedMode">
      <!-- 详情模式的表格 -->
      <el-table
          :data="expenseList"
          border
          style="width: 100%"
          header-cell-style="background-color: #f5f7fa; font-weight: bold; text-align: center;"
          row-class-name="table-row"
      >
        <!-- 单选框列 -->
        <el-table-column
            label="选择"
            width="100"
            align="center"
        >
          <template slot-scope="scope">
            <el-radio
                v-model="selectedId"
                :label="scope.row.id"
            ></el-radio>
          </template>
        </el-table-column>

        <!-- 开支内容列 -->
        <el-table-column
            prop="expensecontent"
            label="开支内容"
            width="250"
            align="center"
        ></el-table-column>

        <!-- "开支内容描述" 列 -->
        <el-table-column
            prop="expensedescription"
            label="开支内容描述"
            align="left"
        ></el-table-column>
      </el-table>
    </div>

    <div v-else>
      <!-- 简洁模式下每行两个选择 -->
      <el-row :gutter="20">
        <el-col :span="12" v-for="(item, index) in expenseList" :key="item.id" v-if="index % 2 === 0">
          <el-row>
            <el-col :span="12">
              <el-radio v-model="selectedId" :label="expenseList[index].id">
                {{ expenseList[index].expensecontent }}
              </el-radio>
            </el-col>
            <el-col :span="12" v-if="expenseList[index + 1]">
              <el-radio v-model="selectedId" :label="expenseList[index + 1].id">
                {{ expenseList[index + 1].expensecontent }}
              </el-radio>
            </el-col>
          </el-row>
        </el-col>
      </el-row>
    </div>

    <!-- 打印当前选择的ID -->
    <div style="margin-top: 20px;">
      <p>当前选择的序号: {{ selectedId }}</p>
    </div>

    <!-- 下一步按钮 -->
    <div style="display: flex; justify-content: center; align-items: center; margin-top: 20px;">
      <div>
        <el-button size="large" class="large-button" @click="goTo">
          确定
        </el-button>
      </div>
    </div>

    <!-- Log 按钮 -->
    <div>
      <el-button @click="handleLog">Log</el-button>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";
import {EventBus} from "@/utils/eventBus";

export default {
  data() {
    return {
      selectedId: null, // 用于保存选择的单选框的值
      expenseList: [], // 数据列表
      isDetailedMode: true, // 控制模式，默认是详情模式
    };
  },
  mounted() {
    // 获取数据
    request.get("/zquRecl/getAllInfo").then(res => {
      this.expenseList = res.data;
    }).catch(error => {
      console.error("请求失败", error);
    });
  },
  methods: {
    handleLog() {
      console.log(this.expenseList);
      console.log(this.selectedId);
    },
    goTo() {
      let formId = null;
      let selectedExpenseContent = '';

      // 遍历列表，查找与 selectedId 匹配的项
      this.expenseList.forEach(item => {
        if (item.id === this.selectedId) {
          // 只保留整数部分
          formId = Math.floor(item.numberforsearch);
          selectedExpenseContent = item.expensecontent; // 保存选中的开支内容
        }
      });

      EventBus.$emit('expense-selected', selectedExpenseContent);

      // 如果找到了 formId，则拼接 URL
      if (formId !== null) {
        const url = '/table' + formId;
        console.log(url);
        EventBus.$emit('expense-selected', selectedExpenseContent, url);
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
