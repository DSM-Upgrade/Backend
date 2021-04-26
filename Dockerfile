FROM openjdk:11-jre-slim
WORKDIR /
COPY ./build/libs/*.jar app.jar
RUN "mkdir -p files/profile_image"
ENTRYPOINT ["java","-jar","./app.jar"]