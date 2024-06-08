package br.com.stockeeper.gestao_inventario.repositories;

import br.com.stockeeper.gestao_inventario.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IinventarioRepository extends JpaRepository <Produto, Integer> {

        boolean existsByName(String produtoNome);
}
