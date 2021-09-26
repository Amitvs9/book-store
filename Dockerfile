FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/book-store.jar
COPY ${JAR_FILE} book-store.jar
ENTRYPOINT ["java","-jar","/book-store.jar"]