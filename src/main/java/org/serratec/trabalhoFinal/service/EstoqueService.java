package org.serratec.trabalhoFinal.service;

import java.util.List;
import java.util.stream.Collectors;

import org.serratec.trabalhoFinal.domain.Estoque;
import org.serratec.trabalhoFinal.domain.Produto;
import org.serratec.trabalhoFinal.dto.EstoqueDTO;
import org.serratec.trabalhoFinal.exception.EstoqueInsuficienteException;
import org.serratec.trabalhoFinal.exception.NotFoundException;
import org.serratec.trabalhoFinal.repository.EstoqueRepository;
import org.springframework.stereotype.Service;

@Service
public class EstoqueService {

	private final EstoqueRepository estoqueRepository;

	public EstoqueService(EstoqueRepository estoqueRepository) {
		this.estoqueRepository = estoqueRepository;
	}
	
	public List<EstoqueDTO> listarTodosProdutos() {
		return estoqueRepository.findAll()
				.stream()
				.map(e -> new EstoqueDTO(
				))
				.collect(Collectors.toList());
	}

	public boolean verificarEstoque(Long produtoId, int quantidadeSolicitada) {
        Estoque estoque = estoqueRepository.findByProdutoId(produtoId);
        return estoque != null && estoque.getQuantidade() >= quantidadeSolicitada;
    }

	public void darBaixaEstoque(Long produtoId, int quantidade) {
        Estoque estoque = estoqueRepository.findByProdutoId(produtoId);
        
        // CORREÇÃO: Verifica se o estoque do produto existe e tem quantidade suficiente.
        if (estoque == null) {
            // Em um cenário real, você provavelmente lançaria um NotFoundException ou criaria o estoque.
            // Para dar baixa, vou assumir que o produto deve existir no estoque.
            throw new NotFoundException("Estoque não encontrado para o produto ID: " + produtoId);
        }
        
        if (estoque.getQuantidade() >= quantidade) {
            estoque.setQuantidade(estoque.getQuantidade() - quantidade);
            estoqueRepository.save(estoque);
        } else {
            // CORREÇÃO: Usa exceção de negócio específica.
            throw new EstoqueInsuficienteException("Estoque insuficiente para o produto ID: " + produtoId + ". Quantidade disponível: " + estoque.getQuantidade());
        }
    }

    public void adicionarEstoque(Long produtoId, int quantidade) {
        Estoque estoque = estoqueRepository.findByProdutoId(produtoId);
        if (estoque != null) {
            estoque.setQuantidade(estoque.getQuantidade() + quantidade);
        } else {
            estoque = new Estoque();
            Produto produto = new Produto();
            produto.setId(produtoId);
            estoque.setProduto(produto);
            estoque.setQuantidade(quantidade);
        }
        estoqueRepository.save(estoque);
    }
    
    public EstoqueDTO getEstoquePorProduto(Long Id) {
    	
    	Estoque estoque = estoqueRepository.findByProdutoId(Id);
    	if(estoque == null) {
    		estoque = new Estoque();
            Produto produto = new Produto();
            produto.setId(Id);
            estoque.setProduto(produto);
            estoque.setQuantidade(0);
            estoqueRepository.save(estoque);
    	}
    	
    	return new EstoqueDTO(
    			estoque.getProduto().getId(),
    			estoque.getProduto().getNome(),
    			estoque.getQuantidade()
    			);
    }
}
