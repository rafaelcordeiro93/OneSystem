package br.com.onesystem.war.view;

import br.com.onesystem.dao.CepDAO;
import br.com.onesystem.domain.Cep;
import br.com.onesystem.war.builder.CepBV;
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
public class CepView extends BasicMBImpl<Cep, CepBV> implements Serializable {

    @Inject
    private CepDAO cepDAO;

    @PostConstruct
    public void init() {
        limparJanela();
    }

    public void add() {
        try {
            Cep novoRegistro = e.construir();
            if (validaCepExistente(novoRegistro)) {
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
            Cep cepExistente = e.construirComID();
            if (cepExistente.getId() != null) {
                if (validaCepExistente(cepExistente)) {
                    updateNoBanco(cepExistente);
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

    private boolean validaCepExistente(Cep novoRegistro) {
        List<Cep> lista = cepDAO.porCep(novoRegistro).listaDeResultados();
        return lista.isEmpty();
    }

    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        if (obj instanceof Cep) {
            e = new CepBV((Cep) event.getObject());
        }
    }

    public void limparJanela() {
        e = new CepBV();
    }

}
