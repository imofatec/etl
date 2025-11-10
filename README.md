# ETL IMO

---

Sistema **ETL (Extract, Transform, Load)** desenvolvido para processar dados de usuarios , cursos e progresso para posteriormente ,calcular a **probabilidade de conclusão de um curso**. O sistema utiliza um algoritmo baseado em **5 dimensões ponderadas** para realizar previsões precisas sobre o engajamento e conclusão de cursos.

---

## 🎯 Algoritmo de Probabilidade

O cálculo considera **5 dimensões com pesos específicos**:

| Dimensão                  | Peso | Descrição |
|---------------------------|------|-----------|
| **Progresso de Conclusão** | 35%  | Percentual de aulas assistidas |
| **Compatibilidade Acadêmica** | 20%  | Match entre formação e nível do curso |
| **Compatibilidade de Interesses** | 20%  | Match entre interesses e categoria do curso |
| **Nível de Experiência**  | 15%  | Histórico de engajamento do usuário |
| **Disponibilidade de Tempo** | 10%  | Tempo disponível vs duração do curso |

---

## 🛠 Tecnologias Utilizadas

- **Java 21** – Linguagem de programação  
- **Spring Boot 3.5.7** – Framework principal  
- **Spring Batch 5.2.4** – Processamento em lote  
- **MongoDB 5.5.2** – Banco de dados NoSQL  
- **PostgreSQL 18.0** – Banco de dados relacional  
- **Flyway** – Gerenciamento de migrations  
- **Maven** – Gerenciamento de dependências  

---

## ⚙️ Configuração

### Clone

Rode no terminal para baixar o projeto:

```bash
git clone https://github.com/imofatec/etl.git
cd etl_imo
```

## Profile

Ative o perfil de desenvolvimento em `src/main/resources/application.properties` e adicione:

```bash
spring.config.import=optional:classpath:.env-dev.properties
spring.profiles.default=prod
spring.profiles.active=dev
```

### Envs
Crie um arquivo em `src/main/resources` chamado `.env-dev.properties` e adicione nele as variáveis de ambiente necessárias

```bash
MONGODB_URI=mongodb://localhost:27017/seu_banco_de_leitura
POSTGRES_URL=jdbc:postgresql://localhost:5432/seu_banco_para_spring_batch
POSTGRES_USERNAME=username
POSTGRES_PASSWORD=senha
```

### Banco de Dados para Spring Batch
Rode o comando `docker compose up -d` para subir o banco do Spring Batch

```bash
docker compose up -d
```

### Spring Boot

```bash
./mvnw spring-boot:run
```
