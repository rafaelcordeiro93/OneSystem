package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.ListaDePrecoDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.ListaDePreco;
import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.war.service.ListaDePrecoService;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.war.builder.ListaDePrecoBV;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.SelectEvent;

@ManagedBean
@ViewScoped
public class ListaDePrecoView implements Serializable {

    private ListaDePrecoBV listaDePreco;
    private ListaDePreco listaDePrecoSelecionada;

    @ManagedProperty("#{listaDePrecoService}")
    private ListaDePrecoService service;

    @PostConstruct
    public void init() {
        limparJanela();
    }

    public void add() {
        try {
            ListaDePreco novoRegistro = listaDePreco.construir();
            if (validaListaPrecoExistente(novoRegistro)) {
                new AdicionaDAO<ListaDePreco>().adiciona(novoRegistro);
                InfoMessage.adicionado();
                limparJanela();
            } else {
                throw new EDadoInvalidoException("registro_ja_existe");
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void update() {
        try {
            ListaDePreco listaDePrecoExistente = listaDePreco.construirComID();
            if (listaDePrecoExistente.getId() != null) {
                if (validaListaPrecoExistente(listaDePrecoExistente)) {
                    new AtualizaDAO<ListaDePreco>(ListaDePreco.class).atualiza(listaDePrecoExistente);
                    InfoMessage.atualizado();
                    limparJanela();
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

    public void delete() {
        try {
            if (listaDePrecoSelecionada != null) {
                new RemoveDAO<ListaDePreco>(ListaDePreco.class).remove(listaDePrecoSelecionada, listaDePrecoSelecionada.getId());
                InfoMessage.removido();
                limparJanela();
            }
        } catch (DadoInvalidoException di) {
            di.print();
        } catch (ConstraintViolationException pe) {
            FatalMessage.print(pe.getMessage(), pe.getCause());
        }
    }

    public void buscaPorId() {
        Long id = listaDePreco.getId();
        if (id != null) {
            try {
                ListaDePrecoDAO dao = new ListaDePrecoDAO();
                ListaDePreco c = dao.buscarListaDePrecoW().porId(id).resultado();
                listaDePrecoSelecionada = c;
                listaDePreco = new ListaDePrecoBV(listaDePrecoSelecionada);
            } catch (DadoInvalidoException die) {
                limparJanela();
                listaDePreco.setId(id);
                die.print();
            }
        }
    }

    public void selecionaListaDePreco(SelectEvent e) {
        ListaDePreco c = (ListaDePreco) e.getObject();
        listaDePreco = new ListaDePrecoBV(c);
        listaDePrecoSelecionada = c;
    }

    private boolean validaListaPrecoExistente(ListaDePreco novoRegistro) {
        List<ListaDePreco> lista = new ListaDePrecoDAO().buscarListaDePrecoW().porNome(novoRegistro).listaDeResultados();
        return lista.isEmpty();
    }

    public void limparJanela() {
        listaDePreco = new ListaDePrecoBV();
        listaDePrecoSelecionada = new ListaDePreco();
    }

    public void desfazer() {
        if (listaDePrecoSelecionada != null) {
            listaDePreco = new ListaDePrecoBV(listaDePrecoSelecionada);
        }
    }

    public ListaDePrecoBV getListaPreco() {
        return listaDePreco;
    }

    public void setListaPreco(ListaDePrecoBV listaDePreco) {
        this.listaDePreco = listaDePreco;
    }

    public ListaDePreco getListaPrecoSelecionada() {
        return listaDePrecoSelecionada;
    }

    public void setListaPrecoSelecionada(ListaDePreco listaDePrecoSelecionada) {
        this.listaDePrecoSelecionada = listaDePrecoSelecionada;
    }

    public ListaDePrecoService getService() {
        return service;
    }

    public void setService(ListaDePrecoService service) {
        this.service = service;
    }

}
