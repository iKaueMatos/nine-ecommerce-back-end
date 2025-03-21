# Histórico de Commits

## Commits Recentes

### feat: adicionar autenticação com Spring Security

- Configurado Spring Security para autenticação e autorização.
- Implementado suporte a tokens JWT.
- Adicionado filtro para validação de tokens em requisições protegidas.

### feat: criar endpoints REST para usuários

- Adicionado controlador REST para operações de CRUD de usuários.
- Configurado mapeamento de rotas no controlador.
- Adicionado DTOs para entrada e saída de dados.

### fix: corrigir erro de conexão com o banco de dados

- Corrigido problema de configuração no arquivo `application.properties`.
- Testado conexão com o banco PostgreSQL.
- Adicionado fallback para configuração padrão.

### chore: configurar Maven Wrapper

- Adicionado Maven Wrapper para facilitar a execução do projeto.
- Atualizado `.gitignore` para incluir arquivos do Maven Wrapper.

### docs: atualizar README com instruções para Spring Boot

- Adicionado detalhes sobre configuração do banco de dados.
- Incluído exemplo de execução do projeto com Maven.
- Documentados endpoints principais.

### refactor: reorganizar pacotes do projeto

- Movido controladores para o pacote `controller`.
- Separado lógica de negócios no pacote `service`.
- Atualizado código para refletir nova estrutura.
- Melhorada a nomenclatura de classes e métodos.

### feat: implementar CRUD de produtos

- Adicionado controlador REST para gerenciar produtos.
- Implementado suporte a categorias e filtros de busca.
- Adicionado validações para entrada de dados.

### feat: adicionar carrinho de compras

- Criado serviço para gerenciar itens no carrinho.
- Adicionado cálculo automático de preços e descontos.
- Implementado endpoint para visualizar o carrinho.

### feat: criar sistema de pedidos

- Adicionado lógica para criação e gerenciamento de pedidos.
- Integrado com gateway de pagamento fictício.
- Adicionado suporte a status de pedidos (pendente, pago, enviado).

### fix: corrigir erro de autenticação

- Corrigido problema na geração de tokens JWT.
- Adicionado teste unitário para validar autenticação.

### chore: configurar logs com SLF4J

- Adicionado suporte a logs para monitorar requisições.
- Configurado nível de log no arquivo `application.properties`.

### docs: atualizar README com detalhes do e-commerce

- Adicionado descrição detalhada das funcionalidades.
- Incluído exemplos de endpoints para produtos, carrinho e pedidos.
