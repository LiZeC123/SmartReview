
module.exports = {
    devServer: {
        proxy: {
            'api/' : {
                target: 'http://localhost:8792',
                ws: true, //代理websockets
                changeOrigin: true,
            },
            'download/': {
                target: 'http://localhost:8792',
                ws: true, //代理websockets
                changeOrigin: true,
            }
        }
    }
}