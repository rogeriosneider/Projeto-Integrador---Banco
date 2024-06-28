# API de Integração Bancária

Este projeto de API permite a integração e gerenciamento de contas e lançamentos bancários através de endpoints RESTful. Utiliza Java com Spring Boot e MySQL como banco de dados.

## Função do Projeto

O projeto tem como objetivo oferecer uma API para realizar operações básicas bancárias, como criação de contas, consulta, encerramento de contas, depósitos, saques e transferências entre contas. Utiliza Swagger para documentação automática da API.

## Passos para Execução

### 1. Configuração do Banco de Dados

1. **Criação da Database, Usuário e Permissões:**

   Execute os seguintes comandos SQL no seu servidor MySQL:

   ```sql
   CREATE DATABASE integration_bank;

   CREATE USER 'integrator'@'localhost' IDENTIFIED BY 'projeto';
   GRANT ALL PRIVILEGES ON integration_bank.* TO 'integrator'@'localhost';
   FLUSH PRIVILEGES;
   ```

### 2. Execução do Projeto

1. Clone o repositório:

   ```
   git clone https://github.com/seu-usuario/integracao-bank.git
   ```

2. Navegue até o diretório do projeto:

   ```
   cd integracao-bank
   ```

3. Compile e execute o projeto com Maven:

   ```
   mvn spring-boot:run
   ```

   Ou execute o arquivo JAR gerado:

   ```
   java -jar target/integracao-bank.jar
   ```

### 3. Acesso ao Swagger

- Após iniciar o projeto, acesse a documentação interativa da API através do Swagger:

  [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

Este README.md fornece uma visão geral do projeto, incluindo os passos necessários para configuração do banco de dados, execução do projeto e acesso à documentação da API via Swagger. Certifique-se de ajustar conforme necessário para o seu ambiente específico.