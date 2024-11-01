package dev.andrenascimento.case_pratico_java_springboot.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import dev.andrenascimento.case_pratico_java_springboot.dtos.ProdutoRequest;
import dev.andrenascimento.case_pratico_java_springboot.dtos.ProdutoResponse;
import dev.andrenascimento.case_pratico_java_springboot.exceptions.ProdutoNotFoundException;
import dev.andrenascimento.case_pratico_java_springboot.mappers.ProdutoMapper;
import dev.andrenascimento.case_pratico_java_springboot.models.Produto;
import dev.andrenascimento.case_pratico_java_springboot.repositories.ProdutoRepository;

@Service
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ProdutoMapper produtoMapper;

    public ProdutoServiceImpl(ProdutoRepository produtoRepository, ProdutoMapper produtoMapper) {
        this.produtoRepository = produtoRepository;
        this.produtoMapper = produtoMapper;
    }

    @Override
    public ProdutoResponse atualizarProduto(Long id, ProdutoRequest produtoRequest) {
        // Verifica se o produto existe
        if (!produtoRepository.existsById(id)) {
            throw new ProdutoNotFoundException(String.format("Não existe o produto com o id: [%s]", id));
        }

        // Se o produto existe, atualiza
        var produtoToSave = produtoMapper.toEntity(produtoRequest);
        produtoToSave.setId(id);
        Produto updatedProduto = produtoRepository.save(produtoToSave);
        return produtoMapper.toResponse(updatedProduto);
    }

    @Override
    public ProdutoResponse criarProduto(ProdutoRequest produtoRequest) {
        Produto produto = produtoMapper.toEntity(produtoRequest);
        Produto savedProduto = produtoRepository.save(produto);
        return produtoMapper.toResponse(savedProduto);
    }

    @Override
    public void excluirProduto(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new ProdutoNotFoundException(String.format("Não existe o produto com o id: [%s]", id));
        }
        produtoRepository.deleteById(id);

    }

    @Override
    public List<ProdutoResponse> listarProdutos() {
        return produtoRepository.findAll().stream()
                .map(produtoMapper::toResponse)
                .collect(Collectors.toList());
    }

}
