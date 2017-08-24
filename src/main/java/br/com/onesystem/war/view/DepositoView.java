package br.com.onesystem.war.view;

import br.com.onesystem.dao.DepositoDAO;
import br.com.onesystem.domain.Deposito;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.war.builder.DepositoBV;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class DepositoView extends BasicMBImpl<Deposito, DepositoBV> implements Serializable {

    @PostConstruct
    public void init() {
        limparJanela();
    }

    public void add() {
        try {
            Deposito novoRegistro = e.construir();
            if (validaDepositoExistente(novoRegistro)) {
                addNoBanco(novoRegistro);
            } else {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("deposito_ja_cadastrado"));
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void update() {
        try {
            Deposito depositoExistente = e.construirComID();
            if (depositoExistente.getId() != null) {
                if (validaDepositoExistente(depositoExistente)) {
                    updateNoBanco(depositoExistente);
                } else {
                    throw new EDadoInvalidoException(new BundleUtil().getMessage("deposito_ja_cadastrado"));
                }
            } else {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("deposito_nao_cadastrado"));
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    private boolean validaDepositoExistente(Deposito novoRegistro) {
        List<Deposito> lista = new DepositoDAO().buscarDepositos().porNome(novoRegistro).listaDeResultados();
        return lista.isEmpty();
    }

    @Override
    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        if (obj instanceof Deposito) {
            e = new DepositoBV((Deposito) event.getObject());
        }
    }

    public void limparJanela() {
        e = new DepositoBV();
    }
}
