
# Используем базовый образ с Maven и Java
FROM jelastic/maven:3.9.5-openjdk-21 AS build

# Указываем рабочую директорию внутри контейнера
WORKDIR /app

# Копируем исходный код в контейнер
COPY pom.xml .
COPY src ./src

# Собираем проект с помощью Maven
RUN mvn clean package -DskipTests

# Используем базовый образ с Java для запуска приложения
FROM openjdk:17-jdk-slim

# Указываем рабочую директорию внутри контейнера
WORKDIR /app

# Копируем собранный JAR-файл из этапа сборки
COPY --from=build /app/target/Bot-1.0-SNAPSHOT.jar /app/Bot-1.0-SNAPSHOT.jar