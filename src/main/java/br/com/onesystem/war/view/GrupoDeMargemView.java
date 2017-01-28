package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.MargemDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.Margem;
import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.war.builder.GrupoDeMargemBV;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.SelectEvent;

@ManagedBean
@ViewScoped
public class GrupoDeMargemView extends BasicMBImpl<Margem> implements Serializable {

    private GrupoDeMargemBV grupo;
    private Margem grupoSelecionada;

    @PostConstruct
    public void init() {
        limparJanela();
    }

    public void add() {
        try {
            Margem novoRegistro = grupo.construir();
            new AdicionaDAO<Margem>().adiciona(novoRegistro);
            InfoMessage.adicionado();
            limparJanela();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void update() {
        try {
            if (grupoSelecionada != null) {
                Margem grupoExistente = grupo.construirComID();
                new AtualizaDAO<Margem>(Margem.class).atualiza(grupoExistente);
                InfoMessage.atualizado();
                limparJanela();
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
                new RemoveDAO<Margem>(Margem.class).remove(grupoSelecionada, grupoSelecionada.getId());
                InfoMessage.removido();
                limparJanela();
            } else {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("registro_nao_encontrado"));
            }
        } catch (DadoInvalidoException di) {
            di.print();
        } catch (ConstraintViolationException pe) {
            FatalMessage.print(pe.getMessage(), pe.getCause());
        }
    }

    @Override
    public void buscaPorId() {
        Long id = grupo.getId();
        if (id != null) {
            try {
                MargemDAO dao = new MargemDAO();
                Margem m = dao.buscarMargemW().porId(id).resultado();
                grupoSelecionada = m;
                grupo = new GrupoDeMargemBV(grupoSelecionada);
            } catch (DadoInvalidoException die) {
                limparJanela();
                grupo.setId(id);
                die.print();
            }
        }
    }

    @Override
    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        if (obj instanceof Margem) {
            grupoSelecionada = (Margem) obj;
            grupo = new GrupoDeMargemBV(grupoSelecionada);
        }
    }

    public void limparJanela() {
        grupo = new GrupoDeMargemBV();
        grupoSelecionada = null;
    }

    public void desfazer() {
        if (grupoSelecionada != null) {
            grupo = new GrupoDeMargemBV(grupoSelecionada);
        }
    }

    public GrupoDeMargemBV getGrupoDeMargem() {
        return grupo;
    }

    public void setGrupoDeMargem(GrupoDeMargemBV grupo) {
        this.grupo = grupo;
    }

    public Margem getGrupoDeMargemSelecionada() {
        return grupoSelecionada;
    }

    public void setGrupoDeMargemSelecionada(Margem grupoSelecionada) {
        this.grupoSelecionada = grupoSelecionada;
    }

}
