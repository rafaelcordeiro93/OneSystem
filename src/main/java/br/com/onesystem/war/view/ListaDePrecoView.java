package br.com.onesystem.war.view;

import br.com.onesystem.dao.ListaDePrecoDAO;
import br.com.onesystem.domain.ListaDePreco;
import br.com.onesystem.war.service.ListaDePrecoService;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.war.builder.ListaDePrecoBV;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class ListaDePrecoView extends BasicMBImpl<ListaDePreco, ListaDePrecoBV> implements Serializable {

    @Inject
    private ListaDePrecoService service;

    @PostConstruct
    public void init() {
        limparJanela();
    }

    public void add() {
        try {
            ListaDePreco novoRegistro = e.construir();
            if (validaListaPrecoExistente(novoRegistro)) {
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
            ListaDePreco listaDePrecoExistente = e.construirComID();
            if (listaDePrecoExistente.getId() != null) {
                if (validaListaPrecoExistente(listaDePrecoExistente)) {
                    updateNoBanco(listaDePrecoExistente);
                } else {
                    throw new EDadoInvalidoException("registro_ja_existe");
                }
            } else {
                throw new EDadoInvalidoException("registro_nao_existe");
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    @Override
    public void selecionar(SelectEvent event) {
        e = new ListaDePrecoBV((ListaDePreco) event.getObject());
    }

    private boolean validaListaPrecoExistente(ListaDePreco novoRegistro) {
        List<ListaDePreco> lista = new ListaDePrecoDAO().buscarListaDePrecoW().porNome(novoRegistro).listaDeResultados();
        return lista.isEmpty();
    }

    public void limparJanela() {
        e = new ListaDePrecoBV();
    }

    public ListaDePrecoService getService() {
        return service;
    }

    public void setService(ListaDePrecoService service) {
        this.service = service;
    }

}
