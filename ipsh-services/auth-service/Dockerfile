FROM amazoncorretto:19.0.2-alpine3.17
RUN apk add openssl=3.0.15-r0
RUN apk add busybox=1.35.0-r31
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} psh-identity-service.jar
ENTRYPOINT ["java","-Xms512M","-Xmx1024M","-jar","/psh-identity-service.jar"]
