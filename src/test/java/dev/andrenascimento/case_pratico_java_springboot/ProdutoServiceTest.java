package dev.andrenascimento.case_pratico_java_springboot;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import dev.andrenascimento.case_pratico_java_springboot.dtos.ProdutoRequest;
import dev.andrenascimento.case_pratico_java_springboot.dtos.ProdutoResponse;
import dev.andrenascimento.case_pratico_java_springboot.exceptions.ProdutoNotFoundException;
import dev.andrenascimento.case_pratico_java_springboot.mappers.ProdutoMapper;
import dev.andrenascimento.case_pratico_java_springboot.models.Produto;
import dev.andrenascimento.case_pratico_java_springboot.repositories.ProdutoRepository;
import dev.andrenascimento.case_pratico_java_springboot.services.ProdutoServiceImpl;

public class ProdutoServiceTest {

    @InjectMocks
    private ProdutoServiceImpl produtoService;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private ProdutoMapper produtoMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldUpdateProductWhenIdIsValid() {
        Long productId = 1L;
        ProdutoRequest request = new ProdutoRequest("Produto Atualizado", 15.0, "Descrição Atualizada", 10);
        Produto existingProduct = new Produto(productId, "Produto Antigo", 10.0, "Descrição Antiga", 5);
        Produto updatedProduct = new Produto(productId, "Produto Atualizado", 15.0, "Descrição Atualizada", 10);

        when(produtoRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(produtoMapper.toEntity(request)).thenReturn(updatedProduct);
        when(produtoRepository.save(updatedProduct)).thenReturn(updatedProduct);
        when(produtoMapper.toResponse(updatedProduct))
                .thenReturn(new ProdutoResponse(productId, "Produto Atualizado", 15.0, "Descrição Atualizada", 10));

        ProdutoResponse response = produtoService.atualizarProduto(productId, request);

        assertNotNull(response);
        assertEquals("Produto Atualizado", response.getNome());
        assertEquals(15.0, response.getPreco());
    }

    @Test
    void shouldThrowProdutoNotFoundExceptionWhenIdDoesNotExist() {
        Long productId = 1L;
        ProdutoRequest request = new ProdutoRequest("Produto Atualizado", 15.0, "Descrição Atualizada", 10);

        when(produtoRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ProdutoNotFoundException.class, () -> {
            produtoService.atualizarProduto(productId, request);
        });
    }

    @Test
    void shouldCreateProductSuccessfully() {
        ProdutoRequest request = new ProdutoRequest("Produto Novo", 20.0, "Descrição do Produto Novo", 5);
        Produto produto = new Produto(null, "Produto Novo", 20.0, "Descrição do Produto Novo", 5);
        Produto savedProduto = new Produto(1L, "Produto Novo", 20.0, "Descrição do Produto Novo", 5);

        when(produtoMapper.toEntity(request)).thenReturn(produto);
        when(produtoRepository.save(produto)).thenReturn(savedProduto);
        when(produtoMapper.toResponse(savedProduto)).thenReturn(new ProdutoResponse(1L, "Produto Novo", 20.0, "Descrição do Produto Novo", 5));

        ProdutoResponse response = produtoService.criarProduto(request);

        assertNotNull(response);
        assertEquals("Produto Novo", response.getNome());
    }

        @Test
    void shouldDeleteProductWhenIdIsValid() {
        Long productId = 1L;

        when(produtoRepository.existsById(productId)).thenReturn(true);

        assertDoesNotThrow(() -> produtoService.excluirProduto(productId));
        verify(produtoRepository).deleteById(productId);
    }

    @Test
    void shouldThrowProdutoNotFoundExceptionWhenDeletingNonExistentProduct() {
        Long productId = 1L;

        when(produtoRepository.existsById(productId)).thenReturn(false);

        assertThrows(ProdutoNotFoundException.class, () -> {
            produtoService.excluirProduto(productId);
        });
    }

    @Test
    void shouldReturnListOfProducts() {
        Produto produto1 = new Produto(1L, "Produto 1", 10.0, "Descrição 1", 5);
        Produto produto2 = new Produto(2L, "Produto 2", 20.0, "Descrição 2", 10);
        
        List<Produto> produtos = Arrays.asList(produto1, produto2);
        
        when(produtoRepository.findAll()).thenReturn(produtos);
        when(produtoMapper.toResponse(produto1)).thenReturn(new ProdutoResponse(1L, "Produto 1", 10.0, "Descrição 1", 5));
        when(produtoMapper.toResponse(produto2)).thenReturn(new ProdutoResponse(2L, "Produto 2", 20.0, "Descrição 2", 10));

        List<ProdutoResponse> response = produtoService.listarProdutos();

        assertNotNull(response);
        assertEquals(2, response.size());
    }

}
