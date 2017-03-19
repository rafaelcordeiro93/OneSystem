/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.valueobjects.TipoPeriodicidade;
import br.com.onesystem.valueobjects.TipoFormaDeRecebimento;
import br.com.onesystem.valueobjects.TipoFormaDeRecebimentoParcela;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author Rafael Fernando Rauber
 */
@Entity
@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "SEQ_FORMADERECEBIMENTO",
        sequenceName = "SEQ_FORMADERECEBIMENTO")
public class FormaDeRecebimento implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_FORMADERECEBIMENTO", strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull(message = "{nome_not_null}")
    @Length(max = 60, message = "{nome_length}")
    @Column(length = 60, nullable = false)
    private String nome;
    @NotNull(message = "{ativo_not_null}")
    @Column(nullable = false)
    private boolean ativo = true;
    @NotNull(message = "{entrada_not_null}")
    @Column(nullable = false)
    private boolean entrada = false;
    @Max(value = 100)
    private BigDecimal porcentagemDeEntrada;
    @Enumerated(EnumType.STRING)
    private TipoFormaDeRecebimento formaPadraoDeEntrada;
    @ManyToOne
    private Cartao cartao;
    @NotNull(message = "{entrada_cartao_not_null}")
    @Column(nullable = false)
    private boolean entradaEmCartao = false;
    @NotNull(message = "{entrada_dinheiro_not_null}")
    @Column(nullable = false)
    private boolean entradaEmDinheiro = false;
    @NotNull(message = "{entrada_cheque_not_null}")
    @Column(nullable = false)
    private boolean entradaEmCheque = false;
    @NotNull(message = "{entrada_credito_not_null}")
    @Column(nullable = false)
    private boolean entradaEmCredito = false;
    @NotNull(message = "{parcela_cheque_not_null}")
    @Column(nullable = false)
    private boolean parcelaEmCheque = false;
    @NotNull(message = "{parcela_cartao_not_null}")
    @Column(nullable = false)
    private boolean parcelaEmCartao = false;
    @NotNull(message = "{parcela_conta_not_null}")
    @Column(nullable = false)
    private boolean parcelaEmConta = false;
    @Min(value = 0, message = "{minimo_parcela_min}")
    @Column(nullable = true)
    private Integer minimoDeParcelas;
    @Min(value = 0, message = "{maximo_parcela_min}")
    @Column(nullable = true)
    private Integer maximoDeParcelas;
    @Min(value = 0, message = "{peridiocidade_min}")
    @Column(nullable = true)
    private Integer periodicidade;
    @NotNull(message = "{tipo_peridiocidade_not_null}")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoPeriodicidade tipoPeriodicidade;
    @Min(value = 0, message = "{dias_primeira_parcela_min}")
    @Column(nullable = true)
    private Integer diasPrimeiraParcela;
    @Enumerated(EnumType.STRING)
    private TipoFormaDeRecebimentoParcela formaPadraoDeParcela;
    @OneToMany(mappedBy = "formaDeRecebimento")
    private List<NotaEmitida> notasEmitidas;

    public FormaDeRecebimento() {
    }

    public FormaDeRecebimento(Long id, String nome, boolean ativo, boolean entrada, BigDecimal porcentagemDeEntrada,
            TipoFormaDeRecebimento formaPadraoDeEntrada, boolean entradaEmCartao, boolean entradaEmDinheiro, boolean entradaEmCheque,
            boolean entradaEmCredito, boolean parcelaEmCheque, boolean parcelaEmCartao, boolean parcelaEmConta, Integer minimoDeParcelas,
            Integer maximoDeParcelas, Integer periodicidade, TipoPeriodicidade tipoPeriodicidade, Integer diasPrimeiraParcela,
            TipoFormaDeRecebimentoParcela formaPadraoDeParcela, Cartao cartao) throws DadoInvalidoException {
        this.id = id;
        this.nome = nome;
        this.ativo = ativo;
        this.entrada = entrada;
        this.porcentagemDeEntrada = porcentagemDeEntrada;
        this.formaPadraoDeEntrada = formaPadraoDeEntrada;
        this.entradaEmCartao = entradaEmCartao;
        this.entradaEmDinheiro = entradaEmDinheiro;
        this.entradaEmCheque = entradaEmCheque;
        this.entradaEmCredito = entradaEmCredito;
        this.parcelaEmCheque = parcelaEmCheque;
        this.parcelaEmCartao = parcelaEmCartao;
        this.parcelaEmConta = parcelaEmConta;
        this.minimoDeParcelas = minimoDeParcelas;
        this.maximoDeParcelas = maximoDeParcelas;
        this.periodicidade = periodicidade;
        this.tipoPeriodicidade = tipoPeriodicidade;
        this.diasPrimeiraParcela = diasPrimeiraParcela;
        this.formaPadraoDeParcela = formaPadraoDeParcela;
        this.cartao = cartao;
        ehValido();
    }

    public Long getId() {
        return id;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public String getNome() {
        return nome;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public boolean isEntrada() {
        return entrada;
    }

    public BigDecimal getPorcentagemDeEntrada() {
        return porcentagemDeEntrada;
    }

    public TipoFormaDeRecebimento getFormaPadraoDeEntrada() {
        return formaPadraoDeEntrada;
    }

    public boolean isEntradaEmCartao() {
        return entradaEmCartao;
    }

    public boolean isEntradaEmDinheiro() {
        return entradaEmDinheiro;
    }

    public boolean isEntradaEmCheque() {
        return entradaEmCheque;
    }

    public boolean isEntradaEmCredito() {
        return entradaEmCredito;
    }

    public boolean isParcelaEmCheque() {
        return parcelaEmCheque;
    }

    public boolean isParcelaEmCartao() {
        return parcelaEmCartao;
    }

    public boolean isParcelaEmConta() {
        return parcelaEmConta;
    }

    public Integer getMinimoDeParcelas() {
        return minimoDeParcelas;
    }

    public Integer getMaximoDeParcelas() {
        return maximoDeParcelas;
    }

    public Integer getPeriodicidade() {
        return periodicidade;
    }

    public List<NotaEmitida> getNotasEmitidas() {
        return notasEmitidas;
    }

    public TipoPeriodicidade getTipoPeriodicidade() {
        return tipoPeriodicidade;
    }

    public Integer getDiasPrimeiraParcela() {
        return diasPrimeiraParcela;
    }

    public TipoFormaDeRecebimentoParcela getFormaPadraoDeParcela() {
        return formaPadraoDeParcela;
    }

    private void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("nome", "ativo", "entrada", "porcentagemDeEntrada", "formaPadraoDeEntrada", "entradaEmCartao", "entradaEmDinheiro",
                "entradaEmCheque", "entradaEmCredito", "parcelaEmCheque", "parcelaEmCartao", "parcelaEmConta", "minimoDeParcelas", "maximoDeParcelas",
                "periodicidade", "tipoPeriodicidade", "diasPrimeiraParcela");
        new ValidadorDeCampos<FormaDeRecebimento>().valida(this, campos);
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof Conta)) {
            return false;
        }
        FormaDeRecebimento outro = (FormaDeRecebimento) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

    @Override
    public String toString() {
        return "FormaDeRecebimento{" + "id=" + id + ", nome=" + nome + ", ativo=" + ativo + ", entrada="
                + entrada + ", porcentagemDeEntrada=" + porcentagemDeEntrada + ", formaPadraoDeEntrada="
                + formaPadraoDeEntrada + ", entradaEmCartao=" + entradaEmCartao + ", entradaEmDinheiro="
                + entradaEmDinheiro + ", entradaEmCheque=" + entradaEmCheque + ", entradaEmCredito="
                + entradaEmCredito + ", parcelaEmCheque=" + parcelaEmCheque + ", parcelaEmCartao="
                + parcelaEmCartao + ", parcelaEmConta=" + parcelaEmConta + ", minimoDeParcelas="
                + minimoDeParcelas + ", maximoDeParcelas=" + maximoDeParcelas + ", periodicidade="
                + periodicidade + ", tipoPeriodicidade=" + tipoPeriodicidade + ", diasPrimeiraParcela="
                + diasPrimeiraParcela + ", formaPadraoDeParcela=" + formaPadraoDeParcela + '}';
    }

}
