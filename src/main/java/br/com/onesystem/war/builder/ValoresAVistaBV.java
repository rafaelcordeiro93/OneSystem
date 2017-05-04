package br.com.onesystem.war.builder;

import br.com.onesystem.domain.BoletoDeCartao;
import br.com.onesystem.domain.Cheque;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.domain.ValoresAVista;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.builder.ValoresAVistaBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;

/**
 *
 * @author Rafael
 */
public class ValoresAVistaBV implements Serializable {

    private Long id;
    private Cotacao cotacao;
    private Integer parcelas;
    private BigDecimal dinheiro;
    private BigDecimal acrescimo;
    private BigDecimal desconto;
    private BigDecimal porcentagemAcrescimo;
    private BigDecimal porcentagemDesconto;
    private BigDecimal despesaCobranca;
    private BigDecimal frete;
    private BoletoDeCartao boletoDeCartao;
    private BigDecimal AFaturar;
    private NotaEmitida notaEmitida;
    private List<Cheque> cheques;
    
     public ValoresAVistaBV() {
       
    }

    public ValoresAVistaBV(Cotacao cotacao) {
        this.cotacao = cotacao;
    }

    public ValoresAVistaBV(ValoresAVista valoresAVista) {
        this.id = valoresAVista.getId();
        this.cotacao = valoresAVista.getCotacao();
        //this.parcelas = valoresAVista.get();
        this.dinheiro = valoresAVista.getDinheiro();
        this.acrescimo = valoresAVista.getAcrescimo();
        this.desconto = valoresAVista.getDesconto();
        // this.porcentagemAcrescimo = valoresAVista.get();
        // this.porcentagemDesconto = valoresAVista.getCotacao();
        this.despesaCobranca = valoresAVista.getDespesaCobranca();
        this.frete = valoresAVista.getFrete();
        this.boletoDeCartao = valoresAVista.getBoletoCartao();
        this.AFaturar = valoresAVista.getaFaturar();
        //this.notaEmitida = valoresAVista.getNota();
        this.cheques = valoresAVista.getCheques();
       
        
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getAcrescimo() {
        return acrescimo;
    }

    public void setAcrescimo(BigDecimal acrescimo) {
        this.acrescimo = acrescimo;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cotacao getCotacao() {
        return cotacao;
    }

    public void setCotacao(Cotacao cotacao) {
        this.cotacao = cotacao;
    }

    public Integer getParcelas() {
        return parcelas;
    }

    public void setParcelas(Integer parcelas) {
        this.parcelas = parcelas;
    }

    public BigDecimal getDinheiro() {
        return dinheiro;
    }

    public String getDinheiroFormatado() {
        NumberFormat nf = NumberFormat.getCurrencyInstance(cotacao.getConta().getMoeda().getBandeira().getLocal());
        return cotacao != null && dinheiro.compareTo(BigDecimal.ZERO) > 0 ? nf.format(dinheiro) : nf.format(BigDecimal.ZERO);
    }

    public void setDinheiro(BigDecimal dinheiro) {
        this.dinheiro = dinheiro;
    }

    public BoletoDeCartao getBoletoDeCartao() {
        return boletoDeCartao;
    }

    public void setBoletoDeCartao(BoletoDeCartao boletoDeCartao) {
        this.boletoDeCartao = boletoDeCartao;
    }

    public BigDecimal getAFaturar() {
        return AFaturar;
    }

    public void setAFaturar(BigDecimal AFaturar) {
        this.AFaturar = AFaturar;
    }

    public NotaEmitida getNotaEmitida() {
        return notaEmitida;
    }

    public void setNotaEmitida(NotaEmitida notaEmitida) {
        this.notaEmitida = notaEmitida;
    }

    public BigDecimal getDespesaCobranca() {
        return despesaCobranca;
    }

    public void setDespesaCobranca(BigDecimal despesaCobranca) {
        this.despesaCobranca = despesaCobranca;
    }

    public BigDecimal getFrete() {
        return frete;
    }

    public void setFrete(BigDecimal frete) {
        this.frete = frete;
    }

    public List<Cheque> getCheques() {
        return cheques;
    }

    public void setCheques(List<Cheque> cheques) {
        this.cheques = cheques;
    }

    public BigDecimal getPorcentagemAcrescimo() {
        return porcentagemAcrescimo;
    }

    public void setPorcentagemAcrescimo(BigDecimal porcentagemAcrescimo) {
        this.porcentagemAcrescimo = porcentagemAcrescimo;
    }

    public BigDecimal getPorcentagemDesconto() {
        return porcentagemDesconto;
    }

    public void setPorcentagemDesconto(BigDecimal porcentagemDesconto) {
        this.porcentagemDesconto = porcentagemDesconto;
    }

    public ValoresAVista construir() throws DadoInvalidoException {
        return new ValoresAVistaBuilder().comAFaturar(AFaturar).comBoletoDeCartao(boletoDeCartao).comDinheiro(dinheiro)
                .comNotaEmitida(notaEmitida).comCotacao(cotacao).comCheques(cheques)
                .comDesconto(desconto).comAcrescimo(acrescimo).comDespesaCobranca(despesaCobranca)
                .comFrete(frete).construir();
    }

    public ValoresAVista construirComID() throws DadoInvalidoException {
        return new ValoresAVistaBuilder().comID(id).comAFaturar(AFaturar).comBoletoDeCartao(boletoDeCartao)
                .comCheques(cheques).comDinheiro(dinheiro)
                .comNotaEmitida(notaEmitida).comCotacao(cotacao)
                .comDesconto(desconto).comAcrescimo(acrescimo).comDespesaCobranca(despesaCobranca)
                .comFrete(frete).construir();
    }

}
