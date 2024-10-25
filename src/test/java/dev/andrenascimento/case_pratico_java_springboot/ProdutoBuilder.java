package dev.andrenascimento.case_pratico_java_springboot;

import dev.andrenascimento.case_pratico_java_springboot.models.Produto;

public class ProdutoBuilder {
    private Long id = 1L;
    private String nome = "Produto 1";
    private Double preco = 10.0;
    private String descricao = "Descrição Produto 1";
    private Integer quantidadeEmEstoque = 10;

    public ProdutoBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public ProdutoBuilder withNome(String nome) {
        this.nome = nome;
        return this;
    }

    public ProdutoBuilder withPreco(Double preco) {
        this.preco = preco;
        return this;
    }

    public ProdutoBuilder withDescricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public ProdutoBuilder withQuantidadeEmEstoque(Integer quantidadeEmEstoque) {
        this.quantidadeEmEstoque = quantidadeEmEstoque;
        return this;
    }

    public Produto build() {
        return new Produto(id, nome, preco, descricao, quantidadeEmEstoque);
    }
}
