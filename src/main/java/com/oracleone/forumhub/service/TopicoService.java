package com.oracleone.forumhub.service;

import com.oracleone.forumhub.domain.topico.Topico;
import com.oracleone.forumhub.domain.topico.TopicoDTO;
import com.oracleone.forumhub.repository.TopicoRepository;
import com.oracleone.forumhub.utils.TopicoConverter;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TopicoService {

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