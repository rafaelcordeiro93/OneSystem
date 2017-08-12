package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.impl.RelatorioContaAbertaImpl;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.valueobjects.SituacaoDeCobranca;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("RECEITA_PROVISIONADA")
public class ReceitaProvisionada extends CobrancaFixa implements Serializable, RelatorioContaAbertaImpl {

    @ManyToOne
    private TipoReceita tipoReceita;

    public ReceitaProvisionada() {
    }

    public ReceitaProvisionada(Long id, Pessoa pessoa, TipoReceita tipoReceita, BigDecimal valor, OperacaoFinanceira operacaoFinanceira,
            Date vencimento, Date emissao, String historico, Cotacao cotacao, List<Baixa> baixas, Date referencia, SituacaoDeCobranca situacaoDeCobranca) throws DadoInvalidoException {
        super(id, emissao, pessoa, cotacao, historico, baixas, operacaoFinanceira, valor, vencimento, referencia, situacaoDeCobranca);
        this.tipoReceita = tipoReceita;
    }

    public TipoReceita getTipoReceita() {
        return tipoReceita;
    }

    @Override
    public BigDecimal getSaldo() {
        return getValor();
    }

    @Override
    public Moeda getMoeda() {
        return getCotacao().getConta().getMoeda();
    }

    @Override
    public BigDecimal getValorBaixado() {
        return BigDecimal.ZERO;
    }

    @Override
    public String getOrigem() {
        return tipoReceita.getNome();
    }

    @Override
    public String getDetalhes() {
        return getTipoReceita().getNome();
    }

}
