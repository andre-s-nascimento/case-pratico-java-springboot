package dev.andrenascimento.case_pratico_java_springboot;

import dev.andrenascimento.case_pratico_java_springboot.dtos.ProdutoRequest;

public class ProdutoRequestBuilder {

    private String nome = "Produto 1";
    private Double preco = 10.0;
    private String descricao = "Descrição Produto 1";
    private Integer quantidadeEmEstoque = 10;

    public ProdutoRequestBuilder withNome(String nome) {
        this.nome = nome;
        return this;
    }

    public ProdutoRequestBuilder withPreco(Double preco) {
        this.preco = preco;
        return this;
    }

    public ProdutoRequestBuilder withDescricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public ProdutoRequestBuilder withQuantidadeEmEstoque(Integer quantidadeEmEstoque) {
        this.quantidadeEmEstoque = quantidadeEmEstoque;
        return this;
    }

    public ProdutoRequest build() {
        return new ProdutoRequest(nome, preco, descricao, quantidadeEmEstoque);
    }
}
