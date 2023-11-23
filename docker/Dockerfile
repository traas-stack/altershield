# 构建阶段
FROM maven:3.8.2-openjdk-8 AS build
RUN mkdir -p /opt/software/app
WORKDIR /opt/software/app
COPY ./ /opt/software/app
RUN mvn clean package

# 运行镜像
FROM openjdk:8-jdk-alpine
WORKDIR /app
COPY --from=build /opt/software/app/src/altershield-bootstrap/target/altershield-bootstrap-0.0.1-SNAPSHOT.jar /app/altershield.jar

# 安装Nginx
EXPOSE 8080
CMD ["java", "-jar", "/app/altershield.jar"]