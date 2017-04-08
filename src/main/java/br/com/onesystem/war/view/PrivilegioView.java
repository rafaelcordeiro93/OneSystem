/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.view;

import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.domain.GrupoDePrivilegio;
import br.com.onesystem.domain.Privilegio;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.RelatorioPrivilegioPorModulo;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.war.service.GrupoPrivilegioService;
import br.com.onesystem.war.service.PrivilegioService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Rafael
 */
@ViewScoped
@ManagedBean
public class PrivilegioView implements Serializable {

    private boolean panel;
    private GrupoDePrivilegio grupoSelecionado;
    private GrupoDePrivilegio outroGrupo;
    private List<GrupoDePrivilegio> grupoLista;
    private List<Privilegio> privilegioLista;
    private List<GrupoDePrivilegio> gruposFiltrados;

    @ManagedProperty("#{privilegioService}")
    private PrivilegioService service;

    @ManagedProperty("#{grupoPrivilegioService}")
    private GrupoPrivilegioService grupoService;

    @PostConstruct
    public void init() {
        limparJanela();
        panel = false;
        grupoSelecionado = new GrupoDePrivilegio();
        privilegioLista = new ArrayList<Privilegio>();
        grupoLista = grupoService.buscarGrupoDePrivilegio();
    }

    public void save() {
        try {
            for (Privilegio p : privilegioLista) {
                new AtualizaDAO<Privilegio>(Privilegio.class).atualiza(p);
            }
            InfoMessage.atualizado();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void permitir() {
        for (Privilegio p : privilegioLista) {
            p.setConsultar(true);
            p.setAdicionar(true);
            p.setRemover(true);
            p.setAlterar(true);
        }
    }

    public void negar() {
        for (Privilegio p : privilegioLista) {
            p.setConsultar(false);
            p.setAdicionar(false);
            p.setRemover(false);
            p.setAlterar(false);
        }
    }

    public void selecionaOutroGrupo(SelectEvent event) {
        outroGrupo = (GrupoDePrivilegio) event.getObject();
    }

    public void copiarDeOutroGrupo() {
        List<Privilegio> lista = service.buscarPrivilegioDoGrupo(outroGrupo);
        for (Privilegio p : privilegioLista) {
            for (Privilegio l : lista) {
                if (p.getJanela().getId().equals(l.getJanela().getId())) {
                    p.setAdicionar(l.isAdicionar());
                    p.setAlterar(l.isAlterar());
                    p.setRemover(l.isRemover());
                    p.setConsultar(l.isConsultar());
                    break;
                }
            }
        }
        Collections.sort(privilegioLista, new RelatorioPrivilegioPorModulo());
    }

    public void limparJanela() {
        privilegioLista = new ArrayList<Privilegio>();
    }

    public void abrirEdicao() {
        limparJanela();
        panel = true;
    }

    public void abrirEdicaoComDados() {
        panel = true;
        buscarDados();
    }

    private void buscarDados() {
        privilegioLista = service.buscarPrivilegioDoGrupo(grupoSelecionado);
    }

    public void fecharEdicao() {
        panel = false;
    }

    public boolean isPanel() {
        return panel;
    }

    public void setPanel(boolean panel) {
        this.panel = panel;
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

    public PrivilegioService getService() {
        return service;
    }

    public void setService(PrivilegioService service) {
        this.service = service;
    }

    public GrupoPrivilegioService getGrupoService() {
        return grupoService;
    }

    public void setGrupoService(GrupoPrivilegioService grupoService) {
        this.grupoService = grupoService;
    }

    public List<Privilegio> getPrivilegioLista() {
        Collections.sort(privilegioLista, new RelatorioPrivilegioPorModulo());
        return privilegioLista;
    }

    public void setPrivilegioLista(List<Privilegio> privilegioLista) {
        this.privilegioLista = privilegioLista;
    }

    public GrupoDePrivilegio getOutroGrupo() {
        return outroGrupo;
    }

    public void setOutroGrupo(GrupoDePrivilegio outroGrupo) {
        this.outroGrupo = outroGrupo;
    }
}
