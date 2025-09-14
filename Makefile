# Loteria360 Backend Makefile

.PHONY: help up down run test clean build docker-build

# Default target
help:
	@echo "Loteria360 Backend - Comandos disponíveis:"
	@echo "  up          - Subir serviços Docker (MySQL + Adminer)"
	@echo "  down        - Parar serviços Docker"
	@echo "  run         - Executar aplicação em modo desenvolvimento"
	@echo "  test        - Executar testes"
	@echo "  clean       - Limpar artefatos Maven"
	@echo "  build       - Compilar projeto"
	@echo "  docker-build- Construir imagem Docker"

# Subir serviços Docker
up:
	docker compose up -d
	@echo "Serviços Docker iniciados!"
	@echo "MySQL: localhost:3306"
	@echo "Adminer: http://localhost:8081"

# Parar serviços Docker
down:
	docker compose down
	@echo "Serviços Docker parados!"

# Executar aplicação
run:
	./mvnw spring-boot:run -Dspring-boot.run.profiles=dev

# Executar testes
test:
	./mvnw test

# Limpar artefatos
clean:
	./mvnw clean

# Compilar projeto
build:
	./mvnw clean compile

# Construir imagem Docker
docker-build:
	docker build -t loteria360-backend .

# Executar com Docker Compose completo
docker-run: up
	@echo "Aguardando MySQL inicializar..."
	@sleep 10
	./mvnw spring-boot:run -Dspring-boot.run.profiles=dev

# Setup completo (primeira execução)
setup: up
	@echo "Aguardando MySQL inicializar..."
	@sleep 15
	@echo "Executando migrações e iniciando aplicação..."
	./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
