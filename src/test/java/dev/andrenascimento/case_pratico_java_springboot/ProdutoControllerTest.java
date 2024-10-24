package dev.andrenascimento.case_pratico_java_springboot;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.andrenascimento.case_pratico_java_springboot.controllers.ProdutoController;
import dev.andrenascimento.case_pratico_java_springboot.dtos.ProdutoRequest;
import dev.andrenascimento.case_pratico_java_springboot.dtos.ProdutoResponse;
import dev.andrenascimento.case_pratico_java_springboot.mappers.ProdutoMapper;
import dev.andrenascimento.case_pratico_java_springboot.models.Produto;
import dev.andrenascimento.case_pratico_java_springboot.services.ProdutoService;

public class ProdutoControllerTest {

    @InjectMocks
    private ProdutoController produtoController;

    @Mock
    private ProdutoService produtoService;

    @Mock
    private ProdutoMapper produtoMapper;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(produtoController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void shouldReturnAProductList() throws Exception {
        // Prepare o retorno do serviço
        List<ProdutoResponse> produtos = generateProdutoResponse(0);
        when(produtoService.listarProdutos()).thenReturn(produtos);

        // Execute o teste
        mockMvc.perform(get("/produtos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void shouldCreateProductWhenRequestIsValid() throws Exception {
        ProdutoRequest request = new ProdutoRequest("Produto 1", 10.0, "Descrição do Produto 1", 5);
        ProdutoResponse response = new ProdutoResponse(1L, "Produto 1", 10.0, "Descrição do Produto 1", 5);
        Produto produto = new Produto(1L, "Produto 1", 10.0, "Descrição do Produto 1", 5);

        when(produtoMapper.toEntity(any(ProdutoRequest.class))).thenReturn(produto);
        when(produtoService.criarProduto(any(ProdutoRequest.class))).thenReturn(response);

        mockMvc.perform(post("/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Produto 1"))
                .andExpect(jsonPath("$.preco").value(10.0))
                .andExpect(jsonPath("$.descricao").value("Descrição do Produto 1"))
                .andExpect(jsonPath("$.quantidadeEmEstoque").value(5));
    }

    @Test
    void shouldReturnBadRequestWhenProductRequestIsInvalid() throws Exception {
        ProdutoRequest request = new ProdutoRequest(null, -10.0, "Descrição", -5); // Nome nulo e preço negativo
        mockMvc.perform(post("/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnInternalServerErrorWhenServiceThrowsException() throws Exception {
        ProdutoRequest request = new ProdutoRequest("Produto 1", 10.0, "Descrição do Produto 1", 5);

        when(produtoMapper.toEntity(any(ProdutoRequest.class))).thenReturn(new Produto());
        when(produtoService.criarProduto(any(ProdutoRequest.class)))
                .thenThrow(new RuntimeException("Erro ao criar produto"));

        mockMvc.perform(post("/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void shouldUpdateProductWhenRequestIsValid() throws Exception {
        Long productId = 1L;
        ProdutoRequest request = new ProdutoRequest("Produto Atualizado", 15.0, "Descrição Atualizada", 10);
        ProdutoResponse response = new ProdutoResponse(productId, "Produto Atualizado", 15.0, "Descrição Atualizada",
                10);

        when(produtoService.atualizarProduto(eq(productId), any(ProdutoRequest.class))).thenReturn(response);

        mockMvc.perform(put("/produtos/{id}", productId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(productId))
                .andExpect(jsonPath("$.nome").value("Produto Atualizado"))
                .andExpect(jsonPath("$.preco").value(15.0))
                .andExpect(jsonPath("$.descricao").value("Descrição Atualizada"))
                .andExpect(jsonPath("$.quantidadeEmEstoque").value(10));
    }

    @Test
    void shouldReturnNotFoundWhenProductIdDoesNotExist() throws Exception {
        Long productId = 1L;
        ProdutoRequest request = new ProdutoRequest("Produto Atualizado", 15.0, "Descrição Atualizada", 10);
    
        // Simula a exceção lançada pelo serviço
        when(produtoService.atualizarProduto(eq(productId), any(ProdutoRequest.class)))
                .thenThrow(new RuntimeException("Produto não encontrado"));
    
        // Executa a requisição PUT
        mockMvc.perform(put("/produtos/{id}", productId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound()); // Espera-se um status 404
    }

    @Test
    void shouldDeleteProductWhenIdIsValid() throws Exception {
        Long productId = 1L;

        doNothing().when(produtoService).excluirProduto(productId);

        mockMvc.perform(delete("/produtos/{id}", productId))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnNotFoundWhenDeletingNonExistentProduct() throws Exception {
        Long productId = 1L;

        doThrow(new RuntimeException("Produto não encontrado")).when(produtoService).excluirProduto(productId);

        mockMvc.perform(delete("/produtos/{id}", productId))
                .andExpect(status().isNotFound());
    }

    List<ProdutoResponse> generateProdutoResponse(int quantidade) {
        List<ProdutoResponse> produtos = new ArrayList<>();

        // Retorna uma lista vazia se a quantidade for 0
        if (quantidade <= 0) {
            return produtos; // Retorna lista vazia para quantidade 0 ou negativa
        }

        // Gera a lista de produtos para quantidade maior que 0
        for (int i = 0; i < quantidade; i++) {
            ProdutoResponse produto = new ProdutoResponse();
            produto.setId(Long.valueOf(i));
            produto.setNome("Produto " + i);
            produto.setPreco(Double.valueOf(i) * 1.99);
            produto.setDescricao("Descrição do Produto " + i);
            produtos.add(produto);
        }

        return produtos;
    }

}
