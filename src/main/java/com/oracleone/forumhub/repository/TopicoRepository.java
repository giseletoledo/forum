package com.oracleone.forumhub.repository;

import com.oracleone.forumhub.domain.topico.Topico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {

    // Verifica se existe um tópico com o mesmo título e mensagem
    boolean existsByTituloAndMensagem(String titulo, String mensagem);

    // Metodo adicional para encontrar tópicos por curso e ano (opcional)
    @Query("SELECT t FROM Topico t WHERE YEAR(t.dataCriacao) = :ano AND t.curso = :curso")
    List<Topico> findByAnoAndCurso(@Param("ano") int ano, @Param("curso") String curso);

}