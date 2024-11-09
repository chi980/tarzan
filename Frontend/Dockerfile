# 첫 번째 빌드 단계 (Node.js 애플리케이션 빌드)
FROM node:22.6.0 AS build

WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN . ls
RUN npm run

RUN npm run build

# 두 번째 단계: Nginx 서버로 배포
FROM nginx:alpine

# 첫 번째 단계에서 빌드한 파일을 Nginx에 복사
COPY --from=build /app/dist /usr/share/nginx/html

# Nginx 포트 80 오픈
EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
