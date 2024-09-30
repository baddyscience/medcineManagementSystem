<template>
  <el-container>
    <el-main>
      <el-button type="primary" @click="dialogVisible = true" style="margin-bottom: 20px;">添加经办</el-button>
      <el-button @click="reorderAgency" style="margin-bottom: 20px; margin-left: 10px;">重新排序</el-button> <!-- 添加重新排序按钮 -->
      <el-button @click="deleteSelectedAgencies" style="margin-bottom: 20px;" :disabled="multipleSelection.length === 0">批量删除</el-button><!-- 多选框列 -->
      <el-table :data="agencys" ref="multipleTable" @selection-change="handleSelectionChange" style="width: 100%; height: calc(100vh - 200px); margin-top: 20px;" stripe>
        <!-- 多选框列 -->
        <el-table-column type="selection" width="50"></el-table-column>
        <el-table-column prop="ano" label="经办人编号" width="100" sortable></el-table-column>
        <el-table-column prop="aname" label="经办人姓名" width="300"></el-table-column>
        <el-table-column label="性别" width="150">
          <template slot-scope="scope">
            <span>{{ scope.row.asex }}</span> <!-- 在浏览时显示文本 -->
          </template>
        </el-table-column>
        <el-table-column prop="aphone" label="电话号码" width="350"></el-table-column>
        <el-table-column prop="aremark" label="备注" width="550"></el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template slot-scope="scope">
            <el-button @click="editAgency(scope.row)" type="text" size="small">编辑</el-button>
            <el-button @click="deleteAgency(scope.row.ano)" type="text" size="small" style="color: red;">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-dialog :title="isEditing ? '编辑经办' : '添加经办'" :visible.sync="dialogVisible">
        <el-form :model="currentAgency" label-width="100px">
          <el-form-item label="经办人姓名" prop="aname">
            <el-input v-model="currentAgency.aname" required placeholder="请输入经办人姓名 "></el-input>
          </el-form-item>
          <el-form-item label="性别" prop="asex">
            <el-select v-model="currentAgency.asex" placeholder="请选择经办人性别" required>
              <el-option label="男" value="男"></el-option>
              <el-option label="女" value="女"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="电话" prop="aphone">
            <el-input type="textarea" v-model="currentAgency.aphone" required placeholder="请输电话号码" rows="1"></el-input>
          </el-form-item>
          <el-form-item label="备注" prop="aremark">
            <el-input type="textarea" v-model="currentAgency.aremark" required placeholder="请输备注" rows="4"></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="saveAgency">{{ isEditing ? '保存' : '添加' }}</el-button>
        </span>
      </el-dialog>
    </el-main>
  </el-container>
</template>

<script>
import request from '@/utils/request';

export default {
  name: 'Agency',
  data() {
    return {
      agencys: [],
      dialogVisible: false,
      isEditing: false,
      currentAgency: {
        ano: null,
        aname: '',
        asex: '',
        aphone: '',
        aremark: ''
      },
      multipleSelection: [] // 存储选中的记录
    };
  },
  methods: {
    // 处理多选框的选中状态
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    // 获取列表
    fetchAgency() {
      request.get('/agency/find')
          .then(response => {
            console.log('Response:', response); // 打印响应数据
            if (response && response.code === "200") {
              console.warn('获取经办人数据:', response.msg);
              this.agencys = response.data.sort((a, b) => a.ano - b.ano);
            } else {
              console.warn('没有数据:', response.msg);
              this.agencys = [];
            }
          })
          .catch(error => {
            console.error('Error fetching Agencys:', error);
            this.agecnys = [];
          });
    },
    // 保存或更新信息
    saveAgency() {
      const method = this.isEditing ? 'put' : 'post';
      const url = this.isEditing
          ? `/agency/update/${this.currentAgency.ano}`
          : '/agency/save';

      request[method](url, this.currentAgency)
          .then(response => {
            this.dialogVisible = false;
            this.$nextTick(() => { // 确保在下次 DOM 更新后执行
              this.fetchAgency();
              this.resetForm();
            });
          })
          .catch(error => {
            console.error('Error saving Agency:', error);
          });
    },
    // 编辑信息
    editAgency(agency) {
      this.currentAgency = { ...agency };
      this.isEditing = true;
      this.dialogVisible = true;
    },
    // 删除
    deleteAgency(ano) {
      this.$confirm('确认删除此经办人吗?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        request.delete(`/agency/delete/${ano}`)
            .then(response => {
              this.fetchAgency();
              this.$message.success('删除成功');
            })
            .catch(error => {
              console.error('Error deleting Agency:', error);
            });
      }).catch(() => {
        this.$message.info('已取消删除');
      });
    },
    deleteSelectedAgencies() {
      this.$confirm('确认删除选中的经办人吗?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 获取选中的经办人编号列表
        const anos = this.multipleSelection.map(item => item.ano);

        // 发送批量删除请求
        request.delete('/agency/deleteBatch', { data: { anos } })
            .then(response => {
              if (response.code === "200") {
                this.$message.success('批量删除成功');
                this.fetchAgency(); // 刷新列表
              } else {
                this.$message.error('删除失败: ' + response.msg);
              }
            })
            .catch(error => {
              console.error('批量删除出错:', error);
            });
      }).catch(() => {
        this.$message.info('已取消删除');
      });
    },
    // 重新排序编号
    reorderAgency() {
      console.log('重新排序按钮被点击'); // 调试信息

      const updatedAgency = this.agencys.map((agency, index) => ({
        ...agency,
        ano: index + 1 // 新的药品编号从 1 开始
      }));

      console.log('更新后的数据:', updatedAgency); // 打印更新的数据

      // 发送更新请求到后端
      request.put('/agency/reorder', updatedAgency)
          .then(response => {
            console.log('重新排序的响应:', response); // 打印响应
            if (response && response.code === "200") {
              this.$message.success('编号重新排序成功');
              this.fetchAgency(); // 重新获取药品列表
            } else {
              this.$message.error('重新排序失败: ' + response.msg);
            }
          })
          .catch(error => {
            console.error('Error reordering Agencys:', error);
          });
    },
    // 重置表单
    resetForm() {
      this.currentAgency = {
        ano: null,
        aname: '',
        asex: '',
        aphone: '',
        aremark: ''
      };
      this.isEditing = false;
    }
  },
  mounted() {
    this.fetchAgency();
  }
};
</script>

<style scoped>
.el-table {
  border: 1px solid #eaecef;
  border-radius: 8px;
  overflow: hidden;
}

.el-table th, .el-table td {
  padding: 16px;
  text-align: center; /* 中心对齐内容 */
}

.el-table th {
  background-color: #f5f7fa;
  color: #333;
  font-weight: bold;
}

.el-table td {
  transition: background-color 0.3s ease;
}

.el-table td:hover {
  background-color: #f0f0f0; /* 鼠标悬停时的背景颜色 */
}

@media (max-width: 768px) {
  .el-table {
    font-size: 12px; /* 在小屏幕上减小字体 */
  }
  .el-table th, .el-table td {
    padding: 10px; /* 在小屏幕上减小内边距 */
  }
}
</style>
