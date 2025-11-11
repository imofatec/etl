# ETL IMO - Sistema de Análise de Probabilidade de Conclusão

Sistema ETL (Extract, Transform, Load) que processa dados de progresso de cursos 

## Tecnologias utilizadas

- **Linguagem**: [Java 21](https://www.java.com/pt-BR/)
- **Framework**: [Spring Boot 3.5.7](https://spring.io/projects/spring-boot)
- **Batch Processing**: [Spring Batch 5.2.4](https://spring.io/projects/spring-batch)
- **Banco de dados NoSQL**: [MongoDB 5.5.2](https://www.mongodb.com/)
- **Banco de dados Relacional**: [PostgreSQL 18.0](https://www.postgresql.org/)
- **Migration**: [Flyway](https://flywaydb.org/)

## Algoritmo de Probabilidade
O sistema calcula a probabilidade de conclusão baseado em **5 dimensões**:

| Dimensão | Peso | Descrição |
|----------|------|-----------|
| **Progresso de Conclusão** | 35% | Percentual de aulas assistidas |
| **Compatibilidade Acadêmica** | 20% | Match entre formação e nível do curso |
| **Compatibilidade de Interesses** | 20% | Match entre interesses e categoria do curso |
| **Nível de Experiência** | 15% | Histórico de engajamento do usuário |
| **Disponibilidade de Tempo** | 10% | Tempo disponível vs duração do curso |

## Requisitos

- Java 21
- Spring Batch
- Maven
- MongoDB 
- PostgreSQL

## Clone

```bash
git clone https://github.com/imofatec/etl
```

## Setup

### Profile

```properties
spring.profiles.default=prod
spring.profiles.active=dev
```

### Envs
Crie um arquivo em `src/main/resources` chamado `.env-dev.properties` e adicione nele as variáveis de ambiente necessárias


MONGODB_URI=mongodb://localhost:27017/x
POSTGRES_URL=jdbc:postgresql://localhost:5432/y
POSTGRES_USERNAME=xxxx
POSTGRES_PASSWORD=xxxx
```

Execute:
```bash
docker-compose up -d
```

### Spring Boot

```bash
./mvnw spring-boot:run
```


```bash
mvn spring-boot:run
```
## Fluxo de Execução

1. **Reader**: Carrega dados de `progress`, `users` e `courses` do MongoDB
   
2. **Processor**: Calcula `completion_probability` para cada registro
   
3. **Writer**: Insere resultados na collection `analytics`

### Exemplo de Resultado
```json
{
  "progress_id": "691084cd6d007306e6f1984b",
  "course_category": "DEV_MOBILE",
  "course_level": "Iniciante",
  "completion_rate": 0.6,
  "completion_probability": 0.6375
}
```
