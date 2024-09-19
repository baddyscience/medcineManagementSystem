const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    port: 7000
  },
  chainWebpack: config =>{
    config.plugin('html')
        .tap(args => {
          args[0].title = "医药管理系统";
          return args;
        })
  }
})