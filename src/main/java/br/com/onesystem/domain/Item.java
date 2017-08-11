package br.com.onesystem.domain;

import br.com.onesystem.valueobjects.CaseType;
import br.com.onesystem.valueobjects.TipoItem;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.CharacterType;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.war.service.ConfiguracaoEstoqueService;
import br.com.onesystem.war.service.EstoqueService;
import br.com.onesystem.war.service.PrecoDeItemService;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
    @Min(value = 0, message = "{estoqueMinimo_min}")
    @Column(nullable = true)
    private BigDecimal estoqueMinimo;
    @Min(value = 0, message = "{estoqueMaximo_min}")
    @Column(nullable = true)
    private BigDecimal estoqueMaximo;
    @OneToMany(mappedBy = "item")
    private List<AjusteDeEstoque> listaDeAjustes;
    @OneToMany(mappedBy = "item")
    private List<ItemDeNota> itensEmitidos;
    @OneToMany(mappedBy = "item")
    private List<ItemOrcado> itensOrcados;
    @OneToMany(mappedBy = "item")
    private List<ItemDeComanda> itensDeComanda;
    @OneToMany(mappedBy = "item")
    private List<PrecoDeItem> precos;
    @ManyToOne
    private Margem margem;
    @ManyToOne
    private Comissao comissao;

    public Item() {
    }

    public Item(Long id) {
        this.id = id;
    }

    public Item(Long id, String barras, String nome, String idFabricante, TipoItem tipoItem,
            String ncm, String idContabil, boolean ativo, GrupoFiscal grupoFiscal, UnidadeMedidaItem unidadeDeMedida,
            Marca marca, Grupo grupo, BigDecimal estoqueMinimo, BigDecimal estoqueMaximo,
            Margem margem, Comissao comissao) throws DadoInvalidoException {
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
        this.margem = margem;
        this.comissao = comissao;
        ehValido();
    }

    public final void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("barras", "idFabricante", "nome", "unidadeDeMedida", "tipoItem",
                "marca", "ncm", "idContabil", "grupo", "ativo", "grupoFiscal", "estoqueMinimo", "estoqueMaximo");
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

    public Margem getMargem() {
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

    public Comissao getComissao() {
        return comissao;
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

    public BigDecimal getPrecoTotal() {
        if (getSaldo().compareTo(BigDecimal.ZERO) == 0) {
            return getPreco();
        } else {
            return getSaldo().multiply(getPreco()).setScale(2, RoundingMode.HALF_UP);
        }
    }

    public BigDecimal getPreco() {
        ConfiguracaoEstoque conf = new ConfiguracaoEstoqueService().buscar();
        if (conf != null && conf.getListaDePreco() != null) {
            PrecoDeItem preco = new PrecoDeItemService().buscaListaDePrecoAtual(this, conf.getListaDePreco(), new Date());
            if (preco == null) {
                return BigDecimal.ZERO;
            } else {
                return preco.getValor();
            }
        } else {
            return BigDecimal.ZERO;
        }
    }

    public BigDecimal getCustoTotal() {
        if (getSaldo().compareTo(BigDecimal.ZERO) == 0) {
            return getPreco();
        } else {
            return getSaldo().multiply(getUltimoCusto()).setScale(2, RoundingMode.HALF_UP);
        }
    }

    public BigDecimal getSaldo() {
        return new EstoqueService().buscaSaldoTotalDeEstoque(this, null);
    }

    public BigDecimal getSaldo(Date data) {
        return new EstoqueService().buscaSaldoTotalDeEstoque(this, data);
    }

    public BigDecimal getUltimoCusto() {
        return new EstoqueService().buscaUltimoCustoItem(this, new Date());
    }

    public BigDecimal getCustoMedio() {
        return new EstoqueService().buscaCustoMedioDeItem(this, new Date());
    }

    @Override
    public String toString() {
        return "Item{" + "id=" + id + ", barras=" + barras + ", nome=" + nome + ", idFabricante=" + idFabricante + ", tipoItem=" + tipoItem + ", ncm=" + ncm + ", idContabil=" + idContabil + ", ativo=" + ativo + ", grupoFiscal=" + grupoFiscal + ", unidadeDeMedida=" + unidadeDeMedida + ", marca=" + marca + ", grupo=" + grupo + ", estoqueMinimo=" + estoqueMinimo + ", estoqueMaximo=" + estoqueMaximo + ", precos=" + precos + '}';
    }

}
