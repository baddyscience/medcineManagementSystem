<template>
  <div class="login-container">
    <div class="login-box">
      <div class="login-image">
        <img src="@/assets/login.jpg" alt="Login Image">
      </div>
      <div class="login-form-container">
        <el-form :model="user" :rules="rules" ref="loginForm" class="login-form">
          <div class="login-title">登录</div>
          <el-form-item prop="username">
            <el-input prefix-icon="el-icon-user" placeholder="请输入用户名" v-model="user.username" size="medium"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input prefix-icon="el-icon-lock" show-password placeholder="请输入密码" v-model="user.password"></el-input>
          </el-form-item>
          <el-form-item prop="code">
            <div class="code-container">
              <el-input prefix-icon="el-icon-lock" placeholder="请输入验证码" v-model="user.code"></el-input>
              <div class="code-image">
                <valid-code @update:value="getCode"/>
              </div>
            </div>
          </el-form-item>
          <el-form-item>
            <el-button class="login-button" type="primary" @click="login">登录</el-button>
          </el-form-item>
          <div class="footer-links">
            <div class="register-link">还没有注册? 请 <span @click="() => { this.$router.push('/register') }">注册</span></div>
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
import validCode from "@/components/validCode.vue";

export default {
  name: "Login",
  components: {
    validCode
  },
  data() {
    const validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入验证码'));
      } else if (value.toString().toLowerCase() !== this.validCode.toString().toLowerCase()){
        callback(new Error('验证码错误'));
      } else {
        callback();
      }
    };

    return {
      validCode: '',
      user: {
        username: '',
        password: '',
        code: '',
      },
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
        ],
        code: [
          { required: true, validator: validatePass, trigger: 'blur' },
        ]
      }
    }
  },
  mounted() {
    this.$refs.validCode.initValidCode(); // 在组件挂载后初始化验证码
  },
  methods: {
    login() {
      this.$refs["loginForm"].validate((valid) => {
        if (valid) {
          this.$request.post("/login", this.user).then(res => {
            if (res.code === "200") {
              this.$message.success("登录成功");
              this.$router.push("/home");
              localStorage.setItem("localStorageUser", JSON.stringify(res.data));
            } else {
              this.$message.error(res.msg);
            }
          });
        }
      });
    },
    getCode(code) {
      this.validCode = code;
    },
    navigateToForgotPassword() {
      this.$router.push('/forgot-password'); // 跳转到忘记密码页面
    }
  }
}
</script>

<style scoped>
.login-container {
  height: 100vh;
  background-color: #f0f2f5;
  display: flex;
  justify-content: center;
  align-items: center;
}

.login-box {
  background-color: #ffffff;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  display: flex;
  width: 80%;
  max-width: 800px;
}

.login-image {
  flex: 1;
  border-top-left-radius: 8px;
  border-bottom-left-radius: 8px;
  overflow: hidden;
}

.login-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.login-form-container {
  flex: 1.5;
  padding: 20px;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.login-title {
  font-size: 24px;
  font-weight: 600;
  text-align: center;
  margin-bottom: 20px;
}

.code-container {
  display: flex;
  align-items: center;
}

.code-image {
  margin-left: 10px;
}

.login-button {
  width: 100%;
}

.footer-links {
  display: flex;
  justify-content: space-between;
  margin-top: 10px;
}

.register-link {
  color: #409EFF;
}

.register-link span {
  cursor: pointer;
}

.forgot-password-link a {
  color: #409EFF;
}
</style>
