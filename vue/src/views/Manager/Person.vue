<template>
  <el-card class="user-card">
    <div slot="header" class="card-header">
      <span>个人信息</span>
    </div>
    <el-form label-width="100px" :model="user" ref="userForm" class="form-container">
      <el-form-item label="用户名" prop="username">
        <el-input v-model="user.username" disabled></el-input>
      </el-form-item>
      <el-form-item label="姓名" prop="name">
        <el-input v-model="user.name"></el-input>
      </el-form-item>
      <el-form-item label="电话" prop="phone">
        <el-input v-model="user.phone"></el-input>
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input v-model="user.email"></el-input>
      </el-form-item>
      <el-form-item label="地址" prop="address">
        <el-input v-model="user.address" type="textarea"></el-input>
      </el-form-item>
      <el-form-item label="身份" prop="role">
        <el-input v-model="user.role" disabled></el-input>
      </el-form-item>
    </el-form>
    <div class="form-footer">
      <el-button type="primary" @click="updateUser">保存</el-button>
    </div>
  </el-card>
</template>

<style scoped>
.user-card {
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
  background-color: #f7f9fc;
  border: 1px solid #dcdfe6;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.card-header {
  font-size: 24px;
  font-weight: bold;
  color: #409eff;
  text-align: center;
}

.form-container {
  margin-top: 20px;
}

.el-form-item__label {
  font-size: 18px;
  color: #34495e;
  font-weight: 500;
}

.el-input {
  border-radius: 4px;
}

.form-footer {
  text-align: center;
  margin-top: 20px;
}

.el-button {
  background-color: #409eff;
  border-color: #409eff;
  color: #fff;
  border-radius: 20px;
  padding: 10px 30px;
  font-size: 16px;
}

.el-button:hover {
  background-color: #66b1ff;
  border-color: #66b1ff;
}
</style>

<script>
export default {
  name: "Person",
  data() {
    return {
      user: JSON.parse(localStorage.getItem("localStorageUser") || "{}")
    };
  },
  methods: {
    updateUser() {
      this.$request.put("/user/updateUserById", this.user).then(res => {
        if (res.code === "200") {
          this.$message.success("上传成功");
        } else {
          this.$message.error(res.msg);
        }
        localStorage.setItem("localStorageUser", JSON.stringify(this.user));
        this.$emit("update:user", this.user);
      });
    }
  }
};
</script>
