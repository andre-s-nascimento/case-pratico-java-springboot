package dev.andrenascimento.case_pratico_java_springboot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import dev.andrenascimento.case_pratico_java_springboot.models.Produto;

public class ProdutoTest {
    @Test
    void shouldCreateProdutoWithAllFields() {
        // Preparação e Execução
        Produto produto = new ProdutoBuilder()
        .withId(1L)
        .withNome("Produto 1")
        .withPreco(10.0)
        .withDescricao("Descrição do Produto 1")
        .withQuantidadeEmEstoque(5)
        .build();

        // Expectativas - Asserções
        assertNotNull(produto);
        assertEquals(1L, produto.getId());
        assertEquals("Produto 1", produto.getNome());
        assertEquals(10.0, produto.getPreco());
        assertEquals("Descrição do Produto 1", produto.getDescricao());
        assertEquals(5, produto.getQuantidadeEmEstoque());
    }

    @Test
    void shouldSetAndGetFieldsCorrectly() {
        // Preparação e Execução
        Produto produto = new Produto();
        produto.setId(2L);
        produto.setNome("Produto 2");
        produto.setPreco(20.0);
        produto.setDescricao("Descrição do Produto 2");
        produto.setQuantidadeEmEstoque(10);

        // Expectativas - Asserções
        assertEquals(2L, produto.getId());
        assertEquals("Produto 2", produto.getNome());
        assertEquals(20.0, produto.getPreco());
        assertEquals("Descrição do Produto 2", produto.getDescricao());
        assertEquals(10, produto.getQuantidadeEmEstoque());
    }
}
