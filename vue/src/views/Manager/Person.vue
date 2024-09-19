

<template>
    <el-card>
      <div slot="header">
        <span>个人信息</span>
      </div>
        <el-form label-width="80px" :model="user" ref="userForm">
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
          <el-form-item label="地址" prop="address" >
            <el-input v-model="user.address" type="textarea"></el-input>
          </el-form-item>
          <el-form-item label="身份" prop="role">
            <el-input v-model="user.role" disabled="true"></el-input>
          </el-form-item>
        </el-form>
        <div>
          <el-button @click="updateUser">保存</el-button>
        </div>
    </el-card>
</template>

<style scoped>
/deep/.el-form-item__label{
  font-size: 20px !important;
  color: #c82333 !important;

}

</style>

<script>
export default {
  name: "Person",
  data(){
    return{
      user:JSON.parse(localStorage.getItem("localStorageUser") || "{}")
    }
  },
  methods:{
    updateUser(){
      this.$request.put("/user/updateUserById", this.user).then(res => {
        if (res.code === '200'){
          this.$message.success("上传成功")
        }else {
          this.$message.error(res.msg)
        }
        localStorage.setItem("localStorageUser", JSON.stringify(this.user))
        this.$emit("update:user", this.user)

      })
    }
  }
}
</script>