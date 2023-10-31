# 使用官方的Java 8镜像作为基础镜像
FROM openjdk:8

# 将本地的jar文件复制到容器中
COPY ./target/*.jar /usr/local/app.jar

# 设置工作目录
WORKDIR /usr/local/

# 运行jar文件
CMD ["java", "-jar", "app.jar"]
