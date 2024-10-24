package dev.andrenascimento.case_pratico_java_springboot.exceptions;

public class ProdutoNotFoundException extends RuntimeException {

    public ProdutoNotFoundException(String message) {
        super(message);
    }

    public ProdutoNotFoundException() {
        super("Produto Não Encontrado!");
    }
}
