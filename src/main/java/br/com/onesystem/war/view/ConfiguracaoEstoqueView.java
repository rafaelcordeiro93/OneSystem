package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.domain.ConfiguracaoEstoque;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.ContaDeEstoque;
import br.com.onesystem.domain.ListaDePreco;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.war.builder.ConfiguracaoEstoqueBV;
import br.com.onesystem.war.service.ConfiguracaoEstoqueService;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.SelectEvent;

@ManagedBean
@ViewScoped
public class ConfiguracaoEstoqueView extends BasicMBImpl<ConfiguracaoEstoque> implements Serializable {
    
    private ConfiguracaoEstoqueBV configuracaoEstoqueBV;
    private ConfiguracaoEstoque configuracao;
    
    @ManagedProperty("#{configuracaoEstoqueService}")
    private ConfiguracaoEstoqueService service;
    
    @PostConstruct
    public void init() {
        configuracao = service.buscar();
        if (configuracao == null) {
            configuracaoEstoqueBV = new ConfiguracaoEstoqueBV();
        } else {
            configuracaoEstoqueBV = new ConfiguracaoEstoqueBV(configuracao);
        }
    }
    
    public void update() {
        try {
            ConfiguracaoEstoque conf = configuracaoEstoqueBV.construir();
            if (configuracao == null) {
                new AdicionaDAO<ConfiguracaoEstoque>().adiciona(conf);
                configuracao = conf;
            } else {
                new AtualizaDAO<ConfiguracaoEstoque>(ConfiguracaoEstoque.class).atualiza(conf);
            }
            InfoMessage.print("Configurações gravadas com sucesso!");
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }
    
    @Override
    public void buscaPorId() {
    }
    
    @Override
    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        if (obj instanceof ContaDeEstoque) {
            ContaDeEstoque conta = (ContaDeEstoque) obj;
            configuracaoEstoqueBV.setContaDeEstoqueEmpresa(conta);
        } else if (obj instanceof ListaDePreco) {
            ListaDePreco lp = (ListaDePreco) obj;
            configuracaoEstoqueBV.setListaDePreco(lp);
        }
    }
    
    public void removeConta() {
        configuracaoEstoqueBV.setContaDeEstoqueEmpresa(null);
    }
    
    public void removeListaDePreco() {
        configuracaoEstoqueBV.setListaDePreco(null);
    }
    
    public ConfiguracaoEstoqueBV getConfiguracaoBV() {
        return configuracaoEstoqueBV;
    }
    
    public void setConfiguracaoBV(ConfiguracaoEstoqueBV configuracaoBV) {
        this.configuracaoEstoqueBV = configuracaoBV;
    }
    
    public ConfiguracaoEstoque getConfiguracao() {
        return configuracao;
    }
    
    public void setConfiguracao(ConfiguracaoEstoque configuracao) {
        this.configuracao = configuracao;
    }
    
    public ConfiguracaoEstoqueService getService() {
        return service;
    }
    
    public void setService(ConfiguracaoEstoqueService service) {
        this.service = service;
    }
    
}
