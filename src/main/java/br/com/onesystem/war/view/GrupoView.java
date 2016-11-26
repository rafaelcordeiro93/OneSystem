package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.Grupo;
import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.war.builder.GrupoBV;
import br.com.onesystem.war.service.GrupoService;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.hibernate.exception.ConstraintViolationException;

@ManagedBean
@ViewScoped
public class GrupoView implements Serializable {

    private boolean panel;
    private GrupoBV grupo;
    private Grupo grupoSelecionada;
    private List<Grupo> grupoLista;
    private List<Grupo> gruposFiltradas;

    @ManagedProperty("#{grupoService}")
    private GrupoService service;

    @PostConstruct
    public void init() {
        limparJanela();
        panel = false;
        grupoLista = service.buscarGrupos();
    }

    public void add() {
        try {
            Grupo novoRegistro = grupo.construir();
            if (!validaGrupoExistente(novoRegistro)) {
                new AdicionaDAO<Grupo>().adiciona(novoRegistro);
                grupoLista.add(novoRegistro);
                InfoMessage.print("¡Grupo '" + novoRegistro.getNome() + "' agregado con éxito!");
                limparJanela();
            } else {
                throw new EDadoInvalidoException("¡Ya existe la grupo!");
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void update() {
        try {
            Grupo grupoExistente = grupo.construirComID();
            if (grupoExistente.getId() != null) {
                if (!validaGrupoExistente(grupoExistente)) {
                    new AtualizaDAO<Grupo>(Grupo.class).atualiza(grupoExistente);
                    grupoLista.set(grupoLista.indexOf(grupoExistente),
                            grupoExistente);
                    if (gruposFiltradas != null && gruposFiltradas.contains(grupoExistente)) {
                        gruposFiltradas.set(gruposFiltradas.indexOf(grupoExistente), grupoExistente);
                    }
                    InfoMessage.print("¡Grupo '" + grupoExistente.getNome() + "' cambiado con éxito!");
                    limparJanela();
                } else {
                    throw new EDadoInvalidoException("¡Ya existe la grupo!");
                }
            } else {
                throw new EDadoInvalidoException("!La grupo no se encontra registrada!");
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void delete() {
        try {
            if (grupoLista != null && grupoLista.contains(grupoSelecionada)) {
                new RemoveDAO<Grupo>(Grupo.class).remove(grupoSelecionada, grupoSelecionada.getId());
                grupoLista.remove(grupoSelecionada);
                if (gruposFiltradas != null && gruposFiltradas.contains(grupoSelecionada)) {
                    gruposFiltradas.remove(grupoSelecionada);
                }
                InfoMessage.print("Grupo '" + this.grupo.getNome() + "' eliminada con éxito!");
                limparJanela();
            }
        } catch (DadoInvalidoException di) {
            di.print();
        } catch (ConstraintViolationException pe) {
            FatalMessage.print(pe.getMessage(), pe.getCause());
        }
    }

    private boolean validaGrupoExistente(Grupo novoRegistro) {
        for (Grupo novaGrupo : grupoLista) {
            if (novoRegistro.getNome().equals(novaGrupo.getNome())) {
                return true;
            }
        }
        return false;
    }

    public void limparJanela() {
        grupo = new GrupoBV();
        grupoSelecionada = new Grupo();
    }

    public void abrirEdicao() {
        limparJanela();
        panel = true;
    }

    public void abrirEdicaoComDados() {
        panel = true;
        grupo = new GrupoBV(grupoSelecionada);
    }

    public void fecharEdicao() {
        panel = false;
    }

    public void desfazer() {
        if (grupoSelecionada != null) {
            grupo = new GrupoBV(grupoSelecionada);
        }
    }

    public GrupoBV getGrupo() {
        return grupo;
    }

    public void setGrupo(GrupoBV grupo) {
        this.grupo = grupo;
    }

    public Grupo getGrupoSelecionada() {
        return grupoSelecionada;
    }

    public void setGrupoSelecionada(Grupo grupoSelecionada) {
        this.grupoSelecionada = grupoSelecionada;
    }

    public List<Grupo> getGrupoLista() {
        return grupoLista;
    }

    public void setGrupoLista(List<Grupo> grupoLista) {
        this.grupoLista = grupoLista;
    }

    public List<Grupo> getGruposFiltradas() {
        return gruposFiltradas;
    }

    public void setGruposFiltradas(List<Grupo> gruposFiltradas) {
        this.gruposFiltradas = gruposFiltradas;
    }

    public boolean isPanel() {
        return panel;
    }

    public void setPanel(boolean panel) {
        this.panel = panel;
    }

    public GrupoService getService() {
        return service;
    }

    public void setService(GrupoService service) {
        this.service = service;
    }

}
