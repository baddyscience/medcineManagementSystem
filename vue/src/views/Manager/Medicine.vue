<template>
  <el-container>

    <el-main>

      <!-- 搜索表单 -->
      <el-form :model="searchForm" inline class="search-container">
        <el-form-item label="编号">
          <el-input v-model="searchForm.mno" placeholder="请输入编号"></el-input>
        </el-form-item>
        <el-form-item label="药品名称">
          <el-input v-model="searchForm.mname" placeholder="请输入药品名称"></el-input>
        </el-form-item>
        <el-form-item label="服用方法">
          <el-select v-model="searchForm.mmode" placeholder="请选择服用方法">
            <el-option label="内服" value="内服"></el-option>
            <el-option label="外用" value="外用"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="药品功效">
          <el-input v-model="searchForm.mefficacy" placeholder="请输入药品功效"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">搜索</el-button>
          <el-button @click="resetSearch">重置</el-button>
        </el-form-item>
      </el-form>

      <el-button type="primary" @click="dialogVisible = true" style="margin-bottom: 20px;">添加药品</el-button>
      <!--
      <el-button @click="reorderMedicines" style="margin-bottom: 20px; margin-left: 10px;">重新排序</el-button> <!-- 添加重新排序按钮 -->
      <el-button @click="deleteSelectedMedicines" style="margin-bottom: 20px;" :disabled="multipleSelection.length === 0">批量删除</el-button><!-- 多选框列 -->
      <el-button @click="exportToPDF" style="margin-bottom: 20px; margin-left: 10px;">保存数据</el-button>
      <el-table :data="medicines" ref="multipleTable" @selection-change="handleSelectionChange" style="width: 100%; height: calc(100vh - 200px); margin-top: 20px;" stripe>
        <el-table-column prop="mno" label="编号" width="100" sortable></el-table-column>
        <el-table-column prop="mname" label="药品名称" width="300"></el-table-column>
        <el-table-column label="服用方法" width="150">
          <template slot-scope="scope">
            <span>{{ scope.row.mmode }}</span> <!-- 在浏览时显示文本 -->
          </template>
        </el-table-column>
        <el-table-column prop="mefficacy" label="药品功效" width="750"></el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template slot-scope="scope">
            <el-button @click="editMedicine(scope.row)" type="text" size="small">编辑</el-button>
            <!-- 判断是否可以删除 -->
            <template v-if="clients.some(client => client.mno === scope.row.mno)">
              <!-- 不可删除按钮 -->
              <el-button type="text" size="small" disabled style="color: grey;">不可删除</el-button>
            </template>
            <template v-else>
              <!-- 可删除按钮 -->
              <el-button
                  @click="deleteMedicine(scope.row.mno)"
                  type="text"
                  size="small"
                  style="color: red;">
                删除
              </el-button>
            </template>
          </template>
        </el-table-column>
        <el-table-column type="selection" width="50"></el-table-column>
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
import jsPDF from 'jspdf';
import 'jspdf-autotable';
import './dengl-normal'


export default {
  name: 'Medicine',
  data() {
    return {
      medicines: [],
      clients: [],
      dialogVisible: false,
      isEditing: false,
      loading: false, // 搜索加载状态
      noResults: false, // 无结果状态
      currentMedicine: {
        mno: null,
        mname: '',
        mmode: '',
        mefficacy: ''
      },
      searchForm: { // 搜索表单
        mno: '',
        mname: '',
        mmode: '',
        mefficacy: ''
      },
      multipleSelection: [], // 存储选中的记录
    };
  },
  methods: {
    handleSearch() {
      const query = { ...this.searchForm };
      Object.keys(query).forEach(key => {
        if (!query[key]) {
          delete query[key];
        }
      });

      this.loading = true; // 显示加载状态
      this.noResults = false; // 重置无结果状态

      request.get('/medicine/search', { params: query })
          .then(response => {
            this.medicines = response.data;

            // 判断是否有结果
            this.noResults = this.medicines.length === 0;
            this.loading = false; // 关闭加载状态
          })
          .catch(error => {
            this.loading = false; // 关闭加载状态
            this.$message.error('查询失败，请稍后再试');
            this.medicines = [];
            this.noResults = true; // 查询失败时显示无结果状态
          });
    },

    resetSearch() {
      this.searchForm = {
        mno: '',
        mname: '',
        mmode: '',
        mefficacy: ''
      };
      this.fetchMedicines();
      this.noResults = false; // 重置无结果状态
    },
    // 处理多选框的选中状态
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    // 获取药品列表
    fetchMedicines() {
      request.get('/medicine/find')
          .then(response => {
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
    // 获取列表
    fetchclient() {
      request.get('/client/find')
          .then(response => {
            console.log('Response:', response); // 打印响应数据
            if (response && response.code === "200") {
              console.warn('获取经办人数据:', response.msg);
              this.clients = response.data.sort((a, b) => a.cno - b.cno);
            } else {
              console.warn('没有数据:', response.msg);
              this.clients = [];
            }
          })
          .catch(error => {
            console.error('Error fetching clients:', error);
            this.clients = [];
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
      if (this.clients.some(client => client.mno === mno)) {
        this.$message.warning('该药品已关联到客户，无法删除');
        return;
      }

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
    // 批量删除
    deleteSelectedMedicines() {
      // 过滤出不可删除的药品（与客户相关联的药品）
      const nonDeletableMedicines = this.multipleSelection.filter(item =>
          this.clients.some(client => client.mno === item.mno)
      );

      if (nonDeletableMedicines.length > 0) {
        this.$message.warning('选中的药品中存在不可删除的项目');
        return;
      }

      this.$confirm('确认删除选中的药品吗?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 获取选中的经办人编号列表
        const mnos = this.multipleSelection.map(item => item.mno);

        // 发送批量删除请求
        request.delete('/medicine/deleteBatch', { data:  mnos  })
            .then(response => {
              if (response.code === "200") {
                this.$message.success('批量删除成功');
                this.fetchMedicines(); // 刷新列表
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
    },
    exportToPDF() {
      const doc = new jsPDF();

      doc.setFont('dengl');
      doc.text("表单数据", 10, 10);

      const columns = ["mno", "mname", "mmode", "mefficacy"];
      const rows = this.medicines.map(medicine => [
        medicine.mno,
        medicine.mname,
        medicine.mmode,
        medicine.mefficacy
      ]);

      doc.autoTable({
        head: [columns],
        body: rows,
        styles: {
          font: 'dengl', // 确保使用中文字体
          fontStyle: 'normal'
        }
      });

      doc.save('药品表单数据.pdf');
    }
  },
  mounted() {
    this.fetchMedicines();
    this.fetchclient();
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

.search-container {
  margin: 20px;
}
</style>
