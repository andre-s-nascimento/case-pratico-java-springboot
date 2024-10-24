package dev.andrenascimento.case_pratico_java_springboot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dev.andrenascimento.case_pratico_java_springboot.dtos.ProdutoRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class ProdutoRequestTest {
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldBeValidWhenAllFieldsAreValid() {
        ProdutoRequest request = new ProdutoRequest("Produto 1", 10.0, "Descrição do Produto 1", 5);
        Set<ConstraintViolation<ProdutoRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    void shouldBeInvalidWhenNomeIsNull() {
        ProdutoRequest request = new ProdutoRequest(null, 10.0, "Descrição", 5);
        Set<ConstraintViolation<ProdutoRequest>> violations = validator.validate(request);
        assertEquals(1, violations.size());
        assertEquals("Nome do produto é obrigatório", violations.iterator().next().getMessage());
    }

    @Test
    void shouldBeInvalidWhenNomeIsShorterThanTwoCharacters() {
        ProdutoRequest request = new ProdutoRequest("A", 10.0, "Descrição", 5);
        Set<ConstraintViolation<ProdutoRequest>> violations = validator.validate(request);
        assertEquals(1, violations.size());
        assertEquals("O nome do produto deve ter no mínimo 2 caracteres", violations.iterator().next().getMessage());
    }
}
