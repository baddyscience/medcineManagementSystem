<!--
这是一个拒绝的页面, 表示当前用户没有访问权限

当用户访问了不该访问的页面时会跳转到这个页面, 在index.js里设置
-->

<template>
  <div class="page-403">
    <h1>访问被拒绝</h1>
    <p>抱歉，你没有权限访问这个页面。</p>
    <router-link to="/home" class="home-link">返回首页</router-link>
  </div>
</template>

<script>
export default {
  name: 'AccessDenied'
}
</script>

<style scoped>
.page-403 {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
  text-align: center;
  background: #f8d7da;
  color: #721c24;
}

.page-403 h1 {
  font-size: 2.5em;
  margin-bottom: 20px;
}

.page-403 p {
  font-size: 1.5em;
  margin-bottom: 30px;
}

.home-link {
  display: inline-block;
  padding: 10px 25px;
  font-size: 1em;
  color: #fff;
  background-color: #dc3545;
  text-decoration: none;
  border-radius: 5px;
  transition: background-color 0.3s;
}

.home-link:hover {
  background-color: #c82333;
}
</style>
