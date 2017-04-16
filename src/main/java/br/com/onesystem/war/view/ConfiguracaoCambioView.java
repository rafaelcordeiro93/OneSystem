package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.domain.ConfiguracaoCambio;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.Despesa;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.ErrorMessage;
import br.com.onesystem.war.builder.ConfiguracaoCambioBV;
import br.com.onesystem.war.service.ConfiguracaoCambioService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.inject.Named;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class ConfiguracaoCambioView implements Serializable {

    private ConfiguracaoCambioBV configuracaoCambioBV;
    private ConfiguracaoCambio configuracaoCambio;
    private List<Pessoa> pessoasDivisaoLucro = new ArrayList<Pessoa>();
    private Pessoa pessoaDivisaoLucro;

    @ManagedProperty("#{configuracaoCambioService}")
    private ConfiguracaoCambioService service;

    @PostConstruct
    public void init() {
        try {
            configuracaoCambio = service.buscar();
            if (configuracaoCambio != null) {
                for (Pessoa pessoa : configuracaoCambio.getPessoaDivisaoLucro()) {
                    pessoasDivisaoLucro.add(pessoa);
                }
                configuracaoCambioBV = new ConfiguracaoCambioBV(configuracaoCambio);
            }
        } catch (EDadoInvalidoException ex) {
            configuracaoCambioBV = new ConfiguracaoCambioBV();
        }
    }

    public void selecionaPessoa(SelectEvent event) {
        Pessoa pessoa = (Pessoa) event.getObject();
        if (!configuracaoCambioBV.getPessoaDivisaoLucro().contains(pessoa)) {
            configuracaoCambioBV.getPessoaDivisaoLucro().add(pessoa);
        } else {
            ErrorMessage.print("Pessoa já selecionada.");
        }
    }

    public void selecionaPessoaCaixa(SelectEvent event) {
        Pessoa pessoa = (Pessoa) event.getObject();
        configuracaoCambioBV.setPessoaCaixa(pessoa);
    }

    public void selecionaContaCaixa(SelectEvent event) {
        Conta conta = (Conta) event.getObject();
        configuracaoCambioBV.setContaCaixa(conta);
    }

    public void deletePessoa() {
        if (pessoaDivisaoLucro != null) {
            configuracaoCambioBV.getPessoaDivisaoLucro().remove(pessoaDivisaoLucro);
            pessoaDivisaoLucro = null;
        }
    }

    public void update() {
        try {
            ConfiguracaoCambio conf = configuracaoCambioBV.construir();
            if (configuracaoCambioBV.getId() == null) {
                validarDados();
                new AdicionaDAO<ConfiguracaoCambio>().adiciona(conf);
                configuracaoCambioBV.setId(conf.getId());
                addCambio(conf);
                atualizarLista();
            } else {
                validarDados();
                deleteCambio();
                addCambio(conf);
                new AtualizaDAO<ConfiguracaoCambio>().atualiza(configuracaoCambioBV.construir());
                atualizarLista();
            }
            InfoMessage.print("Configurações de Câmbio gravadas com sucesso!");
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    private void validarDados() throws EDadoInvalidoException {
        if (configuracaoCambioBV.isAtivo()) {
            if ((configuracaoCambioBV.getDespesaDivisaoLucro() == null
                    || configuracaoCambioBV.getPessoaCaixa() == null
                    || configuracaoCambioBV.getContaCaixa() == null
                    || configuracaoCambioBV.getPessoaDivisaoLucro().isEmpty())) {
                throw new EDadoInvalidoException("A despesa a conta de caixa e as pessoas para divisão de lucro devem ser informadas.");
            }
            if (!configuracaoCambioBV.getPessoaDivisaoLucro().contains(configuracaoCambioBV.getPessoaCaixa())) {
                throw new EDadoInvalidoException("A pessoa de caixa deve estar selecionada na lista de pessoas de divisão de lucro.");
            }
        }
    }

    private void atualizarLista() {
        pessoasDivisaoLucro = new ArrayList<Pessoa>();
        for (Pessoa pessoa : configuracaoCambioBV.getPessoaDivisaoLucro()) {
            pessoasDivisaoLucro.add(pessoa);
        }
    }

    private void deleteCambio() throws DadoInvalidoException, ConstraintViolationException {
        for (Pessoa pessoa : pessoasDivisaoLucro) {
            if (!configuracaoCambioBV.getPessoaDivisaoLucro().contains(pessoa)) {
                pessoa.setConfiguracaoCambio(null);
                new AtualizaDAO<Pessoa>().atualiza(pessoa);
            }
        }
    }

    private void addCambio(ConfiguracaoCambio conf) throws ConstraintViolationException, DadoInvalidoException {
        for (Pessoa pessoa : configuracaoCambioBV.getPessoaDivisaoLucro()) {
            pessoa.setConfiguracaoCambio(conf);
            new AtualizaDAO<Pessoa>().atualiza(pessoa);
        }
    }

    public void selecionaDespesaDivisaoLucro(SelectEvent event) {
        Despesa moeda = (Despesa) event.getObject();
        configuracaoCambioBV.setDespesaDivisaoLucro(moeda);
    }

    public ConfiguracaoCambioBV getConfiguracaoCambioBV() {
        return configuracaoCambioBV;
    }

    public void setConfiguracaoCambioBV(ConfiguracaoCambioBV configuracaoCambioBV) {
        this.configuracaoCambioBV = configuracaoCambioBV;
    }

    public ConfiguracaoCambio getConfiguracaoCambio() {
        return configuracaoCambio;
    }

    public void setConfiguracaoCambio(ConfiguracaoCambio configuracaoCambio) {
        this.configuracaoCambio = configuracaoCambio;
    }

    public ConfiguracaoCambioService getService() {
        return service;
    }

    public void setService(ConfiguracaoCambioService service) {
        this.service = service;
    }

    public Pessoa getPessoaDivisaoLucro() {
        return pessoaDivisaoLucro;
    }

    public void setPessoaDivisaoLucro(Pessoa pessoaDivisaoLucro) {
        this.pessoaDivisaoLucro = pessoaDivisaoLucro;
    }

}
