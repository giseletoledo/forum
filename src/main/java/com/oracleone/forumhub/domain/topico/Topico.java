package com.oracleone.forumhub.domain.topico;

import com.oracleone.forumhub.utils.TopicoConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    // Construtor vazio (necessário para JPA)
    public Topico() {}

    // Construtor com argumentos
    public Topico(String titulo, String mensagem, String autor, String curso) {
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.dataCriacao = LocalDateTime.now();
        this.estado = Estado.ABERTO;
        this.autor = autor;
        this.curso = curso;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    @Repository
    public static interface TopicoRepository extends JpaRepository<Topico, Long> {

        // Verifica se existe um tópico com o mesmo título e mensagem
        boolean existsByTituloAndMensagem(String titulo, String mensagem);

        // Metodo adicional para encontrar tópicos por curso e ano (opcional)
        @Query("SELECT t FROM Topico t WHERE t.curso = :curso AND FUNCTION('YEAR', t.dataCriacao) = :year")
        Optional<Topico> findByCursoAndDataCriacaoYear(@Param("curso") String curso, @Param("year") int year);
    }

    @Service
    public static class TopicoService {

        private final TopicoRepository topicoRepository;

        public TopicoService(TopicoRepository topicoRepository) {
            this.topicoRepository = topicoRepository;
        }

        public List<TopicoDTO> listarTopicos() {
            return topicoRepository.findAll(Sort.by(Sort.Direction.ASC, "dataCriacao"))
                    .stream()
                    .map(TopicoConverter::toDTO)
                    .collect(Collectors.toList());
        }

        public TopicoDTO criarTopico(TopicoDTO topicoDTO) {
            if (topicoRepository.existsByTituloAndMensagem(topicoDTO.titulo(), topicoDTO.mensagem())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tópico duplicado");
            }
            Topico topico = topicoRepository.save(new Topico(topicoDTO.titulo(), topicoDTO.mensagem(), topicoDTO.autor(), topicoDTO.curso()));
            return TopicoConverter.toDTO(topico);
        }

        public TopicoDTO detalharTopico(Long id) {
            Topico topico;
            topico = topicoRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tópico não encontrado"));
            return TopicoConverter.toDTO(topico);
        }

        public TopicoDTO atualizarTopico(Long id, TopicoDTO topicoDTO) {
            Topico topico = topicoRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tópico não encontrado"));
            topico.setTitulo(topicoDTO.titulo());
            topico.setMensagem(topicoDTO.mensagem());
            topico.setAutor(topicoDTO.autor());
            topico.setCurso(topicoDTO.curso());
            topicoRepository.save(topico);
            return TopicoConverter.toDTO(topico);
        }

        public void excluirTopico(Long id) {
            if (!topicoRepository.existsById(id)) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tópico não encontrado");
            }
            topicoRepository.deleteById(id);
        }

        public Optional<Topico> findTopicosByCursoAndYear(String curso, int year) {
            return topicoRepository.findByCursoAndDataCriacaoYear(curso, year);
        }

    }
}

