package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.ContaDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.Banco;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.war.builder.ContaBV;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.SelectEvent;

@ManagedBean
@ViewScoped
public class ContaView extends BasicMBImpl<Conta> implements Serializable {

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

    @Override
    public void selecionar(SelectEvent e) {
        if(conta == null){
            limparJanela();
        }
        Object obj = e.getObject();
        if (obj instanceof Conta) {
            Conta c = (Conta) e.getObject();
            conta = new ContaBV(c);
            contaSelecionada = c;
        } else if (obj instanceof Banco) {
            Banco banco = (Banco) obj;
            conta.setBanco(banco);
        } else if (obj instanceof Moeda) {
            Moeda moeda = (Moeda) obj;
            conta.setMoeda(moeda);
        }
    }

    @Override
    public void buscaPorId() {
        Long id = conta.getId();
        if (id != null) {
            try {
                ContaDAO dao = new ContaDAO();
                Conta c = dao.buscarContaW().porId(id).resultado();
                contaSelecionada = c;
                conta = new ContaBV(contaSelecionada);
            } catch (DadoInvalidoException die) {
                limparJanela();
                conta.setId(id);
                die.print();
            }
        }
    }

    public void limparJanela() {
        conta = new ContaBV();
        contaSelecionada = null;
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
