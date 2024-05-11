FROM openjdk:21-slim
EXPOSE 8182
COPY "./target/stock-container-0.0.1-SNAPSHOT.jar" app.jar
CMD [ "java", "-jar", "app.jar" ]