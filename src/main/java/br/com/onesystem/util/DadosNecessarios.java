/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.util;

import br.com.onesystem.dao.CotacaoDAO;
import br.com.onesystem.domain.Configuracao;
import br.com.onesystem.domain.ConfiguracaoEstoque;
import br.com.onesystem.domain.ConfiguracaoVenda;
import br.com.onesystem.domain.ContaDeEstoque;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.FormaDeRecebimento;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.war.builder.DadosNecessariosBV;
import br.com.onesystem.war.service.ColunaService;
import br.com.onesystem.war.service.ConfiguracaoEstoqueService;
import br.com.onesystem.war.service.ConfiguracaoService;
import br.com.onesystem.war.service.ConfiguracaoVendaService;
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
    private FacesContext fc = FacesContext.getCurrentInstance();
    private BundleUtil b = new BundleUtil();

    public List<DadosNecessariosBV> valida(String janela) {
        switch (janela) {
            case "/notaEmitida.xhtml": {
                Moeda moeda = getMoedaPadrao();
                getCotacaoEmMoedaPadrao(moeda);
                getContaDeEstoque();
                getOperacoes();
                getFormaRecebimentoDevolucaoPadraoEmpresa();
                break;
            }
            case "/orcamento.xhtml": {
                Moeda moeda = getMoedaPadrao();
                getCotacaoEmMoedaPadrao(moeda);
                break;
            }
            case "/comanda.xhtml": {
                Moeda moeda = getMoedaPadrao();
                getCotacaoEmMoedaPadrao(moeda);
//                getOperacaoDeComanda(); Implantação Futura
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

    private FormaDeRecebimento getFormaRecebimentoDevolucaoPadraoEmpresa() {
        DadosNecessariosBV bv = new DadosNecessariosBV(b.getLabel("Configuracoes"), "/configuracao.xhtml");
        try {
            ConfiguracaoVendaService configuracaoService = new ConfiguracaoVendaService();
            ConfiguracaoVenda conf = configuracaoService.buscar();
            if (conf.getFormaDeRecebimentoDevolucaoEmpresa() == null) {
                bv.getLista().add(b.getLabel("Deve_Selecionar_Forma_Recebimento_Devolucao_Padrao"));
                pendencias.add(bv);
                return null;
            }
            return conf.getFormaDeRecebimentoDevolucaoEmpresa();
        } catch (NullPointerException npe) {
            bv.getLista().add(b.getLabel("Deve_Selecionar_Forma_Recebimento_Devolucao_Padrao"));
            pendencias.add(bv);
            return null;
        }
    }

    private Cotacao getCotacaoEmMoedaPadrao(Moeda moeda) {
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

    private Operacao getOperacaoDeComanda() {
        DadosNecessariosBV bv = new DadosNecessariosBV(b.getLabel("Configuracoes"), "/configuracao.xhtml");

        ConfiguracaoVendaService configuracaoVendaService = new ConfiguracaoVendaService();
        ConfiguracaoVenda conf = configuracaoVendaService.buscar();
        try {
            if (conf.getOperacaoDeComanda() == null) {
                bv.getLista().add(b.getMessage("Operacao_Comanda_Not_Null"));
                pendencias.add(bv);
                return null;
            }
            return conf.getOperacaoDeComanda();
        } catch (NullPointerException npe) {
            bv.getLista().add(b.getMessage("Operacao_Comanda_Not_Null"));
            pendencias.add(bv);
            return null;
        }
    }

    private List<Operacao> getOperacoes() {
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
