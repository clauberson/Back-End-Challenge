# Sistema de Gerenciamento de Clientes e Contas Bancárias

## Descrição

Esta aplicação é um sistema de gerenciamento de clientes e contas bancárias. Ela permite cadastrar clientes (Pessoa Física ou Jurídica), criar contas bancárias para esses clientes e realizar transferências entre as contas. A aplicação também inclui tratamento de exceções, logging e testes unitários.

## Funcionalidades

1. **Cadastro de Clientes**:
   - Pessoa Física (PF) ou Pessoa Jurídica (PJ).
   - Cada cliente possui Nome, CPF/CNPJ, Endereço e Senha.
   - CPF/CNPJ devem ser únicos no sistema.

2. **Gerenciamento de Contas**:
   - Cada conta possui Id, Agência, Saldo e Status (Ativa/Inativa).
   - Id e Agência devem ser únicos no sistema.
   - As contas podem ser criadas para clientes existentes.

3. **Transferências**:
   - Realização de transferências entre contas.
   - Verificação do status da conta e saldo suficiente antes da transferência.
   - Operação transacional com suporte a reversão em caso de falhas.
   - Envio de notificações para ambos os clientes envolvidos na transferência.

## Requisitos Técnicos

- Java 17
- Spring Boot
- Spring Data JPA
- Banco de Dados H2
- Testes unitários com JUnit
- Documentação Swagger

## Configuração e Execução

### Pré-requisitos

- [Java 17](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html) deve estar instalado.
- [Maven](https://maven.apache.org/download.cgi) deve estar instalado.

### Instruções para Iniciar a Aplicação

1. Clone o repositório para sua máquina local:
   ```sh
   git clone https://github.com/seu-usuario/seu-repositorio.git
   cd seu-repositorio
   ```

2. Compile e execute a aplicação usando Maven:
   ```sh
   mvn clean install
   mvn spring-boot:run
   ```

3. A aplicação estará disponível em `http://localhost:8080`.

4. Acesse a documentação Swagger em `http://localhost:8080/swagger-ui.html` para explorar as APIs disponíveis.

### Instruções para Rodar os Testes

1. Para executar os testes unitários, use o comando Maven:
   ```sh
   mvn test
   ```

## Endpoints da API

### Cadastro de Clientes

- **POST /customers**
  - Payload:
    ```json
    {
        "name": "Ash Ketchum",
        "document": "12365478902",
        "address": "Cidade de Pallet",
        "password": "StrongPassword951!#@"
    }
    ```

### Criação de Contas

- **POST /accounts/{customerId}**
  - Payload:
    ```json
    {
        "agency": "0001",
        "balance": 1000,
        "active": true
    }
    ```

### Transferência entre Contas

- **POST /accounts/transfer**
  - Parâmetros:
    - `fromAccountId`: Id da conta de origem.
    - `toAccountId`: Id da conta de destino.
    - `amount`: Valor a ser transferido.
  - Exemplo de solicitação:
    ```
    POST /accounts/transfer?fromAccountId=1&toAccountId=2&amount=200
    ```

## Tratamento de Erros

- **400 Bad Request**: Para erros de validação ou negócios, como saldo insuficiente ou conta inexistente.
- **500 Internal Server Error**: Para erros inesperados no servidor.

## Logging

A aplicação utiliza SLF4J para logging. Os logs são configurados para diferentes níveis de severidade (DEBUG, INFO, WARN, ERROR) e ajudam no monitoramento e depuração da aplicação.

## Banco de Dados

A aplicação utiliza H2, um banco de dados em memória, para armazenamento dos dados. A configuração do banco de dados pode ser encontrada em `src/main/resources/application.properties`.

## Proposta de melhoria

- A aplicação poderia ser reescrita com arquitetura de  microserviços, para que cada serviço opere individualmente, reduzindo o acoplamento.

- A criação de perfis de acessos e controle de acesso através do Spring Security.

- Uso de API Gateway para fornecer uma camada adicional de segurança.

- Uso de alguma biblioteca de resiliência para permitir retentativa em caso de falhas.

- Uso de mensageria como por exemplo o Kafka ou RabbitMQ para aumentar o desacoplamento entre serviços.

- Cache de Segundo Nível, uso do Ehcache com Hibernate para otimizar o acesso a dados.

- Cache HTTP, uso de cabeçalhos HTTP Cache-Control para controlar o cache no lado do cliente e intermediários.

## Licença

Este projeto está licenciado sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---

