#TODO
FROM openjdk:16-jdk-alpine
ADD build/libs/Recreation-Module-0.0.1-SNAPSHOT.jar recreation-app.jar
ENTRYPOINT ["java","-jar","recreation-app.jar"]