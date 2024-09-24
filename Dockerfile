FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/Paypilot-0.0.1-SNAPSHOT.jar Paypilot-0.0.1-SNAPSHOT.jar
EXPOSE 8080
CMD [ "java" ,"-jar" ,"Paypilot-0.0.1-SNAPSHOT.jar" ]