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
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.mockito.Mockito.doThrow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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

    @Test
    @DisplayName("Listar todos os Produtos")
    void listarCase1() {
        // Arranjo
        Produto produto1 = new Produto("Produto1", 10, "Descrição1", 100.0, "Detalhe1", "Marca1", "Modelo1", 4.5, CORRENTE, null);
        Produto produto2 = new Produto("Produto2", 20, "Descrição2", 200.0, "Detalhe2", "Marca2", "Modelo2", 4.8, CORRENTE, null);
        List<Produto> produtos = Arrays.asList(produto1, produto2);

        Mockito.when(inventarioRepository.findAll()).thenReturn(produtos);

        // Act
        List<Produto> resultado = service.listar();

        // Assert
        assertEquals(produtos, resultado);
    }

    @Test
    @DisplayName("Listar produtos com exceção")
    void listarCase2() {
        // Arranjo
        doThrow(new RuntimeException("Erro ao acessar o repositório")).when(inventarioRepository).findAll();

        // Act & Assert
        InventarioException exception = assertThrows(InventarioException.class, () -> {
            service.listar();
        });

        assertTrue(exception.getMessage().contains("Falha ao listar produtos"));
    }

    @Test
    @DisplayName("Listar produto por nome")
    void buscarPorNomeCase1() {
        // Arranjo
        Produto produto1 = new Produto("Produto1", 10, "Descrição1", 100.0, "Detalhe1", "Marca1", "Modelo1", 4.5, CORRENTE, null);

        Optional<Produto> produto = Optional.of(produto1);

        Mockito.when(inventarioRepository.findByName(produto1.getNome())).thenReturn(produto);

        // Act
        Optional<Produto> resultado = service.buscarPorNome(produto1.getNome());

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals(produto1.getNome(), resultado.get().getNome());
    }

    @Test
    @DisplayName("Listar produto inexistente por nome")
    void buscarPorNomeCase2() {
        // Arranjo
        Produto produto1 = new Produto("Produto1", 10, "Descrição1", 100.0, "Detalhe1", "Marca1", "Modelo1", 4.5, CORRENTE, null);

        Optional<Produto> produto = Optional.empty();

        Mockito.when(inventarioRepository.findByName(produto1.getNome())).thenReturn(produto);

        // Act
        Optional<Produto> resultado = service.buscarPorNome(produto1.getNome());

        // Assert
        assertFalse(resultado.isPresent());
    }

    @Test
    @DisplayName("Listar produto por ID")
    void buscarPorIdCase1() {
        // Arranjo
        Produto produto1 = new Produto("Produto1", 10, "Descrição1", 100.0, "Detalhe1", "Marca1", "Modelo1", 4.5, CORRENTE, null);

        Optional<Produto> produto = Optional.of(produto1);

        Mockito.when(inventarioRepository.findById(1)).thenReturn(produto);

        // Act
        Optional<Produto> resultado = service.buscarPorId(1);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals(produto1.getNome(), resultado.get().getNome());
    }

    @Test
    @DisplayName("Listar produto inexistente")
    void buscarPorIDCase2() {
        // Arranjo
        Optional<Produto> produto = Optional.empty();

        Mockito.when(inventarioRepository.findById(1)).thenReturn(produto);

        // Act
        Optional<Produto> resultado = service.buscarPorId(1);

        // Assert
        assertFalse(resultado.isPresent());
    }


}