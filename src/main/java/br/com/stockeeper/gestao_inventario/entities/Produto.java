package br.com.stockeeper.gestao_inventario.entities;

import br.com.stockeeper.gestao_inventario.enums.Categoria;
import br.com.stockeeper.gestao_inventario.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Builder;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_produto")
    private Integer id;

    @Column(name = "nome_produto", nullable = false, length = 50)
    @NotNull(message = "{name.not.blank}")
    @NotBlank(message = "{name.not.blank}")
    @Size(max = 50, min = 3, message = "{max.character}")
    private String nome;

    @Column(name = "qtd_estoque", nullable = false)
    @PositiveOrZero(message = "{positivoorzero}")
    @NotNull(message = "{amount.not.blank}")
    private Integer qtd;

    @Column(length = 50)
    @Size(max = 50, min = 3, message = "{max.character}")
    private String material;

    @Column(name = "preco_produto", nullable = false)
    @PositiveOrZero(message = "{positivoorzero}")
    @NotNull(message = "{preco.not.blank}")
    private Double preco;

    @Column(length = 200)
    @Size(max = 200, min = 3, message = "{max.character}")
    private String descricao;

    @Column(length = 50)
    @Size(max = 50, min = 3, message = "{max.character}")
    private String fornecedor;

    @Column(length = 50)
    @Size(max = 50, min = 3, message = "{max.character}")
    private String marca;

    @Column()
    @PositiveOrZero(message = "{positivoorzero}")
    private Double peso;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{categoria.not.null}")
    private Categoria categoria;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCriacao;

    @OneToMany(mappedBy = "produto")
    @NotNull(message = "{lista.imagem.not.empty}")
    @NotEmpty(message = "{lista.imagem.not.empty}")
    private List<Imagem> imagens = new ArrayList<>();

    public Produto(String nome, Integer qtd, String material, Double preco, String descricao, String fornecedor, String marca, Double peso, Categoria categoria, List<Imagem> imagems) {
        this.nome = nome;
        this.qtd = qtd;
        this.material = material;
        this.preco = preco;
        this.descricao = descricao;
        this.fornecedor = fornecedor;
        this.marca = marca;
        this.peso = peso;
        this.status = Status.ATIVO;
        this.categoria = categoria;
        this.dateCriacao = new Date();
        this.imagens = imagems;
    }

    public Produto() {
        this.dateCriacao = new Date();
        this.status = Status.ATIVO;
    }

    public Integer getId() {
        return id;
    }

    public Date getDateCriacao() {
        return dateCriacao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getQtd() {
        return qtd;
    }

    public void setQtd(Integer qtd) {
        this.qtd = qtd;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public List<Imagem> getImagens() {
        return imagens;
    }
}
