package dev.andrenascimento.case_pratico_java_springboot.services;

import java.util.List;

import dev.andrenascimento.case_pratico_java_springboot.dtos.ProdutoRequest;
import dev.andrenascimento.case_pratico_java_springboot.dtos.ProdutoResponse;

public interface ProdutoService {
    List<ProdutoResponse> listarProdutos();

    ProdutoResponse criarProduto(ProdutoRequest produtoRequest);

    ProdutoResponse atualizarProduto(Long id, ProdutoRequest produtoRequest);

    void excluirProduto(Long id);
}
