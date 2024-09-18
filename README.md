# MythKrouz

**MythKrouz** é um projeto de gerenciamento e criação de universos fictícios, proporcionando uma plataforma para escritores e criadores desenvolverem e organizarem mundos ricos e complexos. O sistema oferece uma API RESTful para gerenciar personagens, eventos, territórios, itens e agora livros, além de garantir segurança e integridade dos dados com suporte a autenticação de usuários e versionamento de banco de dados.

## Funcionalidades

- **Criação e Gerenciamento de Universos**: Permite aos usuários criar e gerenciar universos fictícios.
- **Gestão de Personagens**: Adicione e edite personagens, incluindo suas características e relacionamentos.
- **Eventos e Territórios**: Gerencie eventos e territórios dentro de cada universo.
- **Itens e Equipamentos**: Adicione e associe itens a personagens e territórios.
- **Cadastro e Controle de Livros**: Cadastre livros, favoritar e listar livros.
- **Autenticação de Usuários**: Controle de acesso e autenticação usando Spring Security.
- **Persistência de Dados**: Gerenciamento de dados com PostgreSQL e migrações de banco de dados usando Flyway.

## Tecnologias

- **Back-end:**
  - **Spring Boot**: Para construção da API REST e lógica de negócios.
  - **Spring Security**: Para autenticação e controle de usuários.
  - **Spring Data JPA**: Para interação com o banco de dados PostgreSQL.
  - **Flyway**: Para gerenciamento de migrações do banco de dados.
  - **PostgreSQL**: Banco de dados relacional para armazenamento de dados.

- **Front-end:**
  - **HTML/CSS/JavaScript**: Para construção da interface do usuário.
  - **Bootstrap**: Para estilização e responsividade da interface.
  - **(Alternativa ao Thymeleaf)**: Frameworks JavaScript como **Vue.js** ou **React** para uma experiência de usuário mais dinâmica.

## Instalação

### 1. Clonando o Repositório

```bash
git clone https://github.com/seu-usuario/mythkrouz.git
cd mythkrouz
```

### 2. Configuração do Banco de Dados

1. Configure o PostgreSQL e crie um banco de dados para o projeto.
2. Atualize o arquivo `src/main/resources/application.properties` com as configurações do banco de dados:

    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/mythkrouz
    spring.datasource.username=seu-usuario
    spring.datasource.password=sua-senha
    ```

### 3. Executando Migrações com Flyway

As migrações do banco de dados são gerenciadas automaticamente pelo Flyway. Certifique-se de que as configurações estejam corretas e execute a aplicação para aplicar as migrações.

### 4. Executando o Projeto

Para iniciar o servidor Spring Boot, use o seguinte comando:

```bash
./mvnw spring-boot:run
```

## Uso

1. **API REST**: A API estará disponível em `http://localhost:8080`. Você pode usar ferramentas como Postman ou cURL para interagir com os endpoints.
2. **Front-end**: Construa suas páginas HTML/CSS/JavaScript para interagir com a API. O front-end pode ser servido a partir do mesmo servidor Spring Boot ou de um servidor separado, dependendo da sua configuração.

### Funcionalidades de Livros

- **Cadastro de Livros**: Crie e adicione livros ao sistema com detalhes como título, autor, descrição e gênero.
- **Favoritar Livros**: Permite que os usuários marquem livros como favoritos.
- **Listar Livros**: Visualize todos os livros cadastrados e filtrados por critérios como favoritos, gênero, etc.

## Contribuição

Contribuições são bem-vindas! Se você deseja colaborar com o projeto, siga estas etapas:

1. Faça um fork do repositório.
2. Crie uma branch para a sua feature ou correção de bug (`git checkout -b minha-feature`).
3. Faça commit das suas alterações (`git commit -am 'Adiciona nova feature'`).
4. Envie para o repositório remoto (`git push origin minha-feature`).
5. Abra um Pull Request.

## Licença

Este projeto é licenciado sob a [MIT License](LICENSE).

---

**MythKrouz** é um projeto em evolução e continua a ser aprimorado. Fique à vontade para explorar, contribuir e ajudar a criar universos fantásticos!

