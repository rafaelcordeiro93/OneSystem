package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
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

    private boolean imprimeComanda;

    public ConfiguracaoVenda() {
    }

    public ConfiguracaoVenda(Long id, FormaDeRecebimento formaDeRecebimentoDevolucaoEmpresa, boolean imprimeComanda,
            Operacao operacaoDeComanda) throws DadoInvalidoException {
        this.id = id;
        this.formaDeRecebimentoDevolucaoEmpresa = formaDeRecebimentoDevolucaoEmpresa;
        this.imprimeComanda = imprimeComanda;
        this.operacaoDeComanda = operacaoDeComanda;
        ehValido();
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
