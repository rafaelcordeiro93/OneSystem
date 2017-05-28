package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.valueobjects.OperacaoFisica;
import br.com.onesystem.war.service.ConfiguracaoEstoqueService;
import br.com.onesystem.war.service.ConfiguracaoService;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

@Entity
@SequenceGenerator(initialValue = 1, allocationSize = 1, sequenceName = "SEQ_CONFIGURACAOVENDA",
        name = "SEQ_CONFIGURACAOVENDA")
@NamedQueries({
    @NamedQuery(name = "ConfiguracaoVenda.busca", query = "select v from ConfiguracaoVenda v")
})
public class ConfiguracaoVenda implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_CONFIGURACAOVENDA", strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull(message = "{forma_recebimento_devolucao_empresa_not_null}")
    @OneToOne
    private FormaDeRecebimento formaDeRecebimentoDevolucaoEmpresa;
    @OneToOne
    private Operacao operacaoDeComanda;
    @OneToOne
    private Operacao operacaoDeCondicional;
    @OneToOne
    private Operacao operacaoDeDevolucaoCondicional;

    private boolean imprimeComanda;

    public ConfiguracaoVenda() {
    }

    public ConfiguracaoVenda(Long id, FormaDeRecebimento formaDeRecebimentoDevolucaoEmpresa, boolean imprimeComanda,
            Operacao operacaoDeComanda, Operacao operacaoDeCondicional, Operacao operacaoDeDevolucaoCondicional) throws DadoInvalidoException {
        this.id = id;
        this.formaDeRecebimentoDevolucaoEmpresa = formaDeRecebimentoDevolucaoEmpresa;
        this.imprimeComanda = imprimeComanda;
        this.operacaoDeComanda = operacaoDeComanda;
        if (operacaoDeCondicional != null && operacaoDeCondicional.getOperacaoDeEstoque() != null
                && buscaOperacaoDeEstoqueDa(operacaoDeCondicional).getOperacaoFisica().equals(OperacaoFisica.SAIDA)) {
            this.operacaoDeCondicional = operacaoDeCondicional;
            if (!operacaoDeCondicional.getOperacaoFinanceira().equals(OperacaoFinanceira.SEM_ALTERACAO)) {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("Operacao_Financeira_Condicional_Deve_Ser_Sem_Alteracao"));
            }
        } else if (operacaoDeCondicional != null && operacaoDeCondicional.getOperacaoDeEstoque() != null
                && !buscaOperacaoDeEstoqueDa(operacaoDeCondicional).getOperacaoFisica().equals(OperacaoFisica.SAIDA)) {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("Operacao_De_Estoque_Condicional_Deve_Ser_Saida"));
        }
        if (operacaoDeDevolucaoCondicional != null && operacaoDeDevolucaoCondicional.getOperacaoDeEstoque() != null
                && buscaOperacaoDeEstoqueDa(operacaoDeDevolucaoCondicional).getOperacaoFisica().equals(OperacaoFisica.ENTRADA)) {
            this.operacaoDeDevolucaoCondicional = operacaoDeDevolucaoCondicional;
            if (!operacaoDeDevolucaoCondicional.getOperacaoFinanceira().equals(OperacaoFinanceira.SEM_ALTERACAO)) {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("Operacao_Financeira_Devolucao_Condicional_Deve_Ser_Sem_Alteracao"));
            }
        } else if (operacaoDeDevolucaoCondicional != null && operacaoDeDevolucaoCondicional.getOperacaoDeEstoque() != null
                && !buscaOperacaoDeEstoqueDa(operacaoDeDevolucaoCondicional).getOperacaoFisica().equals(OperacaoFisica.ENTRADA)) {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("Operacao_De_Estoque_Devolucao_Condicional_Deve_Ser_Entrada"));
        }
        ehValido();
    }

    private OperacaoDeEstoque buscaOperacaoDeEstoqueDa(Operacao operacao) throws DadoInvalidoException {
        ConfiguracaoEstoque c = new ConfiguracaoEstoqueService().buscar();
        OperacaoDeEstoque operacaoDeEstoque = operacao.getOperacaoDeEstoque().stream().filter(o -> o.getContaDeEstoque().equals(c.getContaDeEstoqueEmpresa())).findFirst().orElseThrow(() -> new EDadoInvalidoException(new BundleUtil().getMessage("Operacao_De_Estoque_Condicional_Not_Null")));
        return operacaoDeEstoque;
    }

    private void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("formaDeRecebimentoDevolucaoEmpresa");
        new ValidadorDeCampos<>().valida(this, campos);
    }

    public Long getId() {
        return id;
    }

    public FormaDeRecebimento getFormaDeRecebimentoDevolucaoEmpresa() {
        return formaDeRecebimentoDevolucaoEmpresa;
    }

    public boolean isImprimeComanda() {
        return imprimeComanda;
    }

    public Operacao getOperacaoDeDevolucaoCondicional() {
        return operacaoDeDevolucaoCondicional;
    }

    public Operacao getOperacaoDeCondicional() {
        return operacaoDeCondicional;
    }

    public Operacao getOperacaoDeComanda() {
        return operacaoDeComanda;
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof ConfiguracaoVenda)) {
            return false;
        }
        ConfiguracaoVenda outro = (ConfiguracaoVenda) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

    @Override
    public String toString() {
        return "ConfiguracaoEstoque{" + "id=" + id + ", formaDeRecebimentoDevolucaoEmpresa=" + formaDeRecebimentoDevolucaoEmpresa + '}';
    }

}
