package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.Cambio;
import br.com.onesystem.domain.Despesa;
import br.com.onesystem.domain.DespesaProvisionada;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.Recepcao;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.valueobjects.TipoFormaPagRec;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Rafael
 */
public class DespesaProvisionadaBuilder {

    private Long id;
    private Pessoa pessoa;
    private String historico;
    private BigDecimal valor;
    private BigDecimal saldo;
    private Date vencimento;
    private Date emissao = Calendar.getInstance().getTime();
    private Date ultimoPagamento = Calendar.getInstance().getTime();
    private OperacaoFinanceira operacaoFinanceira;
    private Recepcao recepcao;
    private Cambio cambio;
    private NotaEmitida notaEmitida;
    private List<Baixa> baixas = new ArrayList<Baixa>();
    private TipoFormaPagRec tipoFormaPagRec;
    private Moeda moeda;
    private Despesa despesa;

    public DespesaProvisionadaBuilder comId(Long id) {
        this.id = id;
        return this;
    }

    public DespesaProvisionadaBuilder comPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        return this;
    }

    public DespesaProvisionadaBuilder comHistorico(String historico) {
        this.historico = historico;
        return this;
    }

    public DespesaProvisionadaBuilder comValor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public DespesaProvisionadaBuilder comVencimento(Date vencimento) {
        this.vencimento = vencimento;
        return this;
    }
    
    public DespesaProvisionadaBuilder comDespesa(Despesa despesa) {
        this.despesa = despesa;
        return this;
    }


    public DespesaProvisionadaBuilder comCambio(Cambio cambio) {
        this.cambio = cambio;
        return this;
    }
    
    public DespesaProvisionadaBuilder comMoeda(Moeda moeda) {
        this.moeda = moeda;
        return this;
    }

    public DespesaProvisionada construir() throws DadoInvalidoException {
        return new DespesaProvisionada(id, pessoa, despesa, valor, vencimento, historico, cambio, false, moeda);
    }

}
