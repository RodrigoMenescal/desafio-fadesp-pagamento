
# **Sistema de Pagamento**

Este é um sistema para gerenciar pagamentos utilizando diferentes métodos, como boleto, cartão de crédito, cartão de débito e PIX. O projeto utiliza uma arquitetura modular


## **Tecnologias Utilizadas**

- **Java 17**: Linguagem principal.
- **Spring Boot 3.x**: Framework para desenvolvimento.
- **Spring Data JPA**: Gerenciamento de banco de dados relacional.
- **Hibernate**: ORM para integração com o banco de dados.
- **H2 Database**: Banco de dados em memória para desenvolvimento e testes.
- **Lombok**: Redução de código repetitivo.
- **Maven**: Gerenciamento de dependências e build.
- **Swagger**: Para documentação de APIs REST.

---

## **Pré-requisitos**
Certifique-se de ter as seguintes ferramentas instaladas:
- JDK 17 ou superior.
- Maven 3.8+.
---

## **Estrutura do Projeto**

```plaintext
src/main/java/com/fadesp/pagamento/
├── controller           # Controladores REST
├── domain
│   ├── dto              # Data Transfer Objects (DTOs)
│   ├── entity           # Entidades JPA (modelos de dados)
│   ├── enums            # Enumeradores (status, operações, etc.)
├── exception            # Classes para tratamento de exceções personalizadas
├── repository           # Interfaces para acesso ao banco de dados
├── service              # Lógica de negócios (serviços principais)
├── strategy             # Implementação do padrão Strategy para métodos de pagamento
└── utils                # Utilitários (ex.: validação de documentos)
````

## **Endpoints**

## Pagamentos
#### Criar Pagamento


```text
  Método: POST
  URL: /v1/pagamentos
```

Realiza pagamento com Cartão
```json
{
  "codigo": 123,
  "cpfCnpj": "416.869.840-81",
  "operacao": "CARTAO_DEBITO",
  "numeroCartao": "**** **** **** 5535",
  "valor": 150.75
}
```

Realiza pagamento sem Cartão
```json
{
  "codigo": 123,
  "cpfCnpj": "416.869.840-81",
  "operacao": "BOLETO",
  "valor": 150.75
}
```


#### Ataualiza status


```text
  Método: PUT
  URL: /v1/pagamentos/<ID>/status
```
```json
"PENDENTE"
```

#### Listar Pagamentos

Lista todos pagamentos
```text
  Método: GET
  URL: v1/pagamentos/listar
```
Filtra os pagamentos
```text
  Método: GET
  URL: /v1/pagamentos/filtro?codigo=123&status=PROCESSADO_FALHA&cpfCnpj=416.869.840-81
```
#### Remove Pagamento


```text
  Método: DELETE
  URL: /v1/pagamentos/<id>
```