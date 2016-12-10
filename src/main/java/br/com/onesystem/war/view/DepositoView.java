package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.DepositoDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.Cidade;
import br.com.onesystem.domain.Deposito;
import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.war.builder.DepositoBV;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.war.builder.CidadeBV;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.SelectEvent;

@ManagedBean
@ViewScoped
public class DepositoView implements Serializable {

    private DepositoBV deposito;
    private Deposito depositoSelecionada;

    @PostConstruct
    public void init() {
        limparJanela();
    }

    public void add() {
        try {
            Deposito novoRegistro = deposito.construir();
            if (validaDepositoExistente(novoRegistro)) {
                new AdicionaDAO<Deposito>().adiciona(novoRegistro);
                InfoMessage.adicionado();
                limparJanela();
            } else {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("deposito_ja_cadastrado"));
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void update() {
        try {
            Deposito depositoExistente = deposito.construirComID();
            if (depositoExistente.getId() != null) {
                if (validaDepositoExistente(depositoExistente)) {
                    new AtualizaDAO<Deposito>(Deposito.class).atualiza(depositoExistente);
                    InfoMessage.atualizado();
                    limparJanela();
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

    public void delete() {
        try {
            if (depositoSelecionada != null ) {
                new RemoveDAO<Deposito>(Deposito.class).remove(depositoSelecionada, depositoSelecionada.getId());
                InfoMessage.removido();
                limparJanela();
            }
        } catch (DadoInvalidoException di) {
            di.print();
        } catch (ConstraintViolationException pe) {
            FatalMessage.print(pe.getMessage(), pe.getCause());
        }
    }

    private boolean validaDepositoExistente(Deposito novoRegistro) {
           List<Deposito> lista = new DepositoDAO().buscarDepositos().porNome(novoRegistro).listaDeResultados();
        return lista.isEmpty();
    }
    
     public void selecionaDeposito(SelectEvent e) {
        Deposito d = (Deposito) e.getObject();
        deposito = new DepositoBV(d);
        depositoSelecionada = d;
    }

    public void limparJanela() {
        deposito = new DepositoBV();
        depositoSelecionada = null;
    }

   
    public void desfazer() {
        if (depositoSelecionada != null) {
            deposito = new DepositoBV(depositoSelecionada);
        }
    }

    public DepositoBV getDeposito() {
        return deposito;
    }

    public void setDeposito(DepositoBV deposito) {
        this.deposito = deposito;
    }

    public Deposito getDepositoSelecionada() {
        return depositoSelecionada;
    }

    public void setDepositoSelecionada(Deposito depositoSelecionada) {
        this.depositoSelecionada = depositoSelecionada;
    }
}
