package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.IVA;
import br.com.onesystem.util.ErrorMessage;
import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.war.builder.IVABV;
import br.com.onesystem.war.service.IVAService;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.hibernate.exception.ConstraintViolationException;

@ManagedBean
@ViewScoped
public class IVAView implements Serializable {

    private boolean panel;
    private IVABV iva;
    private IVA ivaSelecionada;
    private List<IVA> ivaLista;
    private List<IVA> ivasFiltradas;

    @ManagedProperty("#{ivaService}")
    private IVAService service;

    @PostConstruct
    public void init() {
        limparJanela();
        panel = false;
        ivaLista = service.buscarIVAs();
    }

    public void add() {
        try {
            IVA novoRegistro = iva.construir();
            if (!validaIVAExistente(novoRegistro)) {
                new AdicionaDAO<IVA>().adiciona(novoRegistro);
                ivaLista.add(novoRegistro);
                InfoMessage.print("¡Iva '" + novoRegistro.getNome() + "' agregado con éxito!");
                limparJanela();
            } else {
                throw new EDadoInvalidoException("¡Ya existe la ciudad!");
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void update() {
        try {
            IVA ivaExistente = iva.construirComID();
            if (ivaExistente.getId() != null) {
                if (!validaIVAExistente(ivaExistente)) {
                    new AtualizaDAO<IVA>(IVA.class).atualiza(ivaExistente);
                    ivaLista.set(ivaLista.indexOf(ivaExistente),
                            ivaExistente);
                    if (ivasFiltradas != null && ivasFiltradas.contains(ivaExistente)) {
                        ivasFiltradas.set(ivasFiltradas.indexOf(ivaExistente), ivaExistente);
                    }
                    InfoMessage.print("¡Iva '" + ivaExistente.getNome() + "' cambiado con éxito!");
                    limparJanela();
                } else {
                    throw new EDadoInvalidoException("¡Ya existe la ciudad!");
                }
            } else {
                throw new EDadoInvalidoException("!La ciudad no se encontra registrada!");
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void delete() {
        try {
            if (ivaLista != null && ivaLista.contains(ivaSelecionada)) {
                new RemoveDAO<IVA>(IVA.class).remove(ivaSelecionada, ivaSelecionada.getId());
                ivaLista.remove(ivaSelecionada);
                if (ivasFiltradas != null && ivasFiltradas.contains(ivaSelecionada)) {
                    ivasFiltradas.remove(ivaSelecionada);
                }
                InfoMessage.print("Iva '" + this.iva.getNome() + "' eliminada con éxito!");
                limparJanela();
            }
        } catch (DadoInvalidoException di) {
            di.print();
        } catch (ConstraintViolationException pe) {
            FatalMessage.print(pe.getMessage(), pe.getCause());
        }
    }

    private boolean validaIVAExistente(IVA novoRegistro) {
        for (IVA novaIVA : ivaLista) {
            if (novoRegistro.getNome().equals(novaIVA.getNome())
                    && novoRegistro.getIva().equals(novaIVA.getIva())) {
                return true;
            }
        }
        return false;
    }

    public void limparJanela() {
        iva = new IVABV();
        ivaSelecionada = new IVA();
    }

    public void abrirEdicao() {
        limparJanela();
        panel = true;
    }

    public void abrirEdicaoComDados() {
        panel = true;
        iva = new IVABV(ivaSelecionada);
    }

    public void fecharEdicao() {
        panel = false;
    }

    public void desfazer() {
        if (ivaSelecionada != null) {
            iva = new IVABV(ivaSelecionada);
        }
    }

    public IVABV getIVA() {
        return iva;
    }

    public void setIVA(IVABV iva) {
        this.iva = iva;
    }

    public IVA getIVASelecionada() {
        return ivaSelecionada;
    }

    public void setIVASelecionada(IVA ivaSelecionada) {
        this.ivaSelecionada = ivaSelecionada;
    }

    public List<IVA> getIVALista() {
        return ivaLista;
    }

    public void setIVALista(List<IVA> ivaLista) {
        this.ivaLista = ivaLista;
    }

    public List<IVA> getIVAsFiltradas() {
        return ivasFiltradas;
    }

    public void setIVAsFiltradas(List<IVA> ivasFiltradas) {
        this.ivasFiltradas = ivasFiltradas;
    }

    public boolean isPanel() {
        return panel;
    }

    public void setPanel(boolean panel) {
        this.panel = panel;
    }

    public IVAService getService() {
        return service;
    }

    public void setService(IVAService service) {
        this.service = service;
    }

}
