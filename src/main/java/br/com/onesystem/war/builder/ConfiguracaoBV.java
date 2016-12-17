package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Configuracao;
import br.com.onesystem.domain.Despesa;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.TipoDeCalculoDeCusto;
import br.com.onesystem.valueobjects.TipoDeFormacaoDePreco;
import java.io.Serializable;

public class ConfiguracaoBV implements Serializable {

    private Long id;
    private Moeda moedaPadrao;
    private Despesa despesaDeComissao;
    private TipoDeFormacaoDePreco tipoDeFormacaoDePreco;
    private TipoDeCalculoDeCusto tipoDeCalculoDeCusto;

    public ConfiguracaoBV(Configuracao configuracaoSelecionada) {
        this.id = configuracaoSelecionada.getId();
        this.despesaDeComissao = configuracaoSelecionada.getDespesaDeComissao();
        this.moedaPadrao = configuracaoSelecionada.getMoedaPadrao();
        this.tipoDeFormacaoDePreco = configuracaoSelecionada.getTipoDeFormacaoDePreco();
        this.tipoDeCalculoDeCusto = configuracaoSelecionada.getTipoDeCalculoDeCusto();
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

    public Despesa getDespesaDeComissao() {
        return despesaDeComissao;
    }

    public void setDespesaDeComissao(Despesa despesaDeComissao) {
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
    
    public Configuracao construir() throws DadoInvalidoException {
        return new Configuracao(id, despesaDeComissao, moedaPadrao, tipoDeFormacaoDePreco, tipoDeCalculoDeCusto);
    }

}
