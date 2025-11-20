FROM gradle:latest AS build
WORKDIR /home/gradle/src
COPY . .
RUN gradle build

FROM eclipse-temurin:25-jdk
WORKDIR /app
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar
ENTRYPOINT ["bash", "-c", "java -jar app.jar 2> >(grep -v 'sun.misc.Unsafe')"]