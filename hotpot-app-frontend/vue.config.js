module.exports = {
  productionSourceMap: false,
  configureWebpack: {
    output: {
      filename: '[name].[hash:8].js'
    },
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
