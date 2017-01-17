/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.view;

import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.Cambio;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.Despesa;
import br.com.onesystem.domain.DespesaProvisionada;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.Receita;
import br.com.onesystem.domain.ReceitaProvisionada;
import br.com.onesystem.domain.Recepcao;
import br.com.onesystem.domain.Titulo;
import br.com.onesystem.domain.Transferencia;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.war.builder.BaixaBV;
import br.com.onesystem.war.service.BaixaService;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Rafael
 */
@ManagedBean
@ViewScoped
public class BaixaView implements Serializable {

    private boolean panel;
    private BaixaBV baixa;
    private Baixa baixaSelecionada;
    private List<Baixa> baixaLista;
    private List<Baixa> baixasFiltradas;
    private String statusButton = "RedButton";

    @ManagedProperty("#{baixaService}")
    private BaixaService service;

    @PostConstruct
    public void init() {
        limparJanela();
        panel = false;
        baixaLista = service.buscarBaixas();
    }

    public void update() {
        try {
            Baixa baixaExistente = baixa.construirComID();
            atualizaNoBanco(baixaExistente);
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    private void atualizaNoBanco(Baixa baixaExistente) throws ConstraintViolationException, DadoInvalidoException {
        if (baixaExistente.getId() != null) {
            new AtualizaDAO<Baixa>(Baixa.class).atualiza(baixaExistente);
            baixaLista.set(baixaLista.indexOf(baixaSelecionada),
                    baixaExistente);
            if (baixasFiltradas != null && baixasFiltradas.contains(baixaExistente)) {
                baixasFiltradas.set(baixasFiltradas.indexOf(baixaExistente), baixaExistente);
            }
            InfoMessage.print("¡Baixa '" + baixaExistente.getId() + "' cambiado con éxito!");
            limparJanela();
        } else {
            throw new EDadoInvalidoException("!La baixa no se encontra registrada!");
        }
    }

    public void fecharEdicao() {
        panel = false;
    }

    public void abrirEdicaoComDados() {
        panel = true;
        baixa = new BaixaBV(baixaSelecionada);
    }

    public void cancelar() {
        try {
            Baixa baixa = this.baixa.construirComID();
            baixa.cancelar();
            atualizaNoBanco(baixa);
            this.baixa.setCancelada(true);
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void atuStatusButton() {
        if (this.baixa.getUnidadeFinanciera().equals(OperacaoFinanceira.ENTRADA)) {
            statusButton = "GreenButton";
        } else {
            statusButton = "RedButton";
        }
        RequestContext.getCurrentInstance().update("conteudo:statusButton");
    }

    public void selecionaPessoa(SelectEvent event) {
        Pessoa pessoaSelecionada = (Pessoa) event.getObject();
        this.baixa.setPessoa(pessoaSelecionada);
    }

    public void selecionaDespesa(SelectEvent event) {
        Despesa despesaSelecionada = (Despesa) event.getObject();
        this.baixa.setDespesa(despesaSelecionada);
    }

    public void selecionaReceita(SelectEvent event) {
        Receita receitaSelecionada = (Receita) event.getObject();
        this.baixa.setReceita(receitaSelecionada);
    }

    public void selecionaCambio(SelectEvent event) {
        Cambio cambioSelecionado = (Cambio) event.getObject();
        this.baixa.setCambio(cambioSelecionado);
    }

    public void selecionaDespesaProvisionada(SelectEvent event) {
        DespesaProvisionada despesaSelecionada = (DespesaProvisionada) event.getObject();
        this.baixa.setDespesaProvisionada(despesaSelecionada);
    }

    public void selecionaReceitaProvisionada(SelectEvent event) {
        ReceitaProvisionada receitaSelecionada = (ReceitaProvisionada) event.getObject();
        this.baixa.setReceitaProvisionada(receitaSelecionada);
    }

    public void selecionaTitulo(SelectEvent event) {
        Titulo tituloSelecionado = (Titulo) event.getObject();
        this.baixa.setTitulo(tituloSelecionado);
    }

    public void selecionaRecepcao(SelectEvent event) {
        Recepcao recepcaoSelecionado = (Recepcao) event.getObject();
        this.baixa.setRecepcao(recepcaoSelecionado);
    }

    public void selecionaTransferencia(SelectEvent event) {
        Transferencia transferenciaSelecionada = (Transferencia) event.getObject();
        this.baixa.setTransferencia(transferenciaSelecionada);
    }

    public void selecionaConta(SelectEvent event) {
        Conta contaSelecionada = (Conta) event.getObject();
        this.baixa.setConta(contaSelecionada);
    }

    public void limparJanela() {
        baixa = new BaixaBV();
        baixaSelecionada = new Baixa();
    }

    public boolean isPanel() {
        return panel;
    }

    public void setPanel(boolean panel) {
        this.panel = panel;
    }

    public BaixaBV getBaixa() {
        return baixa;
    }

    public void setBaixa(BaixaBV baixa) {
        this.baixa = baixa;
    }

    public Baixa getBaixaSelecionada() {
        return baixaSelecionada;
    }

    public void setBaixaSelecionada(Baixa baixaSelecionada) {
        this.baixaSelecionada = baixaSelecionada;
    }

    public List<Baixa> getBaixaLista() {
        return baixaLista;
    }

    public void setBaixaLista(List<Baixa> baixaLista) {
        this.baixaLista = baixaLista;
    }

    public List<Baixa> getBaixasFiltradas() {
        return baixasFiltradas;
    }

    public void setBaixasFiltradas(List<Baixa> baixasFiltradas) {
        this.baixasFiltradas = baixasFiltradas;
    }

    public BaixaService getService() {
        return service;
    }

    public void setService(BaixaService service) {
        this.service = service;
    }

}
