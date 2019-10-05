## BUILDER
FROM gradle:5.4.1-jdk8-alpine AS builder
USER root
COPY src src
COPY build.gradle build.gradle
COPY settings.gradle settings.gradle
RUN gradle jar -DskipTests

# TESTER
FROM builder as tester
RUN gradle test

# RUNNER
FROM openjdk:8u212-jdk-alpine
COPY --from=builder /home/gradle/build/libs/*.jar app.jar
COPY newrelic newrelic
COPY run.sh run.sh
EXPOSE 8080

RUN chmod +x ./run.sh

ENTRYPOINT ["./run.sh"]