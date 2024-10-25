package dev.andrenascimento.case_pratico_java_springboot;

import dev.andrenascimento.case_pratico_java_springboot.dtos.ProdutoRequest;
import dev.andrenascimento.case_pratico_java_springboot.dtos.ProdutoResponse;
import dev.andrenascimento.case_pratico_java_springboot.exceptions.ProdutoNotFoundException;
import dev.andrenascimento.case_pratico_java_springboot.mappers.ProdutoMapper;
import dev.andrenascimento.case_pratico_java_springboot.models.Produto;
import dev.andrenascimento.case_pratico_java_springboot.repositories.ProdutoRepository;
import dev.andrenascimento.case_pratico_java_springboot.services.ProdutoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    void shouldUpdateProdutoWhenIdIsValid() {
        // Preparação
        Long produtoId = 1L;
        ProdutoRequest request = new ProdutoRequestBuilder().withNome("Produto Atualizado")
                .withPreco(15.0).withDescricao("Descrição Atualizada").withQuantidadeEmEstoque(10).build();
        Produto produtoExistente = new ProdutoBuilder().withId(produtoId)
                .withNome("Produto Antigo")
                .withPreco(10.0)
                .withDescricao("Descrição Antiga")
                .withQuantidadeEmEstoque(5)
                .build();
        Produto produtoAtualizado = new ProdutoBuilder().withId(produtoId)
                .withNome("Produto Atualizado")
                .withPreco(15.0)
                .withDescricao("Descrição Atualizada")
                .withQuantidadeEmEstoque(10)
                .build();

        when(produtoRepository.findById(produtoId)).thenReturn(Optional.of(produtoExistente));
        when(produtoRepository.existsById(produtoId)).thenReturn(true);
        when(produtoMapper.toEntity(request)).thenReturn(produtoAtualizado);
        when(produtoRepository.save(produtoAtualizado)).thenReturn(produtoAtualizado);
        when(produtoMapper.toResponse(produtoAtualizado))
                .thenReturn(new ProdutoResponseBuilder().withId(produtoId)
                        .withNome("Produto Atualizado").withPreco(15.0).withDescricao("Descrição Atualizada")
                        .withQuantidadeEmEstoque(10).build());

        // Executar o teste
        ProdutoResponse response = produtoService.atualizarProduto(produtoId, request);

        // Expectativas - Asserções
        assertNotNull(response);
        assertEquals("Produto Atualizado", response.getNome());
        assertEquals(15.0, response.getPreco());
    }

    @Test
    void shouldThrowProdutoNotFoundExceptionWhenIdDoesNotExist() {
        // Preparação
        Long produtoId = 1L;
        ProdutoRequest request = new ProdutoRequestBuilder().withNome("Produto Atualizado")
                .withPreco(15.0).withDescricao("Descrição Atualizada").withQuantidadeEmEstoque(10).build();

        when(produtoRepository.findById(produtoId)).thenReturn(Optional.empty());

        // Executar com Expectativas - Asserções
        assertThrows(ProdutoNotFoundException.class, () -> produtoService.atualizarProduto(produtoId, request));
    }

    @Test
    void shouldCreateProdutoSuccessfully() {
        // Preparação
        ProdutoRequest request = new ProdutoRequestBuilder().withNome("Produto Novo")
                .withPreco(20.0)
                .withDescricao("Descrição do Produto Novo")
                .withQuantidadeEmEstoque(5)
                .build();
        Produto produto = new ProdutoBuilder()
                .withId(null)
                .withNome("Produto Novo")
                .withPreco(20.0)
                .withDescricao("Descrição do Produto Novo")
                .withQuantidadeEmEstoque(5)
                .build();
        Produto produtoSalvo = new ProdutoBuilder()
                .withId(1L)
                .withNome("Produto Novo")
                .withPreco(20.0)
                .withDescricao("Descrição do Produto Novo")
                .withQuantidadeEmEstoque(5)
                .build();

        when(produtoMapper.toEntity(request)).thenReturn(produto);
        when(produtoRepository.save(produto)).thenReturn(produtoSalvo);
        when(produtoMapper.toResponse(produtoSalvo)).thenReturn(
                new ProdutoResponse(1L, "Produto Novo", 20.0, "Descrição do Produto Novo", 5));

        // Executar o teste
        ProdutoResponse response = produtoService.criarProduto(request);

        // Expectativas - Asserções
        assertNotNull(response);
        assertEquals("Produto Novo", response.getNome());
    }

    @Test
    void shouldDeleteProdutoWhenIdIsValid() {
        // Preparação
        Long produtoId = 1L;

        when(produtoRepository.existsById(produtoId)).thenReturn(true);

        // Executar o teste - Expectativas - Asserções
        assertDoesNotThrow(() -> produtoService.excluirProduto(produtoId));
        verify(produtoRepository).deleteById(produtoId);
    }

    @Test
    void shouldThrowProdutoNotFoundExceptionWhenDeletingNonExistentProduto() {
        // Preparação
        Long produtoId = 1L;

        when(produtoRepository.existsById(produtoId)).thenReturn(false);

        // Executar o teste - Expectativas - Asserções
        assertThrows(ProdutoNotFoundException.class, () -> produtoService.excluirProduto(produtoId));
    }

    @Test
    void shouldReturnListOfProdutos() {
        // Preparação
        Produto produto1 = new ProdutoBuilder().withId(1L)
                .withNome("Produto 1")
                .withPreco(10.0)
                .withDescricao("Descrição 1")
                .withQuantidadeEmEstoque(5)
                .build();
        Produto produto2 = new ProdutoBuilder().withId(2L)
                .withNome("Produto 2")
                .withPreco(20.0)
                .withDescricao("Descrição 2")
                .withQuantidadeEmEstoque(10)
                .build();

        List<Produto> produtos = Arrays.asList(produto1, produto2);

        when(produtoRepository.findAll()).thenReturn(produtos);
        when(produtoMapper.toResponse(produto1)).thenReturn(new ProdutoResponseBuilder().withId(1L)
                .withNome("Produto 1")
                .withPreco(10.0)
                .withDescricao("Descrição 1")
                .withQuantidadeEmEstoque(5)
                .build());
        when(produtoMapper.toResponse(produto2)).thenReturn(new ProdutoResponseBuilder().withId(2L)
                .withNome("Produto 2")
                .withPreco(20.0)
                .withDescricao("Descrição 2")
                .withQuantidadeEmEstoque(10)
                .build());

        // Executar o teste
        List<ProdutoResponse> response = produtoService.listarProdutos();

        // Expectativas - Asserções
        assertNotNull(response);
        assertEquals(2, response.size());
    }

}
