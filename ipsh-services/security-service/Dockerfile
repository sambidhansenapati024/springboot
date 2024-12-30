FROM amazoncorretto:19.0.2-alpine3.17
RUN apk add openssl=3.0.15-r1
RUN apk add busybox=1.35.0-r31
RUN apk add libssl3=3.0.15-r1
RUN apk add libcrypto3=3.0.15-r1
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} security-service.jar
RUN apk add --no-cache msttcorefonts-installer fontconfig
RUN update-ms-fonts
ENTRYPOINT ["java","-Xms1024M","-Xmx2048M","-jar","/security-service.jar"]