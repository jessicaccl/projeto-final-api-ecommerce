package org.serratec.trabalhoFinal.service;

import org.serratec.trabalhoFinal.domain.StatusAssinatura;

import java.util.List;
import java.util.stream.Collectors;
import org.serratec.trabalhoFinal.domain.Assinatura;
import org.serratec.trabalhoFinal.domain.Cliente;
import org.serratec.trabalhoFinal.domain.Plano;
import org.serratec.trabalhoFinal.dto.AssinaturaDTO;
import org.serratec.trabalhoFinal.exception.NotFoundException;
import org.serratec.trabalhoFinal.repository.AssinaturaRepository;
import org.serratec.trabalhoFinal.repository.ClienteRepository;
import org.serratec.trabalhoFinal.repository.PlanoRepository; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AssinaturaService {

    @Autowired
    private AssinaturaRepository assinaturaRepository;

    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private PlanoRepository planoRepository; 

    private AssinaturaDTO toDto(Assinatura assinatura) {
        AssinaturaDTO dto = new AssinaturaDTO();
        dto.setId(assinatura.getId());
        dto.setEndereco(assinatura.getEndereco());
        dto.setStatus(assinatura.getStatus());
        
        if (assinatura.getCliente() != null) {
            dto.setIdCliente(assinatura.getCliente().getId());
        }
      
        if (assinatura.getPlano() != null) {
            dto.setIdPlano(assinatura.getPlano().getId());
        }
        
        return dto;
    }

    private Assinatura toEntity(AssinaturaDTO dto) {
        Assinatura assinatura = new Assinatura();
        assinatura.setEndereco(dto.getEndereco());

        if (dto.getIdCliente() != null) {
            Cliente cliente = clienteRepository.findById(dto.getIdCliente())
                .orElseThrow(() -> new NotFoundException("Cliente não encontrado"));
            assinatura.setCliente(cliente);
        }

        if (dto.getIdPlano() != null) {
            Plano plano = planoRepository.findById(dto.getIdPlano())
                .orElseThrow(() -> new NotFoundException("Plano não encontrado"));
            assinatura.setPlano(plano);
        }
        
        return assinatura;
    }

    @Transactional(readOnly = true)
    public List<AssinaturaDTO> listar() {
        return assinaturaRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public AssinaturaDTO buscarPorId(Long id) {
        Assinatura assinatura = assinaturaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Assinatura com ID " + id + " não encontrada."));
        
        return toDto(assinatura);
    }
    
    @Transactional
    public AssinaturaDTO criar(AssinaturaDTO dto) {
        Assinatura assinatura = toEntity(dto); 
        assinatura.setStatus(StatusAssinatura.ATIVA);
        assinaturaRepository.save(assinatura);
        return toDto(assinatura);
    }


    @Transactional
    public AssinaturaDTO atualizar(Long id, AssinaturaDTO dto) {
        Assinatura assinatura = assinaturaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Assinatura não encontrada"));

        assinatura.setEndereco(dto.getEndereco());
    
        if (dto.getIdCliente() != null) {
            Cliente cliente = clienteRepository.findById(dto.getIdCliente())
                .orElseThrow(() -> new NotFoundException("Cliente não encontrado"));
            assinatura.setCliente(cliente);
        }
        
        if (dto.getIdPlano() != null) {
            Plano plano = planoRepository.findById(dto.getIdPlano())
                .orElseThrow(() -> new NotFoundException("Plano não encontrado"));
            assinatura.setPlano(plano);
        }
        

        assinaturaRepository.save(assinatura);
        return toDto(assinatura);
    }

    @Transactional
    public void cancelar(Long id) {
        Assinatura assinatura = assinaturaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Assinatura não encontrada."));
        
        assinatura.setStatus(StatusAssinatura.CANCELADA);
        assinaturaRepository.save(assinatura);
    }
    
     @Transactional
     public void pausar(Long id) {
         Assinatura assinatura = assinaturaRepository.findById(id)
                 .orElseThrow(() -> new NotFoundException("Assinatura não encontrada."));
         
         if (assinatura.getStatus() != StatusAssinatura.ATIVA) {
        	 throw new IllegalArgumentException("Só é possível pausar assinaturas que estão ATIVAS.");
         }
         
         assinatura.setStatus(StatusAssinatura.PAUSADA);
         assinaturaRepository.save(assinatura);
     }

     @Transactional
     public void reativar(Long id) {
         Assinatura assinatura = assinaturaRepository.findById(id)
                 .orElseThrow(() -> new NotFoundException("Assinatura não encontrada."));
         
         if (assinatura.getStatus() == StatusAssinatura.ATIVA) {
             throw new IllegalArgumentException("Esta assinatura já está ATIVA.");
         }
         
         assinatura.setStatus(StatusAssinatura.ATIVA);
         assinaturaRepository.save(assinatura);
     }
}