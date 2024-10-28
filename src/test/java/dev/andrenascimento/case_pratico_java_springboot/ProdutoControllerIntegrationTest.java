package dev.andrenascimento.case_pratico_java_springboot;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.andrenascimento.case_pratico_java_springboot.dtos.ProdutoRequest;
import dev.andrenascimento.case_pratico_java_springboot.dtos.ProdutoResponse;
import dev.andrenascimento.case_pratico_java_springboot.exceptions.ProdutoNotFoundException;
import dev.andrenascimento.case_pratico_java_springboot.services.ProdutoService;

@SpringBootTest
@AutoConfigureMockMvc
public class ProdutoControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProdutoService produtoService;

    @Test
    public void shouldListProdutos() throws Exception {
        // Preparação
        ProdutoResponse produtoResponse = new ProdutoResponseBuilder()
                .withId(1L)
                .withNome("Produto A")
                .withPreco(10.0)
                .withDescricao("Descrição do Produto A")
                .withQuantidadeEmEstoque(100)
                .build();

        List<ProdutoResponse> produtos = Collections.singletonList(produtoResponse);
        when(produtoService.listarProdutos()).thenReturn(produtos);

        // Executar Testes + Expectativas - Asserções
        mockMvc.perform(get("/produtos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nome").value("Produto A"))
                .andExpect(jsonPath("$[0].preco").value(10))
                .andExpect(jsonPath("$[0].descricao").value("Descrição do Produto A"))
                .andExpect(jsonPath("$[0].quantidadeEmEstoque").value(100));
    }

    @Test
    public void shouldCreateProduto() throws Exception {
        // Preparação
        ProdutoRequest novoProduto = new ProdutoRequestBuilder()
                .withNome("Produto Teste E")
                .withDescricao("Descrição Teste E")
                .withPreco(50.0)
                .withQuantidadeEmEstoque(500)
                .build();

        ProdutoResponse produtoResponse = new ProdutoResponseBuilder()
                .withId(5L)
                .withNome("Produto Teste E")
                .withDescricao("Descrição Teste E")
                .withPreco(50.0)
                .withQuantidadeEmEstoque(500)
                .build();

        when(produtoService.criarProduto(any(ProdutoRequest.class))).thenReturn(produtoResponse);

        // Executar Testes + Expectativas - Asserções
        mockMvc.perform(post("/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(novoProduto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(5))
                .andExpect(jsonPath("$.nome").value("Produto Teste E"))
                .andExpect(jsonPath("$.preco").value(50.0))
                .andExpect(jsonPath("$.descricao").value("Descrição Teste E"))
                .andExpect(jsonPath("$.quantidadeEmEstoque").value(500));
    }

    @Test
    public void shouldUpdateProduto() throws Exception {
        // Preparação
        Long produtoId = 5L;
        ProdutoRequest produtoAtualizado = new ProdutoRequestBuilder()
                .withNome("Produto Teste E Atualizado")
                .withDescricao("Descrição Teste E Atualizada")
                .withPreco(25.0)
                .withQuantidadeEmEstoque(250)
                .build();

        ProdutoResponse produtoResponse = new ProdutoResponseBuilder()
                .withId(produtoId)
                .withNome("Produto Teste E Atualizado")
                .withDescricao("Descrição Teste E Atualizada")
                .withPreco(25.0)
                .withQuantidadeEmEstoque(250)
                .build();

        when(produtoService.atualizarProduto(any(), any())).thenReturn(produtoResponse);

        // Executar Testes + Expectativas - Asserções
        mockMvc.perform(put("/produtos/{id}", produtoId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(produtoAtualizado)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(5))
                .andExpect(jsonPath("$.nome").value("Produto Teste E Atualizado"))
                .andExpect(jsonPath("$.preco").value(25.0))
                .andExpect(jsonPath("$.descricao").value("Descrição Teste E Atualizada"))
                .andExpect(jsonPath("$.quantidadeEmEstoque").value(250));
    }

    @Test
    public void shouldDeleteProduto() throws Exception {
        // Executar Testes + Expectativas - Asserções
        mockMvc.perform(delete("/produtos/{id}", 1))
                .andExpect(status().isNoContent());
        verify(produtoService, times(1)).excluirProduto(1L);
    }

    @Test
    public void shouldReturnNotFoundWhenDeleteAProdutoThatIsNotFound() throws Exception {
        // Preparação
        doThrow(new ProdutoNotFoundException("Produto Não Encontrado!"))
                .when(produtoService).excluirProduto(129L);

        // Executar Testes + Expectativas - Asserções
        mockMvc.perform(delete("/produtos/{id}", 129L))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Produto Não Encontrado!"));
        verify(produtoService, times(1)).excluirProduto(eq(129L));
    }

    @Test
    public void shouldReturnNotFoundWhenUpdateAProdutoThatIsNotFound() throws Exception {
        // Preparação
        ProdutoRequest produtoAtualizado = new ProdutoRequestBuilder()
                .withNome("Produto Atualizado")
                .withDescricao("Nova Descrição")
                .withPreco(20.0)
                .withQuantidadeEmEstoque(200)
                .build();
                
        when(produtoService.atualizarProduto(eq(129L), any(ProdutoRequest.class)))
            .thenThrow(new ProdutoNotFoundException("Produto Não Encontrado!"));

        // Executar Testes + Expectativas - Asserções
        mockMvc.perform(put("/produtos/{id}", 129L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(produtoAtualizado)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Produto Não Encontrado!"));
                    
        verify(produtoService, times(1)).atualizarProduto(eq(129L), any(ProdutoRequest.class));
    }
}
