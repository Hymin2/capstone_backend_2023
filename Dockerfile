FROM openjdk:11-jdk
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} capstone.jar
ENTRYPOINT ["java","-Dspring.profiles.active=prod", "-jar","/capstone.jar"]