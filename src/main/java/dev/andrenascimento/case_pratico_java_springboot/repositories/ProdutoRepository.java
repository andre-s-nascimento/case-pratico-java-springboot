package dev.andrenascimento.case_pratico_java_springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.andrenascimento.case_pratico_java_springboot.models.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
