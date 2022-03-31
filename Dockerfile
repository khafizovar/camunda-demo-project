FROM maven:3-openjdk-11 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:11-jdk-slim
ENV ENVIRONMENT=""
COPY --from=build /app/target/*.jar /app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar", "camunda.poc.Application", "--spring.profiles.active=${ENVIRONMENT}"]