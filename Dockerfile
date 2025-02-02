FROM openjdk:21-jdk-slim
COPY target/Bot-1.0-SNAPSHOT.jar /bot.jar
CMD ["java", "-jar", "/bot.jar"]