module.exports = {
  productionSourceMap: false,
  configureWebpack: {
    output: {
      filename: '[name].[hash:8].js'
    },
    resolve: {
      alias: {
        'vue$': 'vue/dist/vue.runtime.min.js'
      }
    }
  },
  css: {
    loaderOptions: {
      sass: {
        data: `
          @import "@/scss/constants.scss";
        `
      }
    }
  }
};
