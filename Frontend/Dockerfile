FROM node:22.6.0-alpine as builder

# 작업 디렉토리 설정
WORKDIR /app

# 의존성 파일 복사
COPY package.json package-lock.json ./

# 의존성 설치
RUN npm install

# Vite 프로젝트 빌드
COPY . .
RUN npm run build

# 2. Production stage (Nginx를 사용하여 빌드된 파일 서빙)
FROM nginx:alpine

# Nginx 설정 파일 복사 (필요시 Nginx 설정을 커스터마이즈할 수 있음)
COPY --from=build /app/dist /usr/share/nginx/html

# 80 포트 노출
EXPOSE 80

# Nginx 실행
CMD ["nginx", "-g", "daemon off;"]
