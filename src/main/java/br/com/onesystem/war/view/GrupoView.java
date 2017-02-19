package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.GrupoDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.Grupo;
import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.war.builder.GrupoBV;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.SelectEvent;

@ManagedBean
@ViewScoped
public class GrupoView extends BasicMBImpl<Grupo> implements Serializable {

    private GrupoBV grupo;
    private Grupo grupoSelecionada;

    @PostConstruct
    public void init() {
        limparJanela();
    }

    public void add() {
        try {
            Grupo novoRegistro = grupo.construir();
            if (validaGrupoExistente(novoRegistro)) {
                new AdicionaDAO<Grupo>().adiciona(novoRegistro);
                InfoMessage.adicionado();
                limparJanela();
            } else {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("registro_nao_encontrado"));
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void update() {
        try {
            if (grupoSelecionada != null) {
                Grupo grupoExistente = grupo.construirComID();
                if (validaGrupoExistente(grupoExistente)) {
                    new AtualizaDAO<Grupo>(Grupo.class).atualiza(grupoExistente);
                    InfoMessage.atualizado();
                    limparJanela();
                } else {
                    throw new EDadoInvalidoException(new BundleUtil().getMessage("registro_existe"));
                }
            } else {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("registro_nao_encontrado"));
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void delete() {
        try {
            if (grupoSelecionada != null) {
                new RemoveDAO<Grupo>(Grupo.class).remove(grupoSelecionada, grupoSelecionada.getId());
                InfoMessage.removido();
                limparJanela();
            }
        } catch (DadoInvalidoException di) {
            di.print();
        } catch (ConstraintViolationException pe) {
            FatalMessage.print(pe.getMessage(), pe.getCause());
        }
    }

    @Override
    public void selecionar(SelectEvent e) {
        Grupo g = (Grupo) e.getObject();
        grupo = new GrupoBV(g);
        grupoSelecionada = g;
    }

    @Override
    public void buscaPorId() {
        Long id = grupo.getId();
        if (id != null) {
            try {
                GrupoDAO dao = new GrupoDAO();
                Grupo c = dao.buscarGrupos().porId(id).resultado();
                grupoSelecionada = c;
                grupo = new GrupoBV(grupoSelecionada);
            } catch (DadoInvalidoException die) {
                limparJanela();
                grupo.setId(id);
                die.print();
            }
        }
    }

    private boolean validaGrupoExistente(Grupo novoRegistro) {
        List<Grupo> lista = new GrupoDAO().buscarGrupos().porNome(novoRegistro).listaDeResultados();
        return lista.isEmpty();
    }

    public void limparJanela() {
        grupo = new GrupoBV();
        grupoSelecionada = null;
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

}
