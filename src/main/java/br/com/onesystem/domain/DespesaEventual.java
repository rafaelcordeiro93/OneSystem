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
@DiscriminatorValue("DESPESA_EVENTUAL")
public class DespesaEventual extends CobrancaFixa implements RelatorioContaAbertaImpl {

    @ManyToOne
    private TipoDespesa tipoDespesa;

    public DespesaEventual() {
    }

    public DespesaEventual(Long id, Pessoa pessoa, TipoDespesa tipoDespesa, BigDecimal valor, Date emissao, String historico,
            Cotacao cotacao, OperacaoFinanceira operacaoFinanceira, Date referencia, SituacaoDeCobranca situacaoDeCobranca, Filial filial) throws DadoInvalidoException {
        super(id, emissao, pessoa, cotacao, historico, operacaoFinanceira, valor, emissao, referencia, situacaoDeCobranca, filial);
        this.tipoDespesa = tipoDespesa;
    }

    public TipoDespesa getTipoDespesa() {
        return tipoDespesa;
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

    @Override
    public String getDetalhes() {
        return getTipoDespesa().getNome();
    }

    @Override
    public String getOrigem() {
        return TipoOperacao.AVULSO.getNome();
    }

    @Override
    public ModalidadeDeCobranca getModalidade() {
        return ModalidadeDeCobranca.DESPESA_EVENTUAL;
    }
}
