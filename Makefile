# Loteria360 Backend Makefile

.PHONY: help up down run test clean build

help: ## Show this help message
	@echo "Loteria360 Backend - Available commands:"
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-20s\033[0m %s\n", $$1, $$2}'

up: ## Start PostgreSQL and PgAdmin containers
	docker compose up -d

down: ## Stop all containers
	docker compose down

run: ## Run the Spring Boot application
	./mvnw spring-boot:run -Dspring-boot.run.profiles=dev

test: ## Run tests
	./mvnw test

clean: ## Clean Maven build
	./mvnw clean

build: ## Build the application
	./mvnw clean package -DskipTests

install: ## Install dependencies
	./mvnw clean install -DskipTests

flyway-migrate: ## Run Flyway migrations
	./mvnw flyway:migrate

flyway-info: ## Show Flyway migration info
	./mvnw flyway:info

format: ## Format code with Spotless
	./mvnw spotless:apply

check: ## Check code format
	./mvnw spotless:check

coverage: ## Generate test coverage report
	./mvnw jacoco:report

all: up run ## Start everything (containers + app)

stop: down ## Alias for down
