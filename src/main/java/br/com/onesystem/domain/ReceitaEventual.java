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
@DiscriminatorValue("RECEITA_EVENTUAL")
public class ReceitaEventual extends CobrancaFixa implements RelatorioContaAbertaImpl {

    @ManyToOne
    private TipoReceita tipoReceita;

    public ReceitaEventual() {
    }

    public ReceitaEventual(Long id, Pessoa pessoa, TipoReceita receita, BigDecimal valor, Date emissao, String historico,
            Cotacao cotacao, OperacaoFinanceira operacaoFinanceira, Date referencia, SituacaoDeCobranca situacaoDeCobranca, Filial filial) throws DadoInvalidoException {
        super(id, emissao, pessoa, cotacao, historico, operacaoFinanceira, valor, emissao, referencia, situacaoDeCobranca, filial);
        this.tipoReceita = receita;
    }

    public TipoReceita getTipoReceita() {
        return tipoReceita;
    }

    @Override
    public Moeda getMoeda() {
        return getCotacao().getConta().getMoeda();
    }

    @Override
    public BigDecimal getSaldo() {
        return getValor();
    }

    @Override
    public BigDecimal getValorBaixado() {
        return BigDecimal.ZERO;
    }

    @Override
    public String getOrigem() {
        return TipoOperacao.AVULSO.getNome();
    }

    @Override
    public String getDetalhes() {
        return getTipoReceita().getNome();
    }

    @Override
    public ModalidadeDeCobranca getModalidade() {
        return ModalidadeDeCobranca.RECEITA_EVENTUAL;
    }

}
