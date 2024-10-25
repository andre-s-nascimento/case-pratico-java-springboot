package dev.andrenascimento.case_pratico_java_springboot.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

// Para receber dados na criação e atualização de produtos.
public class ProdutoRequest {
    
    @NotNull(message = "Nome do produto é obrigatório")
    @Size(min = 2, message = "O nome do produto deve ter no mínimo 2 caracteres")
    private String nome;

    @PositiveOrZero(message = "O preço não pode ser menor do que 0")
    private Double preco;
    private String descricao;
    @PositiveOrZero(message = "A quantidade não pode ser menor do que 0")
    private Integer quantidadeEmEstoque;

    
    public ProdutoRequest(
            @NotNull(message = "Nome do produto é obrigatório") 
            @Size(min = 2, message = "O nome do produto deve ter no mínimo 2 caracteres") String nome,
            @PositiveOrZero(message = "O preço não pode ser menor do que 0") Double preco, String descricao, @PositiveOrZero(message = "A quantidade não pode ser menor do que 0") Integer quantidadeEmEstoque) {
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
        this.quantidadeEmEstoque = quantidadeEmEstoque;
    }

    public ProdutoRequest() {
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getQuantidadeEmEstoque() {
        return quantidadeEmEstoque;
    }

    public void setQuantidadeEmEstoque(Integer quantidadeEmEstoque) {
        this.quantidadeEmEstoque = quantidadeEmEstoque;
    }

}
