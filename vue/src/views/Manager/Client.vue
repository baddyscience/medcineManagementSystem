<template>
  <el-container>
    <el-main>

      <!-- 搜索表单 -->
      <el-form :model="searchForm" inline class="search-container">
        <el-form-item label="编号">
          <el-input v-model="searchForm.cno" placeholder="请输入编号"></el-input>
        </el-form-item>
        <el-form-item label="顾客姓名">
          <el-input v-model="searchForm.cname" placeholder="请输入顾客姓名"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-select v-model="searchForm.csex" placeholder="请选择顾客性别">
            <el-option label="男" value="男"></el-option>
            <el-option label="女" value="女"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="年龄">
          <el-input v-model="searchForm.cage" placeholder="请输入年龄"></el-input>
        </el-form-item>
        <el-form-item label="住址">
          <el-input v-model="searchForm.caddress" placeholder="请输入顾客住址"></el-input>
        </el-form-item>
        <el-form-item label="电话号码">
          <el-input v-model="searchForm.cphone" placeholder="请输入电话号码"></el-input>
        </el-form-item>
        <el-form-item label="症状">
          <el-input v-model="searchForm.csymptom" placeholder="请输入症状"></el-input>
        </el-form-item>
        <el-form-item label="药品编号">
          <el-input v-model="searchForm.mno" placeholder="请输入药品编号"></el-input>
        </el-form-item>
        <el-form-item label="经办人编号">
          <el-input v-model="searchForm.ano" placeholder="请输入经办人编号"></el-input>
        </el-form-item>
        <el-form-item label="日期">
          <el-input v-model="searchForm.cdate" placeholder="请输入录入日期"></el-input>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="searchForm.caddress" placeholder="请输入备注"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">搜索</el-button>
          <el-button @click="resetSearch">重置</el-button>
        </el-form-item>
      </el-form>

      <el-button type="primary" @click="dialogVisible = true" style="margin-bottom: 20px;">添加经办</el-button>
      <!--
      <el-button @click="reorderclient" style="margin-bottom: 20px; margin-left: 10px;">重新排序</el-button> <!-- 添加重新排序按钮 -->
      <el-button @click="deleteSelectedClients" style="margin-bottom: 20px;" :disabled="multipleSelection.length === 0">批量删除</el-button><!-- 多选框列 -->
      <el-button @click="exportToPDF" style="margin-bottom: 20px; margin-left: 10px;">保存数据</el-button>
      <el-table :data="clients" ref="multipleTable" @selection-change="handleSelectionChange" style="width: 100%; height: calc(100vh - 200px); margin-top: 20px;" stripe>
        <!-- 多选框列 -->

        <el-table-column prop="cno" label="编号" width="75" sortable></el-table-column>
        <el-table-column prop="cname" label="顾客姓名" width="90"></el-table-column>
        <el-table-column label="性别" width="75">
          <template slot-scope="scope">
            <span>{{ scope.row.csex }}</span> <!-- 在浏览时显示文本 -->
          </template>
        </el-table-column>
        <el-table-column prop="cage" label="年龄" width="80"></el-table-column>
        <el-table-column prop="caddress" label="地址" width="250"></el-table-column>
        <el-table-column prop="cphone" label="电话号码" width="100"></el-table-column>
        <el-table-column prop="csymptom" label="症状" width="150"></el-table-column>
        <el-table-column prop="mno" label="药品编号" width="75"></el-table-column>
        <el-table-column prop="ano" label="经办编号" width="75"></el-table-column>
        <el-table-column prop="cdate" label="录入日期" width="140"></el-table-column>
        <el-table-column prop="cremark" label="备注" width="150"></el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template slot-scope="scope">
            <el-button @click="editclient(scope.row)" type="text" size="small">编辑</el-button>
            <el-button @click="deleteclient(scope.row.cno)" type="text" size="small" style="color: red;">删除</el-button>
          </template>
        </el-table-column>
        <el-table-column type="selection" width="50"></el-table-column>
      </el-table>

      <el-dialog :title="isEditing ? '编辑顾客' : '添加顾客'" :visible.sync="dialogVisible">
        <el-form :model="currentclient" label-width="100px">
          <el-form-item label="顾客姓名" prop="cname">
            <el-input v-model="currentclient.cname" required placeholder="请输入顾客姓名 "></el-input>
          </el-form-item>
          <el-form-item label="性别" prop="csex">
            <el-select v-model="currentclient.csex" placeholder="请选顾客性别" required>
              <el-option label="男" value="男"></el-option>
              <el-option label="女" value="女"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="年龄" prop="cage">
            <el-input type="textarea" v-model="currentclient.cage" required placeholder="请输入年龄" rows="1"></el-input>
          </el-form-item>
          <el-form-item label="住址" prop="caddress">
            <el-input type="textarea" v-model="currentclient.caddress" required placeholder="请输入顾客住址" rows="2"></el-input>
          </el-form-item>
          <el-form-item label="电话" prop="aphone" :rules="[{ required: true, message: '请输入电话号码', trigger: 'blur' }, { pattern: /^1\d{10}$/, message: '电话号码应为11位数字', trigger: 'blur' }]">
            <el-input type="textarea" v-model="currentclient.cphone" required placeholder="请输电话号码" rows="1"></el-input>
          </el-form-item>
          <el-form-item label="症状" prop="csymptom">
            <el-input v-model="currentclient.csymptom" required placeholder="请输入症状"></el-input>
          </el-form-item>
          <template>
            <el-form-item label="药品编号" prop="mno">
              <el-select v-model="currentclient.mno" placeholder="选择药品编号" required>
                <el-option
                    v-for="item in medicines"
                    :key="item.mno"
                    :label="item.mno" :value="item.mno"/>
              </el-select>
            </el-form-item>
          </template>
          <el-form-item label="经办编号" prop="ano">
            <el-select v-model="currentclient.ano" placeholder="请输入经办编号" required >
              <el-option
                  v-for="item in agencys"
                  :key="item.ano"
                  :label="item.ano" :value="item.ano"/>
              </el-select>
          </el-form-item>
          <template>
            <el-form-item label="录入日期" prop="cdate">
              <div>
                <el-date-picker
                    v-model="currentclient.datePart"
                    type="date"
                    placeholder="选择日期"
                    format="yyyy-MM-dd"
                    value-format="yyyy-MM-dd"
                    required>
                </el-date-picker>
                <el-time-picker
                    v-model="currentclient.timePart"
                    format="HH:mm"
                    value-format="HH:mm"
                    placeholder="选择时间"
                    required>
                </el-time-picker>
                <input type="hidden" :value="currentclient.cdate" name="cdate">
              </div>
            </el-form-item>
          </template>
          <el-form-item label="备注" prop="caddress">
            <el-input type="textarea" v-model="currentclient.cremark" required placeholder="请输备注" rows="4"></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="saveclient">{{ isEditing ? '保存' : '添加' }}</el-button>
        </span>
      </el-dialog>
    </el-main>
  </el-container>
