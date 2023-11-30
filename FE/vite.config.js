import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';
import * as path from 'node:path';
import { VitePWA } from 'vite-plugin-pwa';

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    react(),
    VitePWA({
      dest: 'public',
      disable: process.env.NODE_ENV === 'development',
      registerType: 'autoUpdate',
      devOptions: {
        enabled: true,
      },
      manifest: {
        short_name: 'Alphano',
        name: '알파노',
        description: 'AI 예측값 정보제공, 주식 자동매매 서비스 제공',
        start_url: '/',
        launch_handler: {
          client_mode: 'auto',
        },
        icons: [
          {
            src: '/favicon-192x192.png',
            type: 'image/png',
            sizes: '192x192',
          },
          {
            src: '/favicon-32x32.png',
            type: 'image/png',
            sizes: '32x32',
            purpose: 'maskable',
          },
          {
            src: '/mstile-96x96.png',
            type: 'image/png',
            sizes: '96x96',
          },
          {
            src: './icons/apple-touch-icon-57x57.png',
            sizes: '57x57',
            type: 'image/png',
          },
          {
            src: './icons/apple-touch-icon-60x60.png',
            sizes: '60x60',
            type: 'image/png',
          },
          {
            src: './icons/apple-touch-icon-72x72.png',
            sizes: '72x72',
            type: 'image/png',
          },
          {
            src: './icons/apple-touch-icon-76x76.png',
            sizes: '76x76',
            type: 'image/png',
          },
          {
            src: './icons/apple-touch-icon-114x114.png',
            sizes: '114x114',
            type: 'image/png',
          },
          {
            src: './icons/apple-touch-icon-120x120.png',
            sizes: '120x120',
            type: 'image/png',
          },

          {
            src: './icons/apple-touch-icon-144x144.png',
            sizes: '144x144',
            type: 'image/png',
          },
          {
            src: './icons/favicon-192x192.png',
            sizes: '192x192',
            type: 'image/png',
          },
          {
            src: './icons/apple-touch-icon-152x152.png',
            sizes: '152x152',
            type: 'image/png',
            purpose: 'maskable',
          },
          {
            src: './icons/mstile-512x512.png',
            sizes: '512x512',
            type: 'image/png',
            purpose: 'any',
          },
        ],
        screenshots: [
          {
            src: 'main.png',
            sizes: '395x787',
            type: 'image/jpg',
            platform: 'wide',
          },
        ],
        display_override: ['window-controls-overlay', 'standalone', 'browser'],
        edge_side_panel: {
          preferred_width: 400,
        },
      },
    }),
  ],
  define: {
    // By default, Vite doesn't include shims for NodeJS/
    // necessary for segment analytics lib to work
  },
  server: {
    proxy: {
      // '/api': {
      //   target: 'http://k9c106.p.ssafy.io:8081/',
      //   changeOrigin: true,
      //   rewrite: (path) => path.replace(/^\/api/, ''),
      //   secure: false,
      //   ws: true,
      // },
      '/uapi/': {
        target: 'https://openapivts.koreainvestment.com:29443',
        changeOrigin: true,
        secure: false,
        ws: true,
        pathRewrite: { '^/uapi/': '/' },
        onProxyRes: function (proxyRes) {
          proxyRes.headers['Access-Control-Allow-Origin'] =
            'https://k9c106.p.ssafy.io';
        },
      },
    },
  },

  resolve: {
    alias: {
      '@': path.resolve(__dirname, './src'),
    },
  },
});
