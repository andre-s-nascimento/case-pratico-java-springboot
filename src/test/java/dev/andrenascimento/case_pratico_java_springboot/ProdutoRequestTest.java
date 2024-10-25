package dev.andrenascimento.case_pratico_java_springboot;

import dev.andrenascimento.case_pratico_java_springboot.dtos.ProdutoRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProdutoRequestTest {
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldBeValidWhenAllFieldsAreValid() {
        // Preparação
        ProdutoRequest request = new ProdutoRequest();
        request.setNome("Produto 1");
        request.setPreco(10.0);
        request.setDescricao("Descrição do Produto 1");
        request.setQuantidadeEmEstoque(5);
        request.setNome("Produto 1");

        // Executar o teste
        Set<ConstraintViolation<ProdutoRequest>> violations = validator.validate(request);

        // Expectativas - Asserções
        assertTrue(violations.isEmpty());
    }

    @Test
    void shouldBeInvalidWhenNomeIsNull() {
        // Preparação
        ProdutoRequest request = new
                ProdutoRequestBuilder()
                .withNome(null)
                .withPreco(10.0)
                .withDescricao("Descrição do Produto 1")
                .withQuantidadeEmEstoque(5)
                .build();

        // Executar o teste
        Set<ConstraintViolation<ProdutoRequest>> violations = validator.validate(request);

        // Expectativas - Asserções
        assertEquals(1, violations.size());
        assertEquals("Nome do produto é obrigatório", violations.iterator().next().getMessage());
    }

    @Test
    void shouldBeInvalidWhenNomeIsShorterThanTwoCharacters() {
        // Preparação
        ProdutoRequest request = new ProdutoRequestBuilder()
                .withNome("A")
                .withPreco(10.0)
                .withDescricao("Descrição do Produto 1")
                .withQuantidadeEmEstoque(5)
                .build();

        // Executar o teste
        Set<ConstraintViolation<ProdutoRequest>> violations = validator.validate(request);

        // Expectativas - Asserções
        assertEquals(1, violations.size());
        assertEquals("O nome do produto deve ter no mínimo 2 caracteres", violations.iterator().next().getMessage());
    }

    @Test
    void shouldBeInvalidWhenPrecoIsNegative() {
        // Preparação
        ProdutoRequest request = new ProdutoRequestBuilder()
                .withNome("Produto 1")
                .withPreco(-10.0)
                .withDescricao("Descrição do Produto 1")
                .withQuantidadeEmEstoque(5)
                .build();

        // Executar o teste
        Set<ConstraintViolation<ProdutoRequest>> violations = validator.validate(request);

        // Expectativas - Asserções
        assertEquals(1, violations.size());
        assertEquals("O preço não pode ser menor do que 0", violations.iterator().next().getMessage());
    }

    @Test
    void shouldBeInvalidWhenQuantidadeIsNegative() {
        // Preparação
        ProdutoRequest request = new ProdutoRequestBuilder()
                .withNome("Produto 1")
                .withPreco(10.0)
                .withDescricao("Descrição do Produto 1")
                .withQuantidadeEmEstoque(-5)
                .build();

        // Executar o teste
        Set<ConstraintViolation<ProdutoRequest>> violations = validator.validate(request);

        // Expectativas - Asserções
        assertEquals(1, violations.size());
        assertEquals("A quantidade não pode ser menor do que 0", violations.iterator().next().getMessage());
    }
}
