#FROM openjdk:17
##FROM openjdk:17-jdk-alpine
#VOLUME /tmp
#ADD target/urban-mobility-1.0.jar urban-mobility.jar
##ADD target/urban-mobility-1.0.jar app.jar
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]

FROM openjdk:17
ARG JAR_FILE=target/urban-mobility-1.0.jar
COPY ${JAR_FILE} /application.jar
EXPOSE 8081
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]