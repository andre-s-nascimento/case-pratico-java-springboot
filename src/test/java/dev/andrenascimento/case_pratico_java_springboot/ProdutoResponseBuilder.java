package dev.andrenascimento.case_pratico_java_springboot;

import dev.andrenascimento.case_pratico_java_springboot.dtos.ProdutoResponse;

public class ProdutoResponseBuilder {
    private Long id = 1L;
    private String nome = "Produto 1";
    private Double preco = 10.0;
    private String descricao = "Descrição Produto 1";
    private Integer quantidadeEmEstoque = 10;

    public ProdutoResponseBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public ProdutoResponseBuilder withNome(String nome) {
        this.nome = nome;
        return this;
    }

    public ProdutoResponseBuilder withPreco(Double preco) {
        this.preco = preco;
        return this;
    }

    public ProdutoResponseBuilder withDescricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public ProdutoResponseBuilder withQuantidadeEmEstoque(Integer quantidadeEmEstoque) {
        this.quantidadeEmEstoque = quantidadeEmEstoque;
        return this;
    }

    public ProdutoResponse build() {
        return new ProdutoResponse(id, nome, preco, descricao, quantidadeEmEstoque);
    }
}
