FROM maven:3.9.4-eclipse-temurin-17 as build
WORKDIR /app
# Set file encoding for Maven
ENV MAVEN_OPTS="-Dfile.encoding=UTF-8"
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "-Dfile.encoding=UTF-8", "app.jar"]