package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.BaixaDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.Recepcao;
import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.war.builder.RecepcaoBV;
import br.com.onesystem.war.service.RecepcaoService;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.inject.Named;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class RecepcaoView implements Serializable {

    private boolean panel;
    private RecepcaoBV recepcao;
    private Recepcao recepcaoSelecionada;
    private List<Recepcao> recepcaoLista;
    private List<Recepcao> recepcoesFiltradas;

    @ManagedProperty("#{recepcaoService}")
    private RecepcaoService service;

    @PostConstruct
    public void init() {
        limparJanela();
        panel = false;
       
    }

    public void add() {
        try {
            Recepcao novoRegistro = recepcao.construir();
            novoRegistro.gerarTitulo();
            novoRegistro.gerarBaixa();
            new AdicionaDAO<Recepcao>().adiciona(novoRegistro);
            recepcaoLista.add(novoRegistro);
            InfoMessage.print("¡Recepcao '" + novoRegistro.getId() + "' agregado con éxito!");
            limparJanela();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void update() {
        try {
            Recepcao recepcaoExistente = recepcao.construirComID();
            if (recepcaoExistente.getId() != null) {
                recepcaoExistente.getTitulo().setValor(recepcaoExistente.getValor());
                recepcaoExistente.getTitulo().setSaldo(recepcaoExistente.getValor());
                Baixa baixa = new BaixaDAO().eDeRecepcao(recepcaoExistente).eComTitulo().resultado();
                baixa.atualizaValor(recepcaoExistente.getValor());
//                baixa.atualizaTotal(recepcaoExistente.getValor());
                new AtualizaDAO<Recepcao>().atualiza(recepcaoExistente);
                new AtualizaDAO<Baixa>().atualiza(baixa);
                recepcaoLista.set(recepcaoLista.indexOf(recepcaoSelecionada),
                        recepcaoExistente);
                if (recepcoesFiltradas != null && recepcoesFiltradas.contains(recepcaoExistente)) {
                    recepcoesFiltradas.set(recepcoesFiltradas.indexOf(recepcaoSelecionada), recepcaoExistente);
                }
                InfoMessage.print("¡Recepcao '" + recepcaoExistente.getId() + "' cambiado con éxito!");
                limparJanela();
            } else {
                throw new EDadoInvalidoException("!La recepcao no se encontra registrada!");
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void delete() {
        try {
            if (recepcaoLista != null && recepcaoLista.contains(recepcaoSelecionada)) {
                new RemoveDAO<Recepcao>().remove(recepcaoSelecionada, recepcaoSelecionada.getId());
                recepcaoLista.remove(recepcaoSelecionada);
                if (recepcoesFiltradas != null && recepcoesFiltradas.contains(recepcaoSelecionada)) {
                    recepcoesFiltradas.remove(recepcaoSelecionada);
                }
                InfoMessage.print("Recepcao '" + this.recepcao.getId() + "' eliminada con éxito!");
                limparJanela();
            }
        } catch (DadoInvalidoException di) {
            di.print();
        } catch (ConstraintViolationException pe) {
            FatalMessage.print(pe.getMessage(), pe.getCause());
        }
    }

    public void limparJanela() {
        recepcao = new RecepcaoBV();
        recepcaoSelecionada = new Recepcao();
//        recepcaoLista = service.buscarRecepcoes();
    }

    public void selecionaPessoa(SelectEvent event) {
        Pessoa pessoa = (Pessoa) event.getObject();
        recepcao.setPessoa(pessoa);
    }

    public void selecionaConta(SelectEvent event) {
        Conta conta = (Conta) event.getObject();
        recepcao.setConta(conta);
    }

    public void abrirEdicao() {
        limparJanela();
        panel = true;
    }

    public void abrirEdicaoComDados() {
        panel = true;
        recepcao = new RecepcaoBV(recepcaoSelecionada);
    }

    public void fecharEdicao() {
        panel = false;
    }

    public void desfazer() {
        if (recepcaoSelecionada != null) {
            recepcao = new RecepcaoBV(recepcaoSelecionada);
        }
    }

    public RecepcaoBV getRecepcao() {
        return recepcao;
    }

    public void setRecepcao(RecepcaoBV recepcao) {
        this.recepcao = recepcao;
    }

    public Recepcao getRecepcaoSelecionada() {
        return recepcaoSelecionada;
    }

    public void setRecepcaoSelecionada(Recepcao recepcaoSelecionada) {
        this.recepcaoSelecionada = recepcaoSelecionada;
    }

    public List<Recepcao> getRecepcaoLista() {
        return recepcaoLista;
    }

    public void setRecepcaoLista(List<Recepcao> recepcaoLista) {
        this.recepcaoLista = recepcaoLista;
    }

    public List<Recepcao> getRecepcoesFiltradas() {
        return recepcoesFiltradas;
    }

    public void setRecepcoesFiltradas(List<Recepcao> recepcoesFiltradas) {
        this.recepcoesFiltradas = recepcoesFiltradas;
    }

    public boolean isPanel() {
        return panel;
    }

    public void setPanel(boolean panel) {
        this.panel = panel;
    }

    public RecepcaoService getService() {
        return service;
    }

    public void setService(RecepcaoService service) {
        this.service = service;
    }

}
