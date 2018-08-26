FROM openjdk:8-slim
RUN mkdir -p /opt/kotlingboot
ADD . /opt/kotlingboot
WORKDIR /opt/kotlingboot
RUN ./gradlew build

FROM openjdk:8-slim
COPY --from=0 /opt/kotlingboot/build/libs/kotlingboot-0.0.1-SNAPSHOT.jar /opt/app.jar
ENV PORT 8888
CMD ["java", "-jar", "/opt/app.jar"]