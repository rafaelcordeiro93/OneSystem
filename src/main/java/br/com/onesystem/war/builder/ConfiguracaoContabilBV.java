package br.com.onesystem.war.builder;

import br.com.onesystem.domain.ConfiguracaoContabil;
import br.com.onesystem.domain.TipoDespesa;
import br.com.onesystem.domain.TipoReceita;
import br.com.onesystem.domain.builder.ConfiguracaoContabilBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.BuilderView;
import java.io.Serializable;

public class ConfiguracaoContabilBV implements Serializable, BuilderView<ConfiguracaoContabil> {

    private Long id;
    private TipoReceita receitaDeJuros;
    private TipoReceita receitaDeMultas;
    private TipoReceita receitaDeDescontosObtidos;
    private TipoReceita receitaDeVariacaoCambial;
    private TipoDespesa despesaDeJuros;
    private TipoDespesa despesaDeMultas;
    private TipoDespesa despesaDeDescontosConcedidos;
    private TipoDespesa despesaDeVariacaoCambial;

    public ConfiguracaoContabilBV() {
    }

    public ConfiguracaoContabilBV(ConfiguracaoContabil configuracaoContabil) {
        this.id = configuracaoContabil.getId();
        this.receitaDeJuros = configuracaoContabil.getReceitaDeJuros();
        this.receitaDeMultas = configuracaoContabil.getReceitaDeMultas();
        this.receitaDeDescontosObtidos = configuracaoContabil.getReceitaDeDescontosObtidos();
        this.receitaDeVariacaoCambial = configuracaoContabil.getReceitaDeVariacaoCambial();
        this.despesaDeJuros = configuracaoContabil.getDespesaDeJuros();
        this.despesaDeMultas = configuracaoContabil.getDespesaDeMultas();
        this.despesaDeDescontosConcedidos = configuracaoContabil.getDespesaDeDescontosConcedidos();
        this.despesaDeVariacaoCambial = configuracaoContabil.getDespesaDeVariacaoCambial();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoReceita getReceitaDeJuros() {
        return receitaDeJuros;
    }

    public void setReceitaDeJuros(TipoReceita receitaDeJuros) {
        this.receitaDeJuros = receitaDeJuros;
    }

    public TipoReceita getReceitaDeMultas() {
        return receitaDeMultas;
    }

    public void setReceitaDeMultas(TipoReceita receitaDeMultas) {
        this.receitaDeMultas = receitaDeMultas;
    }

    public TipoReceita getReceitaDeDescontosObtidos() {
        return receitaDeDescontosObtidos;
    }

    public void setReceitaDeDescontosObtidos(TipoReceita receitaDeDescontosObtidos) {
        this.receitaDeDescontosObtidos = receitaDeDescontosObtidos;
    }

    public TipoReceita getReceitaDeVariacaoCambial() {
        return receitaDeVariacaoCambial;
    }

    public void setReceitaDeVariacaoCambial(TipoReceita receitaDeVariacaoCambial) {
        this.receitaDeVariacaoCambial = receitaDeVariacaoCambial;
    }

    public TipoDespesa getDespesaDeJuros() {
        return despesaDeJuros;
    }

    public void setDespesaDeJuros(TipoDespesa despesaDeJuros) {
        this.despesaDeJuros = despesaDeJuros;
    }

    public TipoDespesa getDespesaDeMultas() {
        return despesaDeMultas;
    }

    public void setDespesaDeMultas(TipoDespesa despesaDeMultas) {
        this.despesaDeMultas = despesaDeMultas;
    }

    public TipoDespesa getDespesaDeDescontosConcedidos() {
        return despesaDeDescontosConcedidos;
    }

    public void setDespesaDeDescontosConcedidos(TipoDespesa despesaDeDescontosConcedidos) {
        this.despesaDeDescontosConcedidos = despesaDeDescontosConcedidos;
    }

    public TipoDespesa getDespesaDeVariacaoCambial() {
        return despesaDeVariacaoCambial;
    }

    public void setDespesaDeVariacaoCambial(TipoDespesa despesaDeVariacaoCambial) {
        this.despesaDeVariacaoCambial = despesaDeVariacaoCambial;
    }

    @Override
    public ConfiguracaoContabil construir() throws DadoInvalidoException {
        return new ConfiguracaoContabilBuilder().comReceitaDeDescontosObtidos(receitaDeDescontosObtidos).comReceitaDeJuros(receitaDeJuros).comReceitaDeMultas(receitaDeMultas)
                .comReceitaDeVariacaoCambial(receitaDeVariacaoCambial).comDespesaDeDescontosConcedidos(despesaDeDescontosConcedidos)
                .comDespesaDeJuros(despesaDeJuros).comDespesaDeMultas(despesaDeMultas).comDespesaDeVariacaoCambial(despesaDeVariacaoCambial)
                .construir();
    }

    @Override
    public ConfiguracaoContabil construirComID() throws DadoInvalidoException {
        return new ConfiguracaoContabilBuilder().comId(id).comReceitaDeDescontosObtidos(receitaDeDescontosObtidos).comReceitaDeJuros(receitaDeJuros).comReceitaDeMultas(receitaDeMultas)
                .comReceitaDeVariacaoCambial(receitaDeVariacaoCambial).comDespesaDeDescontosConcedidos(despesaDeDescontosConcedidos)
                .comDespesaDeJuros(despesaDeJuros).comDespesaDeMultas(despesaDeMultas).comDespesaDeVariacaoCambial(despesaDeVariacaoCambial)
                .construir();
    }

}
