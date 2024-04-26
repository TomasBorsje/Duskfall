# syntax=docker/dockerfile:1

FROM eclipse-temurin:17-jre-focal
WORKDIR /duskfall-container
COPY . .
RUN chmod +x start.sh
CMD ["./start.sh"]
EXPOSE 25565