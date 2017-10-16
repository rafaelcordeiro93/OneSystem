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
import javax.enterprise.inject.Any;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

@Any
@Entity
@SequenceGenerator(initialValue = 1, allocationSize = 1, sequenceName = "SEQ_CONFIGURACAOVENDA",
        name = "SEQ_CONFIGURACAOVENDA")
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
        this.operacaoDeCondicional = operacaoDeCondicional;
        this.operacaoDeDevolucaoCondicional = operacaoDeDevolucaoCondicional;
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
