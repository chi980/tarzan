# 1단계: 빌드 단계 (Build Stage)
# FROM node:22.6.0 AS build
# 변경 후
FROM node:20 AS build

# 작업 디렉토리 설정
WORKDIR /app

# 의존성 파일 복사 (package.json, package-lock.json)
COPY package*.json ./

# 의존성 설치
RUN npm install

# 소스 코드 복사
COPY . .

# Vite 빌드 실행
RUN npm run build

# 2단계: Nginx 배포 단계 (Production Stage)
FROM nginx:alpine
RUN rm -rf /etc/nginx/conf.d/default.conf
COPY --from=build /app/nginx/default.conf /etc/nginx/conf.d/default.conf

# 빌드된 파일을 Nginx 컨테이너로 복사
RUN rm -rf /usr/share/nginx/html/*
COPY --from=build /app/dist /usr/share/nginx/html

# Nginx 포트 노출
EXPOSE 80

# 기본 Nginx 명령어 실행
CMD ["nginx", "-g", "daemon off;"]
