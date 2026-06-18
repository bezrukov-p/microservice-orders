FROM eclipse-temurin:25-jdk
WORKDIR /app

# Копируем файлы Gradle
COPY gradlew .
COPY gradle gradle
COPY build.gradle* .
COPY settings.gradle* .
COPY src src

# Даем права на выполнение gradlew
RUN chmod +x gradlew

# Собираем приложение
RUN ./gradlew bootJar

RUN cp build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]