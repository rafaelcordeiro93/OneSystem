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
@SequenceGenerator(initialValue = 1, allocationSize = 1, sequenceName = "SEQ_DESPESAPROVISIONADA",
        name = "SEQ_DESPESAPROVISIONADA")
public class DespesaProvisionada implements Serializable, RelatorioContaAbertaImpl {

    @Id
    @GeneratedValue(generator = "SEQ_DESPESAPROVISIONADA", strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    private Pessoa pessoa;

    @ManyToOne
    private Despesa despesa;

    @Min(value = 0, message = "{valor_min}")
    @Max(value = 999999999, message = "{valor_max}")
    @Column(nullable = false)
    private BigDecimal valor;

    @Temporal(TemporalType.TIMESTAMP)
    private Date vencimento;

    @Temporal(TemporalType.TIMESTAMP)
    private Date emissao = Calendar.getInstance().getTime();

    @ManyToOne
    private Cambio cambio;

    @OneToMany(mappedBy = "despesaProvisionada")
    private List<Baixa> baixa;

    @Length(max = 250, min = 0, message = "{historico_length}")
    @Column(length = 250, nullable = true)
    private String historico;

    private boolean divisaoLucroCambioCaixa;
    
    @NotNull(message = "moeda_not_null")
    @ManyToOne(optional = false)
    private Moeda moeda;

    public DespesaProvisionada() {
    }

    
    public DespesaProvisionada(Long id, Pessoa pessoa, Despesa despesa, BigDecimal valor, Date vencimento, String historico,
            Cambio cambio, boolean divisaoLucroCambioCaixa, Moeda moeda) throws DadoInvalidoException {
        this.id = id;
        this.pessoa = pessoa;
        this.despesa = despesa;
        this.valor = valor;
        this.vencimento = vencimento;
        this.historico = historico;
        this.cambio = cambio;
        this.divisaoLucroCambioCaixa = divisaoLucroCambioCaixa;
        this.moeda = moeda;
        ehValido();
    }

    public final void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("valor", "historico", "moeda");
        new ValidadorDeCampos<DespesaProvisionada>().valida(this, campos);
    }
    
    public Long getId() {
        return id;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public Despesa getDespesa() {
        return despesa;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Date getVencimento() {
        return vencimento;
    }

    public Date getEmissao() {
        return emissao;
    }

    public String getHistorico() {
        return historico;
    }

    public Cambio getCambio() {
        return cambio;
    }

    public Moeda getMoeda() {
        return moeda;
    }
    
    public BigDecimal getSaldo(){
        return getValor();
    }
    
    public BigDecimal getValorBaixado(){
        return BigDecimal.ZERO;
    }
    
    public String getOrigem() {
        if (cambio != null) {
            return TipoOperacao.CAMBIO.getNome();
        } else {
            return TipoOperacao.AVULSO.getNome();
        }
    }

    public boolean isDivisaoLucroCambioCaixa() {
        return divisaoLucroCambioCaixa;
    }

    public String getValorFormatado() {
        NumberFormat numeroFormatado = NumberFormat.getCurrencyInstance();
        return numeroFormatado.format(getValor());
    }

    public String getEmissaoFormatadaSemHoras() {
        SimpleDateFormat emissaoFormatada = new SimpleDateFormat("dd/MM/yyyy");
        return getEmissao() != null ? emissaoFormatada.format(getEmissao().getTime()) : "";
    }

    public String getVencimentoFormatadoSemHoras() {
        SimpleDateFormat vencimentoFormadado = new SimpleDateFormat("dd/MM/yyyy");
        return getVencimento() != null ? vencimentoFormadado.format(getVencimento().getTime()) : "";
    }

   @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof DespesaProvisionada)) {
            return false;
        }
        DespesaProvisionada outro = (DespesaProvisionada) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

}
