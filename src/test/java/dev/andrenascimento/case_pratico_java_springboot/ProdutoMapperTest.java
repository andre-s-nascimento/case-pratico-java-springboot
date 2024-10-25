package dev.andrenascimento.case_pratico_java_springboot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import dev.andrenascimento.case_pratico_java_springboot.dtos.ProdutoRequest;
import dev.andrenascimento.case_pratico_java_springboot.dtos.ProdutoResponse;
import dev.andrenascimento.case_pratico_java_springboot.mappers.ProdutoMapperImpl;
import dev.andrenascimento.case_pratico_java_springboot.models.Produto;

public class ProdutoMapperTest {

    private final ProdutoMapperImpl produtoMapper = new ProdutoMapperImpl();

    @Test
    void shouldMapProdutoRequestToProduto() {
        // Preparação
        ProdutoRequest request = new ProdutoRequestBuilder()
        .withNome("Produto 1").withPreco(10.0).withDescricao("Descrição do Produto 1").withQuantidadeEmEstoque(5).build();

        // Executar o teste
        Produto produto = produtoMapper.toEntity(request);

        // Expectativas - Asserções
        assertNotNull(produto);
        assertEquals("Produto 1", produto.getNome());
        assertEquals(10.0, produto.getPreco());
        assertEquals("Descrição do Produto 1", produto.getDescricao());
        assertEquals(5, produto.getQuantidadeEmEstoque());
    }

    @Test
    void shouldMapProdutoToProdutoResponse() {
        // Preparação
        Produto produto = new ProdutoBuilder()
        .withId(1L).withNome("Produto 1").withPreco(10.0).withDescricao("Descrição do Produto 1").withQuantidadeEmEstoque(5).build();

        // Executar o teste
        ProdutoResponse response = produtoMapper.toResponse(produto);

        // Expectativas - Asserções
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Produto 1", response.getNome());
        assertEquals(10.0, response.getPreco());
        assertEquals("Descrição do Produto 1", response.getDescricao());
        assertEquals(5, response.getQuantidadeEmEstoque());
    }

    @Test
    void shouldReturnNullWhenProdutoRequestIsNull() {
        // Executar o teste
        Produto produto = produtoMapper.toEntity(null);

        // Expectativas - Asserções
        assertNull(produto);
    }

    @Test
    void shouldReturnNullWhenProdutoIsNull() {
        // Executar o teste
        ProdutoResponse response = produtoMapper.toResponse(null);

        // Expectativas - Asserções
        assertNull(response);
    }

    @Test
    void shouldMapOptionalFieldsCorrectly() {
        // Preparação
        ProdutoRequest request = new ProdutoRequestBuilder()
        .withNome("Produto 1").withPreco(null).withDescricao("Descrição do Produto 1").withQuantidadeEmEstoque(null).build();

        // Executar o teste
        Produto produto = produtoMapper.toEntity(request);

        // Expectativas - Asserções
        assertNotNull(produto);
        assertEquals("Produto 1", produto.getNome());
        assertNull(produto.getPreco()); // Verifica se o preço é null
        assertEquals("Descrição do Produto 1", produto.getDescricao());
        assertNull(produto.getQuantidadeEmEstoque()); // Verifica se a quantidade é null
    }
}
