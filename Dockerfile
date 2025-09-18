# Multi-stage build para otimizar tamanho da imagem
FROM maven:3.8.4-openjdk-17 AS build

# Definir diretório de trabalho
WORKDIR /app

# Copiar arquivos de dependências
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .

# Download dependencies (cache layer)
RUN mvn dependency:go-offline -B

# Copiar código fonte
COPY src src

# Build da aplicação
RUN mvn clean package -DskipTests

# Imagem final
FROM openjdk:17-jdk-slim

# Instalar dependências necessárias
RUN apt-get update && apt-get install -y \
    curl \
    && rm -rf /var/lib/apt/lists/*

# Criar usuário não-root
RUN groupadd -r loteria && useradd -r -g loteria loteria

# Definir diretório de trabalho
WORKDIR /app

# Copiar JAR da aplicação
COPY --from=build /app/target/loteria360-backend-*.jar app.jar

# Alterar propriedade dos arquivos
RUN chown -R loteria:loteria /app

# Mudar para usuário não-root
USER loteria

# Expor porta
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# Comando de execução
ENTRYPOINT ["java", "-jar", "app.jar"]
