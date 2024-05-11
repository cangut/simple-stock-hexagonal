FROM openjdk:21-slim
EXPOSE 8183
COPY "./target/stock-query-container-0.0.1-SNAPSHOT.jar" app.jar
CMD [ "java", "-jar", "app.jar" ]