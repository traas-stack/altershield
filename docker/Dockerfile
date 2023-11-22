# 构建阶段
FROM maven:3.8.2-openjdk-8 AS build

WORKDIR /app

COPY . /app

RUN mvn clean package

# 运行镜像
FROM openjdk:8-jdk-alpine

WORKDIR /app

COPY --from=build /app/target/altershield.jar /app/altershield.jar

# 安装Nginx
RUN apk add --no-cache nginx

COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 8080

CMD ["java", "-jar", "/app/altershield.jar"]
