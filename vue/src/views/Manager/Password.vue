

<template>
  <el-card>
    <div slot="header">
      <span>修改密码</span>
    </div>
    <el-form ref="form" :model="form" label-width="80px" :rules="rules">
      <el-form-item label="旧密码" prop="oldPassword">
        <el-input v-model="form.oldPassword" show-password></el-input>
      </el-form-item>
      <el-form-item label="新密码" prop="newPassword">
        <el-input v-model="form.newPassword" show-password></el-input>
      </el-form-item>
      <el-form-item label="确认密码" prop="confirmPassword">
        <el-input v-model="form.confirmPassword" show-password></el-input>
      </el-form-item>
    </el-form>
    <div><el-button type="primary" @click="updateUser">修改密码</el-button></div>
  </el-card>
</template>

<style scoped>

</style>

<script>
import request from "@/utils/request";

export default {
  name: "Password",

  data() {

    const validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入旧密码'));
      } else if (value !== this.oldPassword) {
        callback(new Error('与旧密码不一致!'));
      } else {
        callback();
      }
    };

    const validatePass1 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.form.newPassword) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };

    return {
      user: [],
      oldPassword: "",
      rules: {
        oldPassword: [
          { required: true, validator: validatePass, trigger: 'blur' },
        ],
        newPassword: [
          { required: true, message: '请输入新密码', trigger: 'blur' },
        ],
        confirmPassword: [
          { required: true, validator: validatePass1, trigger: 'blur' },
        ]
      },
      form: {
        oldPassword: "",
        newPassword: "",
        confirmPassword: ""
      }
    }
  },
  methods: {
    updateUser(){
      this.user.password = this.form.newPassword
      delete this.user.email
      console.log(this.user)

      this.$request.put("/user/updateUserById", this.user).then(res => {
        if (res.code === '200'){
          this.$router.push("/login")
          this.$message.info("修改成功,请重新登录")
        }else {
          this.$message.error(res.msg)
        }

      })
    }
  },
  mounted() {
    this.user = JSON.parse(localStorage.getItem("localStorageUser") || "{}")
    delete this.user.password
    request.get("/user/getUserById" +"/"+ this.user.id).then(res => {
      this.oldPassword = res.data.password
    })
  }
}
</script>