FROM openjdk:8
ADD target/ecommerce-docker.jar ecommerce-docker.jar
EXPOSE 8086
ENTRYPOINT ["java", "-jar", "ecommerce-docker.jar"]