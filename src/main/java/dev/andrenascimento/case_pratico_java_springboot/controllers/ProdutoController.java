package dev.andrenascimento.case_pratico_java_springboot.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.andrenascimento.case_pratico_java_springboot.dtos.ProdutoRequest;
import dev.andrenascimento.case_pratico_java_springboot.dtos.ProdutoResponse;
import dev.andrenascimento.case_pratico_java_springboot.services.ProdutoService;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponse>> listarProdutos() {
        List<ProdutoResponse> produtos = produtoService.listarProdutos();
        return ResponseEntity.ok(produtos);
    }

    @PostMapping
    public ResponseEntity<ProdutoResponse> criarProduto(@RequestBody ProdutoRequest produtoRequest) {
        ProdutoResponse produtoResponse = produtoService.criarProduto(produtoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponse> atualizarProduto(@PathVariable Long id, @RequestBody ProdutoRequest produtoRequest) {
        ProdutoResponse produtoResponse = produtoService.atualizarProduto(id, produtoRequest);
        return ResponseEntity.ok(produtoResponse); 
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirProduto(@PathVariable Long id) {
        produtoService.excluirProduto(id);
        return ResponseEntity.noContent().build();
    }
}
