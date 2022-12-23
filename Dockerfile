FROM openjdk:8-jdk-alpine
MAINTAINER NaveedAmanat
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} adminservice.jar
ENTRYPOINT ["java","-jar","/adminservice.jar"]
EXPOSE 8484