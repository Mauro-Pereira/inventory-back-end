FROM openjdk:17
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

# Etapa de Build
#FROM gradle:7.6.0-jdk17 AS build

#LABEL maintainer="Mauro Pereira <mauropereira1096@gmail.com>"

#WORKDIR /app

# Copiar arquivos de configuração e baixar dependências
#COPY build.gradle settings.gradle ./
#RUN gradle build --no-daemon || return 0

# Copiar código-fonte e construir o pacote
#COPY src ./src
#RUN gradle build --no-daemon

# Etapa de Execução
#FROM amazoncorretto:17-al2023

#LABEL maintainer="Mauro Pereira <mauropereira1096@gmail.com>"

#WORKDIR /app

# Copiar o jar construído para a imagem final
#COPY --from=build /app/build/libs/*.jar app.jar

#EXPOSE 8080

#CMD ["java", "-jar", "app.jar"]
