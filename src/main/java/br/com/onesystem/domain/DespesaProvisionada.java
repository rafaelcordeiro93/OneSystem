package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.services.impl.RelatorioContaAbertaImpl;
import br.com.onesystem.valueobjects.TipoOperacao;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("DESPESA_PROVISIONADA")
public class DespesaProvisionada extends PerfilDeValor implements RelatorioContaAbertaImpl {

    @ManyToOne
    private TipoDespesa despesa;

    @ManyToOne
    private Cambio cambio;

    private boolean divisaoLucroCambioCaixa;

    public DespesaProvisionada() {
    }

    public DespesaProvisionada(Long id, Pessoa pessoa, TipoDespesa despesa, BigDecimal valor, Date vencimento, Date emissao, String historico,
            Cambio cambio, boolean divisaoLucroCambioCaixa, Cotacao cotacao, List<Baixa> baixa) throws DadoInvalidoException {
        super(id, valor, vencimento, emissao, pessoa, cotacao, historico, baixa);
        this.despesa = despesa;
        this.cambio = cambio;
        this.divisaoLucroCambioCaixa = divisaoLucroCambioCaixa;
        ehValido();
    }

    public final void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("valor", "historico", "cotacao");
        new ValidadorDeCampos<PerfilDeValor>().valida(this, campos);
    }

    public TipoDespesa getDespesa() {
        return despesa;
    }

    public Cambio getCambio() {
        return cambio;
    }

    public Moeda getMoeda() {
        return getCotacao().getConta().getMoeda();
    }

    public BigDecimal getSaldo() {
        return getValor();
    }

    public BigDecimal getValorBaixado() {
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

}
