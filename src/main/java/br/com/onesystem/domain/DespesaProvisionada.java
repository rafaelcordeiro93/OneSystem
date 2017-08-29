package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.impl.RelatorioContaAbertaImpl;
import br.com.onesystem.valueobjects.ModalidadeDeCobranca;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.valueobjects.SituacaoDeCobranca;
import br.com.onesystem.valueobjects.TipoOperacao;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("DESPESA_PROVISIONADA")
public class DespesaProvisionada extends CobrancaFixa implements RelatorioContaAbertaImpl {

    @ManyToOne
    private TipoDespesa tipoDespesa;

    @ManyToOne
    private Cambio cambio;

    private boolean divisaoLucroCambioCaixa;

    public DespesaProvisionada() {
    }

    public DespesaProvisionada(Long id, Pessoa pessoa, TipoDespesa tipoDespesa, BigDecimal valor, Date vencimento, Date emissao, String historico,
            Cambio cambio, boolean divisaoLucroCambioCaixa, Cotacao cotacao, List<Baixa> baixa, OperacaoFinanceira operacaoFinanceira,
            Date referencia, SituacaoDeCobranca situacaoDeCobranca, Filial filial) throws DadoInvalidoException {
        super(id, emissao, pessoa, cotacao, historico, baixa, operacaoFinanceira, valor, vencimento, referencia, situacaoDeCobranca, filial);
        this.tipoDespesa = tipoDespesa;
        this.cambio = cambio;
        this.divisaoLucroCambioCaixa = divisaoLucroCambioCaixa;
    }

    public TipoDespesa getTipoDespesa() {
        return tipoDespesa;
    }

    public Cambio getCambio() {
        return cambio;
    }
    
    @Override
    public ModalidadeDeCobranca getModalidade(){
        return ModalidadeDeCobranca.DESPESA_PROVISIONADA;
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

    @Override
    public String getDetalhes() {
        return getTipoDespesa().getNome();
    }

}
