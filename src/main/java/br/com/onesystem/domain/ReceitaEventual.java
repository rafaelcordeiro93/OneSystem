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
@DiscriminatorValue("RECEITA_EVENTUAL")
public class ReceitaEventual extends Transacao implements RelatorioContaAbertaImpl {

    @ManyToOne
    private Receita receita;

    public ReceitaEventual() {
    }

    public ReceitaEventual(Long id, Pessoa pessoa, Receita receita, BigDecimal valor, Date vencimento, Date emissao, String historico,
            Cotacao cotacao, List<Baixa> baixa) throws DadoInvalidoException {
        super(id, valor, vencimento, emissao, pessoa, cotacao, historico, baixa);
        this.receita = receita;
        ehValido();
    }

    public final void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("valor", "historico", "cotacao");
        new ValidadorDeCampos<Transacao>().valida(this, campos);
    }

    public Receita getReceita() {
        return receita;
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

}
