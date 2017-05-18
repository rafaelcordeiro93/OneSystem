/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.ConfiguracaoVenda;
import br.com.onesystem.domain.FormaDeRecebimento;
import br.com.onesystem.exception.DadoInvalidoException;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class ConfiguracaoVendaBuilder {

    private Long id;
    private FormaDeRecebimento formaDeRecebimentoDevolucaoEmpresa;
    private boolean gerarNumeroComanda;

    public ConfiguracaoVendaBuilder comId(Long id) {
        this.id = id;
        return this;
    }

    public ConfiguracaoVendaBuilder comFormaDeRecebimentoDevolucaoEmpresa(FormaDeRecebimento formaDeRecebimentoDevolucaoEmpresa) {
        this.formaDeRecebimentoDevolucaoEmpresa = formaDeRecebimentoDevolucaoEmpresa;
        return this;
    }

    public ConfiguracaoVendaBuilder comGerarNumeroComanga(boolean gerarNumeroComanda) {
        this.gerarNumeroComanda = gerarNumeroComanda;
        return this;
    }

    public ConfiguracaoVenda construir() throws DadoInvalidoException {
        return new ConfiguracaoVenda(id, formaDeRecebimentoDevolucaoEmpresa, gerarNumeroComanda);
    }

}
