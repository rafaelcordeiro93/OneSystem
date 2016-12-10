package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.DepositoDAO;
import br.com.onesystem.dao.IvaDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.Deposito;
import br.com.onesystem.domain.IVA;
import br.com.onesystem.util.ErrorMessage;
import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.war.builder.IVABV;
import br.com.onesystem.war.service.IVAService;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.war.builder.DepositoBV;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.SelectEvent;

@ManagedBean
@ViewScoped
public class IVAView implements Serializable {

    private IVABV iva;
    private IVA ivaSelecionada;

    @PostConstruct
    public void init() {
        limparJanela();
    }

    public void add() {
        try {
            IVA novoRegistro = iva.construir();
            if (validaIVAExistente(novoRegistro)) {
                new AdicionaDAO<IVA>().adiciona(novoRegistro);
                InfoMessage.adicionado();
                limparJanela();
            } else {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("iva_ja_cadastrado"));
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void update() {
        try {
            IVA ivaExistente = iva.construirComID();
            if (ivaExistente.getId() != null) {
                if (validaIVAExistente(ivaExistente)) {
                    new AtualizaDAO<IVA>(IVA.class).atualiza(ivaExistente);
                    InfoMessage.atualizado();
                    limparJanela();
                } else {
                    throw new EDadoInvalidoException(new BundleUtil().getMessage("iva_ja_cadastrado"));
                }
            } else {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("iva_nao_cadastrado"));
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void delete() {
        try {
            if (ivaSelecionada != null) {
                new RemoveDAO<IVA>(IVA.class).remove(ivaSelecionada, ivaSelecionada.getId());

                InfoMessage.removido();
                limparJanela();
            }
        } catch (DadoInvalidoException di) {
            di.print();
        } catch (ConstraintViolationException pe) {
            FatalMessage.print(pe.getMessage(), pe.getCause());
        }
    }

    private boolean validaIVAExistente(IVA novoRegistro) {
        List<IVA> lista = new IvaDAO().buscarIVA().porNome(novoRegistro).listaDeResultados();
        return lista.isEmpty();
    }

        public void selecionaIVA(SelectEvent e) {
        IVA i = (IVA) e.getObject();
        iva = new IVABV(i);
        ivaSelecionada = i;
    }
    
    public void limparJanela() {
        iva = new IVABV();
        ivaSelecionada = null;
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
}
