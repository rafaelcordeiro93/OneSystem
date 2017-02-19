package br.com.onesystem.war.builder;

import br.com.onesystem.domain.OperacaoDeEstoque;
import br.com.onesystem.domain.Despesa;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.domain.Receita;
import br.com.onesystem.domain.builder.OperacaoBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.valueobjects.TipoContabil;
import br.com.onesystem.valueobjects.TipoLancamento;
import br.com.onesystem.valueobjects.TipoOperacao;
import java.io.Serializable;
import java.util.List;

public class OperacaoBV implements Serializable {

    private Long id;
    private String nome;
    private OperacaoFinanceira operacaoFinanceira;
    private TipoLancamento tipoNota;
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

    public Receita getVendaAVista() {
        return vendaAVista;
    }

    public void setVendaAVista(Receita vendaAVista) {
        this.vendaAVista = vendaAVista;
    }

    public Receita getVendaAPrazo() {
        return vendaAPrazo;
    }

    public void setVendaAPrazo(Receita vendaAPrazo) {
        this.vendaAPrazo = vendaAPrazo;
    }

    public Receita getServicoAVista() {
        return servicoAVista;
    }

    public void setServicoAVista(Receita servicoAVista) {
        this.servicoAVista = servicoAVista;
    }

    public Receita getServicoAPrazo() {
        return servicoAPrazo;
    }

    public void setServicoAPrazo(Receita servicoAPrazo) {
        this.servicoAPrazo = servicoAPrazo;
    }

    public Receita getReceitaFrete() {
        return receitaFrete;
    }

    public void setReceitaFrete(Receita receitaFrete) {
        this.receitaFrete = receitaFrete;
    }

    public Despesa getDespesaCMV() {
        return despesaCMV;
    }

    public void setDespesaCMV(Despesa despesaCMV) {
        this.despesaCMV = despesaCMV;
    }

    public TipoContabil getContabilizarCMV() {
        return contabilizarCMV;
    }

    public void setContabilizarCMV(TipoContabil contabilizarCMV) {
        this.contabilizarCMV = contabilizarCMV;
    }

    public Despesa getCompraAVista() {
        return compraAVista;
    }

    public void setCompraAVista(Despesa compraAVista) {
        this.compraAVista = compraAVista;
    }

    public Despesa getCompraAPrazo() {
        return compraAPrazo;
    }

    public void setCompraAPrazo(Despesa compraAPrazo) {
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
