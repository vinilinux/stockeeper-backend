package br.com.stockeeper.gestao_inventario.services;

import br.com.stockeeper.gestao_inventario.entities.Produto;
import br.com.stockeeper.gestao_inventario.exceptions.InventarioException;
import br.com.stockeeper.gestao_inventario.repositories.IinventarioRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@Service
public class InventarioImpl implements Iinventario{

    private final IinventarioRepository inventarioRepository;

    @Autowired
    private Validator validator;

    public InventarioImpl(IinventarioRepository inventarioRepository) {
        this.inventarioRepository = inventarioRepository;
    }

    @Override
    public boolean salvar(@Validated Produto produto) {
        if (produtoExiste(produto.getNome())){
            return false;
        }

        Set<ConstraintViolation<Produto>> violations = validator.validate(produto);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

         try {
            inventarioRepository.save(produto);
            return true;
        } catch (Exception e) {
            throw new InventarioException("Falha ao salvar o produto", e);
        }
    }

    private boolean produtoExiste(String produtoNome) {
       return inventarioRepository.existsByName(produtoNome);
    }

    public List<Produto> listar() {
        try {
            return inventarioRepository.findAll();
        } catch (Exception e) {
            throw new InventarioException("Falha ao listar produtos", e);
        }
    }

    public Optional<Produto> buscarPorNome(String produtoNome) {
        try {
            return inventarioRepository.findByName(produtoNome);
        } catch (Exception e) {
            throw new InventarioException("Falha ao buscar o produto", e);
        }
    }

    public Optional<Produto> buscarPorId(Integer id) {
        try {
            return inventarioRepository.findById(id);
        } catch (Exception e) {
            throw new InventarioException("Falha ao buscar o produto", e);
        }
    }

    public List<Produto> listarPorMarca(String marca){
        try {
            return inventarioRepository.findByMarca(marca);
        } catch (Exception e) {
            throw new InventarioException("Falha ao buscar o produto", e);
        }
    }

}
