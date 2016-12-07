package br.com.onesystem.domain;

import br.com.onesystem.valueobjects.CaseType;
import br.com.onesystem.valueobjects.TipoItem;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.CharacterType;
import br.com.onesystem.services.ValidadorDeCampos;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

@Entity
@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "SEQ_ITEM",
        sequenceName = "SEQ_ITEM")
public class Item implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_ITEM", strategy = GenerationType.SEQUENCE)
    private Long id;
    @Length(max = 200, message = "{barras_length}")
    @Column(length = 200, nullable = true)
    private String barras;
    @NotNull(message = "{nome_not_null}")
    @Length(min = 4, max = 120, message = "{nome_lenght}")
    @Column(nullable = false, length = 120)
    private String nome;
    @Length(max = 50, message = "{idFabricante_length}")
    @Column(nullable = true, length = 50)
    private String idFabricante;
    @NotNull(message = "{tipoMercaderia_not_null}")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoItem tipoItem;
    @CharacterType(value = CaseType.DIGIT, message = "{ncm_type_digit}")
    @Length(max = 8, min = 8, message = "{ncm_length}")
    @Column(nullable = true, length = 8)
    private String ncm;
    @Length(max = 30, message = "{idContable_length}")
    @Column(nullable = true)
    private String idContabil;
    @NotNull(message = "{ativo_not_null}")
    @Column(nullable = false)
    private boolean ativo;
    @NotNull(message = "{grupo_fiscal_not_null}")
    @ManyToOne(optional = false)
    private GrupoFiscal grupoFiscal;
    @NotNull(message = "{unidade_medida_not_null}")
    @ManyToOne(optional = false)
    private UnidadeMedidaItem unidadeDeMedida;
    @ManyToOne(optional = true)
    private Marca marca;
    @ManyToOne(optional = true)
    private Grupo grupo;
    @NotNull(message = "{estoqueMinimo_not_null}")
    @Min(value = 0, message = "{estoqueMinimo_min}")
    @Column(nullable = false)
    private BigDecimal estoqueMinimo;
    @NotNull(message = "{estoqueMaximo_not_null}")
    @Min(value = 0, message = "{estoqueMaximo_min}")
    @Column(nullable = false)
    private BigDecimal estoqueMaximo;
    @NotNull(message = "{saldo_not_null}")
    @Column(nullable = false)
    private BigDecimal saldo;
    @OneToMany(mappedBy = "item")
    private List<AjusteDeEstoque> listaDeAjustes;
    @OneToMany(mappedBy = "item")
    private List<ItemEmitido> itensEmitidos;
    @OneToMany(mappedBy = "item")
    private List<PrecoDeItem> precos;
    @ManyToOne
    private GrupoDeMargem margem;

    public Item() {
    }

    public Item(Long id, String barras, String nome, String idFabricante, TipoItem tipoItem,
            String ncm, String idContabil, boolean ativo, GrupoFiscal grupoFiscal, UnidadeMedidaItem unidadeDeMedida,
            Marca marca, Grupo grupo, BigDecimal estoqueMinimo, BigDecimal estoqueMaximo, BigDecimal saldo,
            GrupoDeMargem margem) throws DadoInvalidoException {
        this.id = id;
        this.barras = barras;
        this.nome = nome;
        this.idFabricante = idFabricante;
        this.tipoItem = tipoItem;
        this.ncm = ncm;
        this.idContabil = idContabil;
        this.ativo = ativo;
        this.grupoFiscal = grupoFiscal;
        this.unidadeDeMedida = unidadeDeMedida;
        this.marca = marca;
        this.grupo = grupo;
        this.estoqueMinimo = estoqueMinimo;
        this.estoqueMaximo = estoqueMaximo;
        this.saldo = saldo;
        this.margem = margem;
        ehValido();
    }

    public final void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("barras", "idFabricante", "nome", "unidadeDeMedida", "tipoItem",
                "marca", "ncm", "idContabil", "grupo", "ativo", "grupoFiscal", "estoqueMinimo", "estoqueMaximo", "saldo");
        new ValidadorDeCampos<Item>().valida(this, campos);
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof Item)) {
            return false;
        }
        Item outro = (Item) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

    public Long getId() {
        return id;
    }

    public GrupoDeMargem getMargem() {
        return margem;
    }

    public String getBarras() {
        return barras;
    }

    public String getNome() {
        return nome;
    }

    public String getIdFabricante() {
        return idFabricante;
    }

    public TipoItem getTipoItem() {
        return tipoItem;
    }

    public String getNcm() {
        return ncm;
    }

    public String getIdContabil() {
        return idContabil;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public GrupoFiscal getGrupoFiscal() {
        return grupoFiscal;
    }

    public UnidadeMedidaItem getUnidadeDeMedida() {
        return unidadeDeMedida;
    }

    public Marca getMarca() {
        return marca;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public BigDecimal getEstoqueMinimo() {
        return estoqueMinimo;
    }

    public BigDecimal getEstoqueMaximo() {
        return estoqueMaximo;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    @Override
    public String toString() {
        return "Item{" + "id=" + id + ", barras=" + barras + ", nome=" + nome + ", idFabricante=" + idFabricante + ", tipoItem=" + tipoItem + ", ncm=" + ncm + ", idContabil=" + idContabil + ", ativo=" + ativo + ", grupoFiscal=" + grupoFiscal + ", unidadeDeMedida=" + unidadeDeMedida + ", marca=" + marca + ", grupo=" + grupo + ", estoqueMinimo=" + estoqueMinimo + ", estoqueMaximo=" + estoqueMaximo + ", saldo=" + saldo + ", precos=" + precos + '}';
    }

}
