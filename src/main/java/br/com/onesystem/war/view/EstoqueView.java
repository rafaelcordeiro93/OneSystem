package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.Grupo;
import br.com.onesystem.domain.IVA;
import br.com.onesystem.domain.Estoque;
import br.com.onesystem.domain.Marca;

import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.util.InfoMessage;

import br.com.onesystem.war.builder.EstoqueBV;
import br.com.onesystem.war.service.GrupoService;
import br.com.onesystem.war.service.IVAService;
import br.com.onesystem.war.service.EstoqueService;
import br.com.onesystem.war.service.MarcaService;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.hibernate.exception.ConstraintViolationException;

@ManagedBean
@ViewScoped
public class EstoqueView implements Serializable {

    private boolean panel;
    private EstoqueBV estoque;
    private Estoque estoqueSelecionada;
    private List<Estoque> estoqueLista;
    private List<Estoque> estoquesFiltradas;
     

    @ManagedProperty("#{estoqueService}")
    private EstoqueService service;

    @PostConstruct
    public void init() {
        limparJanela();
        panel = false;
        estoqueLista = service.buscarEstoques();
    }

    public void add() {
        try {
            Estoque novoRegistro = estoque.construir();
            if (!validaEstoqueExistente(novoRegistro)) {
                new AdicionaDAO<Estoque>().adiciona(novoRegistro);
                estoqueLista.add(novoRegistro);
                InfoMessage.print("¡Estoque '" + novoRegistro.getItem().getNome()+ "' agregado con éxito!");
                limparJanela();
            } else {
                throw new EDadoInvalidoException("¡Ya existe el estoque!");
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void update() {
        try {
            Estoque estoqueExistente = estoque.construirComID();
            if (estoqueExistente.getId() != null) {
                if (!validaEstoqueExistente(estoqueExistente)) {
                    new AtualizaDAO<Estoque>(Estoque.class).atualiza(estoqueExistente);
                    estoqueLista.set(estoqueLista.indexOf(estoqueExistente),
                            estoqueExistente);
                    if (estoquesFiltradas != null && estoquesFiltradas.contains(estoqueExistente)) {
                        estoquesFiltradas.set(estoquesFiltradas.indexOf(estoqueExistente), estoqueExistente);
                    }
                    InfoMessage.print("¡Estoque '" + estoqueExistente.getItem().getNome() + "' cambiado con éxito!");
                    limparJanela();
                } else {
                    throw new EDadoInvalidoException("¡Ya existe el estoque!");
                }
            } else {
                throw new EDadoInvalidoException("!El estoque no se encontra registrada!");
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void delete() {
        try {
            if (estoqueLista != null && estoqueLista.contains(estoqueSelecionada)) {
                new RemoveDAO<Estoque>(Estoque.class).remove(estoqueSelecionada, estoqueSelecionada.getId());
                estoqueLista.remove(estoqueSelecionada);
                if (estoquesFiltradas != null && estoquesFiltradas.contains(estoqueSelecionada)) {
                    estoquesFiltradas.remove(estoqueSelecionada);
                }
                InfoMessage.print("Estoque '" + this.estoque.getItem().getNome() + "' eliminado con éxito!");
                limparJanela();
            }
        } catch (DadoInvalidoException di) {
            di.print();
        } catch (ConstraintViolationException pe) {
            FatalMessage.print(pe.getMessage(), pe.getCause());
        }
    }

    private boolean validaEstoqueExistente(Estoque novoRegistro) {
        for (Estoque novaEstoque : estoqueLista) {
            if (novoRegistro.getItem().getNome().equals(novaEstoque.getItem().getNome())) {
                return true;
            }
        }
        return false;
    }

    
    public void limparJanela() {
        estoque = new EstoqueBV();
        estoqueSelecionada = new Estoque();
    }

    public void abrirEdicao() {
        limparJanela();
        panel = true;
    }

    public void abrirEdicaoComDados() {
        panel = true;
        estoque = new EstoqueBV(estoqueSelecionada);
    }

    public void fecharEdicao() {
        panel = false;
    }

    public void desfazer() {
        if (estoqueSelecionada != null) {
            estoque = new EstoqueBV(estoqueSelecionada);
        }
    }
    
    public boolean isPanel() {
        return panel;
    }

    public EstoqueBV getEstoque() {
        return estoque;
    }

    public void setEstoque(EstoqueBV estoque) {
        this.estoque = estoque;
    }

    public Estoque getEstoqueSelecionada() {
        return estoqueSelecionada;
    }

    public void setEstoqueSelecionada(Estoque estoqueSelecionada) {
        this.estoqueSelecionada = estoqueSelecionada;
    }

    public List<Estoque> getEstoqueLista() {
        return estoqueLista;
    }

    public void setEstoqueLista(List<Estoque> estoqueLista) {
        this.estoqueLista = estoqueLista;
    }

    public List<Estoque> getEstoquesFiltradas() {
        return estoquesFiltradas;
    }

    public void setEstoquesFiltradas(List<Estoque> estoquesFiltradas) {
        this.estoquesFiltradas = estoquesFiltradas;
    }

    public EstoqueService getService() {
        return service;
    }

    public void setService(EstoqueService service) {
        this.service = service;
    }

}
