package com.oracleone.forumhub.domain.topico;

import java.time.LocalDateTime;

public record TopicoDTO(
        Long id,
        String titulo,
        String mensagem,
        LocalDateTime dataCriacao,
        Estado estado,
        String autor,
        String curso
) {}
