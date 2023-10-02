FROM openjdk:17
VOLUME /tmp
ADD target/urban-mobility-1.0.jar urban-mobility.jar
#ADD target/urban-mobility-1.0.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
