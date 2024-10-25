package dev.andrenascimento.case_pratico_java_springboot;

import dev.andrenascimento.case_pratico_java_springboot.exceptions.GlobalExceptionHandler;
import dev.andrenascimento.case_pratico_java_springboot.exceptions.ProdutoNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GlobalExceptionHandlerTest {
    private final GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

    @Test
    void handleProdutoNotFoundException_ShouldReturnNotFoundStatusAndMessage() {
        // Preparação
        String errorMessage = "Produto não encontrado!";
        ProdutoNotFoundException exception = new ProdutoNotFoundException(errorMessage);

        // Executar o teste
        ResponseEntity<String> response = exceptionHandler.handleProdutoNotFoundException(exception);

        // Expectativas - Asserções
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(errorMessage, response.getBody());
    }

}