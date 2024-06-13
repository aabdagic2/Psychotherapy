FROM maven:3.8.4-openjdk-17 AS MAVEN_BUILD
COPY ./ ./
RUN mvn clean package

FROM openjdk:17
COPY --from=MAVEN_BUILD target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]