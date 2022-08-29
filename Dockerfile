FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
RUN timedatectl set-timezone Asia/Tehran
ENTRYPOINT ["java","-jar","/app.jar"]