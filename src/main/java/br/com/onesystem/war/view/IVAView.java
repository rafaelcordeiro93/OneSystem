package br.com.onesystem.war.view;

import br.com.onesystem.dao.IvaDAO;
import br.com.onesystem.domain.IVA;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.war.builder.IVABV;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class IVAView extends BasicMBImpl<IVA, IVABV> implements Serializable {

    @PostConstruct
    public void init() {
        limparJanela();
    }

    public void add() {
        try {
            IVA novoRegistro = e.construir();
            if (validaIVAExistente(novoRegistro)) {
                addNoBanco(novoRegistro);
            } else {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("iva_ja_cadastrado"));
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void update() {
        try {
            IVA ivaExistente = e.construirComID();
            if (ivaExistente.getId() != null) {
                if (validaIVAExistente(ivaExistente)) {
                    updateNoBanco(ivaExistente);
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

    private boolean validaIVAExistente(IVA novoRegistro) {
        List<IVA> lista = new IvaDAO().buscarIVA().porNome(novoRegistro).listaDeResultados();
        return lista.isEmpty();
    }

    @Override
    public void selecionar(SelectEvent event) {
        e = new IVABV((IVA) event.getObject());
    }

    public void limparJanela() {
        e = new IVABV();
    }

}
