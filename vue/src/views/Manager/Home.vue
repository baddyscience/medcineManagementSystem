

<template>
  <div>
    <div style="margin-right: 20px; box-shadow: 0 0 10px rgba(0,0,0,0.1); padding: 10px 20px; border-radius: 5px; margin-bottom: 10px">
      医药系统登录成功！
    </div>

      <div style="display: flex">
        <el-card style="width: 100%; margin-right: 20px">

          <div slot="header" class="clearfix">
            切换主题颜色
          </div>

          <div style="margin-top: 5px; display: flex; flex-direction: column ;justify-content: center; align-items: center">
            <div style="margin: 20px; display: flex; justify-content: center; align-items: center; text-align: center">
              <strong>主题色</strong></div>

            <div>
              <el-button :style="{ backgroundColor: 'black', color: 'white' }">666</el-button>
              <el-button type="primary">666</el-button>
              <el-button type="danger">666</el-button>
            </div>

          </div>

        </el-card>
      </div>

  </div>
</template>

<style scoped>

</style>

<script>

import {defineAsyncComponent} from "vue";
import request from "@/utils/request";

export default {
  name: 'Home',
  data() {
    return {
      localStorageUser: JSON.parse(localStorage.getItem("localStorageUser") || "{}")
    }
  },
  mounted() {
    if (Object.keys(this.localStorageUser).length === 0){
      this.$router.push("/login")
    }
  },
  methods: {

  }
}
</script>