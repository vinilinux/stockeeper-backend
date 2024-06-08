package br.com.stockeeper.gestao_inventario.services;

import br.com.stockeeper.gestao_inventario.entities.Produto;

import java.util.List;
import java.util.Optional;

public interface Iinventario {

    public boolean salvar(Produto produto);

    public List<Produto> listar();

    public Optional<Produto> buscarPorNome(String produtoNome);

    public Optional<Produto> buscarPorId(Integer id);

    public List<Produto> listarPorMarca(String marca);

}
