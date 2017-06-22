/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.util.MoedaFormatter;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.valueobjects.ModalidadeDeCobranca;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author Rafael Fernando Rauber
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "Cobranca_CLASSE_NOME")
public abstract class Cobranca implements Serializable {

    @Id
    @SequenceGenerator(name = "SEQ_COBRANCA", sequenceName = "SEQ_COBRANCA",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "SEQ_COBRANCA", strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull(message = "{valor_not_null}")
    @Min(value = 0, message = "{valor_min}")
    @Column(nullable = false)
    protected BigDecimal valor;

    @NotNull(message = "{emissao_not_null}")
    @Temporal(TemporalType.TIMESTAMP)
    private Date emissao;

    @NotNull(message = "{pessoa_not_null}")
    @ManyToOne(optional = true)
    private Pessoa pessoa;

    @NotNull(message = "{cotacao_not_null}")
    @ManyToOne(optional = false)
    private Cotacao cotacao;

    @Length(max = 250, min = 0, message = "{historico_length}")
    @Column(length = 250, nullable = true)
    private String historico;

    @OneToMany(mappedBy = "parcela", cascade = {CascadeType.ALL})
    private List<Baixa> baixas;

    @NotNull(message = "{unidadeFinanceira_not_null}")
    @Enumerated(EnumType.STRING)
    private OperacaoFinanceira operacaoFinanceira;

    @NotNull(message = "{vencimento_not_null}")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = true)
    private Date vencimento;

    @ManyToOne
    private Nota nota;

    @OneToMany(mappedBy = "cobranca")
    private List<TipoDeCobranca> tiposDeCobranca;

    @OneToMany(mappedBy = "cobranca")
    private List<FormaDeCobranca> formasDeCobranca;

    private Boolean entrada;

    public Cobranca() {
    }

    public Cobranca(Long id, Date emissao, Pessoa pessoa, Cotacao cotacao, String historico,
            List<Baixa> baixas, OperacaoFinanceira operacaoFinanceira, BigDecimal valor, Date vencimento, Nota nota, Boolean entrada) throws DadoInvalidoException {
        this.id = id;
        this.valor = valor;
        this.emissao = emissao == null ? new Date() : emissao;
        this.pessoa = pessoa;
        this.cotacao = cotacao;
        this.historico = historico;
        this.operacaoFinanceira = operacaoFinanceira;
        this.baixas = baixas;
        this.vencimento = vencimento;
        this.entrada = entrada;
        this.nota = nota;
        ehAbstracaoValida();
    }

    private final void ehAbstracaoValida() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("valor", "emissao", "historico", "cotacao", "operacaoFinanceira", "pessoa");
        if (!(this instanceof Credito)) {
            campos = Arrays.asList("valor", "emissao", "historico", "cotacao", "operacaoFinanceira", "vencimento");
        }
        new ValidadorDeCampos<Cobranca>().valida(this, campos);
    }

    public abstract ModalidadeDeCobranca getModalidade();

    public abstract String getDetalhes();

    public void adiciona(Baixa baixa) {
        if (baixas == null) {
            baixas = new ArrayList<>();
        }
        this.baixas.add(baixa);
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Date getEmissao() {
        return emissao;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public Cotacao getCotacao() {
        return cotacao;
    }

    public String getHistorico() {
        return historico;
    }

    public Date getVencimento() {
        return vencimento;
    }

    public Nota getNota() {
        return nota;
    }

    public void geraPara(Nota nota) {
        this.nota = nota;
        this.emissao = nota.getEmissao();
    }

    public Boolean getEntrada() {
        return entrada;
    }

    public Long getDias() {
        if (vencimento != null) {
            LocalDate v = vencimento.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate e = emissao.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            return ChronoUnit.DAYS.between(e, v);
        }
        return null;
    }

    public DayOfWeek getDiaDaSemana() {
        if (vencimento != null) {
            LocalDate v = vencimento.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            return v.getDayOfWeek();
        }
        return null;
    }

    public String getVencimentoFormatado() {
        if (vencimento != null) {
            SimpleDateFormat emissaoFormatada = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            return getVencimento() != null ? emissaoFormatada.format(getVencimento().getTime()) : "";
        }
        return "";
    }

    public String getVencimentoFormatadoSemHoras() {
        if (vencimento != null) {
            SimpleDateFormat emissaoFormatada = new SimpleDateFormat("dd/MM/yyyy");
            return getVencimento() != null ? emissaoFormatada.format(getVencimento().getTime()) : "";
        }
        return "";
    }

    public List<Baixa> getBaixas() {
        return baixas;
    }

    public boolean getPossuiPagamento() {
        return baixas == null ? false : baixas.isEmpty() ? false : true;
    }

    public OperacaoFinanceira getOperacaoFinanceira() {
        return operacaoFinanceira;
    }

    public String getValorFormatado() {
        return MoedaFormatter.format(cotacao.getConta().getMoeda(), valor);
    }

    public String getEmissaoFormatada() {
        SimpleDateFormat emissaoFormatada = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return getEmissao() != null ? emissaoFormatada.format(getEmissao().getTime()) : "";
    }

    public String getEmissaoFormatadaSemHoras() {
        SimpleDateFormat emissaoFormatada = new SimpleDateFormat("dd/MM/yyyy");
        return getEmissao() != null ? emissaoFormatada.format(getEmissao().getTime()) : "";
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof Cobranca)) {
            return false;
        }
        Cobranca outro = (Cobranca) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

    @Override
    public String toString() {
        return "Cobranca{" + "id=" + id + ", valor=" + valor + ", emissao=" + emissao + ", pessoa=" + pessoa + ", cotacao=" + cotacao + ", historico=" + historico + ", baixas=" + baixas + ", operacaoFinanceira=" + operacaoFinanceira + ", vencimento=" + vencimento + ", nota=" + nota + ", entrada=" + entrada + '}';
    }

}
