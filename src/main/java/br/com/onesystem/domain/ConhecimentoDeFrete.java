package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.services.impl.RelatorioContaAbertaImpl;
import br.com.onesystem.valueobjects.TipoOperacao;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

@Entity
@SequenceGenerator(initialValue = 1, allocationSize = 1, sequenceName = "SEQ_CONHECIMENTODEFRETE",
        name = "SEQ_CONHECIMENTODEFRETE")
public class ConhecimentoDeFrete implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_CONHECIMENTODEFRETE", strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    private Pessoa pessoa;

    @NotNull(message = "{operacao_not_null}")
    @ManyToOne
    private Operacao operacao;

    @Min(value = 0, message = "{valor_min}")
    @Max(value = 999999999, message = "{valor_max}")
    @Column(nullable = false)
    private BigDecimal valorFrete;

    @Min(value = 0, message = "{valor_min}")
    @Max(value = 999999999, message = "{valor_max}")
    @Column(nullable = false)
    private BigDecimal outrasdespesas;

    @Temporal(TemporalType.TIMESTAMP)
    private Date data;

    @Temporal(TemporalType.TIMESTAMP)
    private Date emissao = Calendar.getInstance().getTime();

    @NotNull(message = "moeda_not_null")
    @ManyToOne(optional = false)
    private Moeda moeda;

    @NotNull(message = "{origem_not_null}")
    @ManyToOne(optional = false)
    private Conta conta;

    @OneToMany(mappedBy = "conhecimentoDeFrete")
    private List<Baixa> baixa;
    
      @OneToMany(mappedBy = "conhecimentoDeFrete")
    private List<Titulo> titulo;

    public ConhecimentoDeFrete() {
    }

    public ConhecimentoDeFrete(Long id, Pessoa pessoa, Operacao operacao, BigDecimal valorFrete, BigDecimal despesas,
            Date data, Date emissao, Moeda moeda, Conta conta) throws DadoInvalidoException {
        this.id = id;
        this.pessoa = pessoa;
        this.operacao = operacao;
        this.valorFrete = valorFrete;
        this.outrasdespesas = despesas;
        this.data = data;
        this.emissao = emissao;
        this.moeda = moeda;
        this.conta = conta;

        ehValido();
    }

    public Long getId() {
        return id;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public Operacao getOperacao() {
        return operacao;
    }

    public BigDecimal getValorFrete() {
        return valorFrete;
    }

    public BigDecimal getOutrasdespesas() {
        return outrasdespesas;
    }

    public Date getData() {
        return data;
    }

    public Date getEmissao() {
        return emissao;
    }

    public List<Baixa> getBaixa() {
        return baixa;
    }

    public Moeda getMoeda() {
        return moeda;
    }

    public Conta getConta() {
        return conta;
    }

    public final void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("valor", "historico", "moeda");
        new ValidadorDeCampos<ConhecimentoDeFrete>().valida(this, campos);
    }

    public String getValorFormatado() {
        NumberFormat numeroFormatado = NumberFormat.getCurrencyInstance();
        return numeroFormatado.format(getValorFrete());
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
        if (!(objeto instanceof ConhecimentoDeFrete)) {
            return false;
        }
        ConhecimentoDeFrete outro = (ConhecimentoDeFrete) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

}
