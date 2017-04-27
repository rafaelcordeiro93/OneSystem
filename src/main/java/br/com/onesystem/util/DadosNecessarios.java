/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.util;

import br.com.onesystem.dao.CotacaoDAO;
import br.com.onesystem.domain.Configuracao;
import br.com.onesystem.domain.ConfiguracaoEstoque;
import br.com.onesystem.domain.ContaDeEstoque;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.war.builder.DadosNecessariosBV;
import br.com.onesystem.war.service.ColunaService;
import br.com.onesystem.war.service.ConfiguracaoEstoqueService;
import br.com.onesystem.war.service.ConfiguracaoService;
import br.com.onesystem.war.service.OperacaoService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class DadosNecessarios implements Serializable {

    private List<DadosNecessariosBV> pendencias = new ArrayList<>();
    FacesContext fc = FacesContext.getCurrentInstance();

    public List<DadosNecessariosBV> valida(String janela) {
        switch (janela) {
            case "/notaEmitida.xhtml": {
                Moeda moeda = getMoedaPadrao();
                getCotacaoEmMoedaPadrao(moeda);
                getContaDeEstoque();
                getOperacoes();
                break;
            }
            case "/orcamento.xhtml": {
                Moeda moeda = getMoedaPadrao();
                getCotacaoEmMoedaPadrao(moeda);
                break;
            }
            case "/relatorioDeBalancoFisico.xhtml": {
                getMoedaPadrao();
                getContaDeEstoque();
                break;
            }
            case "/ajusteDeEstoque.xhtml": {
                getMoedaPadrao();
                getContaDeEstoque();
                break;
            }
            case "/item.xhtml": {
                getContaDeEstoque();
                break;
            }
            default:
                break;
        }
        return pendencias;
    }

    private ContaDeEstoque getContaDeEstoque() {
        BundleUtil b = new BundleUtil();
        DadosNecessariosBV bv = new DadosNecessariosBV(b.getLabel("Configuracoes"), "/configuracao.xhtml");

        ConfiguracaoEstoqueService configuracaoEstoqueService = new ConfiguracaoEstoqueService();
        ConfiguracaoEstoque conf = configuracaoEstoqueService.buscar();
        try {
            if (conf.getContaDeEstoqueEmpresa() == null) {
                bv.getLista().add(b.getMessage("conta_de_estoque_empresa_not_null"));
                pendencias.add(bv);
                return null;
            }
            return conf.getContaDeEstoqueEmpresa();
        } catch (NullPointerException npe) {
            bv.getLista().add(b.getMessage("conta_de_estoque_empresa_not_null"));
            pendencias.add(bv);
            return null;
        }
    }

    private Moeda getMoedaPadrao() {
        BundleUtil b = new BundleUtil();
        DadosNecessariosBV bv = new DadosNecessariosBV(b.getLabel("Configuracoes"), "/configuracao.xhtml");
        try {
            ConfiguracaoService configuracaoService = new ConfiguracaoService();
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

    private Cotacao getCotacaoEmMoedaPadrao(Moeda moeda) {
        BundleUtil b = new BundleUtil();
        DadosNecessariosBV bv = new DadosNecessariosBV(b.getLabel("Cotacao"), "/cotacao.xhtml");
        try {
            if (moeda != null) {
                Cotacao cotacao = new CotacaoDAO().buscarCotacoes().porMoeda(moeda).naMaiorEmissao(new Date()).resultado();
                if (cotacao == null) {
                    bv.getLista().add(b.getMessage("Cotacao_Dia_Deve_Ser_Cadastrada"));
                    pendencias.add(bv);
                    return null;
                }
                return cotacao;
            } else {
                bv.getLista().add(b.getMessage("Cotacao_Dia_Deve_Ser_Cadastrada"));
                pendencias.add(bv);
                return null;
            }
        } catch (DadoInvalidoException ex) {
            bv.getLista().add(b.getMessage("Cotacao_Dia_Deve_Ser_Cadastrada"));
            pendencias.add(bv);
            return null;
        }
    }

    private List<Operacao> getOperacoes() {
        BundleUtil b = new BundleUtil();
        DadosNecessariosBV bv = new DadosNecessariosBV(b.getLabel("Operacao"), "/operacoes.xhtml");
        OperacaoService operacaoService = new OperacaoService();
        List<Operacao> operacoes = operacaoService.buscar();
        if (operacoes.isEmpty()) {
            bv.getLista().add(b.getMessage("operacao_not_null"));
            pendencias.add(bv);
            return null;
        }
        return operacoes;
    }

}
