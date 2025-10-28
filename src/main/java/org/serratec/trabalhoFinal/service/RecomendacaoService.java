package org.serratec.trabalhoFinal.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.serratec.trabalhoFinal.domain.Pedido;
import org.serratec.trabalhoFinal.domain.Produto;
import org.serratec.trabalhoFinal.repository.PedidoRepository;
import org.serratec.trabalhoFinal.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class RecomendacaoService {
	
	@Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Produto> recomendarCafes(Long clienteId) {
    	List<Pedido> pedidos = pedidoRepository.findByClienteId(clienteId);

        if (pedidos.isEmpty()) {
            return produtoRepository.findTop5ByOrderByQuantidadeVendidaDesc();
        }

        Set<Long> produtosComprados = pedidos.stream()
                .flatMap(p -> p.getItens().stream())
                .map(item -> item.getProduto().getId())
                .collect(Collectors.toSet());

        return produtoRepository.findProdutosRelacionados(produtosComprados);
    }
}
