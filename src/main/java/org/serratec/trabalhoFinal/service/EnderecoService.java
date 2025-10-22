package org.serratec.trabalhoFinal.service;

import java.util.List;
import java.util.stream.Collectors;

import org.serratec.trabalhoFinal.domain.Endereco;
import org.serratec.trabalhoFinal.dto.EnderecoDTO;
import org.serratec.trabalhoFinal.repository.EnderecoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;

    public EnderecoService(EnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }

    @Transactional
    public Endereco buscarOuCriar(EnderecoDTO dto) {
        return enderecoRepository.findByCep(dto.getCep())
                .orElseGet(() -> {
                    Endereco novo = new Endereco();
                    novo.setCep(dto.getCep());
                    novo.setLogradouro(dto.getLogradouro());
                    novo.setComplemento(dto.getComplemento());
                    novo.setBairro(dto.getBairro());
                    novo.setLocalidade(dto.getLocalidade());
                    novo.setUf(dto.getUf());
                    return enderecoRepository.save(novo);
                });
    }

    public EnderecoDTO buscar(String cep) {
        Endereco endereco = enderecoRepository.findByCep(cep)
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado"));
        return toDto(endereco);
    }
    
    public List<EnderecoDTO> listarTodos() {
        return enderecoRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }


    private EnderecoDTO toDto(Endereco e) {
        EnderecoDTO dto = new EnderecoDTO();
        dto.setCep(e.getCep());
        dto.setLogradouro(e.getLogradouro());
        dto.setComplemento(e.getComplemento());
        dto.setBairro(e.getBairro());
        dto.setLocalidade(e.getLocalidade());
        dto.setUf(e.getUf());
        return dto;
    }
}
