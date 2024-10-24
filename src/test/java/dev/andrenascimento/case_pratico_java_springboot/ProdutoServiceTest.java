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
        Long produtoId = 1L;
        ProdutoRequest request = new ProdutoRequest("Produto Atualizado", 15.0, "Descrição Atualizada", 10);
        Produto produtoExistente = new Produto(produtoId, "Produto Antigo", 10.0, "Descrição Antiga", 5);
        Produto produtoAtualizado = new Produto(produtoId, "Produto Atualizado", 15.0, "Descrição Atualizada", 10);

        when(produtoRepository.findById(produtoId)).thenReturn(Optional.of(produtoExistente));
        when(produtoMapper.toEntity(request)).thenReturn(produtoAtualizado);
        when(produtoRepository.save(produtoAtualizado)).thenReturn(produtoAtualizado);
        when(produtoMapper.toResponse(produtoAtualizado))
                .thenReturn(new ProdutoResponse(produtoId, "Produto Atualizado", 15.0, "Descrição Atualizada", 10));

        ProdutoResponse response = produtoService.atualizarProduto(produtoId, request);

        assertNotNull(response);
        assertEquals("Produto Atualizado", response.getNome());
        assertEquals(15.0, response.getPreco());
    }

    @Test
    void shouldThrowProdutoNotFoundExceptionWhenIdDoesNotExist() {
        Long produtoId = 1L;
        ProdutoRequest request = new ProdutoRequest("Produto Atualizado", 15.0, "Descrição Atualizada", 10);

        when(produtoRepository.findById(produtoId)).thenReturn(Optional.empty());

        assertThrows(ProdutoNotFoundException.class, () -> {
            produtoService.atualizarProduto(produtoId, request);
        });
    }

    @Test
    void shouldCreateProductSuccessfully() {
        ProdutoRequest request = new ProdutoRequest("Produto Novo", 20.0, "Descrição do Produto Novo", 5);
        Produto produto = new Produto(null, "Produto Novo", 20.0, "Descrição do Produto Novo", 5);
        Produto produtoSalvo = new Produto(1L, "Produto Novo", 20.0, "Descrição do Produto Novo", 5);

        when(produtoMapper.toEntity(request)).thenReturn(produto);
        when(produtoRepository.save(produto)).thenReturn(produtoSalvo);
        when(produtoMapper.toResponse(produtoSalvo)).thenReturn(new ProdutoResponse(1L, "Produto Novo", 20.0, "Descrição do Produto Novo", 5));

        ProdutoResponse response = produtoService.criarProduto(request);

        assertNotNull(response);
        assertEquals("Produto Novo", response.getNome());
    }

        @Test
    void shouldDeleteProductWhenIdIsValid() {
        Long produtoId = 1L;

        when(produtoRepository.existsById(produtoId)).thenReturn(true);

        assertDoesNotThrow(() -> produtoService.excluirProduto(produtoId));
        verify(produtoRepository).deleteById(produtoId);
    }

    @Test
    void shouldThrowProdutoNotFoundExceptionWhenDeletingNonExistentProduct() {
        Long produtoId = 1L;

        when(produtoRepository.existsById(produtoId)).thenReturn(false);

        assertThrows(ProdutoNotFoundException.class, () -> {
            produtoService.excluirProduto(produtoId);
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
