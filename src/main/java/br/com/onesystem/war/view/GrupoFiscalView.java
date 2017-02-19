package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.GrupoFiscalDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.GrupoFiscal;
import br.com.onesystem.domain.GrupoFiscal;
import br.com.onesystem.domain.IVA;
import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.war.builder.GrupoFiscalBV;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.war.builder.GrupoFiscalBV;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.SelectEvent;

@ManagedBean
@ViewScoped
public class GrupoFiscalView extends BasicMBImpl<GrupoFiscal> implements Serializable {

    private GrupoFiscalBV grupoFiscal;
    private GrupoFiscal grupoFiscalSelecionada;

    @PostConstruct
    public void init() {
        limparJanela();
    }

    public void add() {
        try {
            GrupoFiscal novoRegistro = grupoFiscal.construir();
            new AdicionaDAO<GrupoFiscal>().adiciona(novoRegistro);
            InfoMessage.adicionado();
            limparJanela();

        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void update() {
        try {
            if (grupoFiscalSelecionada != null) {
                GrupoFiscal grupoFiscalExistente = grupoFiscal.construirComID();
                new AtualizaDAO<GrupoFiscal>(GrupoFiscal.class).atualiza(grupoFiscalExistente);
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
            if (grupoFiscalSelecionada != null) {
                new RemoveDAO<GrupoFiscal>(GrupoFiscal.class).remove(grupoFiscalSelecionada, grupoFiscalSelecionada.getId());
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

    public void limparJanela() {
        grupoFiscal = new GrupoFiscalBV();
        grupoFiscalSelecionada = null;
    }

    @Override
    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        if (obj instanceof GrupoFiscal) {
            grupoFiscalSelecionada = (GrupoFiscal) event.getObject();
            grupoFiscal = new GrupoFiscalBV(grupoFiscalSelecionada);
        } else if (obj instanceof IVA) {
            IVA iva = (IVA) event.getObject();
            grupoFiscal.setIva(iva);
        }
    }

    @Override
    public void buscaPorId() {
        Long id = grupoFiscal.getId();
        if (id != null) {
            try {
                GrupoFiscalDAO dao = new GrupoFiscalDAO();
                GrupoFiscal c = dao.buscarGrupos().porId(id).resultado();
                grupoFiscalSelecionada = c;
                grupoFiscal = new GrupoFiscalBV(grupoFiscalSelecionada);
            } catch (DadoInvalidoException die) {
                limparJanela();
                grupoFiscal.setId(id);
                die.print();
            }
        }
    }

    public void desfazer() {
        if (grupoFiscalSelecionada != null) {
            grupoFiscal = new GrupoFiscalBV(grupoFiscalSelecionada);
        }
    }

    public GrupoFiscalBV getGrupoFiscal() {
        return grupoFiscal;
    }

    public void setGrupoFiscal(GrupoFiscalBV grupoFiscal) {
        this.grupoFiscal = grupoFiscal;
    }

    public GrupoFiscal getGrupoFiscalSelecionada() {
        return grupoFiscalSelecionada;
    }

    public void setGrupoFiscalSelecionada(GrupoFiscal grupoFiscalSelecionada) {
        this.grupoFiscalSelecionada = grupoFiscalSelecionada;
    }

}
