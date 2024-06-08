package br.com.stockeeper.gestao_inventario.repositories;

import br.com.stockeeper.gestao_inventario.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IinventarioRepository extends JpaRepository <Produto, Integer> {

        boolean existsByName(String produtoNome);

        Optional<Produto> findByName(String produtoNome);

        List<Produto> findByMarca(String marca);
}
