# Sistema de Gerenciamento de Produtos

Este é um sistema simples para gerenciar produtos em uma aplicação Spring Boot.

## Funcionalidades

- **Criar produtos:** Permite adicionar novos produtos ao sistema com nome, preço, descrição e quantidade em estoque.
- **Listar produtos:** Lista todos os produtos cadastrados.
- **Atualizar produtos:** Permite editar informações de um produto existente.
- **Excluir produtos:** Remove um produto do sistema.

## Arquitetura

O sistema é dividido em camadas:

- **Controllers:** Recebem as requisições HTTP e as direcionam para os serviços correspondentes.
Services: Implementam a lógica de negócio para cada operação (criar, listar, atualizar, excluir).
- **Repositories:** Fornecem uma interface para interagir com o banco de dados (JPA Repository).
DTOs (Data Transfer Objects): Modelos utilizados para transportar dados entre as camadas (ProdutoRequest e ProdutoResponse).
- **Mappers:** Mapeiam objetos DTO para entidades e vice-versa (ProdutoMapper).
- **Exceptions:** Exceções personalizadas para tratar erros específicos do sistema (ProdutoNotFoundException).
- **GlobalExceptionHandler:** Classe responsável por tratar exceções globalmente e retornar respostas HTTP adequadas.

## Tecnologias

- Java
- Spring Boot
- Spring Data JPA
- Banco de dados H2 (em memória)
- Maven (gerenciamento de dependências)

## Como executar

1. Clone o repositório.

2. Configure o banco de dados (opcional - por padrão, utiliza o H2 em memória).

3. Compile e execute a aplicação Spring Boot.

4. Acesse a API utilizando uma ferramenta como Postman ou Insomnia.

## Endpoints

### GET /produtos

Lista todos os produtos.

### POST /produtos

Cria um novo produto.

Corpo da requisição (JSON):

```json
{
  "nome": "Nome do Produto",
  "preco": 10.0,
  "descricao": "Descrição do produto",
  "quantidadeEmEstoque": 5
}
```

### PUT /produtos/{id}

Atualiza um produto existente.

Parâmetros:

- id: ID do produto a ser atualizado.

Corpo da requisição (JSON):

```json
{
  "nome": "Nome do Produto Atualizado",
  "preco": 15.0,
  "descricao": "Descrição atualizada do produto",
  "quantidadeEmEstoque": 10
}
```

### DELETE /produtos/{id}

Exclui um produto.

Parâmetros:

id: ID do produto a ser excluído.

## Considerações

Este sistema é uma versão simplificada para fins de demonstração. Em um sistema real, seria importante adicionar mais funcionalidades, segurança, validações, tratamento de erros mais robusto e testes mais abrangentes.
