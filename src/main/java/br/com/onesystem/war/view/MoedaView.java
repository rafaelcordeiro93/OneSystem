package br.com.onesystem.war.view;

import br.com.onesystem.dao.MoedaDAO;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.valueobjects.TipoBandeira;
import br.com.onesystem.war.builder.MoedaBV;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class MoedaView extends BasicMBImpl<Moeda, MoedaBV> implements Serializable {

    @PostConstruct
    public void init() {
        limparJanela();
    }

    public void add() {
        try {
            Moeda novoRegistro = e.construir();
            if (validaMoedaExistente(novoRegistro)) {
                addNoBanco(novoRegistro);
            } else {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("registro_existe"));
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void update() {
        try {
            Moeda moedaExistente = e.construirComID();
            if (moedaExistente != null && moedaExistente.getId() != null) {
                if (validaMoedaExistente(moedaExistente)) {
                    updateNoBanco(moedaExistente);
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

    public void selecionar(SelectEvent event) {
        e = new MoedaBV((Moeda) event.getObject());
    }

    public List<TipoBandeira> getBandeiras() {
        return Arrays.asList(TipoBandeira.values());
    }

    private boolean validaMoedaExistente(Moeda novoRegistro) {
        List<Moeda> lista = new MoedaDAO().buscarMoedas().porNome(novoRegistro).porSigla(novoRegistro).listaDeResultados();
        if (novoRegistro.getId() != null && !lista.isEmpty()) {
            return novoRegistro.getId().compareTo(lista.get(0).getId()) == 0;
        } else {
            return lista.isEmpty();
        }
    }

    public void limparJanela() {
        e = new MoedaBV();
    }

}
