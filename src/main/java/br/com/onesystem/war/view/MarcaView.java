package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.Marca;
import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.war.builder.MarcaBV;
import br.com.onesystem.war.service.MarcaService;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.hibernate.exception.ConstraintViolationException;

@ManagedBean
@ViewScoped
public class MarcaView implements Serializable {

    private boolean panel;
    private MarcaBV marca;
    private Marca marcaSelecionada;
    private List<Marca> marcaLista;
    private List<Marca> marcasFiltradas;

    @ManagedProperty("#{marcaService}")
    private MarcaService service;

    @PostConstruct
    public void init() {
        limparJanela();
        panel = false;
        marcaLista = service.buscarMarcas();
    }

    public void add() {
        try {
            Marca novoRegistro = marca.construir();
            if (!validaMarcaExistente(novoRegistro)) {
                new AdicionaDAO<Marca>().adiciona(novoRegistro);
                marcaLista.add(novoRegistro);
                InfoMessage.print("¡Marca '" + novoRegistro.getNome() + "' agregado con éxito!");
                limparJanela();
            } else {
                throw new EDadoInvalidoException("¡Ya existe la marca!");
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void update() {
        try {
            Marca marcaExistente = marca.construirComID();
            if (marcaExistente.getId() != null) {
                if (!validaMarcaExistente(marcaExistente)) {
                    new AtualizaDAO<Marca>(Marca.class).atualiza(marcaExistente);
                    marcaLista.set(marcaLista.indexOf(marcaExistente),
                            marcaExistente);
                    if (marcasFiltradas != null && marcasFiltradas.contains(marcaExistente)) {
                        marcasFiltradas.set(marcasFiltradas.indexOf(marcaExistente), marcaExistente);
                    }
                    InfoMessage.print("¡Marca '" + marcaExistente.getNome() + "' cambiado con éxito!");
                    limparJanela();
                } else {
                    throw new EDadoInvalidoException("¡Ya existe la marca!");
                }
            } else {
                throw new EDadoInvalidoException("!La marca no se encontra registrada!");
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void delete() {
        try {
            if (marcaLista != null && marcaLista.contains(marcaSelecionada)) {
                new RemoveDAO<Marca>(Marca.class).remove(marcaSelecionada, marcaSelecionada.getId());
                marcaLista.remove(marcaSelecionada);
                if (marcasFiltradas != null && marcasFiltradas.contains(marcaSelecionada)) {
                    marcasFiltradas.remove(marcaSelecionada);
                }
                InfoMessage.print("Marca '" + this.marca.getNome() + "' eliminada con éxito!");
                limparJanela();
            }
        } catch (DadoInvalidoException di) {
            di.print();
        } catch (ConstraintViolationException pe) {
            FatalMessage.print(pe.getMessage(), pe.getCause());
        }
    }

    private boolean validaMarcaExistente(Marca novoRegistro) {
        for (Marca novaMarca : marcaLista) {
            if (novoRegistro.getNome().equals(novaMarca.getNome())) {
                return true;
            }
        }
        return false;
    }

    public void limparJanela() {
        marca = new MarcaBV();
        marcaSelecionada = new Marca();
    }

    public void abrirEdicao() {
        limparJanela();
        panel = true;
    }

    public void abrirEdicaoComDados() {
        panel = true;
        marca = new MarcaBV(marcaSelecionada);
    }

    public void fecharEdicao() {
        panel = false;
    }

    public void desfazer() {
        if (marcaSelecionada != null) {
            marca = new MarcaBV(marcaSelecionada);
        }
    }

    public MarcaBV getMarca() {
        return marca;
    }

    public void setMarca(MarcaBV marca) {
        this.marca = marca;
    }

    public Marca getMarcaSelecionada() {
        return marcaSelecionada;
    }

    public void setMarcaSelecionada(Marca marcaSelecionada) {
        this.marcaSelecionada = marcaSelecionada;
    }

    public List<Marca> getMarcaLista() {
        return marcaLista;
    }

    public void setMarcaLista(List<Marca> marcaLista) {
        this.marcaLista = marcaLista;
    }

    public List<Marca> getMarcasFiltradas() {
        return marcasFiltradas;
    }

    public void setMarcasFiltradas(List<Marca> marcasFiltradas) {
        this.marcasFiltradas = marcasFiltradas;
    }

    public boolean isPanel() {
        return panel;
    }

    public void setPanel(boolean panel) {
        this.panel = panel;
    }

    public MarcaService getService() {
        return service;
    }

    public void setService(MarcaService service) {
        this.service = service;
    }

}
