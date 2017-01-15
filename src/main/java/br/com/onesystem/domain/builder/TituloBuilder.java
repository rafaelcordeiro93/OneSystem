package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.Cambio;
import br.com.onesystem.domain.Cidade;
import br.com.onesystem.domain.ConhecimentoDeFrete;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.Recepcao;
import br.com.onesystem.domain.Titulo;
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
public class TituloBuilder {

    private Long id;
    private Pessoa pessoa;
    private String historico;
    private BigDecimal valor;
    private BigDecimal saldo;
    private Date vencimento;
    private Date emissao = Calendar.getInstance().getTime();
    private OperacaoFinanceira operacaoFinanceira;
    private Recepcao recepcao;
    private Cambio cambio;
    private NotaEmitida notaEmitida;
    private List<Baixa> baixas = new ArrayList<Baixa>();
    private TipoFormaPagRec tipoFormaPagRec;
    private Moeda moeda;
private ConhecimentoDeFrete conhecimentoDeFrete;
    
    public TituloBuilder comId(Long id) {
        this.id = id;
        return this;
    }

    public TituloBuilder comPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        return this;
    }

    public TituloBuilder comHistorico(String historico) {
        this.historico = historico;
        return this;
    }

    public TituloBuilder comValor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public TituloBuilder comSaldo(BigDecimal saldo) {
        this.saldo = saldo;
        return this;
    }

    public TituloBuilder comVencimento(Date vencimento) {
        this.vencimento = vencimento;
        return this;
    }
    
     public TituloBuilder comEmissao(Date emissao) {
        this.emissao = emissao;
        return this;
    }

    public TituloBuilder comOperacaoFinanceira(OperacaoFinanceira operacaoFinanceira) {
        this.operacaoFinanceira = operacaoFinanceira;
        return this;
    }

    public TituloBuilder comRecepcao(Recepcao recepcao) {
        this.recepcao = recepcao;
        return this;
    }

    public TituloBuilder comCambio(Cambio cambio) {
        this.cambio = cambio;
        return this;
    }

    public TituloBuilder comNotaEmitida(NotaEmitida notaEmitida) {
        this.notaEmitida = notaEmitida;
        return this;
    }

    public TituloBuilder comBaixas(List<Baixa> baixas) {
        this.baixas = baixas;
        return this;
    }

    public TituloBuilder comTipoFormaPagRec(TipoFormaPagRec tipoFormaPagRec) {
        this.tipoFormaPagRec = tipoFormaPagRec;
        return this;
    }

    public TituloBuilder comMoeda(Moeda moeda) {
        this.moeda = moeda;
        return this;
    }

    public Titulo construir() throws DadoInvalidoException {
        return new Titulo(id, pessoa, historico, valor, saldo, emissao, operacaoFinanceira,
                tipoFormaPagRec, vencimento, recepcao, cambio, moeda, notaEmitida, conhecimentoDeFrete);
    }

}
