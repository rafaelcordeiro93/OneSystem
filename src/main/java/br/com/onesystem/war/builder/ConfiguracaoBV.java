package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Configuracao;
import br.com.onesystem.domain.TipoDespesa;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.BuilderView;
import br.com.onesystem.valueobjects.TipoDeCalculoDeCusto;
import br.com.onesystem.valueobjects.TipoDeFormacaoDePreco;
import java.io.Serializable;

public class ConfiguracaoBV implements Serializable, BuilderView<Configuracao> {

    private Long id;
    private Moeda moedaPadrao;
    private TipoDespesa despesaDeComissao;
    private TipoDeFormacaoDePreco tipoDeFormacaoDePreco;
    private TipoDeCalculoDeCusto tipoDeCalculoDeCusto;
    private String caminhoImpressoraTexto;
    private boolean utilizarCep;

    public ConfiguracaoBV(Configuracao c) {
        this.id = c.getId();
        this.despesaDeComissao = c.getDespesaDeComissao();
        this.moedaPadrao = c.getMoedaPadrao();
        this.tipoDeFormacaoDePreco = c.getTipoDeFormacaoDePreco();
        this.tipoDeCalculoDeCusto = c.getTipoDeCalculoDeCusto();
        this.caminhoImpressoraTexto = c.getCaminhoImpressoraTexto();
        this.utilizarCep = c.isUtilizarCep();
    }

    public ConfiguracaoBV() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Moeda getMoedaPadrao() {
        return moedaPadrao;
    }

    public TipoDespesa getDespesaDeComissao() {
        return despesaDeComissao;
    }

    public void setDespesaDeComissao(TipoDespesa despesaDeComissao) {
        this.despesaDeComissao = despesaDeComissao;
    }

    public void setMoedaPadrao(Moeda moedaPadrao) {
        this.moedaPadrao = moedaPadrao;
    }

    public TipoDeFormacaoDePreco getTipoDeFormacaoDePreco() {
        return tipoDeFormacaoDePreco;
    }

    public void setTipoDeFormacaoDePreco(TipoDeFormacaoDePreco tipoDeFormacaoDePreco) {
        this.tipoDeFormacaoDePreco = tipoDeFormacaoDePreco;
    }

    public TipoDeCalculoDeCusto getTipoDeCalculoDeCusto() {
        return tipoDeCalculoDeCusto;
    }

    public void setTipoDeCalculoDeCusto(TipoDeCalculoDeCusto tipoDeCalculoDeCusto) {
        this.tipoDeCalculoDeCusto = tipoDeCalculoDeCusto;
    }

    public String getCaminhoImpressoraTexto() {
        return caminhoImpressoraTexto;
    }

    public void setCaminhoImpressoraTexto(String caminhoImpressoraTexto) {
        this.caminhoImpressoraTexto = caminhoImpressoraTexto;
    }

    public boolean isUtilizarCep() {
        return utilizarCep;
    }

    public void setUtilizarCep(boolean utilizarCep) {
        this.utilizarCep = utilizarCep;
    }

    public Configuracao construir() throws DadoInvalidoException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Configuracao construirComID() throws DadoInvalidoException {
        return new Configuracao(id, despesaDeComissao, moedaPadrao, tipoDeFormacaoDePreco, tipoDeCalculoDeCusto, caminhoImpressoraTexto, utilizarCep);
    }

}
