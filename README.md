# Nine Ecommerce Back-End

Este é o repositório do back-end do projeto **Nine**, uma aplicação de e-commerce desenvolvida em **Java** utilizando o framework **Spring Boot**. O sistema fornece funcionalidades essenciais para gerenciar um e-commerce, incluindo autenticação de usuários, gerenciamento de produtos, pedidos, carrinho de compras e integração com gateways de pagamento.

## Funcionalidades Principais

- **Autenticação e Autorização**:

  - Registro e login de usuários com suporte a tokens JWT.
  - Controle de acesso baseado em papéis (admin, cliente, etc.).
- **Gerenciamento de Produtos**:

  - CRUD (Create, Read, Update, Delete) de produtos.
  - Suporte a categorias e filtros de busca.

## Como Executar

1. **Clone o repositório**:

   ```bash
   git clone https://github.com/seu-usuario/nine-ecommerce-back-end.git
   ```
2. **Configure o banco de dados**:

   - Crie um banco de dados no PostgreSQL (ou outro banco configurado).
   - Atualize as credenciais no arquivo `application.properties`:
     ```properties
     spring.datasource.url=jdbc:postgresql://localhost:5432/nine
     spring.datasource.username=seu-usuario
     spring.datasource.password=sua-senha
     spring.jpa.hibernate.ddl-auto=update
     ```
3. **Compile e execute o projeto**:

   ```bash
   ./mvnw spring-boot:run
   ```
4. O servidor estará disponível em: [http://localhost:8080](http://localhost:8080)

## Endpoints Principais

### Autenticação

- `POST /auth/login`: Autenticação de usuários.

  - **Request Body**:
    ```json
    {
      "username": "usuario",
      "password": "senha"
    }
    ```
  - **Response**:
    ```json
    {
      "token": "jwt-token-gerado"
    }
    ```
- `POST /auth/register`: Registro de novos usuários.

  - **Request Body**:
    ```json
    {
      "username": "novoUsuario",
      "password": "novaSenha"
    }
    ```

### Recursos

- `GET /api/resource`: Exemplo de endpoint protegido.
  - **Headers**:
    ```
    Authorization: Bearer <jwt-token>
    ```

## Testes

Para executar os testes automatizados, utilize o comando:

```bash
./mvnw test
```

## Contribuição

Contribuições são bem-vindas! Por favor, abra uma issue ou envie um pull request.

## Licença

Este projeto está licenciado sob a licença MIT. Veja o arquivo [LICENSE](./LICENSE) para mais detalhes.
