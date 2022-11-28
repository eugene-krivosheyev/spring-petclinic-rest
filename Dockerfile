FROM eclipse-temurin:11-jre
WORKDIR /opt
ENTRYPOINT ["java", "-jar", "app.jar"]
COPY target/spring-petclinic-2.7.0-SNAPSHOT.jar ./app.jar
