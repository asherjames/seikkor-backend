FROM openjdk:8

ADD . /opt/seikkor-api/

WORKDIR /opt/seikkor-api

CMD ./gradlew clean build

CMD java -jar build/libs/*.jar