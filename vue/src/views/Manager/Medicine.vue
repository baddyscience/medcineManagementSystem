<template>
  <el-container>

    <el-main>
      <el-button type="primary" @click="dialogVisible = true">添加药品</el-button>
      <el-table :data="medicines" style="width: 100%; margin-top: 20px;">
        <el-table-column prop="mno" label="药品编号" width="180"></el-table-column>
        <el-table-column prop="mname" label="药品名称" width="200"></el-table-column>
        <el-table-column prop="mmode" label="药品规格" width="180"></el-table-column>
        <el-table-column prop="mefficacy" label="药品功效" width="250"></el-table-column>
        <el-table-column label="操作" width="200">
          <template slot-scope="scope">
            <el-button @click="editMedicine(scope.row)" type="text" size="small">编辑</el-button>
            <el-button @click="deleteMedicine(scope.row.mno)" type="text" size="small" style="color: red;">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-dialog :title="isEditing ? '编辑药品' : '添加药品'" :visible.sync="dialogVisible">
        <el-form :model="currentMedicine">
          <el-form-item label="药品名称" prop="mname">
            <el-input v-model="currentMedicine.mname" required></el-input>
          </el-form-item>
          <el-form-item label="药品规格" prop="mmode">
            <el-input v-model="currentMedicine.mmode" required></el-input>
          </el-form-item>
          <el-form-item label="药品功效" prop="mefficacy">
            <el-input type="textarea" v-model="currentMedicine.mefficacy" required></el-input>
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
import axios from 'axios';

export default {
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
    fetchMedicines() {
      // 确保正确的 URL 路径
      axios.get('/medicine/find').then(response => {
        this.medicines = response.data;
      }).catch(error => {
        console.error('Error fetching medicines:', error);
      });
    },
    saveMedicine() {
      const method = this.isEditing ? 'put' : 'post';
      // 使用正确的 URL 路径
      const url = this.isEditing ? `/medicine/update/${this.currentMedicine.mno}` : '/medicine/save';
      axios[method](url, this.currentMedicine)
          .then(response => {
            this.dialogVisible = false;
            this.fetchMedicines();
            this.resetForm();
          })
          .catch(error => {
            console.error('Error saving medicine:', error);
          });
    },
    editMedicine(medicine) {
      this.currentMedicine = {...medicine};
      this.isEditing = true;
      this.dialogVisible = true;
    },
    deleteMedicine(mno) {
      this.$confirm('确认删除此药品吗?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 使用正确的 URL 路径
        axios.delete(`/medicine/delete/${mno}`).then(response => {
          this.fetchMedicines();
          this.$message.success('删除成功');
        }).catch(error => {
          console.error('Error deleting medicine:', error);
        });
      }).catch(() => {
        this.$message.info('已取消删除');
      });
    },
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
.el-header {
  background-color: #f2f2f2;
  padding: 10px;
}

.el-table {
  margin-top: 20px;
}
</style>
