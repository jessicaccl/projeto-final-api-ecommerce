package org.serratec.trabalhoFinal.service;

import java.util.List;
import java.util.stream.Collectors;

import org.serratec.trabalhoFinal.domain.Plano;
import org.serratec.trabalhoFinal.dto.PlanoDTO;
import org.serratec.trabalhoFinal.exception.NotFoundException; 
import org.serratec.trabalhoFinal.repository.PlanoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlanoService {

    @Autowired
    private PlanoRepository planoRepository;
    
    private PlanoDTO toDto(Plano plano) {
        PlanoDTO dto = new PlanoDTO();
        dto.setId(plano.getId());
        dto.setNome(plano.getNome());
        dto.setDescricao(plano.getDescricao());
        dto.setGramas(plano.getGramas());
        dto.setPrecoMensal(plano.getPrecoMensal());
        return dto;
    }

    
    private Plano toEntity(PlanoDTO dto, Plano plano) {
        
        plano.setNome(dto.getNome());
        plano.setDescricao(dto.getDescricao());
        plano.setGramas(dto.getGramas());
        plano.setPrecoMensal(dto.getPrecoMensal());
        return plano;
    }

   
    @Transactional(readOnly = true)
    public List<PlanoDTO> listarTodos() {
        return planoRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PlanoDTO buscarPorId(Long id) {
        Plano plano = planoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Plano com ID " + id + " não encontrado."));
        return toDto(plano);
    }

    @Transactional
    public PlanoDTO criar(PlanoDTO dto) {
               
        Plano plano = new Plano();
        plano = toEntity(dto, plano);
        
        planoRepository.save(plano);
        return toDto(plano);
    }

    @Transactional
    public PlanoDTO atualizar(Long id, PlanoDTO dto) {
        Plano plano = planoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Plano com ID " + id + " não encontrado."));
        
        plano = toEntity(dto, plano); 
        
        planoRepository.save(plano);
        return toDto(plano);
    }

    @Transactional
    public void deletar(Long id) {
        if (!planoRepository.existsById(id)) {
            throw new NotFoundException("Plano com ID " + id + " não encontrado para exclusão.");
        }
       
        planoRepository.deleteById(id);
    }
}