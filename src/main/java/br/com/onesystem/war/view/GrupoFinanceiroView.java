package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.GrupoFinanceiro;
import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.war.builder.GrupoFinanceiroBV;
import br.com.onesystem.war.service.GrupoFinanceiroService;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.exception.impl.IDadoInvalidoException;
import br.com.onesystem.valueobjects.ClassificacaoFinanceira;
import br.com.onesystem.valueobjects.NaturezaFinanceira;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.hibernate.exception.ConstraintViolationException;

@ManagedBean
@ViewScoped
public class GrupoFinanceiroView implements Serializable {

    private boolean panel;
    private GrupoFinanceiroBV grupoFinanceiro;
    private GrupoFinanceiro grupoFinanceiroSelecionado;
    private List<GrupoFinanceiro> grupoFinanceiroLista;
    private List<GrupoFinanceiro> gruposFinanceirosFiltrados;

    @ManagedProperty("#{grupoFinanceiroService}")
    private GrupoFinanceiroService service;

    @PostConstruct
    public void init() {
        limparJanela();
        panel = false;
        grupoFinanceiroLista = service.buscarGruposFinanceiros();
    }

    public void add() {
        try {
            GrupoFinanceiro novoRegistro = grupoFinanceiro.construir();
            grupoFinanceiroExiste(false);
            new AdicionaDAO<GrupoFinanceiro>().adiciona(novoRegistro);
            grupoFinanceiroLista.add(novoRegistro);
            InfoMessage.print("¡GrupoFinanceiro '" + novoRegistro.getNome() + "' agregado con éxito!");
            limparJanela();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void update() {
        try {
            GrupoFinanceiro grupoFinanceiroExistente = grupoFinanceiro.construirComID();
            if (grupoFinanceiroExistente.getId() != null) {
                grupoFinanceiroExiste(true);
                new AtualizaDAO<GrupoFinanceiro>(GrupoFinanceiro.class).atualiza(grupoFinanceiroExistente);
                grupoFinanceiroLista.set(grupoFinanceiroLista.indexOf(grupoFinanceiroExistente),
                        grupoFinanceiroExistente);
                if (gruposFinanceirosFiltrados != null && gruposFinanceirosFiltrados.contains(grupoFinanceiroExistente)) {
                    gruposFinanceirosFiltrados.set(gruposFinanceirosFiltrados.indexOf(grupoFinanceiroExistente), grupoFinanceiroExistente);
                }
                InfoMessage.print("¡GrupoFinanceiro '" + grupoFinanceiroExistente.getNome() + "' cambiado con éxito!");
                limparJanela();
            } else {
                throw new EDadoInvalidoException("!La grupoFinanceiro no se encontra registrada!");
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void delete() {
        try {
            if (grupoFinanceiroLista != null && grupoFinanceiroLista.contains(grupoFinanceiroSelecionado)) {
                new RemoveDAO<GrupoFinanceiro>(GrupoFinanceiro.class).remove(grupoFinanceiroSelecionado, grupoFinanceiroSelecionado.getId());
                grupoFinanceiroLista.remove(grupoFinanceiroSelecionado);
                if (gruposFinanceirosFiltrados != null && gruposFinanceirosFiltrados.contains(grupoFinanceiroSelecionado)) {
                    gruposFinanceirosFiltrados.remove(grupoFinanceiroSelecionado);
                }
                InfoMessage.print("GrupoFinanceiro '" + this.grupoFinanceiro.getNome() + "' eliminada con éxito!");
                limparJanela();
            }
        } catch (DadoInvalidoException di) {
            di.print();
        } catch (ConstraintViolationException pe) {
            FatalMessage.print(pe.getMessage(), pe.getCause());
        }
    }

    public void grupoFinanceiroExiste(Boolean novoRegistro) throws DadoInvalidoException {
        String nome = grupoFinanceiro.getNome();
        try {
            if (nome != null && !nome.trim().equals("")) {
                if (novoRegistro) {
                    for (GrupoFinanceiro grupoFinanceiroDaLista : grupoFinanceiroLista) {
                        if (grupoFinanceiroDaLista.getNome().equals(nome)
                                && grupoFinanceiroDaLista.getId() != grupoFinanceiro.getId()
                                && grupoFinanceiroDaLista.getNaturezaFinanceira() == grupoFinanceiro.getNaturezaFinanceira()) {
                            throw new IDadoInvalidoException("¡Grupo Financiero ya existe!");
                        }
                    }
                } else {
                    for (GrupoFinanceiro grupoFinanceiroDaLista : grupoFinanceiroLista) {
                        if (grupoFinanceiroDaLista.getNome().equals(nome) && grupoFinanceiroDaLista.getNaturezaFinanceira() == grupoFinanceiro.getNaturezaFinanceira()) {
                            throw new IDadoInvalidoException("¡Grupo Financiero ya existe!");
                        }
                    }
                }
            }
        } catch (NullPointerException npe) {
        }
    }

    public List<NaturezaFinanceira> getNaturezasFinanceiras() {
        return Arrays.asList(NaturezaFinanceira.values());
    }
    
    public List<ClassificacaoFinanceira> getClassificacaoFinanceira() {
        return Arrays.asList(ClassificacaoFinanceira.values());
    }

    public void limparJanela() {
        grupoFinanceiro = new GrupoFinanceiroBV();
        grupoFinanceiroSelecionado = new GrupoFinanceiro();
    }

    public void abrirEdicao() {
        limparJanela();
        panel = true;
    }

    public void abrirEdicaoComDados() {
        panel = true;
        grupoFinanceiro = new GrupoFinanceiroBV(grupoFinanceiroSelecionado);
    }

    public void fecharEdicao() {
        panel = false;
    }

    public void desfazer() {
        if (grupoFinanceiroSelecionado != null) {
            grupoFinanceiro = new GrupoFinanceiroBV(grupoFinanceiroSelecionado);
        }
    }

    public GrupoFinanceiroBV getGrupoFinanceiro() {
        return grupoFinanceiro;
    }

    public void setGrupoFinanceiro(GrupoFinanceiroBV grupoFinanceiro) {
        this.grupoFinanceiro = grupoFinanceiro;
    }

    public GrupoFinanceiro getGrupoFinanceiroSelecionado() {
        return grupoFinanceiroSelecionado;
    }

    public void setGrupoFinanceiroSelecionado(GrupoFinanceiro grupoFinanceiroSelecionado) {
        this.grupoFinanceiroSelecionado = grupoFinanceiroSelecionado;
    }

    public List<GrupoFinanceiro> getGrupoFinanceiroLista() {
        return grupoFinanceiroLista;
    }

    public void setGrupoFinanceiroLista(List<GrupoFinanceiro> grupoFinanceiroLista) {
        this.grupoFinanceiroLista = grupoFinanceiroLista;
    }

    public List<GrupoFinanceiro> getGruposFinanceirosFiltrados() {
        return gruposFinanceirosFiltrados;
    }

    public void setGruposFinanceirosFiltrados(List<GrupoFinanceiro> gruposFinanceirosFiltrados) {
        this.gruposFinanceirosFiltrados = gruposFinanceirosFiltrados;
    }

    public boolean isPanel() {
        return panel;
    }

    public void setPanel(boolean panel) {
        this.panel = panel;
    }

    public GrupoFinanceiroService getService() {
        return service;
    }

    public void setService(GrupoFinanceiroService service) {
        this.service = service;
    }

}
