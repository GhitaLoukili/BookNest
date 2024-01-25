import { defineConfig } from 'vitest/config'
import react from '@vitejs/plugin-react'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  test: {
    setupFiles: ['./src/tests/testSetup.test.tsx'],
    globals: true,
    environment: 'jsdom',
    },
})