</template>

<script>
import request from '@/utils/request';
import jsPDF from "jspdf";
import 'jspdf-autotable';
import './dengl-normal'
import Agency from "@/views/Manager/Agency.vue";

export default {
  name: 'client',
  computed: {
    Agency() {
      return Agency
    }
  },
  data() {
    return {
      clients: [],
      medicines: [],
      agencys: [],
      dialogVisible: false,
      isEditing: false,
      loading: false, // 搜索加载状态
      noResults: false, // 无结果状态
      currentclient: {
        cno: null,
        cname: '',
        csex: '',
        cage: '',
        caddress: '',
        cphone: '',
        csymptom: '',
        mno: '',
        ano: '',
        cdate: '',
        cremark: '',
        datePart: '',
        timePart: ''
      },
      currentMedicine: {
        mno: null,
        mname: '',
        mmode: '',
        mefficacy: ''
      },
      currentAgency: {
        ano: null,
        aname: '',
        asex: '',
        aphone: '',
        aremark: ''
      },
      searchForm: { // 搜索表单
        cno: '',
        cname: '',
        csex: '',
        cage: '',
        caddress: '',
        cphone: '',
        csymptom: '',
        mno: '',
        ano: '',
        cdate: '',
        cremark: ''
      },
      multipleSelection: [] // 存储选中的记录
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

      request.get('/client/search', { params: query })
          .then(response => {
            this.clients = response.data;

            // 判断是否有结果
            this.noResults = this.clients.length === 0;
            this.loading = false; // 关闭加载状态
          })
          .catch(error => {
            this.loading = false; // 关闭加载状态
            this.$message.error('查询失败，请稍后再试');
            this.clients = [];
            this.noResults = true; // 查询失败时显示无结果状态
          });
    },

    resetSearch() {
      this.searchForm = {
        cno: '',
        cname: '',
        csex: '',
        cage: '',
        caddress: '',
        cphone: '',
        csymptom: '',
        mno: '',
        ano: '',
        cdate: '',
        cremark: ''
      };
      this.fetchclient();
      this.noResults = false; // 重置无结果状态
    },
    // 处理多选框的选中状态
    handleSelectionChange(val) {
      this.multipleSelection = val;
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
            this.agencys = [];
          });
    },
    // 保存或更新信息
    saveclient() {
      const method = this.isEditing ? 'put' : 'post';
      const url = this.isEditing
          ? `/client/update/${this.currentclient.cno}`
          : '/client/save';

      request[method](url, this.currentclient)
          .then(response => {
            this.dialogVisible = false;
            this.$nextTick(() => { // 确保在下次 DOM 更新后执行
              this.fetchclient();
              this.resetForm();
            });
          })
          .catch(error => {
            console.error('Error saving client:', error);
          });
    },
    // 编辑信息
    editclient(client) {
      this.currentclient = { ...client };
      this.isEditing = true;
      this.dialogVisible = true;
    },
    // 删除
    deleteclient(cno) {
      this.$confirm('确认删除此经办人吗?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        request.delete(`/client/delete/${cno}`)
            .then(response => {
              this.fetchclient();
              this.$message.success('删除成功');
            })
            .catch(error => {
              console.error('Error deleting client:', error);
            });
      }).catch(() => {
        this.$message.info('已取消删除');
      });
    },
    deleteSelectedClients() {
      this.$confirm('确认删除选中的经办人吗?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 获取所有选中的经办人编号，确保数据是数组
        const cnos = this.multipleSelection.map(item => item.cno);

        // 发送删除请求，直接发送数组
        request.delete('/client/deleteBatch', { data: cnos })
            .then(response => {
              if (response.code === "200") {
                this.$message.success('批量删除成功'); // 显示删除成功提示

                // 清空选中的项，并重新获取经办人列表以刷新编号
                this.multipleSelection = [];
                this.fetchclient(); // 调用方法刷新经办人数据
              } else {
                this.$message.error('删除失败: ' + response.msg);
              }
            })
            .catch(error => {
              console.error('批量删除出错:', error);
              this.$message.error('删除失败，请重试'); // 显示错误提示
            });
      }).catch(() => {
        this.$message.info('已取消删除'); // 用户取消删除时提示
      });
    },
    // 重新排序编号
    reorderclient() {
      console.log('重新排序按钮被点击'); // 调试信息

      const updatedclient = this.clients.map((client, index) => ({
        ...client,
        cno: index + 1 // 新的药品编号从 1 开始
      }));

      console.log('更新后的数据:', updatedclient); // 打印更新的数据

      // 发送更新请求到后端
      request.put('/client/reorder', updatedclient)
          .then(response => {
            console.log('重新排序的响应:', response); // 打印响应
            if (response && response.code === "200") {
              this.$message.success('编号重新排序成功');
              this.fetchclient(); // 重新获取药品列表
            } else {
              this.$message.error('重新排序失败: ' + response.msg);
            }
          })
          .catch(error => {
            console.error('Error reordering clients:', error);
          });
    },
    // 重置表单
    resetForm() {
      this.currentclient = {
        cno: null,
        cname: '',
        csex: '',
        cage: '',
        caddress: '',
        cphone: '',
        csymptom: '',
        mno: '',
        ano: '',
        cdate: '',
        cremark: ''
      };
      this.isEditing = false;
    },
    exportToPDF() {
      const doc = new jsPDF();

      doc.setFont('dengl');
      doc.text("表单数据", 10, 10);

      const columns = ["cno", "cname", "csex", "cage", "caddress", "cphone", "csymptom", "mno", "ano", "cdate", "cremark"];
      const rows = this.clients.map(client => [
        client.cno,
        client.cname,
        client.csex,
        client.cage,
        client.caddress,
        client.cphone,
        client.csymptom,
        client.mno,
        client.ano,
        client.cdate,
        client.cremark
      ]);

      doc.autoTable({
        head: [columns],
        body: rows,
        styles: {
          font: 'dengl', // 确保使用中文字体
          fontStyle: 'normal'
        }
      });

      doc.save('顾客表单数据.pdf');
    },

  },
  watch: {
    'currentclient.datePart'(newVal) {
      if (newVal && this.currentclient.timePart) {
        this.currentclient.cdate = `${newVal} ${this.currentclient.timePart}`;
      }
    },
    'currentclient.timePart'(newVal) {
      if (newVal && this.currentclient.datePart) {
        this.currentclient.cdate = `${this.currentclient.datePart} ${newVal}`;
      }
    }
  },
  mounted() {
    this.fetchclient();
    this.fetchMedicines();
    this.fetchAgency();
    if (this.currentclient.cdate) {
      const [date, time] = this.currentclient.cdate.split(' ');
      this.currentclient.datePart = date;
      this.currentclient.timePart = time;
    }
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
