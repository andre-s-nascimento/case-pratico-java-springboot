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
        ProdutoRequest request = new ProdutoRequest("Produto 1", 10.0, "Descrição do Produto 1", 5);

        Produto produto = produtoMapper.toEntity(request);

        assertNotNull(produto);
        assertEquals("Produto 1", produto.getNome());
        assertEquals(10.0, produto.getPreco());
        assertEquals("Descrição do Produto 1", produto.getDescricao());
        assertEquals(5, produto.getQuantidadeEmEstoque());
    }

    @Test
    void shouldMapProdutoToProdutoResponse() {
        Produto produto = new Produto(1L, "Produto 1", 10.0, "Descrição do Produto 1", 5);

        ProdutoResponse response = produtoMapper.toResponse(produto);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Produto 1", response.getNome());
        assertEquals(10.0, response.getPreco());
        assertEquals("Descrição do Produto 1", response.getDescricao());
        assertEquals(5, response.getQuantidadeEmEstoque());
    }

    @Test
    void shouldReturnNullWhenProdutoRequestIsNull() {
        Produto produto = produtoMapper.toEntity(null);

        assertNull(produto);
    }

    @Test
    void shouldReturnNullWhenProdutoIsNull() {
        ProdutoResponse response = produtoMapper.toResponse(null);

        assertNull(response);
    }

    @Test
    void shouldMapOptionalFieldsCorrectly() {
        ProdutoRequest request = new ProdutoRequest("Produto 1", null, "Descrição do Produto 1", null);

        Produto produto = produtoMapper.toEntity(request);

        assertNotNull(produto);
        assertEquals("Produto 1", produto.getNome());
        assertNull(produto.getPreco()); // Verifica se o preço é null
        assertEquals("Descrição do Produto 1", produto.getDescricao());
        assertNull(produto.getQuantidadeEmEstoque()); // Verifica se a quantidade é null
    }
}
