package com.oracleone.forumhub.utils;

import com.oracleone.forumhub.dto.TopicoDTO;
import com.oracleone.forumhub.model.Topico;

public class TopicoConverter {

    public static TopicoDTO toDTO(Topico topico) {
        return new TopicoDTO(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getDataCriacao(),
                topico.getEstado(),
                topico.getAutor(),
                topico.getCurso()
        );
    }

    public static Topico toEntity(TopicoDTO topicoDTO) {
        Topico topico = new Topico();
        topico.setTitulo(topicoDTO.titulo());
        topico.setMensagem(topicoDTO.mensagem());
        topico.setDataCriacao(topicoDTO.dataCriacao());
        topico.setEstado(topicoDTO.estado());
        topico.setAutor(topicoDTO.autor());
        topico.setCurso(topicoDTO.curso());
        return topico;
    }
}
