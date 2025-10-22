package org.serratec.trabalhoFinal.service;

import java.util.List;
import java.util.stream.Collectors;

import org.serratec.trabalhoFinal.domain.Cliente;
import org.serratec.trabalhoFinal.domain.Endereco;
import org.serratec.trabalhoFinal.dto.ClienteCriacaoDTO;
import org.serratec.trabalhoFinal.dto.ClienteDTO;
import org.serratec.trabalhoFinal.dto.EnderecoDTO;
import org.serratec.trabalhoFinal.exception.NotFoundException;
import org.serratec.trabalhoFinal.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final EnderecoService enderecoService;
    private final EmailService emailService;

    public ClienteService(ClienteRepository clienteRepository, EnderecoService enderecoService,
                          EmailService emailService) {
        this.clienteRepository = clienteRepository;
        this.enderecoService = enderecoService;
        this.emailService = emailService;
    }

    @Transactional
    public ClienteDTO criar(ClienteCriacaoDTO dto) {
        Cliente c = new Cliente();
        c.setNome(dto.getNome());
        c.setTelefone(dto.getTelefone());
        c.setEmail(dto.getEmail());
        c.setCpf(dto.getCpf());

        Endereco end = enderecoService.buscarOuCriar(dto.getEnderecoDTO());
        c.setEndereco(end);

        Cliente saved = clienteRepository.save(c);
        emailService.enviarNotificacaoCliente(saved, "criado");
        return toDto(saved);
    }

    @Transactional
    public ClienteDTO atualizar(Long id, ClienteCriacaoDTO dto) {
        Cliente c = clienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente não encontrado"));

        c.setNome(dto.getNome());
        c.setTelefone(dto.getTelefone());
        c.setEmail(dto.getEmail());
        c.setCpf(dto.getCpf());

        Endereco end = enderecoService.buscarOuCriar(dto.getEnderecoDTO());
        c.setEndereco(end);

        Cliente saved = clienteRepository.save(c);
        emailService.enviarNotificacaoCliente(saved, "atualizado");
        return toDto(saved);
    }

    public ClienteDTO buscarPorId(Long id) {
        Cliente c = clienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente não encontrado"));
        return toDto(c);
    }
    
    public List<ClienteDTO> listarTodos() {
        return clienteRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private ClienteDTO toDto(Cliente c) {
        ClienteDTO dto = new ClienteDTO();
        dto.setId(c.getId());
        dto.setNome(c.getNome());
        dto.setTelefone(c.getTelefone());
        dto.setEmail(c.getEmail());
        dto.setCpf(c.getCpf());

        if (c.getEndereco() != null) {
            EnderecoDTO ed = new EnderecoDTO();
            ed.setCep(c.getEndereco().getCep());
            ed.setLogradouro(c.getEndereco().getLogradouro());
            ed.setComplemento(c.getEndereco().getComplemento());
            ed.setBairro(c.getEndereco().getBairro());
            ed.setLocalidade(c.getEndereco().getLocalidade());
            ed.setUf(c.getEndereco().getUf());
            dto.setEndereco(ed);
        }

        return dto;
    }
}
