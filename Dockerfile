FROM eclipse-temurin:21-jre-alpine

COPY build/libs/PoCSeminararbeit-1.0-SNAPSHOT.jar app.jar

COPY resources/certs/keystore_server keystore_server
COPY resources/certs/truststore_server truststore_server

ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080
EXPOSE 443