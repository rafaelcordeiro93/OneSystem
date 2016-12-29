package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.ContaDeEstoque;
import br.com.onesystem.domain.Despesa;
import br.com.onesystem.domain.Filial;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.domain.Receita;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.valueobjects.TipoContabil;
import br.com.onesystem.valueobjects.TipoNota;
import br.com.onesystem.valueobjects.TipoOperacao;
import java.util.List;

/**
 *
 * @author Rafael
 */
public class OperacaoBuilder {

    private Long id;
    private String nome;
    private OperacaoFinanceira operacaoFinanceira;
    private TipoNota tipoNota;
    private TipoOperacao tipoOperacao;
    private Receita vendaAVista;
    private Receita vendaAPrazo;
    private Receita servicoAVista;
    private Receita servicoAPrazo;
    private Receita receitaFrete;
    private Despesa despesaCMV;
    private TipoContabil contabilizarCMV;
    private Despesa compraAVista;
    private Despesa compraAPrazo;

    public OperacaoBuilder comID(Long ID) {
        this.id = ID;
        return this;
    }

    public OperacaoBuilder comNome(String nome) {
        this.nome = nome;
        return this;
    }

    public OperacaoBuilder comOperacaoFinanceira(OperacaoFinanceira operacaoFinanceira) {
        this.operacaoFinanceira = operacaoFinanceira;
        return this;
    }

    public OperacaoBuilder comTipoNota(TipoNota tipoNota) {
        this.tipoNota = tipoNota;
        return this;
    }

    public OperacaoBuilder comTipoOperacao(TipoOperacao tipoOperacao) {
        this.tipoOperacao = tipoOperacao;
        return this;
    }

    public OperacaoBuilder comVendaAVista(Receita vendaAVista) {
        this.vendaAVista = vendaAVista;
        return this;
    }

    public OperacaoBuilder comVendaAPrazo(Receita vendaAPrazo) {
        this.vendaAPrazo = vendaAPrazo;
        return this;
    }

    public OperacaoBuilder comServicoAVista(Receita servicoAVista) {
        this.servicoAVista = servicoAVista;
        return this;
    }

    public OperacaoBuilder comServicoAPrazo(Receita servicoAPrazo) {
        this.servicoAPrazo = servicoAPrazo;
        return this;
    }

    public OperacaoBuilder comReceitaFrete(Receita receitaFrete) {
        this.receitaFrete = receitaFrete;
        return this;
    }

    public OperacaoBuilder comDespesaCMV(Despesa despesaCMV) {
        this.despesaCMV = despesaCMV;
        return this;
    }

    public OperacaoBuilder comTipoContabil(TipoContabil contabilizarCMV) {
        this.contabilizarCMV = contabilizarCMV;
        return this;
    }

    public OperacaoBuilder comCompraAVista(Despesa compraAVista) {
        this.compraAVista = compraAVista;
        return this;
    }

    public OperacaoBuilder comCompraAPrazo(Despesa compraAPrazo) {
        this.compraAPrazo = compraAPrazo;
        return this;
    }

    public Operacao construir() throws DadoInvalidoException {
        return new Operacao(id, nome, operacaoFinanceira, tipoNota, tipoOperacao, vendaAVista, vendaAPrazo, servicoAVista,
                servicoAPrazo, receitaFrete, despesaCMV, contabilizarCMV, compraAVista, compraAPrazo);
    }

}
