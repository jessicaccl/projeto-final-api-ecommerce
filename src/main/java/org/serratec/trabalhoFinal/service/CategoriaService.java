package org.serratec.trabalhoFinal.service;

import org.serratec.trabalhoFinal.domain.Categoria;
import org.serratec.trabalhoFinal.dto.CategoriaDTO;
import org.serratec.trabalhoFinal.exception.NotFoundException;
import org.serratec.trabalhoFinal.repository.CategoriaRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaService {
    private final CategoriaRepository repo;
    public CategoriaService(CategoriaRepository repo) { 
    	this.repo = repo; 
    }

    public CategoriaDTO criar(CategoriaDTO dto) {
    	
        Categoria c = new Categoria();
        c.setNome(dto.getNome());
        Categoria saved = repo.save(c);
        dto.setId(saved.getId());
        return dto;
    }

    public CategoriaDTO atualizar(Long id, CategoriaDTO dto) {
    	
        Categoria c = repo.findById(id).orElseThrow(() -> new NotFoundException("Categoria não encontrada"));
        c.setNome(dto.getNome());
        repo.save(c);
        dto.setId(c.getId());
        return dto;
    }

    public List<CategoriaDTO> listar() {
    	
        return repo.findAll().stream().map(c -> {
            CategoriaDTO dto = new CategoriaDTO();
            dto.setId(c.getId());
            dto.setNome(c.getNome());
            return dto;
        }).collect(Collectors.toList());
    }

    public CategoriaDTO buscarPorId(Long id) {
        Categoria categoria = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Categoria não encontrada"));
        return toDto(categoria);
    }
    
    private CategoriaDTO toDto(Categoria c) {
        CategoriaDTO dto = new CategoriaDTO();
        dto.setId(c.getId());
        dto.setNome(c.getNome());
 

        return dto;
    }

    public void deletar(Long id) {
        try {
            repo.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Não é possível deletar a categoria: existem produtos associados.");
        }
    }
}