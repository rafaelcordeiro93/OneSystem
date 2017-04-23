/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.GrupoDePrivilegio;
import br.com.onesystem.domain.Janela;
import br.com.onesystem.domain.Privilegio;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.war.builder.GrupoDePrivilegioBV;
import br.com.onesystem.war.service.GrupoPrivilegioService;
import br.com.onesystem.war.service.JanelaService;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.inject.Named;
import org.hibernate.exception.ConstraintViolationException;

/**
 *
 * @author Rafael
 */
@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class GrupoPrivilegioView implements Serializable {

    private boolean panel;
    private GrupoDePrivilegioBV grupoPrivilegioBV;
    private GrupoDePrivilegio grupoSelecionado;
    private List<GrupoDePrivilegio> grupoLista;
    private List<GrupoDePrivilegio> gruposFiltrados;

    @ManagedProperty("#{grupoPrivilegioService}")
    private GrupoPrivilegioService service;

    @ManagedProperty("#{janelaService}")
    private JanelaService serviceJanela;

    @PostConstruct
    public void init() {
        limparJanela();
        panel = false;
        grupoPrivilegioBV = new GrupoDePrivilegioBV();
        grupoLista = service.buscarGrupoDePrivilegio();
    }

    public void add() {
        try {
            GrupoDePrivilegio novoRegistro = grupoPrivilegioBV.construir();
            new AdicionaDAO<GrupoDePrivilegio>().adiciona(novoRegistro);
            geraPrivilegio(novoRegistro);
            grupoLista.add(novoRegistro);
            InfoMessage.print("¡GrupoDePrivilegio '" + novoRegistro.getNome() + "' agregado con éxito!");
            limparJanela();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void geraPrivilegio(GrupoDePrivilegio grupo) {
        List<Janela> janelas = serviceJanela.buscarJanelas();
        for (Janela j : janelas) {
            try {
                new AdicionaDAO<Privilegio>().adiciona(new Privilegio(null, j, false, false, false, false, grupo));
            } catch (DadoInvalidoException die) {
                die.print();
                break;                
            }
        }
    }

    public void update() {
        try {
            GrupoDePrivilegio grupoExistente = grupoPrivilegioBV.construirComId();
            if (grupoExistente.getId() != null) {
                new AtualizaDAO<GrupoDePrivilegio>().atualiza(grupoExistente);
                grupoLista.set(grupoLista.indexOf(grupoExistente),
                        grupoExistente);
                if (gruposFiltrados != null && gruposFiltrados.contains(grupoExistente)) {
                    gruposFiltrados.set(gruposFiltrados.indexOf(grupoExistente), grupoExistente);
                }
                InfoMessage.print("¡GrupoDePrivilegio '" + grupoExistente.getNome() + "' cambiado con éxito!");
                limparJanela();
            } else {
                throw new EDadoInvalidoException("!La grupo no se encontra registrada!");
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void delete() {
        try {
            if (grupoLista != null && grupoLista.contains(grupoSelecionado)) {
                new RemoveDAO<GrupoDePrivilegio>().remove(grupoSelecionado, grupoSelecionado.getId());
                grupoLista.remove(grupoSelecionado);
                if (gruposFiltrados != null && gruposFiltrados.contains(grupoSelecionado)) {
                    gruposFiltrados.remove(grupoSelecionado);
                }
                InfoMessage.print("GrupoDePrivilegio '" + this.grupoPrivilegioBV.getNome() + "' eliminada con éxito!");
                limparJanela();
            }
        } catch (DadoInvalidoException di) {
            di.print();
        } catch (ConstraintViolationException pe) {
            FatalMessage.print(pe.getMessage(), pe.getCause());
        }
    }

    public void limparJanela() {
        grupoPrivilegioBV = new GrupoDePrivilegioBV();
        grupoSelecionado = new GrupoDePrivilegio();
    }

    public void abrirEdicao() {
        limparJanela();
        panel = true;
    }

    public void abrirEdicaoComDados() {
        panel = true;
        grupoPrivilegioBV = new GrupoDePrivilegioBV(grupoSelecionado);
    }

    public void fecharEdicao() {
        panel = false;
    }

    public void desfazer() {
        if (grupoSelecionado != null) {
            grupoPrivilegioBV = new GrupoDePrivilegioBV(grupoSelecionado);
        }
    }

    public boolean isPanel() {
        return panel;
    }

    public void setPanel(boolean panel) {
        this.panel = panel;
    }

    public GrupoDePrivilegioBV getGrupoPrivilegioBV() {
        return grupoPrivilegioBV;
    }

    public void setGrupoPrivilegioBV(GrupoDePrivilegioBV grupoPrivilegioBV) {
        this.grupoPrivilegioBV = grupoPrivilegioBV;
    }

    public GrupoDePrivilegio getGrupoSelecionado() {
        return grupoSelecionado;
    }

    public void setGrupoSelecionado(GrupoDePrivilegio grupoSelecionado) {
        this.grupoSelecionado = grupoSelecionado;
    }

    public List<GrupoDePrivilegio> getGrupoLista() {
        return grupoLista;
    }

    public void setGrupoLista(List<GrupoDePrivilegio> grupoLista) {
        this.grupoLista = grupoLista;
    }

    public List<GrupoDePrivilegio> getGruposFiltrados() {
        return gruposFiltrados;
    }

    public void setGruposFiltrados(List<GrupoDePrivilegio> gruposFiltrados) {
        this.gruposFiltrados = gruposFiltrados;
    }

    public GrupoPrivilegioService getService() {
        return service;
    }

    public void setService(GrupoPrivilegioService service) {
        this.service = service;
    }

    public JanelaService getServiceJanela() {
        return serviceJanela;
    }

    public void setServiceJanela(JanelaService serviceJanela) {
        this.serviceJanela = serviceJanela;
    }
    
}
