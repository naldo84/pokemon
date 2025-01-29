# Project Pokemon

## Descrição

Este é um projeto desenvolvido em Java que tem como objetivo criar um sistema simples para gerenciar Pokémons. Ele utiliza uma arquitetura MVC (Model-View-Controller) e inclui as camadas de DTO, repositório e serviço para organização e escalabilidade do código.

## Estrutura do Projeto

A estrutura do projeto é organizada conforme abaixo:

```
src/
├── main/
│   ├── java/com.pokemonProject.pokemon/
│   │   ├── controller/    # Contém os controllers da API
│   │   ├── dto/           # Contém os DTOs para requisições e respostas
│   │   ├── model/         # Modelos representando entidades do banco de dados
│   │   ├── repository/    # Interfaces de acesso ao banco de dados
│   │   ├── service/       # Contém a lógica de negócios
│   ├── resources/
│   │   ├── static/        # Arquivos estáticos (se necessário)
│   │   ├── templates/     # Templates (caso utilize Thymeleaf)
│   │   ├── application.properties  # Configurações do projeto
│   │   ├── API-Pokemon.postman_collection.json  # Collection do Postman
```

### Diretórios e Classes

- **controller**: Contém o `PokemonController`, que é responsável por gerenciar as requisições e respostas HTTP.
- **dto**: Contém o `PokemonDto`, que define os objetos de transferência de dados entre as camadas.
- **model**: Contém o `PokemonModel`, que representa a estrutura dos dados no sistema.
- **repository**: Contém o `PokemonRepository`, que é a interface para interação com o banco de dados.
- **service**: Contém a lógica de negócio implementada no `PokemonService`.
- **PokemonApplication**: Classe principal do projeto, que inicializa o sistema.

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot** para criação do backend
- **Maven** como gerenciador de dependências
- **Banco de Dados Postgres** (via Docker) para armazenamento de dados
- **Checkstyle** (Padronizaçaõ de código)
- **Swagger** (Documentação da API)
- **Postman** (Testes de API via Collection)

## Funcionalidades

- Criar novos Pokémons
- Consultar Pokémons cadastrados
- Atualizar informações de um Pokémon
- Deletar Pokémons

## Configuração do Projeto

1. Clone este repositório para o seu ambiente local.
2. Certifique-se de ter o JDK 17 instalado.
3. No terminal, navegue até o diretório do projeto e execute o comando:
   ```
   mvn spring-boot:run
   ```
4. O projeto estará disponível em `http://localhost:8080`.

## ✅ Padronização com Checkstyle

O projeto usa **Checkstyle** para garantir a padronização do código. Imports não utilizados são bloqueados automaticamente.

Para validar manualmente, execute:

```sh
mvn checkstyle:check
```

Se houver violações, o Maven exibirá os erros no console.

## 📊 Documentação com Swagger

O projeto utiliza **Swagger** para documentar os endpoints da API.

Para acessar a documentação da API, inicie a aplicação e acesse:

```
http://localhost:8080/swagger-ui.html
```

## 📌 Collection do Postman

A Collection do Postman está disponível em `src/main/resources/API-Pokemon.postman_collection.json`.

Para importar no Postman:

1. Abra o Postman.
2. Clique em **Import**.
3. Selecione o arquivo `API-Pokemon.postman_collection.json`.
4. Pronto! Agora você pode testar os endpoints da API.

## Contribuição

Contribuições são bem-vindas! Por favor, abra um Pull Request com suas modificações.

---
🌍 Desenvolvido por **Erinaldo Silva**