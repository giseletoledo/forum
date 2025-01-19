# <img src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/java/java-plain.svg"  width="6%" /> API ForumHub

## Descrição
A API ForumHub é uma aplicação backend desenvolvida em Java utilizando Spring Boot, destinada à gestão de tópicos em um fórum. Ela permite a criação, edição e consulta de tópicos baseados em cursos e datas de criação.

## Funcionalidades
- **Criar Tópico**: Permite a criação de novos tópicos com título, mensagem, autor e curso.
- **Consultar Tópicos**: Recupera tópicos por ano e curso, com suporte a buscas case-insensitive.
- **Atualizar Tópico**: Edita as informações de um tópico existente.
- **Deletar Tópico**: Remove tópicos do banco de dados.

## Tecnologias Utilizadas
- **Java 17**
- **Spring Boot 3.x**
- **Spring Data JPA**
- **H2 Database (para desenvolvimento e testes)**
- **MySQL/PostgreSQL (para produção)**

## Estrutura do Projeto
```
forumhub/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/oracleone/forumhub/
│   │   │       ├── domain/
│   │   │       │   └── topico/  # Contém a entidade Topico
│   │   │       ├── repository/ # Repositório JPA para Topico
│   │   │       ├── service/    # Lógica de negócio
│   │   │       └── controller/ # Controladores REST
│   │   └── resources/
│   │       └── application.properties  # Configurações da aplicação
└───
```

## Entidade `Topico`
```java
@Entity
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String titulo;

    @NotBlank
    private String mensagem;

    @NotNull
    private LocalDateTime dataCriacao;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    @NotBlank
    private String autor;

    @NotBlank
    private String curso;

    // Construtores, Getters e Setters...
}
```

## Repositório `TopicoRepository`
```java
@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {
    @Query("SELECT t FROM Topico t WHERE LOWER(t.curso) = LOWER(:curso) AND YEAR(t.dataCriacao) = :ano")
    List<Topico> findByAnoAndCurso(@Param("ano") int ano, @Param("curso") String curso);
}
```

## Endpoints

### Criar Tópico
- **POST** `/api/topicos`
- **Body**:
  ```json
  {
    "titulo": "Frontend Next.js",
    "mensagem": "Como criar um Frontend com Next.js",
    "autor": "Gisele",
    "curso": "Next.js"
  }
  ```
- **Resposta**: 201 Created

### Consultar Tópicos por Ano e Curso
- **GET** `/api/topicos?ano=2025&curso=Next.js`
- **Resposta**: 200 OK
  ```json
  [
    {
      "id": 1,
      "titulo": "Frontend Next.js",
      "mensagem": "Como criar um Frontend com Next.js",
      "dataCriacao": "2025-01-14T15:36:03",
      "estado": "ABERTO",
      "autor": "Gisele",
      "curso": "Next.js"
    }
  ]
  ```

## Configuração do Projeto
1. Clone o repositório: `git clone https://github.com/giseletoledo/forum`
2. Navegue até a pasta do projeto: `cd forumhub`
3. Configure o banco de dados em `src/main/resources/application.properties`.
4. Execute a aplicação: `./mvnw spring-boot:run`

## Testes
- **Executar Testes**: `./mvnw test`

## Contribuição
1. Fork o repositório.
2. Crie uma branch: `git checkout -b feature/nova-feature`
3. Faça suas alterações e commite: `git commit -m 'Adiciona nova feature'`
4. Envie para o branch principal: `git push origin feature/nova-feature`
5. Abra um Pull Request.

## Licença
Este projeto está licenciado sob a MIT License - veja o arquivo [LICENSE](LICENSE) para mais detalhes.

