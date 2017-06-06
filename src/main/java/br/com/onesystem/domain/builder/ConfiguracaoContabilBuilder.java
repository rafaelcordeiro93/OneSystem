/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.ConfiguracaoContabil;
import br.com.onesystem.domain.TipoDespesa;
import br.com.onesystem.domain.TipoReceita;
import br.com.onesystem.exception.DadoInvalidoException;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class ConfiguracaoContabilBuilder {

    private Long id;
    private TipoReceita receitaDeJuros;
    private TipoReceita receitaDeMultas;
    private TipoReceita receitaDeDescontosObtidos;
    private TipoReceita receitaDeVariacaoCambial;
    private TipoDespesa despesaDeJuros;
    private TipoDespesa despesaDeMultas;
    private TipoDespesa despesaDeDescontosConcedidos;
    private TipoDespesa despesaDeVariacaoCambial;

    public ConfiguracaoContabilBuilder comDespesaDeVariacaoCambial(TipoDespesa despesaDeVariacaoCambial) {
        this.despesaDeVariacaoCambial = despesaDeVariacaoCambial;
        return this;
    }

    public ConfiguracaoContabilBuilder comDespesaDeDescontosConcedidos(TipoDespesa despesaDeDescontosConcedidos) {
        this.despesaDeDescontosConcedidos = despesaDeDescontosConcedidos;
        return this;
    }

    public ConfiguracaoContabilBuilder comDespesaDeMultas(TipoDespesa despesaDeMultas) {
        this.despesaDeMultas = despesaDeMultas;
        return this;
    }

    public ConfiguracaoContabilBuilder comDespesaDeJuros(TipoDespesa despesaDeJuros) {
        this.despesaDeJuros = despesaDeJuros;
        return this;
    }

    public ConfiguracaoContabilBuilder comId(Long id) {
        this.id = id;
        return this;
    }

    public ConfiguracaoContabilBuilder comReceitaDeJuros(TipoReceita receitaDeJuros) {
        this.receitaDeJuros = receitaDeJuros;
        return this;
    }

    public ConfiguracaoContabilBuilder comReceitaDeMultas(TipoReceita receitaDeMultas) {
        this.receitaDeMultas = receitaDeMultas;
        return this;
    }

    public ConfiguracaoContabilBuilder comReceitaDeDescontosObtidos(TipoReceita receitaDeDescontosObtidos) {
        this.receitaDeDescontosObtidos = receitaDeDescontosObtidos;
        return this;
    }

    public ConfiguracaoContabilBuilder comReceitaDeVariacaoCambial(TipoReceita receitaDeVariacaoCambial) {
        this.receitaDeVariacaoCambial = receitaDeVariacaoCambial;
        return this;
    }

    public ConfiguracaoContabil construir() throws DadoInvalidoException {
        return new ConfiguracaoContabil(id, receitaDeJuros, receitaDeMultas, receitaDeDescontosObtidos, receitaDeVariacaoCambial, despesaDeJuros, despesaDeMultas, despesaDeDescontosConcedidos, despesaDeVariacaoCambial);
    }

}
