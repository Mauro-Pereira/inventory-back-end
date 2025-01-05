FROM gradle:7.6.0-jdk17 AS build

LABEL maintainer="Mauro Pereira <mauropereira1096@gmail.com>"

WORKDIR /app

# Copiar apenas o arquivo de configuração do Gradle e baixar as dependências
COPY build.gradle settings.gradle ./
RUN gradle build --no-daemon || return 0

# Copiar o restante do código e construir o pacote
COPY src ./src
RUN gradle build --no-daemon

# Usar uma imagem mais enxuta para a execução
FROM amazoncorretto:17-al2023

WORKDIR /app

# Copiar o jar construído para a imagem final
COPY --from=build /app/build/libs/Inventory-0.0.1-SNAPSHOT.jar .

EXPOSE 8080

CMD [ "java", "-jar", "Inventory-0.0.1-SNAPSHOT.jar" ]