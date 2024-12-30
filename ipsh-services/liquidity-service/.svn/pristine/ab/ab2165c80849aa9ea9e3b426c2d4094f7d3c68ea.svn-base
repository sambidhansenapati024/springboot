FROM amazoncorretto:19.0.2-alpine3.17
RUN apk add openssl=3.0.12-r5
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /liquidity-service.jar
ENTRYPOINT ["java","-jar","/liquidity-service.jar"]