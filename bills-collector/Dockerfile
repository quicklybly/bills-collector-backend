FROM gradle:8.7-jdk21-alpine AS build
WORKDIR /app
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .

RUN gradle dependencies

COPY src src
RUN gradle bootJar --no-daemon

FROM eclipse-temurin:21-jre-alpine

RUN addgroup -S spring && adduser -S spring -G spring

USER spring:spring
WORKDIR /app

ENV LANG ru_RU.UTF-8
ENV LANGUAGE ru_RU:ru
ENV LC_ALL ru_RU.UTF-8

COPY --from=build /app/build/libs/*.jar app.jar

ENTRYPOINT ["java","-jar","/app/app.jar"]