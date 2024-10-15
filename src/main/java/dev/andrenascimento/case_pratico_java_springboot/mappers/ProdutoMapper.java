package dev.andrenascimento.case_pratico_java_springboot.mappers;

import dev.andrenascimento.case_pratico_java_springboot.dtos.ProdutoRequest;
import dev.andrenascimento.case_pratico_java_springboot.dtos.ProdutoResponse;
import dev.andrenascimento.case_pratico_java_springboot.models.Produto;

public interface ProdutoMapper {
    
    ProdutoResponse toResponse(Produto produto);

    Produto toEntity(ProdutoRequest produtoRequest);

}
