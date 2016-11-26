package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.Cidade;
import br.com.onesystem.util.ErrorMessage;
import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.war.builder.CidadeBV;
import br.com.onesystem.war.service.CidadeService;
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
public class CidadeView implements Serializable {

    private boolean panel;
    private CidadeBV cidade;
    private Cidade cidadeSelecionada;
    private List<Cidade> cidadeLista;
    private List<Cidade> cidadesFiltradas;

    @ManagedProperty("#{cidadeService}")
    private CidadeService service;

    @PostConstruct
    public void init() {
        limparJanela();
        panel = false;
        cidadeLista = service.buscarCidades();
    }

    public void add() {
        try {
            Cidade novoRegistro = cidade.construir();
            if (!validaCidadeExistente(novoRegistro)) {
                new AdicionaDAO<Cidade>().adiciona(novoRegistro);
                cidadeLista.add(novoRegistro);
                InfoMessage.print("¡Ciudad '" + novoRegistro.getNome() + "' agregado con éxito!");
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
            Cidade cidadeExistente = cidade.construirComID();
            if (cidadeExistente.getId() != null) {
                if (!validaCidadeExistente(cidadeExistente)) {
                    new AtualizaDAO<Cidade>(Cidade.class).atualiza(cidadeExistente);
                    cidadeLista.set(cidadeLista.indexOf(cidadeExistente),
                            cidadeExistente);
                    if (cidadesFiltradas != null && cidadesFiltradas.contains(cidadeExistente)) {
                        cidadesFiltradas.set(cidadesFiltradas.indexOf(cidadeExistente), cidadeExistente);
                    }
                    InfoMessage.print("¡Ciudad '" + cidadeExistente.getNome() + "' cambiado con éxito!");
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
            if (cidadeLista != null && cidadeLista.contains(cidadeSelecionada)) {
                new RemoveDAO<Cidade>(Cidade.class).remove(cidadeSelecionada, cidadeSelecionada.getId());
                cidadeLista.remove(cidadeSelecionada);
                if (cidadesFiltradas != null && cidadesFiltradas.contains(cidadeSelecionada)) {
                    cidadesFiltradas.remove(cidadeSelecionada);
                }
                InfoMessage.print("Ciudad '" + this.cidade.getNome() + "' eliminada con éxito!");
                limparJanela();
            }
        } catch (DadoInvalidoException di) {
            di.print();
        } catch (ConstraintViolationException pe) {
            FatalMessage.print(pe.getMessage(), pe.getCause());
        }
    }

    private boolean validaCidadeExistente(Cidade novoRegistro) {
        for (Cidade novaCidade : cidadeLista) {
            if (novoRegistro.getNome().equals(novaCidade.getNome())
                    && novoRegistro.getUf().equals(novaCidade.getUf())
                    && novoRegistro.getPais().equals(novaCidade.getPais())) {
                return true;
            }
        }
        return false;
    }

    public void limparJanela() {
        cidade = new CidadeBV();
        cidadeSelecionada = new Cidade();
    }

    public void abrirEdicao() {
        limparJanela();
        panel = true;
    }

    public void abrirEdicaoComDados() {
        panel = true;
        cidade = new CidadeBV(cidadeSelecionada);
    }

    public void fecharEdicao() {
        panel = false;
    }

    public void desfazer() {
        if (cidadeSelecionada != null) {
            cidade = new CidadeBV(cidadeSelecionada);
        }
    }

    public CidadeBV getCidade() {
        return cidade;
    }

    public void setCidade(CidadeBV cidade) {
        this.cidade = cidade;
    }

    public Cidade getCidadeSelecionada() {
        return cidadeSelecionada;
    }

    public void setCidadeSelecionada(Cidade cidadeSelecionada) {
        this.cidadeSelecionada = cidadeSelecionada;
    }

    public List<Cidade> getCidadeLista() {
        return cidadeLista;
    }

    public void setCidadeLista(List<Cidade> cidadeLista) {
        this.cidadeLista = cidadeLista;
    }

    public List<Cidade> getCidadesFiltradas() {
        return cidadesFiltradas;
    }

    public void setCidadesFiltradas(List<Cidade> cidadesFiltradas) {
        this.cidadesFiltradas = cidadesFiltradas;
    }

    public boolean isPanel() {
        return panel;
    }

    public void setPanel(boolean panel) {
        this.panel = panel;
    }

    public CidadeService getService() {
        return service;
    }

    public void setService(CidadeService service) {
        this.service = service;
    }

}
