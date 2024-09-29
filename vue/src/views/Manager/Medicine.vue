<template>
  <el-container>
    <el-main>
      <el-button type="primary" @click="dialogVisible = true" style="margin-bottom: 20px;">添加药品</el-button>
      <el-button @click="reorderMedicines" style="margin-bottom: 20px; margin-left: 10px;">重新排序</el-button> <!-- 添加重新排序按钮 -->
      <el-table :data="medicines" style="width: 100%; height: calc(100vh - 200px); margin-top: 20px;" stripe>
        <el-table-column prop="mno" label="药品编号" width="100" sortable></el-table-column>
        <el-table-column prop="mname" label="药品名称" width="300"></el-table-column>
        <el-table-column label="服用方法" width="150">
          <template slot-scope="scope">
            <span>{{ scope.row.mmode }}</span> <!-- 在浏览时显示文本 -->
          </template>
        </el-table-column>
        <el-table-column prop="mefficacy" label="药品功效" width="750"></el-table-column>
        <el-table-column label="操作" width="200">
          <template slot-scope="scope">
            <el-button @click="editMedicine(scope.row)" type="text" size="small">编辑</el-button>
            <el-button @click="deleteMedicine(scope.row.mno)" type="text" size="small" style="color: red;">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-dialog :title="isEditing ? '编辑药品' : '添加药品'" :visible.sync="dialogVisible">
        <el-form :model="currentMedicine" label-width="100px">
          <el-form-item label="药品名称" prop="mname">
            <el-input v-model="currentMedicine.mname" required placeholder="请输入药品名称"></el-input>
          </el-form-item>
          <el-form-item label="服用方法" prop="mmode">
            <el-select v-model="currentMedicine.mmode" placeholder="请选择服用方法" required>
              <el-option label="内服" value="内服"></el-option>
              <el-option label="外用" value="外用"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="药品功效" prop="mefficacy">
            <el-input type="textarea" v-model="currentMedicine.mefficacy" required placeholder="请输入药品功效" rows="4"></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="saveMedicine">{{ isEditing ? '保存' : '添加' }}</el-button>
        </span>
      </el-dialog>
    </el-main>
  </el-container>
</template>

<script>
import request from '@/utils/request';

export default {
  name: 'Medicine',
  data() {
    return {
      medicines: [],
      dialogVisible: false,
      isEditing: false,
      currentMedicine: {
        mno: null,
        mname: '',
        mmode: '',
        mefficacy: ''
      }
    };
  },
  methods: {
    // 获取药品列表
    fetchMedicines() {
      request.get('/medicine/find')
          .then(response => {
            console.log('Response:', response); // 打印响应数据
            if (response && response.code === "200") {
              console.warn('获取药品数据:', response.msg);
              this.medicines = response.data.sort((a, b) => a.mno - b.mno);
            } else {
              console.warn('没有药品数据:', response.msg);
              this.medicines = [];
            }
          })
          .catch(error => {
            console.error('Error fetching medicines:', error);
            this.medicines = [];
          });
    },
    // 保存或更新药品信息
    saveMedicine() {
      const method = this.isEditing ? 'put' : 'post';
      const url = this.isEditing
          ? `/medicine/update/${this.currentMedicine.mno}`
          : '/medicine/save';

      request[method](url, this.currentMedicine)
          .then(response => {
            this.dialogVisible = false;
            this.$nextTick(() => { // 确保在下次 DOM 更新后执行
              this.fetchMedicines();
              this.resetForm();
            });
          })
          .catch(error => {
            console.error('Error saving medicine:', error);
          });
    },
    // 编辑药品信息
    editMedicine(medicine) {
      this.currentMedicine = { ...medicine };
      this.isEditing = true;
      this.dialogVisible = true;
    },
    // 删除药品
    deleteMedicine(mno) {
      this.$confirm('确认删除此药品吗?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        request.delete(`/medicine/delete/${mno}`)
            .then(response => {
              this.fetchMedicines();
              this.$message.success('删除成功');
            })
            .catch(error => {
              console.error('Error deleting medicine:', error);
            });
      }).catch(() => {
        this.$message.info('已取消删除');
      });
    },
    // 重新排序药品编号
    reorderMedicines() {
      console.log('重新排序按钮被点击'); // 调试信息

      const updatedMedicines = this.medicines.map((medicine, index) => ({
        ...medicine,
        mno: index + 1 // 新的药品编号从 1 开始
      }));

      console.log('更新后的药品数据:', updatedMedicines); // 打印更新的数据

      // 发送更新请求到后端
      request.put('/medicine/reorder', updatedMedicines)
          .then(response => {
            console.log('重新排序的响应:', response); // 打印响应
            if (response && response.code === "200") {
              this.$message.success('药品编号重新排序成功');
              this.fetchMedicines(); // 重新获取药品列表
            } else {
              this.$message.error('重新排序失败: ' + response.msg);
            }
          })
          .catch(error => {
            console.error('Error reordering medicines:', error);
          });
    },
    // 重置表单
    resetForm() {
      this.currentMedicine = {
        mno: null,
        mname: '',
        mmode: '',
        mefficacy: ''
      };
      this.isEditing = false;
    }
  },
  mounted() {
    this.fetchMedicines();
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
