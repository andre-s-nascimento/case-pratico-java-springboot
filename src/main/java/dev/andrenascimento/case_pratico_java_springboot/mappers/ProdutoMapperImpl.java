package dev.andrenascimento.case_pratico_java_springboot.mappers;

import org.springframework.stereotype.Component;

import dev.andrenascimento.case_pratico_java_springboot.dtos.ProdutoRequest;
import dev.andrenascimento.case_pratico_java_springboot.dtos.ProdutoResponse;
import dev.andrenascimento.case_pratico_java_springboot.models.Produto;

@Component
public class ProdutoMapperImpl implements ProdutoMapper{

    @Override
    public ProdutoResponse toResponse(Produto produto) {
        if (produto == null) {
            return null; // Retorna null se o ProdutoRequest for nulo
        }
        ProdutoResponse response = new ProdutoResponse();
        response.setId(produto.getId());
        response.setNome(produto.getNome());
        response.setPreco(produto.getPreco());
        response.setDescricao(produto.getDescricao());
        response.setQuantidadeEmEstoque(produto.getQuantidadeEmEstoque());
        return response;

    }

    @Override
    public Produto toEntity(ProdutoRequest produtoRequest) {
        if (produtoRequest == null) {
            return null; // Retorna null se o ProdutoRequest for nulo
        }
        Produto produto = new Produto();
        produto.setNome(produtoRequest.getNome());
        produto.setPreco(produtoRequest.getPreco());
        produto.setDescricao(produtoRequest.getDescricao());
        produto.setQuantidadeEmEstoque(produtoRequest.getQuantidadeEmEstoque());
        return produto;
    }
    
}
