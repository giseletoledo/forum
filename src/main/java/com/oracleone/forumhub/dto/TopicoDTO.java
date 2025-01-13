package com.oracleone.forumhub.dto;

import com.oracleone.forumhub.model.Estado;

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
