package br.com.onesystem.war.builder;

import br.com.onesystem.domain.OperacaoDeEstoque;
import br.com.onesystem.domain.TipoDespesa;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.domain.TipoReceita;
import br.com.onesystem.domain.builder.OperacaoBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.BuilderView;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.valueobjects.TipoContabil;
import br.com.onesystem.valueobjects.TipoLancamento;
import br.com.onesystem.valueobjects.TipoOperacao;
import java.io.Serializable;
import java.util.List;

public class OperacaoBV implements Serializable, BuilderView<Operacao> {

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

    public OperacaoBV(Operacao operacaoSelecionada) {
        this.id = operacaoSelecionada.getId();
        this.nome = operacaoSelecionada.getNome();
        this.operacaoFinanceira = operacaoSelecionada.getOperacaoFinanceira();
        this.tipoNota = operacaoSelecionada.getTipoNota();
        this.tipoOperacao = operacaoSelecionada.getTipoOperacao();
        this.vendaAVista = operacaoSelecionada.getVendaAVista();
        this.vendaAPrazo = operacaoSelecionada.getVendaAPrazo();
        this.servicoAVista = operacaoSelecionada.getServicoAVista();
        this.servicoAPrazo = operacaoSelecionada.getServicoAPrazo();
        this.receitaFrete = operacaoSelecionada.getReceitaFrete();
        this.despesaCMV = operacaoSelecionada.getDespesaCMV();
        this.contabilizarCMV = operacaoSelecionada.getContabilizarCMV();
        this.compraAVista = operacaoSelecionada.getCompraAVista();
        this.compraAPrazo = operacaoSelecionada.getCompraAPrazo();
        this.operacaoDeEstoque = operacaoSelecionada.getOperacaoDeEstoque();
    }

    public OperacaoBV() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public OperacaoFinanceira getOperacaoFinanceira() {
        return operacaoFinanceira;
    }

    public void setOperacaoFinanceira(OperacaoFinanceira operacaoFinanceira) {
        this.operacaoFinanceira = operacaoFinanceira;
    }

    public TipoLancamento getTipoNota() {
        return tipoNota;
    }

    public void setTipoNota(TipoLancamento tipoNota) {
        this.tipoNota = tipoNota;
    }

    public TipoOperacao getTipoOperacao() {
        return tipoOperacao;
    }

    public void setTipoOperacao(TipoOperacao tipoOperacao) {
        this.tipoOperacao = tipoOperacao;
    }

    public TipoReceita getVendaAVista() {
        return vendaAVista;
    }

    public void setVendaAVista(TipoReceita vendaAVista) {
        this.vendaAVista = vendaAVista;
    }

    public TipoReceita getVendaAPrazo() {
        return vendaAPrazo;
    }

    public void setVendaAPrazo(TipoReceita vendaAPrazo) {
        this.vendaAPrazo = vendaAPrazo;
    }

    public TipoReceita getServicoAVista() {
        return servicoAVista;
    }

    public void setServicoAVista(TipoReceita servicoAVista) {
        this.servicoAVista = servicoAVista;
    }

    public TipoReceita getServicoAPrazo() {
        return servicoAPrazo;
    }

    public void setServicoAPrazo(TipoReceita servicoAPrazo) {
        this.servicoAPrazo = servicoAPrazo;
    }

    public TipoReceita getReceitaFrete() {
        return receitaFrete;
    }

    public void setReceitaFrete(TipoReceita receitaFrete) {
        this.receitaFrete = receitaFrete;
    }

    public TipoDespesa getDespesaCMV() {
        return despesaCMV;
    }

    public void setDespesaCMV(TipoDespesa despesaCMV) {
        this.despesaCMV = despesaCMV;
    }

    public TipoContabil getContabilizarCMV() {
        return contabilizarCMV;
    }

    public void setContabilizarCMV(TipoContabil contabilizarCMV) {
        this.contabilizarCMV = contabilizarCMV;
    }

    public TipoDespesa getCompraAVista() {
        return compraAVista;
    }

    public void setCompraAVista(TipoDespesa compraAVista) {
        this.compraAVista = compraAVista;
    }

    public TipoDespesa getCompraAPrazo() {
        return compraAPrazo;
    }

    public void setCompraAPrazo(TipoDespesa compraAPrazo) {
        this.compraAPrazo = compraAPrazo;
    }

    public List<OperacaoDeEstoque> getOperacaoDeEstoque() {
        return operacaoDeEstoque;
    }

    public void setOperacaoDeEstoque(List<OperacaoDeEstoque> operacaoDeEstoque) {
        this.operacaoDeEstoque = operacaoDeEstoque;
    }

    public Operacao construir() throws DadoInvalidoException {
        return new OperacaoBuilder().comNome(nome).comOperacaoFinanceira(operacaoFinanceira).comTipoNota(tipoNota).comTipoOperacao(tipoOperacao).comVendaAVista(vendaAVista).
                comVendaAPrazo(vendaAPrazo).comServicoAVista(servicoAVista).comServicoAPrazo(servicoAPrazo).comReceitaFrete(receitaFrete).comDespesaCMV(despesaCMV).
                comTipoContabil(contabilizarCMV).comCompraAVista(compraAVista).comCompraAPrazo(compraAPrazo).comOperacaoDeEstoque(operacaoDeEstoque).construir();
    }

    public Operacao construirComID() throws DadoInvalidoException {
        return new OperacaoBuilder().comID(id).comNome(nome).comOperacaoFinanceira(operacaoFinanceira).comTipoNota(tipoNota).comTipoOperacao(tipoOperacao).comVendaAVista(vendaAVista).
                comVendaAPrazo(vendaAPrazo).comServicoAVista(servicoAVista).comServicoAPrazo(servicoAPrazo).comReceitaFrete(receitaFrete).comDespesaCMV(despesaCMV).
                comTipoContabil(contabilizarCMV).comCompraAVista(compraAVista).comCompraAPrazo(compraAPrazo).comOperacaoDeEstoque(operacaoDeEstoque).construir();
    }
}
