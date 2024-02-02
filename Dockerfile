FROM openjdk:8u312-jdk-oracle
WORKDIR /app
COPY authority-0.0.1.jar .
ENTRYPOINT ["java","-jar","authority-0.0.1.jar"]