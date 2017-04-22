package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.OperacaoDeEstoque;
import br.com.onesystem.domain.TipoDespesa;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.domain.TipoReceita;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.valueobjects.TipoContabil;
import br.com.onesystem.valueobjects.TipoLancamento;
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
    private TipoLancamento tipoNota;
    private TipoOperacao tipoOperacao;
    private TipoReceita vendaAVista;
    private TipoReceita vendaAPrazo;
    private TipoReceita servicoAVista;
    private TipoReceita servicoAPrazo;
    private TipoReceita receitaFrete;
    private TipoDespesa despesaCMV;
    private TipoContabil contabilizarCMV;
    private TipoDespesa compraAVista;
    private TipoDespesa compraAPrazo;
    private List<OperacaoDeEstoque> operacaoDeEstoque;

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

    public OperacaoBuilder comTipoNota(TipoLancamento tipoNota) {
        this.tipoNota = tipoNota;
        return this;
    }

    public OperacaoBuilder comTipoOperacao(TipoOperacao tipoOperacao) {
        this.tipoOperacao = tipoOperacao;
        return this;
    }

    public OperacaoBuilder comVendaAVista(TipoReceita vendaAVista) {
        this.vendaAVista = vendaAVista;
        return this;
    }

    public OperacaoBuilder comVendaAPrazo(TipoReceita vendaAPrazo) {
        this.vendaAPrazo = vendaAPrazo;
        return this;
    }

    public OperacaoBuilder comServicoAVista(TipoReceita servicoAVista) {
        this.servicoAVista = servicoAVista;
        return this;
    }

    public OperacaoBuilder comServicoAPrazo(TipoReceita servicoAPrazo) {
        this.servicoAPrazo = servicoAPrazo;
        return this;
    }

    public OperacaoBuilder comReceitaFrete(TipoReceita receitaFrete) {
        this.receitaFrete = receitaFrete;
        return this;
    }

    public OperacaoBuilder comDespesaCMV(TipoDespesa despesaCMV) {
        this.despesaCMV = despesaCMV;
        return this;
    }

    public OperacaoBuilder comTipoContabil(TipoContabil contabilizarCMV) {
        this.contabilizarCMV = contabilizarCMV;
        return this;
    }

    public OperacaoBuilder comCompraAVista(TipoDespesa compraAVista) {
        this.compraAVista = compraAVista;
        return this;
    }

    public OperacaoBuilder comCompraAPrazo(TipoDespesa compraAPrazo) {
        this.compraAPrazo = compraAPrazo;
        return this;
    }
    
     public OperacaoBuilder comOperacaoDeEstoque(List<OperacaoDeEstoque> operacaoDeEstoque) {
        this.operacaoDeEstoque = operacaoDeEstoque;
        return this;
    }

    public Operacao construir() throws DadoInvalidoException {
        return new Operacao(id, nome, operacaoFinanceira, tipoNota, tipoOperacao, vendaAVista, vendaAPrazo, servicoAVista,
                servicoAPrazo, receitaFrete, despesaCMV, contabilizarCMV, compraAVista, compraAPrazo, operacaoDeEstoque);
    }

}
