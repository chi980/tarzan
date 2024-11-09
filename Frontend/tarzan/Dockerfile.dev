# FROM node:22.6.0-alpine as builder

# # 작업 폴더를 만들고 npm 설치
# WORKDIR /usr/src/app
# COPY package.json /usr/src/app/package.json
# RUN npm install --force

# # 소스를 작업폴더로 복사하고 빌드
# COPY . /usr/src/app
# RUN npm run build
# Use a base image with Nginx
FROM nginx:alpine

# Copy the build files from the Vite build process
COPY dist/ /usr/share/nginx/html/

# Expose port 80 for serving the app
EXPOSE 80

# Start Nginx when the container is run
CMD ["nginx", "-g", "daemon off;"]
