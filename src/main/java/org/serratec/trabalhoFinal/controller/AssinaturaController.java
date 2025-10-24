package org.serratec.trabalhoFinal.controller;

import java.util.List;
import org.serratec.trabalhoFinal.dto.AssinaturaDTO;
import org.serratec.trabalhoFinal.service.AssinaturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/assinaturas")
public class AssinaturaController {

	@Autowired
    private AssinaturaService assinaturaService;

    @GetMapping
    public ResponseEntity<List<AssinaturaDTO>> listarAssinaturas() {
        return ResponseEntity.ok(assinaturaService.listar());
    }

    @PostMapping
    public ResponseEntity<AssinaturaDTO> cadastrarNovaAssinatura(@Validated @RequestBody AssinaturaDTO dto) {
        return new ResponseEntity<>(assinaturaService.criar(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssinaturaDTO> alterarAssinatura(@PathVariable Long id, @Validated @RequestBody AssinaturaDTO dto) {
        return ResponseEntity.ok(assinaturaService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelarAssinatura(@PathVariable Long id) {
        assinaturaService.cancelar(id);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/{id}/pausar")
    public ResponseEntity<Void> pausarAssinatura(@PathVariable Long id) {
        assinaturaService.pausar(id);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/{id}/reativar")
    public ResponseEntity<Void> reativarAssinatura(@PathVariable Long id) {
        assinaturaService.pausar(id);
        return ResponseEntity.noContent().build();
    }
    
}