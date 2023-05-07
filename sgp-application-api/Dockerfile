FROM eclipse-temurin:11

RUN apt-get update && apt-get -y upgrade
RUN apt-get install dumb-init

RUN groupadd javarunner && useradd -g javarunner -s /bin/sh runner
USER runner
WORKDIR /home/runner

COPY sgp-application-api/target/sgp-application-api-1.0-SNAPSHOT.jar sgp-application-api.jar

CMD ["dumb-init", "java", "-jar", "sgp-application-api"]