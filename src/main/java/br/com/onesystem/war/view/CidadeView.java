package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.CidadeDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.Cidade;
import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.war.builder.CidadeBV;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.services.LogPhaseListener;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import br.com.onesystem.war.util.UsuarioLogadoUtil;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.SelectEvent;

@ManagedBean
@ViewScoped
public class CidadeView extends BasicMBImpl<Cidade> implements Serializable {

    private CidadeBV cidade;
    private Cidade cidadeSelecionada;

    @PostConstruct
    public void init() {
        limparJanela();
    }

    public void add() {
        try {
            Cidade novoRegistro = cidade.construir();
            if (validaCidadeExistente(novoRegistro)) {
                new AdicionaDAO<Cidade>().adiciona(novoRegistro);
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
            Cidade cidadeExistente = cidade.construirComID();
            if (cidadeExistente.getId() != null) {
                if (validaCidadeExistente(cidadeExistente)) {
                    new AtualizaDAO<Cidade>(Cidade.class).atualiza(cidadeExistente);
                    InfoMessage.atualizado();
                    limparJanela();
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

    public void delete() {
        try {
            if (cidadeSelecionada != null) {
                new RemoveDAO<Cidade>(Cidade.class).remove(cidadeSelecionada, cidadeSelecionada.getId());
                InfoMessage.removido();
                limparJanela();
            }
        } catch (DadoInvalidoException di) {
            di.print();
        } catch (ConstraintViolationException pe) {
            FatalMessage.print(pe.getMessage(), pe.getCause());
        }
    }

    private boolean validaCidadeExistente(Cidade novoRegistro) {
        List<Cidade> lista = new CidadeDAO().buscarCidades().porNome(novoRegistro).listaDeResultados();
        return lista.isEmpty();
    }

    @Override
    public void buscaPorId() {
        Long id = cidade.getId();
        if (id != null) {
            try {
                CidadeDAO dao = new CidadeDAO();
                Cidade c = dao.buscarCidades().porId(id).resultado();
                cidadeSelecionada = c;
                cidade = new CidadeBV(cidadeSelecionada);
            } catch (DadoInvalidoException die) {
                limparJanela();
                cidade.setId(id);
                die.print();
            }
        }
    }

    public void selecionar(SelectEvent e) {
        Cidade c = (Cidade) e.getObject();
        cidade = new CidadeBV(c);
        cidadeSelecionada = c;
    }

    public void limparJanela() {
        cidade = new CidadeBV();
        cidadeSelecionada = null;
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

}
