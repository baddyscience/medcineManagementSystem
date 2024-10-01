const { defineConfig } = require('@vue/cli-service');
const path = require('path'); // 导入 path 模块

function resolve(dir) {
    return path.join(__dirname, dir);
}

module.exports = defineConfig({
    transpileDependencies: true,
    devServer: {
        port: 7000
    },
    chainWebpack: config => {
        config.plugin('html')
            .tap(args => {
                args[0].title = "医药管理系统";
                return args;
            });

    }
});
