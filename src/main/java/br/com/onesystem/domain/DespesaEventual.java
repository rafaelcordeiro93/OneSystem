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
@DiscriminatorValue("DESPESA_EVENTUAL")
public class DespesaEventual extends PerfilDeValor implements RelatorioContaAbertaImpl {

    @ManyToOne
    private TipoDespesa despesa;

    public DespesaEventual() {
    }

    public DespesaEventual(Long id, Pessoa pessoa, TipoDespesa despesa, BigDecimal valor, Date vencimento, Date emissao, String historico,
            Cotacao cotacao, List<Baixa> baixa) throws DadoInvalidoException {
        super(id, valor, vencimento, emissao, pessoa, cotacao, historico, baixa);
        this.despesa = despesa;
        ehValido();
    }

    public final void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("valor", "historico", "cotacao");
        new ValidadorDeCampos<PerfilDeValor>().valida(this, campos);
    }

    public TipoDespesa getDespesa() {
        return despesa;
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
    public String getOrigem() {
        return TipoOperacao.AVULSO.getNome();
    }

}
