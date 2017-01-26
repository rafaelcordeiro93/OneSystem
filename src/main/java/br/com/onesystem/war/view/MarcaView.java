package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.MarcaDAO;
import br.com.onesystem.dao.MarcaDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.Marca;
import br.com.onesystem.domain.Marca;
import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.war.builder.MarcaBV;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.war.builder.MarcaBV;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.SelectEvent;

@ManagedBean
@ViewScoped
public class MarcaView implements Serializable {

    private MarcaBV marca;
    private Marca marcaSelecionada;

    @PostConstruct
    public void init() {
        limparJanela();
    }

    public void add() {
        try {
            Marca novoRegistro = marca.construir();
            if (validaMarcaExistente(novoRegistro)) {
                new AdicionaDAO<Marca>().adiciona(novoRegistro);
                InfoMessage.adicionado();
                limparJanela();
            } else {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("marca_ja_cadastrado"));
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void update() {
        try {
            Marca marcaExistente = marca.construirComID();
            if (marcaExistente.getId() != null) {
                if (validaMarcaExistente(marcaExistente)) {
                    new AtualizaDAO<Marca>(Marca.class).atualiza(marcaExistente);

                    InfoMessage.atualizado();
                    limparJanela();
                } else {
                    throw new EDadoInvalidoException(new BundleUtil().getMessage("marca_ja_cadastrado"));
                }
            } else {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("marca_nao_cadastrado"));
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void delete() {
        try {
            if (marcaSelecionada != null) {
                new RemoveDAO<Marca>(Marca.class).remove(marcaSelecionada, marcaSelecionada.getId());
                InfoMessage.removido();
                limparJanela();
            }
        } catch (DadoInvalidoException di) {
            di.print();
        } catch (ConstraintViolationException pe) {
            FatalMessage.print(pe.getMessage(), pe.getCause());
        }
    }

    private boolean validaMarcaExistente(Marca novoRegistro) {
        List<Marca> lista = new MarcaDAO().buscarMarcas().porNome(novoRegistro).listaDeResultados();
        return lista.isEmpty();
    }

    public void selecionaMarca(SelectEvent e) {
        Marca m = (Marca) e.getObject();
        marca = new MarcaBV(m);
        marcaSelecionada = m;
    }

    public void buscaPorId() {
        Long id = marca.getId();
        if (id != null) {
            try {
                MarcaDAO dao = new MarcaDAO();
                Marca c = dao.buscarMarcas().porId(id).resultado();
                marcaSelecionada = c;
                marca = new MarcaBV(marcaSelecionada);
            } catch (DadoInvalidoException die) {
                limparJanela();
                marca.setId(id);
                die.print();
            }
        }
    }

    public void limparJanela() {
        marca = new MarcaBV();
        marcaSelecionada = null;
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

}
