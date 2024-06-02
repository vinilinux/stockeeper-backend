package br.com.stockeeper.gestao_inventario.entities;

import br.com.stockeeper.gestao_inventario.enums.Categoria;
import br.com.stockeeper.gestao_inventario.enums.Status;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoTest {

    @Test
    @DisplayName("Deveria instancia corretamente um objeto")
    public void testConstrutorCase1() {

        String nomeEsperado = "Corrente de prata";
        Integer qtd = 20;
        String materialEsperado = "prata";
        Double preco = 30.0;
        String descricao = "teste";
        String fornecedor = "marcos";
        String marca = "vivara";
        Double peso = 2.0;
        Categoria categoria = Categoria.CORRENTE;

        List<Imagem> imagens = new ArrayList<>();

        Imagem imagem1 = new Imagem();
        imagem1.setUrlImagem("url1");

        Imagem imagem2 = new Imagem();
        imagem2.setUrlImagem("url2");

        imagens.add(imagem1);
        imagens.add(imagem2);

        Produto produto = new Produto(
                nomeEsperado,
                qtd,
                materialEsperado,
                preco,
                descricao,
                fornecedor,
                marca,
                peso,
                categoria,
                imagens
        );

        assertNotNull(produto);
        assertEquals(2, produto.getImagens().size());
        assertEquals("url1", produto.getImagens().get(0).getUrlImagem());
        assertEquals("url2", produto.getImagens().get(1).getUrlImagem());
        assertEquals(Status.ATIVO, produto.getStatus());
        assertEquals(Categoria.CORRENTE, produto.getCategoria());

    }

    @Test
    @DisplayName("Deve gerar uma falha ao instanciar o objeto ")
    public void testConstrutorCase2() {

        // Arrange
        String nomeEsperado = "Co";
        Integer qtd = -1;
        String materialEsperado = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua";
        Double preco = null;
        String descricao = "t";
        String fornecedor = null;
        String marca = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua";
        Double peso = 2.0;
        Categoria categoria = null;

        List<Imagem> imagens = null;

        Imagem imagem1 = new Imagem();
        imagem1.setUrlImagem("url1");

        Imagem imagem2 = new Imagem();
        imagem2.setUrlImagem("url2");

        Produto produto = new Produto(
                nomeEsperado,
                qtd,
                materialEsperado,
                preco,
                descricao,
                fornecedor,
                marca,
                peso,
                categoria,
                imagens
        );

        // Act
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Produto>> violations = validator.validate(produto);


        //Assert
        assertTrue(!violations.isEmpty());
        violations.forEach(
                violation -> {
                    System.out.println(violation.getPropertyPath() + ": " + violation.getMessage());
                }
        );
    }
}