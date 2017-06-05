package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.impl.RelatorioContaAbertaImpl;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@DiscriminatorValue("RECEITA_PROVISIONADA")
public class ReceitaProvisionada extends CobrancaFixa implements Serializable, RelatorioContaAbertaImpl {

    @ManyToOne
    private TipoReceita tipoReceita;

    public ReceitaProvisionada() {
    }

    public ReceitaProvisionada(Long id, Pessoa pessoa, TipoReceita tipoReceita, BigDecimal valor, OperacaoFinanceira operacaoFinanceira,
            Date vencimento, Date emissao, String historico, Cotacao cotacao, List<Baixa> baixas, Integer mesReferencia, Integer anoReferencia) throws DadoInvalidoException {
        super(id, emissao, pessoa, cotacao, historico, baixas, operacaoFinanceira, valor, vencimento, mesReferencia, anoReferencia);
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
