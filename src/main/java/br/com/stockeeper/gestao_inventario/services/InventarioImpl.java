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

import java.util.Set;

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
}
