package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.GrupoFiscal;
import br.com.onesystem.domain.IVA;
import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.war.builder.GrupoFiscalBV;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.SelectEvent;

@ManagedBean
@ViewScoped
public class GrupoFiscalView implements Serializable {

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

    public void selecionaGrupoFiscal(SelectEvent event) {
        grupoFiscalSelecionada = (GrupoFiscal) event.getObject();
        grupoFiscal = new GrupoFiscalBV(grupoFiscalSelecionada);
    }

    public void selecionaIVA(SelectEvent event) {
        IVA iva = (IVA) event.getObject();
        grupoFiscal.setIva(iva);
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
