# **💸 Desafio Gestão API - Sistema de gestão de projetos e tarefas**

## **Descrição do Projeto**
Esta é uma API Rest para gerenciamento de um sistema de gestão de projetos desenvolvido em **Java** utilizando o **Spring Boot**. A aplicação oferece funcionalidades como criação de projetos e tarefas, busca por parametros, deleção, atualização e documentação com **Swagger UI**.

O objetivo deste projeto é resolver o [desafio](https://github.com/matheuslf/dev.matheuslf.desafio.inscritos) proposto por [Matheus Leandro Ferreira](https://github.com/matheuslf) demonstrando habilidades em desenvolvimento backend, persistência de dados, documentação e testes.

---

## **Índice**
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Funcionalidades](#funcionalidades)
- [Como Executar](#como-executar)
    - [Pré-requisitos](#pré-requisitos)
    - [Instalação](#instalação)
    - [Executando o Projeto](#executando-o-projeto)
- [Commits Semânticos](#commits-semânticos)
- [Endpoints da API](#endpoints-da-api)
- [Contribuição](#contribuição)
- [Licença](#licença)

---

## **Tecnologias Utilizadas**
- **Java 17**
- **Spring Boot 3+**
    - Spring Web
    - Spring Data JPA
    - Spring Validation
    - Flyway
- **PostgreSQL** como banco de dados relacional
- **JUnit 5** e **Mockito** para testes
- **Springdoc OpenAPI** para documentação da API (Swagger UI)
- **Maven** como gerenciador de dependências
- **Lombok** para reduzir boilerplate de código
- **Mapstruct** para transformar classes

---

## **Funcionalidades**
1. **Projeto**
    - Cadastro.
    - Buscar todos os projetos.

2. **Tarefa**
    - Cadastro.
    - Buscar tarefa por parametros.
    - Atualização de tarefa.
    - Exclusão de tarefa.

3. **Otimização da DB (Em Desenvolvimento)**
    - Paginação.
    - Simplificação de consultas.
    - Cache.

4. **Conteinerização (Em Desenvolvimento)**
    - Dockerfile.
    - Docker-Compose.

5. **Documentação da API (Em desenvolvimento)**
    - Documentação gerada pelo Springdoc OpenAPI, acessível via Swagger UI.
    - README do projeto no GitHub.

6. **Testes**
    - Testes unitários para validação das regras de negócio.
    - Testes de integração para os endpoints e o banco de dados.

---

## **Como Executar**

### **Pré-requisitos**
Antes de começar, certifique-se de ter instalado:
- **Java 17**
- **Maven**
- **Docker** (opcional, para executar o banco de dados PostgreSQL via container)
- Uma IDE como **IntelliJ IDEA** ou **Eclipse**

### **Instalação**
1. Clone este repositório:
```bash
   git clone https://github.com/MatheusHBMelo/desafio-api-gestao-de-projetos
   cd gestao_api
```
2. Configure o banco de dados PostgreSQL:

-   Certifique-se de que o PostgreSQL esteja rodando localmente.
-   Crie um banco de dados chamado `gestaoapidb`.
-   Atualize as credenciais no arquivo `application.properties`.

```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/springbankdb 
   spring.datasource.username=seu-usuario 
   spring.datasource.password=sua-senha
```

3. Compile o projeto:

```bash
   mvn clean install
```

### Executando o Projeto

- Inicie o servidor Spring Boot:

```bash
   mvn spring-boot:run
```
- A aplicação estará disponível em: http://localhost:8080


## **Commits Semânticos**

Adotei a convenção de **commits semânticos** para manter o histórico do repositório organizado e facilitar o entendimento das mudanças realizadas. Utilize o seguinte padrão para mensagens de commit:

### **Estrutura**
```properties
<tipo>: <descrição breve>
```

### **Tipos de Commit**

-   **feat**: Adição de uma nova funcionalidade.
    -   Exemplo: `feat: adiciona endpoint para criação de contas`
-   **fix**: Correção de bugs.
    -   Exemplo: `fix: corrige erro de autenticação no login`
-   **refactor**: Refatoração de código sem alterar funcionalidades.
    -   Exemplo: `refactor: melhora organização do serviço de transações`
-   **chore**: Alterações menores ou tarefas que não alteram o comportamento do código (e.g., atualizações de dependências).
    -   Exemplo: `chore: atualiza versão do Spring Boot`
-   **docs**: Alterações na documentação.
    -   Exemplo: `docs: adiciona seção sobre commits semânticos no README`
-   **test**: Adição ou modificação de testes.
    -   Exemplo: `test: adiciona teste unitário para o serviço de usuários`
-   **style**: Alterações relacionadas à formatação de código, semântica ou ajustes visuais.
    -   Exemplo: `style: aplica formatação ao código do controller`

## **Endpoints da API**

### Exemplos de Endpoints

#### **Projeto**

-   `POST /projects`: Criação de novo projeto (salva no banco de dados).
-   `GET /projects`: Retorna lista de projetos (no banco de dados).

#### **Tarefa**

-   `POST /tasks`: Criar nova tarefa vinculada a um projeto.
-   `GET /tasks?status=&priority=&projectId=`: Buscar tarefas com filtros.
-   `PUT /tasks/{id}/status`: Atualizar apenas o status da tarefa.
-   `DELETE /tasks/{id}`: Remover tarefa.

## **Contribuição**

Contribuições são bem-vindas! Siga os passos abaixo:

1.  Faça um fork deste repositório.
2.  Crie uma branch para sua feature ou correção:
```bash
git checkout -b feature/nova-feature 
``` 
3.  Faça o commit de suas alterações:
```bash
git commit -m "feat: detalhe da nova funcionalidade"
``` 
4.  Envie suas mudanças:

```bash
git push origin feature/nova-feature
```  
5.  Abra um Pull Request.

## **Licença**

Este projeto está licenciado sob a Licença MIT.

----------

### **Autor**

**Matheus Barbosa**

-   [LinkedIn](https://www.linkedin.com/in/matheushbmelo)
-   [GitHub](https://github.com/MatheusHBMelo)