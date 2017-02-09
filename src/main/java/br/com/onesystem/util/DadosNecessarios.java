/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.util;

import br.com.onesystem.dao.CotacaoDAO;
import br.com.onesystem.domain.Configuracao;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.war.builder.DadosNecessariosBV;
import br.com.onesystem.war.service.ConfiguracaoService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class DadosNecessarios implements Serializable {

    private List<DadosNecessariosBV> pendencias = new ArrayList<DadosNecessariosBV>();
    private ConfiguracaoService configuracaoService;

    public List<DadosNecessariosBV> valida(String janela) {
        if (janela.equals("/notaEmitida.xhtml")) {
            Moeda moeda = getMoedaPadrao();
            getCotacaoEmMoedaPadrao(moeda);
        } else if (janela.equals("/relatorioDeBalancoFisico.xhtml")) {
            Moeda moeda = getMoedaPadrao();
        }
        return pendencias;
    }

    private Moeda getMoedaPadrao() {
        BundleUtil b = new BundleUtil();
        DadosNecessariosBV bv = new DadosNecessariosBV(b.getLabel("Configuracoes"), "/configuracao.xhtml");
        try {
            configuracaoService = new ConfiguracaoService();
            Configuracao conf = configuracaoService.buscar();
            if (conf.getMoedaPadrao() == null) {
                bv.getLista().add(b.getMessage("moeda_padrao_not_null"));
                pendencias.add(bv);
                return null;
            }
            return conf.getMoedaPadrao();
        } catch (DadoInvalidoException ex) {
            bv.getLista().add(b.getMessage("moeda_padrao_not_null"));
            pendencias.add(bv);
            return null;
        }
    }

    private void getCotacaoEmMoedaPadrao(Moeda moeda) {
        BundleUtil b = new BundleUtil();
        DadosNecessariosBV bv = new DadosNecessariosBV(b.getLabel("Cotacao"), "/cotacao.xhtml");
        try {
            if (moeda != null) {
                Cotacao cotacao = new CotacaoDAO().buscarCotacoes().porMoeda(moeda).naMaiorEmissao(new Date()).resultado();
                if (cotacao == null) {
                    bv.getLista().add(b.getMessage("Cotacao_Dia_Deve_Ser_Cadastrada"));
                    pendencias.add(bv);
                }
            } else {
                bv.getLista().add(b.getMessage("Cotacao_Dia_Deve_Ser_Cadastrada"));
                pendencias.add(bv);
            }
        } catch (DadoInvalidoException ex) {
            bv.getLista().add(b.getMessage("Cotacao_Dia_Deve_Ser_Cadastrada"));
            pendencias.add(bv);
        }
    }

}
