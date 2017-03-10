FROM openjdk:8

ADD . /opt/seikkor-api/

WORKDIR /opt/seikkor-api

RUN ./gradlew clean build

ENTRYPOINT ["java", "-jar", "build/libs/seikkor-api-0.0.1.jar"]