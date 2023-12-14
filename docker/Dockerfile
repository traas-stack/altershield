# 构建阶段
FROM maven:3.8.2-openjdk-8 AS build
ENV MAVEN_OPTS="-Xmx1024m"

# 设置阿里云的镜像仓库
RUN sed -i 's/http:\/\/deb.debian.org/http:\/\/mirrors.aliyun.com/g' /etc/apt/sources.list && \
    sed -i 's/http:\/\/security.debian.org/http:\/\/mirrors.aliyun.com/g' /etc/apt/sources.list

# 设置Maven镜像仓库
RUN mkdir -p /root/.m2 && \
    echo "<settings><mirrors><mirror><id>aliyun</id><url>https://maven.aliyun.com/repository/public</url><mirrorOf>central</mirrorOf></mirror></mirrors></settings>" > /root/.m2/settings.xml

# 创建工作目录并复制项目文件
RUN mkdir -p /opt/software/app
WORKDIR /opt/software/app
COPY ./ /opt/software/app

# 构建项目
RUN mvn clean package

# 运行镜像
FROM openjdk:8-jdk-alpine
# 安装telnet和curl工具
RUN apk update && apk add --no-cache curl

# 安装telnet和curl工具
RUN apk update && apk add --no-cache curl
RUN mkdir -p /home/admin/logs
RUN chmod -R 777 /home/admin/logs

WORKDIR /app
COPY --from=build /opt/software/app/src/altershield-bootstrap/target/altershield-bootstrap-0.0.1-SNAPSHOT.jar /app/altershield.jar

# 安装Nginx
EXPOSE 8080
CMD ["java", "-jar", "/app/altershield.jar"]