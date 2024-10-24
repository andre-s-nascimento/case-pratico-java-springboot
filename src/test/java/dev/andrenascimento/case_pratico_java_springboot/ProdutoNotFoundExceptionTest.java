package dev.andrenascimento.case_pratico_java_springboot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import dev.andrenascimento.case_pratico_java_springboot.exceptions.ProdutoNotFoundException;

public class ProdutoNotFoundExceptionTest {
    @Test
    void shouldCreateExceptionWithCustomMessage() {
        String customMessage = "Produto com ID 1 não encontrado!";
        ProdutoNotFoundException exception = new ProdutoNotFoundException(customMessage);

        assertEquals(customMessage, exception.getMessage());
    }

    @Test
    void shouldCreateExceptionWithDefaultMessage() {
        ProdutoNotFoundException exception = new ProdutoNotFoundException();

        assertEquals("Produto Não Encontrado!", exception.getMessage());
    }
}
