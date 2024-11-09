# 빌드 단계
FROM node:18 AS build

# 작업 디렉토리 설정
WORKDIR /app

# package.json과 package-lock.json을 복사하여 의존성 설치
COPY package.json package-lock.json ./
RUN npm install

# 애플리케이션 파일을 복사
COPY . .

# Vue.js 빌드
RUN npm run build

# 배포 단계 (Nginx 사용)
FROM nginx:alpine

# 빌드된 파일을 Nginx 서버로 복사
COPY --from=build /app/dist /usr/share/nginx/html

# Nginx 포트 80으로 설정
EXPOSE 80

# Nginx 서버 시작
CMD ["nginx", "-g", "daemon off;"]
