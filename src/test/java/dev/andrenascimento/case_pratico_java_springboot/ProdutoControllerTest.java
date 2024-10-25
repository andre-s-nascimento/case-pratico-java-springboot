package dev.andrenascimento.case_pratico_java_springboot;

import dev.andrenascimento.case_pratico_java_springboot.controllers.ProdutoController;
import dev.andrenascimento.case_pratico_java_springboot.dtos.ProdutoRequest;
import dev.andrenascimento.case_pratico_java_springboot.dtos.ProdutoResponse;
import dev.andrenascimento.case_pratico_java_springboot.exceptions.ProdutoNotFoundException;
import dev.andrenascimento.case_pratico_java_springboot.services.ProdutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProdutoControllerTest {

    @InjectMocks private ProdutoController produtoController;

    @Mock private ProdutoService produtoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnAListOfProdutos() {
        // Preparação
        List<ProdutoResponse> produtos = generateProdutoResponse(2);
        when(produtoService.listarProdutos()).thenReturn(produtos);

        // Executar o teste
        ResponseEntity<List<ProdutoResponse>> response = produtoController.listarProdutos();

        // Expectativas - Asserções
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(produtos, response.getBody());
        verify(produtoService, times(1)).listarProdutos();
    }

    @Test
    void shouldCreateProdutoWhenRequestIsValid() {
        // Preparação
        ProdutoRequest produtoRequest = new ProdutoRequestBuilder().withNome("Produto 1")
                .withPreco(10.0)
                .withDescricao("Descrição do Produto 1")
                .withQuantidadeEmEstoque(5)
                .build();
        ProdutoResponse produtoResponse = new ProdutoResponseBuilder().withId(5L)
                .withNome("Produto 1")
                .withPreco(10.0)
                .withDescricao("Descrição do Produto 1")
                .withQuantidadeEmEstoque(5)
                .build();

        when(produtoService.criarProduto(produtoRequest)).thenReturn(produtoResponse);

        // Executar o teste
        ResponseEntity<ProdutoResponse> response = produtoController.criarProduto(produtoRequest);

        // Expectativas - Asserts
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(produtoResponse, response.getBody());
        verify(produtoService, times(1)).criarProduto(produtoRequest);
    }

    @Test
    void shouldUpdateProductWhenRequestIsValid() {
        // Preparação
        Long produtoId = 1L;
        ProdutoRequest produtoRequest = new ProdutoRequestBuilder().withNome("Produto Atualizado")
                .withPreco(15.0)
                .withDescricao("Descrição Atualizada")
                .withQuantidadeEmEstoque(10)
                .build();
        ProdutoResponse produtoResponse = new ProdutoResponseBuilder().withId(produtoId)
                .withNome("Produto Atualizado")
                .withPreco(15.0)
                .withDescricao("Descrição Atualizada")
                .withQuantidadeEmEstoque(10)
                .build();

        when(produtoService.atualizarProduto(produtoId, produtoRequest)).thenReturn(produtoResponse);

        // Execute o teste
        ResponseEntity<ProdutoResponse> response = produtoController.atualizarProduto(produtoId, produtoRequest);

        // Expectativas - Asserts
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(produtoResponse, response.getBody());
        verify(produtoService, times(1)).atualizarProduto(produtoId, produtoRequest);
    }

    @Test
    void shouldDeleteProductWhenIdIsValid() {
        // Preparação
        Long produtoId = 1L;

        doNothing().when(produtoService).excluirProduto(produtoId);

        // Execute o teste
        ResponseEntity<Void> response = produtoController.excluirProduto(produtoId);

        // Expectativas - Asserts
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(produtoService, times(1)).excluirProduto(produtoId);
    }

    @Test
    void shouldReturnNotFoundWhenDeletingNonExistentProduto() {
        // Preparação
        Long produtoId = 1L;
        doThrow(ProdutoNotFoundException.class).when(produtoService).excluirProduto(produtoId);

        // Execute o teste
        assertThrows(ProdutoNotFoundException.class, () -> produtoController.excluirProduto(produtoId));

        // Expectativas - Asserts
        verify(produtoService, times(1)).excluirProduto(produtoId);
    }

    @Test
    void shouldReturnInternalServerErrorWhenServiceThrowsException() {
        // Preparação
        Long produtoId = 1L;
        ProdutoRequest produtoRequest = new ProdutoRequest();
        doThrow(new RuntimeException("Erro genérico")).when(produtoService).atualizarProduto(produtoId, produtoRequest);

        // Executar o teste
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> produtoController.atualizarProduto(produtoId, produtoRequest));

        // Expectativas - Asserções
        assertEquals("Erro genérico", exception.getMessage());

    }

    @Test
    void shouldReturnNotFoundWhenProdutoIdDoesNotExist() {
        // Preparação
        Long produtoId = 1L;
        ProdutoRequest produtoRequest = new ProdutoRequest();
        doThrow(ProdutoNotFoundException.class).when(produtoService).atualizarProduto(produtoId, produtoRequest);

        // Executar o teste - Expectativas - Asserções
        assertThrows(ProdutoNotFoundException.class,
                () -> produtoController.atualizarProduto(produtoId, produtoRequest));
    }

    List<ProdutoResponse> generateProdutoResponse(int quantidade) {
        List<ProdutoResponse> produtos = new ArrayList<>();

        // Retorna uma lista vazia se a quantidade for 0
        if (quantidade <= 0) {
            return produtos; // Retorna lista vazia para quantidade 0 ou negativa
        }

        // Gera a lista de produtos para quantidade maior que 0
        for (int i = 0; i < quantidade; i++) {
            ProdutoResponse produto = new ProdutoResponseBuilder().withId((long) i)
                    .withNome("Produto " + i)
                    .withPreco((double) i * 1.99)
                    .withDescricao("Descrição do Produto " + i)
                    .withQuantidadeEmEstoque(i)
                    .build();
            produtos.add(produto);
        }

        return produtos;
    }

}
