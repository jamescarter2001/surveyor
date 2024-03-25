/** @type {import('next').NextConfig} */
const nextConfig = {
    reactStrictMode: true,
    env: {
        SURVEYOR_BACKEND_URL: process.env.SURVEYOR_BACKEND_URL
    }
};

export default nextConfig;
