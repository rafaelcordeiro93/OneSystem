/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.util;

import br.com.onesystem.dao.CotacaoDAO;
import br.com.onesystem.domain.Caixa;
import br.com.onesystem.domain.Configuracao;
import br.com.onesystem.domain.ConfiguracaoContabil;
import br.com.onesystem.domain.ConfiguracaoEstoque;
import br.com.onesystem.domain.ConfiguracaoVenda;
import br.com.onesystem.domain.ContaDeEstoque;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.FormaDeRecebimento;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.domain.TipoDespesa;
import br.com.onesystem.domain.TipoReceita;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.FDadoInvalidoException;
import br.com.onesystem.war.builder.DadosNecessariosBV;
import br.com.onesystem.war.service.ConfiguracaoContabilService;
import br.com.onesystem.war.service.ConfiguracaoEstoqueService;
import br.com.onesystem.war.service.ConfiguracaoService;
import br.com.onesystem.war.service.ConfiguracaoVendaService;
import br.com.onesystem.war.service.OperacaoService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.context.FacesContext;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class DadosNecessarios implements Serializable {

    private List<DadosNecessariosBV> pendencias = new ArrayList<>();
    private FacesContext fc = FacesContext.getCurrentInstance();
    private BundleUtil b = new BundleUtil();
    private ConfiguracaoContabil configuracaoContabil;
    private ConfiguracaoEstoque configuracaoEstoque;
    private Configuracao configuracao;
    private ConfiguracaoVenda configuracaoVenda;

    public void init() {
        try {
            configuracaoContabil = new ConfiguracaoContabilService().buscar();
            configuracaoEstoque = new ConfiguracaoEstoqueService().buscar();
            configuracao = new ConfiguracaoService().buscar();
            configuracaoVenda = new ConfiguracaoVendaService().buscar();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public List<DadosNecessariosBV> valida(String janela) {
        init();
        switch (janela) {
            case "/notaEmitida.xhtml": {
                Moeda moeda = getMoedaPadrao();
                getCotacaoEmMoedaPadrao(moeda);
                getContaDeEstoque();
                getOperacoes();
                getFormaRecebimentoDevolucaoPadraoEmpresa();
                getReceitaDeDescontosObtidos();
                getReceitaDeJuros();
                getReceitaDeMultas();
                getReceitaDeVariacaoCambial();
                getDespesaDeDescontosConcedidos();
                getDespesaDeJuros();
                getDespesaDeMultas();
                getDespesaDeVariacaoCambial();
                getCaixa();
                break;
            }
            case "/notaRecebida.xhtml": {
                Moeda moeda = getMoedaPadrao();
                getCotacaoEmMoedaPadrao(moeda);
                getContaDeEstoque();
                getOperacoes();
                getReceitaDeDescontosObtidos();
                getReceitaDeJuros();
                getReceitaDeMultas();
                getReceitaDeVariacaoCambial();
                getDespesaDeDescontosConcedidos();
                getDespesaDeJuros();
                getDespesaDeMultas();
                getDespesaDeVariacaoCambial();
                getCaixa();
                break;
            }
            case "/desdobramentoDeVenda.xhtml": {
                Moeda moeda = getMoedaPadrao();
                getCotacaoEmMoedaPadrao(moeda);
                getContaDeEstoque();
                getOperacoes();
                getFormaRecebimentoDevolucaoPadraoEmpresa();
                getReceitaDeDescontosObtidos();
                getReceitaDeJuros();
                getReceitaDeMultas();
                getReceitaDeVariacaoCambial();
                getDespesaDeDescontosConcedidos();
                getDespesaDeJuros();
                getDespesaDeMultas();
                getDespesaDeVariacaoCambial();
                getCaixa();
                break;
            }
            case "/recebimento.xhtml": {
                Moeda moeda = getMoedaPadrao();
                getCotacaoEmMoedaPadrao(moeda);
                getReceitaDeDescontosObtidos();
                getReceitaDeJuros();
                getReceitaDeMultas();
                getReceitaDeVariacaoCambial();
                getDespesaDeDescontosConcedidos();
                getDespesaDeJuros();
                getDespesaDeMultas();
                getDespesaDeVariacaoCambial();
                getCaixa();
                break;
            }
            case "/pagamento.xhtml": {
                Moeda moeda = getMoedaPadrao();
                getCotacaoEmMoedaPadrao(moeda);
                getReceitaDeDescontosObtidos();
                getReceitaDeJuros();
                getReceitaDeMultas();
                getReceitaDeVariacaoCambial();
                getDespesaDeDescontosConcedidos();
                getDespesaDeJuros();
                getDespesaDeMultas();
                getDespesaDeVariacaoCambial();
                getCaixa();
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
            case "/condicional.xhtml": {
                Moeda moeda = getMoedaPadrao();
                getCotacaoEmMoedaPadrao(moeda);
                getOperacaoDeCondicional();
                break;
            }
            case "/relatorioDeBalancoFisico.xhtml": {
                getMoedaPadrao();
                getContaDeEstoque();
                break;
            }
            case "/ajusteDeEstoque.xhtml": {
                getMoedaPadrao();
                getOperacoes();
                getContaDeEstoque();
                break;
            }
            case "/item.xhtml": {
                getContaDeEstoque();
                break;
            }
            case "/despesaProvisionada.xhtml": {
                Moeda moeda = getMoedaPadrao();
                getCotacaoEmMoedaPadrao(moeda);
                break;
            }
            case "/receitaProvisionada.xhtml": {
                Moeda moeda = getMoedaPadrao();
                getCotacaoEmMoedaPadrao(moeda);
                break;
            }
            case "/faturaLegada.xhtml": {
                Moeda moeda = getMoedaPadrao();
                getCotacaoEmMoedaPadrao(moeda);
                break;
            }
            case "/faturaEmitida.xhtml": {
                Moeda moeda = getMoedaPadrao();
                getCotacaoEmMoedaPadrao(moeda);
                break;
            }
            case "/faturaRecebida.xhtml": {
                Moeda moeda = getMoedaPadrao();
                getCotacaoEmMoedaPadrao(moeda);
                break;
            }
            default:
                break;
        }
        return pendencias;
    }

    private ContaDeEstoque getContaDeEstoque() {
        DadosNecessariosBV bv = new DadosNecessariosBV(b.getLabel("Configuracoes"), "/configuracao.xhtml");
        try {
            if (configuracaoEstoque.getContaDeEstoqueEmpresa() == null) {
                throw new NullPointerException();
            }
            return configuracaoEstoque.getContaDeEstoqueEmpresa();
        } catch (NullPointerException npe) {
            bv.getLista().add(b.getMessage("conta_de_estoque_empresa_not_null"));
            pendencias.add(bv);
            return null;
        }
    }

    private Moeda getMoedaPadrao() {
        DadosNecessariosBV bv = new DadosNecessariosBV(b.getLabel("Configuracoes"), "/configuracao.xhtml");
        try {
            if (configuracao.getMoedaPadrao() == null) {
                throw new NullPointerException();
            }
            return configuracao.getMoedaPadrao();
        } catch (NullPointerException ex) {
            bv.getLista().add(b.getMessage("moeda_padrao_not_null"));
            pendencias.add(bv);
            return null;
        }
    }

    private FormaDeRecebimento getFormaRecebimentoDevolucaoPadraoEmpresa() {
        DadosNecessariosBV bv = new DadosNecessariosBV(b.getLabel("Configuracoes"), "/configuracao.xhtml");
        try {

            if (configuracaoVenda.getFormaDeRecebimentoDevolucaoEmpresa() == null) {
                throw new NullPointerException();
            }
            return configuracaoVenda.getFormaDeRecebimentoDevolucaoEmpresa();
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
                Cotacao cotacao = new CotacaoDAO().porMoeda(moeda).porCotacaoEmpresa().naMaiorEmissao(new Date()).resultado();
                if (cotacao == null) {
                    throw new FDadoInvalidoException("");
                }
                return cotacao;
            } else {
                throw new FDadoInvalidoException("");
            }
        } catch (DadoInvalidoException ex) {
            bv.getLista().add(b.getMessage("Cotacao_Dia_Deve_Ser_Cadastrada"));
            pendencias.add(bv);
            return null;
        }
    }

    private Operacao getOperacaoDeComanda() {
        DadosNecessariosBV bv = new DadosNecessariosBV(b.getLabel("Configuracoes"), "/configuracao.xhtml");
        try {
            if (configuracaoVenda.getOperacaoDeComanda() == null) {
                throw new NullPointerException();
            }
            return configuracaoVenda.getOperacaoDeComanda();
        } catch (NullPointerException npe) {
            bv.getLista().add(b.getMessage("Operacao_Comanda_Not_Null"));
            pendencias.add(bv);
            return null;
        }
    }

    private Operacao getOperacaoDeCondicional() {
        DadosNecessariosBV bv = new DadosNecessariosBV(b.getLabel("Configuracoes"), "/configuracao.xhtml");
        try {
            if (configuracaoVenda.getOperacaoDeCondicional() == null) {
                throw new NullPointerException();
            }
            return configuracaoVenda.getOperacaoDeCondicional();
        } catch (NullPointerException npe) {
            bv.getLista().add(b.getMessage("Operacao_Condicional_Not_Null"));
            pendencias.add(bv);
            return null;
        }
    }

    private List<Operacao> getOperacoes() {
        DadosNecessariosBV bv = new DadosNecessariosBV(b.getLabel("Operacao"), "/operacoes.xhtml");
        try {
            OperacaoService operacaoService = new OperacaoService();
            List<Operacao> operacoes = operacaoService.buscar();
            if (operacoes.isEmpty()) {
                throw new NullPointerException();
            }
            return operacoes;
        } catch (NullPointerException npe) {
            bv.getLista().add(b.getMessage("operacao_not_null"));
            pendencias.add(bv);
            return null;
        }
    }

    private TipoReceita getReceitaDeJuros() {
        DadosNecessariosBV bv = new DadosNecessariosBV(b.getLabel("Configuracoes"), "/configuracao.xhtml");
        try {
            if (configuracaoContabil.getReceitaDeJuros() == null) {
                throw new NullPointerException();
            }
            return configuracaoContabil.getReceitaDeJuros();
        } catch (NullPointerException npe) {
            bv.getLista().add(b.getMessage("Receita_Juros_not_null"));
            pendencias.add(bv);
            return null;
        }
    }

    private TipoReceita getReceitaDeMultas() {
        DadosNecessariosBV bv = new DadosNecessariosBV(b.getLabel("Configuracoes"), "/configuracao.xhtml");
        try {
            if (configuracaoContabil.getReceitaDeMultas() == null) {
                throw new NullPointerException();
            }
            return configuracaoContabil.getReceitaDeMultas();
        } catch (NullPointerException npe) {
            bv.getLista().add(b.getMessage("Receita_Multas_not_null"));
            pendencias.add(bv);
            return null;
        }
    }

    private TipoReceita getReceitaDeDescontosObtidos() {
        DadosNecessariosBV bv = new DadosNecessariosBV(b.getLabel("Configuracoes"), "/configuracao.xhtml");
        try {
            if (configuracaoContabil.getReceitaDeDescontosObtidos() == null) {
                throw new NullPointerException();
            }
            return configuracaoContabil.getReceitaDeDescontosObtidos();
        } catch (NullPointerException npe) {
            bv.getLista().add(b.getMessage("Receita_Descontos_Obtidos_not_null"));
            pendencias.add(bv);
            return null;
        }
    }

    private TipoReceita getReceitaDeVariacaoCambial() {
        DadosNecessariosBV bv = new DadosNecessariosBV(b.getLabel("Configuracoes"), "/configuracao.xhtml");
        try {
            if (configuracaoContabil.getReceitaDeVariacaoCambial() == null) {
                throw new NullPointerException();
            }
            return configuracaoContabil.getReceitaDeVariacaoCambial();
        } catch (NullPointerException npe) {
            bv.getLista().add(b.getMessage("Receita_Variacao_Cambial_not_null"));
            pendencias.add(bv);
            return null;
        }
    }

    private TipoDespesa getDespesaDeDescontosConcedidos() {
        DadosNecessariosBV bv = new DadosNecessariosBV(b.getLabel("Configuracoes"), "/configuracao.xhtml");
        try {
            if (configuracaoContabil.getDespesaDeDescontosConcedidos() == null) {
                throw new NullPointerException();
            }
            return configuracaoContabil.getDespesaDeDescontosConcedidos();
        } catch (NullPointerException npe) {
            bv.getLista().add(b.getMessage("Despesa_Descontos_Obtidos_not_null"));
            pendencias.add(bv);
            return null;
        }
    }

    private TipoDespesa getDespesaDeJuros() {
        DadosNecessariosBV bv = new DadosNecessariosBV(b.getLabel("Configuracoes"), "/configuracao.xhtml");
        try {
            if (configuracaoContabil.getDespesaDeJuros() == null) {
                throw new NullPointerException();
            }
            return configuracaoContabil.getDespesaDeJuros();
        } catch (NullPointerException npe) {
            bv.getLista().add(b.getMessage("Despesa_Juros_not_null"));
            pendencias.add(bv);
            return null;
        }
    }

    private TipoDespesa getDespesaDeMultas() {
        DadosNecessariosBV bv = new DadosNecessariosBV(b.getLabel("Configuracoes"), "/configuracao.xhtml");
        try {
            if (configuracaoContabil.getDespesaDeMultas() == null) {
                throw new NullPointerException();
            }
            return configuracaoContabil.getDespesaDeMultas();
        } catch (NullPointerException npe) {
            bv.getLista().add(b.getMessage("Despesa_Multas_not_null"));
            pendencias.add(bv);
            return null;
        }
    }

    private TipoDespesa getDespesaDeVariacaoCambial() {
        DadosNecessariosBV bv = new DadosNecessariosBV(b.getLabel("Configuracoes"), "/configuracao.xhtml");
        try {
            if (configuracaoContabil.getDespesaDeVariacaoCambial() == null) {
                throw new NullPointerException();
            }
            return configuracaoContabil.getDespesaDeVariacaoCambial();
        } catch (NullPointerException npe) {
            bv.getLista().add(b.getMessage("Despesa_Variacao_Cambial_not_null"));
            pendencias.add(bv);
            return null;
        }
    }

    private Caixa getCaixa() {
        DadosNecessariosBV bv = new DadosNecessariosBV(b.getLabel("Caixa"), "/caixa.xhtml");
        try {
            Object object = SessionUtil.getObject("caixa", FacesContext.getCurrentInstance());
            if (object == null) {
                throw new NullPointerException();
            } else {
                return (Caixa) object;
            }
        } catch (DadoInvalidoException | NullPointerException ex) {
            bv.getLista().add(b.getMessage("Caixa_Deve_Estar_Logado"));
            pendencias.add(bv);
            return null;
        }
    }
}
