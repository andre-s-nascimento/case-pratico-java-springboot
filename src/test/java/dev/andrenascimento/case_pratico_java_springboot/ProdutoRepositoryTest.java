package dev.andrenascimento.case_pratico_java_springboot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import dev.andrenascimento.case_pratico_java_springboot.models.Produto;
import dev.andrenascimento.case_pratico_java_springboot.repositories.ProdutoRepository;

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
        Produto produto = new Produto(null, "Produto 1", 10.0, "Descrição do Produto 1", 5);
        
        produtoRepository.save(produto);
        
        Optional<Produto> produtoEncontrado = produtoRepository.findById(produto.getId());
        
        assertTrue(produtoEncontrado.isPresent());
        assertEquals("Produto 1", produtoEncontrado.get().getNome());
    }

    @Test
    void shouldReturnEmptyWhenProdutoNotFound() {
        Optional<Produto> produtoEncontrado = produtoRepository.findById(999L); // ID que não existe
        
        assertFalse(produtoEncontrado.isPresent());
    }

    @Test
    void shouldDeleteProduto() {
        Produto produto = new Produto(null, "Produto 1", 10.0, "Descrição do Produto 1", 5);
        
        produto = produtoRepository.save(produto); // Salva o produto
        
        produtoRepository.deleteById(produto.getId()); // Exclui o produto
        
        Optional<Produto> produtoEncontrado = produtoRepository.findById(produto.getId());
        
        assertFalse(produtoEncontrado.isPresent()); // Verifica se o produto foi excluído
    }

    @Test
    void shouldFindAllProdutos() {
        Produto produto1 = new Produto(null, "Produto 1", 10.0, "Descrição do Produto 1", 5);
        Produto produto2 = new Produto(null, "Produto 2", 20.0, "Descrição do Produto 2", 10);
        
        produtoRepository.save(produto1);
        produtoRepository.save(produto2);
        
        List<Produto> produtos = produtoRepository.findAll();
        
        assertEquals(2, produtos.size()); // Verifica se dois produtos foram salvos
    }
}
