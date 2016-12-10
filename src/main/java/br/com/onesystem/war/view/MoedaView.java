package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.MoedaDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.util.ErrorMessage;
import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.war.builder.MoedaBV;
import br.com.onesystem.war.service.MoedaService;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
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
public class MoedaView implements Serializable {

    private MoedaBV moeda;
    private Moeda moedaSelecionada;

    @PostConstruct
    public void init() {
        limparJanela();
    }

    public void add() {
        try {
            Moeda novoRegistro = moeda.construir();
            if (validaMoedaExistente(novoRegistro)) {
                new AdicionaDAO<Moeda>().adiciona(novoRegistro);
                InfoMessage.adicionado();
                limparJanela();
            } else {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("registro_existe"));
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void update() {
        try {
            Moeda moedaExistente = moeda.construirComID();
            if (moedaSelecionada != null) {
                if (validaMoedaExistente(moedaExistente)) {
                    new AtualizaDAO<Moeda>(Moeda.class).atualiza(moedaExistente);
                    InfoMessage.atualizado();
                    limparJanela();
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

    public void delete() {
        try {
            if (moedaSelecionada != null) {
                new RemoveDAO<Moeda>(Moeda.class).remove(moedaSelecionada, moedaSelecionada.getId());
                InfoMessage.removido();
                limparJanela();
            }
        } catch (DadoInvalidoException di) {
            di.print();
        } catch (ConstraintViolationException pe) {
            FatalMessage.print(pe.getMessage(), pe.getCause());
        }
    }

    public void selecionaMoeda(SelectEvent e) {
        moedaSelecionada = (Moeda) e.getObject();
        moeda = new MoedaBV(moedaSelecionada);
    }

    private boolean validaMoedaExistente(Moeda novoRegistro) {
        List<Moeda> lista = new MoedaDAO().buscarMoedas().porNome(novoRegistro).porSigla(novoRegistro).listaDeResultados();
        return lista.isEmpty();
    }

    public void desfazer() {
        if (moedaSelecionada != null) {
            moeda = new MoedaBV(moedaSelecionada);
        }
    }

    public void limparJanela() {
        moeda = new MoedaBV();
        moedaSelecionada = null;
    }

    public MoedaBV getMoeda() {
        return moeda;
    }

    public void setMoeda(MoedaBV moeda) {
        this.moeda = moeda;
    }

    public Moeda getMoedaSelecionada() {
        return moedaSelecionada;
    }

    public void setMoedaSelecionada(Moeda moedaSelecionada) {
        this.moedaSelecionada = moedaSelecionada;
    }

}
