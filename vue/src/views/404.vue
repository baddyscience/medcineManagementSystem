<template>
  <div class="page-404">
    <h1>哎呀！页面不见了。</h1>
    <p>我们找不到你请求的页面。可能它已经被移除，或者你输入了错误的地址。</p>
    <router-link to="/home" class="home-link">返回首页</router-link>
  </div>
</template>

<script>
export default {
  name: 'PageNotFound'
}
</script>

<style scoped>
.page-404 {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
  text-align: center;
  background: #f2f2f2;
  color: #333;
}

.page-404 h1 {
  font-size: 2em;
  margin-bottom: 20px;
}

.page-404 p {
  font-size: 1.2em;
  margin-bottom: 20px;
}

.home-link {
  display: inline-block;
  padding: 10px 20px;
  font-size: 1em;
  color: #fff;
  background-color: #007BFF;
  text-decoration: none;
  border-radius: 5px;
  transition: background-color 0.3s;
}

.home-link:hover {
  background-color: #0056b3;
}
</style>
