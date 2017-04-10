/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.GrupoDePrivilegioDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.GrupoDePrivilegio;
import br.com.onesystem.domain.Janela;
import br.com.onesystem.domain.Privilegio;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.war.builder.GrupoDePrivilegioBV;
import br.com.onesystem.war.service.JanelaService;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Rafael
 */
@ViewScoped
@ManagedBean
public class GrupoPrivilegioView extends BasicMBImpl<GrupoDePrivilegio> implements Serializable {

    private GrupoDePrivilegioBV grupoPrivilegio;
    private GrupoDePrivilegio grupoSelecionado;

    @ManagedProperty("#{janelaService}")
    private JanelaService janelaService;

    @PostConstruct
    public void init() {
        limparJanela();
    }

    public void add() {
        try {
            GrupoDePrivilegio novoRegistro = grupoPrivilegio.construir();
            if (validaGrupoExistente(novoRegistro)) {
                new AdicionaDAO<GrupoDePrivilegio>().adiciona(novoRegistro);
                geraJanelasdoPrivilegio(novoRegistro);
                InfoMessage.adicionado();
                limparJanela();
            } else {
                throw new EDadoInvalidoException("!La grupo ja se encontra registrada!");
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void geraJanelasdoPrivilegio(GrupoDePrivilegio grupo) {
        List<Janela> janelas = janelaService.buscarJanelas();
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
            GrupoDePrivilegio grupoExistente = grupoPrivilegio.construirComId();
            if (grupoExistente.getId() != null) {
                if (validaGrupoExistente(grupoExistente)) {
                    new AtualizaDAO<GrupoDePrivilegio>(GrupoDePrivilegio.class).atualiza(grupoExistente);
                    InfoMessage.atualizado();
                    limparJanela();
                } else {
                    throw new EDadoInvalidoException("!La grupo no se encontra registrada!");
                }
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void delete() {
        grupoSelecionado = grupoPrivilegio.construirComId();
        try {
            if (grupoSelecionado != null) {
                new RemoveDAO<GrupoDePrivilegio>(GrupoDePrivilegio.class).remove(grupoSelecionado, grupoSelecionado.getId());
                InfoMessage.removido();
                limparJanela();
            }
        } catch (DadoInvalidoException di) {
            di.print();
        } catch (ConstraintViolationException pe) {
            FatalMessage.print(pe.getMessage(), pe.getCause());
        }
    }

    private boolean validaGrupoExistente(GrupoDePrivilegio novoRegistro) {
        List<GrupoDePrivilegio> lista = new GrupoDePrivilegioDAO().buscarGrupos().porNome(novoRegistro).listaDeResultados();
        return lista.isEmpty();
    }

    @Override
    public void selecionar(SelectEvent e) {
        if (grupoPrivilegio == null) {
            limparJanela();
        }
        Object obj = e.getObject();
        if (obj instanceof GrupoDePrivilegio) {
            GrupoDePrivilegio c = (GrupoDePrivilegio) e.getObject();
            grupoPrivilegio = new GrupoDePrivilegioBV(c);
            grupoSelecionado = c;
        }
    }

    @Override
    public void buscaPorId() {
        Long id = grupoPrivilegio.getId();
        if (id != null) {
            try {
                GrupoDePrivilegioDAO dao = new GrupoDePrivilegioDAO();
                GrupoDePrivilegio c = dao.buscarGrupos().porId(id).resultado();
                grupoPrivilegio = new GrupoDePrivilegioBV(c);
                grupoSelecionado = c;
            } catch (DadoInvalidoException die) {
                limparJanela();
                grupoPrivilegio.setId(id);
                die.print();
            }
        }
    }

    public void limparJanela() {
        grupoPrivilegio = new GrupoDePrivilegioBV();
        grupoSelecionado = null;
    }

    public void desfazer() {
        if (grupoSelecionado != null) {
            grupoPrivilegio = new GrupoDePrivilegioBV(grupoSelecionado);
        }
    }

    public GrupoDePrivilegioBV getGrupoPrivilegio() {
        return grupoPrivilegio;
    }

    public void setGrupoPrivilegio(GrupoDePrivilegioBV grupoPrivilegio) {
        this.grupoPrivilegio = grupoPrivilegio;
    }

    public GrupoDePrivilegio getGrupoSelecionado() {
        return grupoSelecionado;
    }

    public void setGrupoSelecionado(GrupoDePrivilegio grupoSelecionado) {
        this.grupoSelecionado = grupoSelecionado;
    }

    public JanelaService getJanelaService() {
        return janelaService;
    }

    public void setJanelaService(JanelaService janelaService) {
        this.janelaService = janelaService;
    }

}
