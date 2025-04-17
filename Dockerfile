FROM openjdk:17-slim

# Install netcat so wait-for-it.sh works
RUN apt-get update && apt-get install -y netcat

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

ENTRYPOINT ["/wait-for-it.sh", "mariadb", "3306", "--", "java", "-jar", "/app.jar"]
