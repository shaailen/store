FROM openjdk:17-jdk-slim

EXPOSE 8080

ADD build/libs/store-1.0.0-SNAPSHOT.jar store.jar

ENTRYPOINT ["java","-jar","/store.jar"]