FROM eclipse-temurin:17-jdk AS builder

WORKDIR /app

COPY gradlew .
COPY gradle gradle
COPY build.gradle* settings.gradle* ./
RUN ./gradlew dependencies --no-daemon

COPY src src

RUN ./gradlew bootJar

FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]