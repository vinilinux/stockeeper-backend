package br.com.stockeeper.gestao_inventario.services;

import br.com.stockeeper.gestao_inventario.entities.Imagem;
import br.com.stockeeper.gestao_inventario.entities.Produto;
import br.com.stockeeper.gestao_inventario.exceptions.InventarioException;
import br.com.stockeeper.gestao_inventario.repositories.IinventarioRepository;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static br.com.stockeeper.gestao_inventario.enums.Categoria.CORRENTE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
class InventarioImplTest {

    @MockBean
    private IinventarioRepository inventarioRepository;

    @Autowired
    private Iinventario service;

    private Validator validator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    @DisplayName("Adicionando com sucesso o produto")
    void salvarCase1() {
        // Arranjo

        List<Imagem> imagens = new ArrayList<>();

        imagens.add(new Imagem("/teste"));

        Produto produto = new Produto(
                "PS5",
                20,
                "LED",
                4000.0,
                "TV de LED",
                "Samsung",
                "Samsung",
                4.5,
                CORRENTE,
                imagens
        );

        Mockito.when(inventarioRepository.save(Mockito.any(Produto.class))).thenReturn(produto);

        boolean produtoSalvo = service.salvar(produto);

        assertTrue(produtoSalvo);

    }

    @Test
    @DisplayName("Adicionando um produto que já existe")
    void salvarCase2() {
        // Arranjo

        List<Imagem> imagens = new ArrayList<>();

        imagens.add(new Imagem("/teste"));

        Produto produto = new Produto(
                "PS5",
                20,
                "LED",
                4000.0,
                "TV de LED",
                "Samsung",
                "Samsung",
                4.5,
                CORRENTE,
                imagens
        );

        Mockito.when(inventarioRepository.existsByName(anyString())).thenReturn(true);

        boolean produtoSalvo = service.salvar(produto);

        assertFalse(produtoSalvo);

    }

    @Test
    @DisplayName("Adicionando um produto que não tenha todos os campos preenchido")
    void salvarCase3() {
        // Arranjo

        List<Imagem> imagens = new ArrayList<>();

        imagens.add(new Imagem("/teste"));

        Produto produto = new Produto(
                "PS5",
                null,
                "LED",
                4000.0,
                "TV de LED",
                "Samsung",
                "Samsung",
                4.5,
                CORRENTE,
                imagens
        );

        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () -> {
            service.salvar(produto);
                });
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        String errorMessage = exception.getConstraintViolations().stream()
                        .map(violation -> violation.getMessage())
                                .collect(Collectors.joining(", "));

        assertTrue(errorMessage.contains("Quantidade não pode estar em branco ou nulo."));


    }
}