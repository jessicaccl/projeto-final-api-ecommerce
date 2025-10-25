package org.serratec.trabalhoFinal.service;

import org.serratec.trabalhoFinal.domain.StatusAssinatura;

import java.util.List;
import java.util.stream.Collectors;
import org.serratec.trabalhoFinal.domain.Assinatura;
import org.serratec.trabalhoFinal.domain.Cliente;
import org.serratec.trabalhoFinal.dto.AssinaturaDTO;
import org.serratec.trabalhoFinal.exception.NotFoundException;
import org.serratec.trabalhoFinal.repository.AssinaturaRepository;
import org.serratec.trabalhoFinal.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AssinaturaService {

    @Autowired
    private AssinaturaRepository assinaturaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    
    private AssinaturaDTO toDto(Assinatura assinatura) {
        AssinaturaDTO dto = new AssinaturaDTO();
        dto.setId(assinatura.getId());
        dto.setPlano(assinatura.getPlano());
        dto.setEndereco(assinatura.getEndereco());
        dto.setTipoCafe(assinatura.getTipoCafe());
        dto.setStatus(assinatura.getStatus());
        if (assinatura.getCliente() != null) {
            dto.setIdCliente(assinatura.getCliente().getId());
        }
        return dto;
    }


    private Assinatura toEntity(AssinaturaDTO dto) {
        Assinatura assinatura = new Assinatura();
        assinatura.setPlano(dto.getPlano());
        assinatura.setEndereco(dto.getEndereco());
        assinatura.setTipoCafe(dto.getTipoCafe());

       
        if (dto.getIdCliente() != null) {
            Cliente cliente = clienteRepository.findById(dto.getIdCliente())
                .orElseThrow(() -> new NotFoundException("Cliente não encontrado"));
            assinatura.setCliente(cliente);
        }
        return assinatura;
    }

  
    @Transactional(readOnly = true)
    public List<AssinaturaDTO> listar() {
        return assinaturaRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
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

        
        assinatura.setPlano(dto.getPlano());
        assinatura.setEndereco(dto.getEndereco());
        assinatura.setTipoCafe(dto.getTipoCafe());

        
        if (dto.getIdCliente() != null) {
            Cliente cliente = clienteRepository.findById(dto.getIdCliente())
                .orElseThrow(() -> new NotFoundException("Cliente não encontrado"));
            assinatura.setCliente(cliente);
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
        
        assinatura.setStatus(StatusAssinatura.PAUSADA);
        assinaturaRepository.save(assinatura);
    }
    
    @Transactional
    public void reativar(Long id) {
        Assinatura assinatura = assinaturaRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Assinatura não encontrada."));
        
        assinatura.setStatus(StatusAssinatura.ATIVA);
        assinaturaRepository.save(assinatura);
    }
    
}