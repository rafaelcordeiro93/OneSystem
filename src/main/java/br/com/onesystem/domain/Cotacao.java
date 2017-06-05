package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "SEQ_COTACAO",
        sequenceName = "SEQ_COTACAO")
public class Cotacao implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_COTACAO", strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull(message = "{valor_not_null}")
    private BigDecimal valor;
    @NotNull(message = "{data_not_null}")
    @Temporal(TemporalType.TIMESTAMP)
    private Date emissao;
    @NotNull(message = "{conta_not_null}")
    @ManyToOne(optional = false)
    private Conta conta;
    @OneToMany(mappedBy = "cotacao")
    private List<Cobranca> parcelas;
    @OneToMany(mappedBy = "cotacao")
    private List<CobrancaFixa> movimentoFixos;
    @OneToMany(mappedBy = "cotacao")
    private List<Recepcao> recepcoes;
    @OneToMany(mappedBy = "cotacao")
    private List<ConhecimentoDeFrete> conhecimentoDeFretes;
    @OneToMany(mappedBy = "cotacao")
    private List<Orcamento> orcamentos;
    @OneToMany(mappedBy = "cotacao")
    private List<ValorPorCotacao> valores;
    @OneToMany(mappedBy = "cotacaoPadrao")
    private List<Recebimento> recebimentos;
    @OneToMany(mappedBy = "cotacao")
    private List<TipoDeCobranca> tiposDeCobranca;
    @OneToMany(mappedBy = "cotacao")
    private List<FormaDeCobranca> formasDeCobranca;

    public Cotacao() {
    }

    public Cotacao(Long id, BigDecimal valor, Date data, Conta conta) throws DadoInvalidoException {
        this.id = id;
        this.valor = valor;
        this.emissao = data;
        this.conta = conta;
        ehValido();
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public String getValorFormatado() {
        return NumberFormat.getCurrencyInstance(conta.getMoeda().getBandeira().getLocal()).format(valor);
    }

    public Conta getConta() {
        return conta;
    }

    public Date getEmissao() {
        return emissao;
    }

    private void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("valor", "conta");
        new ValidadorDeCampos<Cotacao>().valida(this, campos);
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof Cotacao)) {
            return false;
        }
        Cotacao outro = (Cotacao) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

    @Override
    public String toString() {
        return "Cotacao{" + "id=" + id + ", valor=" + valor + ", emissao=" + emissao + ", conta=" + conta.getMoedaNomeESiglaMoeda() + '}';
    }

}
