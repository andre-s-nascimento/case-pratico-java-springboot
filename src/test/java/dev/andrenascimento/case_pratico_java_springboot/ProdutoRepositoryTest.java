package dev.andrenascimento.case_pratico_java_springboot;

import dev.andrenascimento.case_pratico_java_springboot.models.Produto;
import dev.andrenascimento.case_pratico_java_springboot.repositories.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class ProdutoRepositoryTest {
    @Autowired
    private ProdutoRepository produtoRepository;

    @BeforeEach
    public void setUp() {
        produtoRepository.deleteAll(); // Limpa a base de dados antes de cada teste
    }

    @Test
    void shouldSaveAndFindProduto() {
        // Preparação
        Produto produto = new Produto(null, "Produto 1", 10.0, "Descrição do Produto 1", 5);

        // Executar o teste
        produtoRepository.save(produto);
        Optional<Produto> produtoEncontrado = produtoRepository.findById(produto.getId());

        // Expectativas - Asserções
        assertTrue(produtoEncontrado.isPresent());
        assertEquals("Produto 1", produtoEncontrado.get().getNome());
    }

    @Test
    void shouldReturnEmptyWhenProdutoNotFound() {
        // Executar o teste
        Optional<Produto> produtoEncontrado = produtoRepository.findById(999L); // ID que não existe

        // Expectativas - Asserções
        assertFalse(produtoEncontrado.isPresent());
    }

    @Test
    void shouldDeleteProduto() {
        // Preparação
        Produto produto = new Produto(null, "Produto 1", 10.0, "Descrição do Produto 1", 5);
        produto = produtoRepository.save(produto); // Salva o produto

        // Executar o teste
        produtoRepository.deleteById(produto.getId()); // Exclui o produto

        // Expectativas - Asserções
        Optional<Produto> produtoEncontrado = produtoRepository.findById(produto.getId());
        assertFalse(produtoEncontrado.isPresent()); // Verifica se o produto foi excluído
    }

    @Test
    void shouldFindAllProdutos() {
        // Preparação
        Produto produto1 = new ProdutoBuilder()
                .withId(null)
                .withNome("Produto 1")
                .withPreco(10.0)
                .withDescricao("Descrição do Produto 1")
                .withQuantidadeEmEstoque(5).build();
        Produto produto2 = new ProdutoBuilder()
                .withId(null)
                .withNome("Produto 2")
                .withPreco(20.0)
                .withDescricao("Descrição do Produto 2")
                .withQuantidadeEmEstoque(10).build();

        // Executar o teste
        produtoRepository.save(produto1);
        produtoRepository.save(produto2);

        // Expectativas - Asserções
        List<Produto> produtos = produtoRepository.findAll();
        assertEquals(2, produtos.size()); // Verifica se dois produtos foram salvos
    }
}
