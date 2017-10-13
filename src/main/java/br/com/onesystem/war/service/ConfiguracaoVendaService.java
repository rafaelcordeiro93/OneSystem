package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistrosNaMemoria;
import br.com.onesystem.dao.ConfiguracaoVendaDAO;
import br.com.onesystem.domain.ConfiguracaoEstoque;
import br.com.onesystem.domain.ConfiguracaoVenda;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.domain.OperacaoDeEstoque;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.valueobjects.OperacaoFisica;
import br.com.onesystem.war.view.selecao.SelecaoOperacaoView;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

public class ConfiguracaoVendaService implements Serializable {

    @Inject
    private ConfiguracaoVendaDAO dao;

    @Inject
    private ConfiguracaoEstoque configuracaoEstoque;

    public ConfiguracaoVendaService() {
    }

    @Produces
    public ConfiguracaoVenda buscar() {
        return dao.resultado();
    }

    private OperacaoDeEstoque buscaOperacaoDeEstoqueDa(Operacao operacao) throws DadoInvalidoException {
        OperacaoDeEstoque operacaoDeEstoque = operacao.getOperacaoDeEstoque().stream().filter(o -> o.getContaDeEstoque().equals(configuracaoEstoque.getContaDeEstoqueEmpresa())).findFirst().orElseThrow(() -> new EDadoInvalidoException(new BundleUtil().getMessage("Operacao_De_Estoque_Condicional_Not_Null")));
        return operacaoDeEstoque;
    }

    public void validaConfiguracao(ConfiguracaoVenda configuracaoVenda) throws DadoInvalidoException {
        if (configuracaoVenda.getOperacaoDeCondicional() != null && configuracaoVenda.getOperacaoDeCondicional().getOperacaoDeEstoque() != null
                && buscaOperacaoDeEstoqueDa(configuracaoVenda.getOperacaoDeCondicional()).getOperacaoFisica().equals(OperacaoFisica.SAIDA)) {
            if (!configuracaoVenda.getOperacaoDeCondicional().getOperacaoFinanceira().equals(OperacaoFinanceira.SEM_ALTERACAO)) {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("Operacao_Financeira_Condicional_Deve_Ser_Sem_Alteracao"));
            }
        } else if (configuracaoVenda.getOperacaoDeCondicional() != null && configuracaoVenda.getOperacaoDeCondicional().getOperacaoDeEstoque() != null
                && !buscaOperacaoDeEstoqueDa(configuracaoVenda.getOperacaoDeCondicional()).getOperacaoFisica().equals(OperacaoFisica.SAIDA)) {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("Operacao_De_Estoque_Condicional_Deve_Ser_Saida"));
        }
        if (configuracaoVenda.getOperacaoDeDevolucaoCondicional() != null && configuracaoVenda.getOperacaoDeDevolucaoCondicional().getOperacaoDeEstoque() != null
                && buscaOperacaoDeEstoqueDa(configuracaoVenda.getOperacaoDeDevolucaoCondicional()).getOperacaoFisica().equals(OperacaoFisica.ENTRADA)) {
            if (!configuracaoVenda.getOperacaoDeDevolucaoCondicional().getOperacaoFinanceira().equals(OperacaoFinanceira.SEM_ALTERACAO)) {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("Operacao_Financeira_Devolucao_Condicional_Deve_Ser_Sem_Alteracao"));
            }
        } else if (configuracaoVenda.getOperacaoDeDevolucaoCondicional() != null && configuracaoVenda.getOperacaoDeDevolucaoCondicional().getOperacaoDeEstoque() != null
                && !buscaOperacaoDeEstoqueDa(configuracaoVenda.getOperacaoDeDevolucaoCondicional()).getOperacaoFisica().equals(OperacaoFisica.ENTRADA)) {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("Operacao_De_Estoque_Devolucao_Condicional_Deve_Ser_Entrada"));
        }
        List<String> campos = Arrays.asList("formaDeRecebimentoDevolucaoEmpresa");
        new ValidadorDeCampos<>().valida(this, campos);
    }

}
