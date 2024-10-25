package dev.andrenascimento.case_pratico_java_springboot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import dev.andrenascimento.case_pratico_java_springboot.dtos.ProdutoResponse;

public class ProdutoResponseTest {
    
    @Test
    void shouldCreateProdutoResponseWithAllFields() {
        // Preparação e Execução
        ProdutoResponse response = new ProdutoResponseBuilder()
        .withId(1L)
        .withNome("Produto 1")
        .withPreco(10.0)
        .withDescricao("Descrição do Produto 1")
        .withQuantidadeEmEstoque(5)
        .build();

        // Expectativas - Asserções
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Produto 1", response.getNome());
        assertEquals(10.0, response.getPreco());
        assertEquals("Descrição do Produto 1", response.getDescricao());
        assertEquals(5, response.getQuantidadeEmEstoque());
    }

    @Test
    void shouldSetAndGetFieldsCorrectly() {
        // Preparação e Execução
        ProdutoResponse response = new ProdutoResponse();
        response.setId(2L);
        response.setNome("Produto 2");
        response.setPreco(20.0);
        response.setDescricao("Descrição do Produto 2");
        response.setQuantidadeEmEstoque(10);

        // Expectativas - Asserções
        assertEquals(2L, response.getId());
        assertEquals("Produto 2", response.getNome());
        assertEquals(20.0, response.getPreco());
        assertEquals("Descrição do Produto 2", response.getDescricao());
        assertEquals(10, response.getQuantidadeEmEstoque());
    }
}
