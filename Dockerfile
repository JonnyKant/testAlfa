FROM openjdk:13
COPY build/libs/testAlfa-0.0.1-SNAPSHOT.jar testAlfa-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/testAlfa-0.0.1-SNAPSHOT.jar"]