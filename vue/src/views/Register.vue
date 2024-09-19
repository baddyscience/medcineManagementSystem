<template>
  <div class="register-container">
    <div class="register-box">
      <div class="register-image">
        <img src="@/assets/login.jpg" alt="Register Image">
      </div>
      <div class="register-form-container">
        <el-form :model="user" :rules="rules" ref="registerForm" class="register-form">
          <div class="register-title">注册</div>
          <el-form-item prop="username">
            <el-input prefix-icon="el-icon-user" placeholder="请输入用户名" v-model="user.username" size="medium"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input prefix-icon="el-icon-lock" show-password placeholder="请输入密码" v-model="user.password"></el-input>
          </el-form-item>
          <el-form-item prop="confirmPassword">
            <el-input prefix-icon="el-icon-lock" placeholder="确认密码" v-model="user.confirmPassword"></el-input>
          </el-form-item>
          <el-form-item prop="role">
            <el-select v-model="user.role" placeholder="您的身份">
              <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button class="register-button" type="primary" @click="register">注册</el-button>
          </el-form-item>
          <div class="footer-links">
            <div class="login-link">已有帐户? 请 <span @click="() => { this.$router.push('/login') }">登录</span></div>
            <div class="forgot-password-link">
              <a @click.prevent="navigateToForgotPassword">忘记密码?</a>
            </div>
          </div>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Register",
  data() {
    const confirmPassword = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.user.password) {
        callback(new Error('前后密码不一致'));
      } else {
        callback();
      }
    };

    return {
      options: [
        { value: '管理员', label: '管理员' },
        { value: '用户', label: '用户' },
      ],
      user: {
        username: '',
        password: '',
        confirmPassword: '',
        role: '',
      },
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
        ],
        confirmPassword: [
          { required: true, trigger: 'blur', validator: confirmPassword },
        ],
        role: [
          { required: true, message: '请选择身份', trigger: 'change' },
        ],
      }
    }
  },
  methods: {
    register() {
      this.$refs["registerForm"].validate((valid) => {
        if (valid) {
          this.$request.post("/register", this.user).then(res => {
            if (res.code === '200') {
              this.$message.success("注册成功");
              this.$router.push("/login");
            } else {
              this.$message.error(res.msg);
            }
          });
        }
      });
    },
    navigateToForgotPassword() {
      this.$router.push('/forgot-password');
    }
  }
}
</script>

<style scoped>
.register-container {
  height: 100vh;
  background-color: #f0f2f5;
  display: flex;
  justify-content: center;
  align-items: center;
}

.register-box {
  background-color: #ffffff;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  display: flex;
  width: 80%;
  max-width: 800px;
}

.register-image {
  flex: 1;
  border-top-left-radius: 8px;
  border-bottom-left-radius: 8px;
  overflow: hidden;
}

.register-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.register-form-container {
  flex: 1.5;
  padding: 20px;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.register-title {
  font-size: 24px;
  font-weight: 600;
  text-align: center;
  margin-bottom: 20px;
}

.register-button {
  width: 100%;
}

.footer-links {
  display: flex;
  justify-content: space-between;
  margin-top: 10px;
}

.login-link {
  color: #409EFF;
}

.login-link span {
  cursor: pointer;
}

.forgot-password-link a {
  color: #409EFF;
}
</style>
