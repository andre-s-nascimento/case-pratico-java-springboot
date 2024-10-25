package dev.andrenascimento.case_pratico_java_springboot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import dev.andrenascimento.case_pratico_java_springboot.exceptions.ProdutoNotFoundException;

public class ProdutoNotFoundExceptionTest {
    @Test
    void shouldCreateExceptionWithCustomMessage() {
        // Preparação
        String customMessage = "Produto com ID 1 não encontrado!";

        // Executar o teste
        ProdutoNotFoundException exception = new ProdutoNotFoundException(customMessage);

        // Expectativas - Asserções
        assertEquals(customMessage, exception.getMessage());
    }

    @Test
    void shouldCreateExceptionWithDefaultMessage() {
        // Executar o teste
        ProdutoNotFoundException exception = new ProdutoNotFoundException();

        // Expectativas - Asserções
        assertEquals("Produto Não Encontrado!", exception.getMessage());
    }
}
