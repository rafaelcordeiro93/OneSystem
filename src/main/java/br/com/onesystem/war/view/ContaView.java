package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.Banco;
import br.com.onesystem.domain.Cidade;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.war.builder.CidadeBV;
import br.com.onesystem.war.builder.ContaBV;
import br.com.onesystem.war.service.ContaService;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.SelectEvent;

@ManagedBean
@ViewScoped
public class ContaView implements Serializable {

    private ContaBV conta;
    private Conta contaSelecionada;

    @PostConstruct
    public void init() {
        limparJanela();

    }

    public void add() {
        try {
            Conta novoRegistro = conta.construir();
            new AdicionaDAO<Conta>().adiciona(novoRegistro);
            InfoMessage.adicionado();
            limparJanela();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void update() {
        try {
            Conta contaExistente = conta.construirComID();
            if (contaExistente.getId() != null) {
                new AtualizaDAO<Conta>(Conta.class).atualiza(contaExistente);

                InfoMessage.atualizado();
                limparJanela();
            } else {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("conta_nao_registrada"));
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void delete() {
        try {
            if (contaSelecionada != null) {
                new RemoveDAO<Conta>(Conta.class).remove(contaSelecionada, contaSelecionada.getId());
                InfoMessage.removido();
                limparJanela();
            }
        } catch (DadoInvalidoException di) {
            di.print();
        } catch (ConstraintViolationException pe) {
            FatalMessage.print(pe.getMessage(), pe.getCause());
        }
    }

      public void selecionaConta(SelectEvent e) {
        Conta c = (Conta) e.getObject();
        conta = new ContaBV(c);
        contaSelecionada = c;
    }
    
    public void limparJanela() {
        conta = new ContaBV();
        contaSelecionada = null;
    }

    public void selecionarBanco(SelectEvent event) {
        Banco banco = (Banco) event.getObject();
        conta.setBanco(banco);
    }

    public void selecionarMoeda(SelectEvent event) {
        Moeda moeda = (Moeda) event.getObject();
        conta.setMoeda(moeda);
    }

    public void desfazer() {
        if (contaSelecionada != null) {
            conta = new ContaBV(contaSelecionada);
        }
    }

    public ContaBV getConta() {
        return conta;
    }

    public void setConta(ContaBV conta) {
        this.conta = conta;
    }

    public Conta getContaSelecionada() {
        return contaSelecionada;
    }

    public void setContaSelecionada(Conta contaSelecionada) {
        this.contaSelecionada = contaSelecionada;
    }
}
