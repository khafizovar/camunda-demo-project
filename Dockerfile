FROM maven:3-openjdk-8 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:8-jdk-alpine
ENV ENVIRONMENT=""
COPY --from=build /app/gtm-basis/target/*.jar /app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar", "camunda.poc.Application", "--spring.profiles.active=${ENVIRONMENT}"]