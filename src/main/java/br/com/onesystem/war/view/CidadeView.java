package br.com.onesystem.war.view;

import br.com.onesystem.dao.CidadeDAO;
import br.com.onesystem.domain.Cidade;
import br.com.onesystem.domain.Estado;
import br.com.onesystem.war.builder.CidadeBV;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class CidadeView extends BasicMBImpl<Cidade, CidadeBV> implements Serializable {

    @Inject
    private CidadeDAO cidadeDAO;
    
    @PostConstruct
    public void init() {
        limparJanela();
    }

    public void add() {
        try {
            Cidade novoRegistro = e.construir();
            if (validaCidadeExistente(novoRegistro)) {
                addNoBanco(novoRegistro);
            } else {
                throw new EDadoInvalidoException("registro_ja_existe");
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void update() {
        try {
            Cidade cidadeExistente = e.construirComID();
            if (cidadeExistente.getId() != null) {
                if (validaCidadeExistente(cidadeExistente)) {
                    updateNoBanco(cidadeExistente);
                } else {
                    throw new EDadoInvalidoException(new BundleUtil().getMessage("registro_ja_existe"));
                }
            } else {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("registro_nao_existe"));
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    private boolean validaCidadeExistente(Cidade novoRegistro) {
        List<Cidade> lista = cidadeDAO.porNome(novoRegistro).listaDeResultados();
        return lista.isEmpty();
    }

    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        if (obj instanceof Cidade) {
            e = new CidadeBV((Cidade) event.getObject());
        } else if (obj instanceof Estado) {
            e.setEstado((Estado) obj);
        }
    }

    public void limparJanela() {
        e = new CidadeBV();
    }

}
