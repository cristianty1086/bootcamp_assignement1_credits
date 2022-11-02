FROM openjdk:11
VOLUME /tmp
EXPOSE 443
ADD ./target/credits-0.0.1-SNAPSHOT.jar ms-credits.jar
ENTRYPOINT ["java", "-jar", "ms-credits.jar"]