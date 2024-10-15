package dev.andrenascimento.case_pratico_java_springboot.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

// Para receber dados na criação e atualização de produtos.
public class ProdutoRequest {
    
    @NotNull(message = "Nome do produto é obrigatório")
    @Size(min = 2, message = "O nome do produto deve ter no mínimo 2 caracteres")
    private String nome;

    private Double preco;
    private String descricao;
    private Integer quantidadeEmEstoque;

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
