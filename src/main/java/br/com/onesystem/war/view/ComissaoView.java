package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.ComissaoDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.Comissao;
import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.war.builder.ComissaoBV;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.SelectEvent;

@ManagedBean
@ViewScoped
public class ComissaoView implements Serializable {

    private ComissaoBV comissao;
    private Comissao comissaoSelecionada;

    @PostConstruct
    public void init() {
        limparJanela();
    }

    public void add() {
        try {
            Comissao novoRegistro = comissao.construir();
            if (validaComissaoExistente(novoRegistro)) {
                new AdicionaDAO<Comissao>().adiciona(novoRegistro);
                InfoMessage.adicionado();
                limparJanela();
            } else {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("comissao_ja_registrada"));
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void update() {
        try {
            Comissao comissaoExistente = comissao.construirComID();
            if (comissaoExistente.getId() != null) {
                if (validaComissaoExistente(comissaoExistente)) {
                    new AtualizaDAO<Comissao>(Comissao.class).atualiza(comissaoExistente);
                    InfoMessage.atualizado();
                    limparJanela();
                } else {
                    throw new EDadoInvalidoException(new BundleUtil().getMessage("comissao_ja_registrada"));
                }
            } else {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("comissao_nao_registrada"));
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void delete() {
        try {
            if (comissaoSelecionada != null) {
                new RemoveDAO<Comissao>(Comissao.class).remove(comissaoSelecionada, comissaoSelecionada.getId());
                InfoMessage.removido();
                limparJanela();
            }
        } catch (DadoInvalidoException di) {
            di.print();
        } catch (ConstraintViolationException pe) {
            FatalMessage.print(pe.getMessage(), pe.getCause());
        }
    }

    private boolean validaComissaoExistente(Comissao novoRegistro) {
        List<Comissao> lista = new ComissaoDAO().buscarComissaos().porNome(novoRegistro).listaDeResultados();
        return lista.isEmpty();
    }

    public void selecionaComissao(SelectEvent e) {
        Comissao c = (Comissao) e.getObject();
        comissao = new ComissaoBV(c);
        comissaoSelecionada = c;
    }

        public void buscaPorId() {
        Long id = comissao.getId();
        if (id != null) {
            try {
                ComissaoDAO dao = new ComissaoDAO();
                Comissao c = dao.buscarComissaos().porId(id).resultado();
                comissaoSelecionada = c;
                comissao = new ComissaoBV(comissaoSelecionada);
            } catch (DadoInvalidoException die) {
                limparJanela();
                comissao.setId(id);
                die.print();
            }
        }
    }
    
    public void limparJanela() {
        comissao = new ComissaoBV();
        comissaoSelecionada = null;
    }

    public void desfazer() {
        if (comissaoSelecionada != null) {
            comissao = new ComissaoBV(comissaoSelecionada);
        }
    }

    public ComissaoBV getComissao() {
        return comissao;
    }

    public void setComissao(ComissaoBV comissao) {
        this.comissao = comissao;
    }

    public Comissao getComissaoSelecionada() {
        return comissaoSelecionada;
    }

    public void setComissaoSelecionada(Comissao comissaoSelecionada) {
        this.comissaoSelecionada = comissaoSelecionada;
    }

}
