package br.com.onesystem.war.view;

import br.com.onesystem.dao.ComissaoDAO;
import br.com.onesystem.domain.Comissao;
import br.com.onesystem.war.builder.ComissaoBV;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class ComissaoView extends BasicMBImpl<Comissao, ComissaoBV> implements Serializable {

    @PostConstruct
    public void init() {
        limparJanela();
    }

    public void add() {
        try {
            Comissao novoRegistro = e.construir();
            if (validaComissaoExistente(novoRegistro)) {
                addNoBanco(novoRegistro);
            } else {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("comissao_ja_registrada"));
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void update() {
        try {
            Comissao comissaoExistente = e.construirComID();
            if (comissaoExistente.getId() != null) {
                if (validaComissaoExistente(comissaoExistente)) {
                    addNoBanco(comissaoExistente);
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

    private boolean validaComissaoExistente(Comissao novoRegistro) {
        List<Comissao> lista = new ComissaoDAO().buscarComissaos().porNome(novoRegistro).listaDeResultados();
        return lista.isEmpty();
    }

    @Override
    public void selecionar(SelectEvent event) {
        e = new ComissaoBV((Comissao) event.getObject());
    }

    public void limparJanela() {
        e = new ComissaoBV();
    }
}
