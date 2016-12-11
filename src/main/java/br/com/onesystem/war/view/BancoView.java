package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.AjusteDeEstoque;
import br.com.onesystem.domain.Banco;
import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.war.builder.BancoBV;
import br.com.onesystem.war.service.BancoService;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.war.builder.AjusteDeEstoqueBV;
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
public class BancoView implements Serializable {

    private BancoBV banco;
    private Banco bancoSelecionada;

    @PostConstruct
    public void init() {
        limparJanela();
    }

    public void add() {
        try {
            Banco novoRegistro = banco.construir();
            new AdicionaDAO<Banco>().adiciona(novoRegistro);
            InfoMessage.adicionado();
            limparJanela();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void update() {
        try {

            if (bancoSelecionada.getId() != null) {
                Banco bancoExistente = banco.construirComID();
                new AtualizaDAO<Banco>(Banco.class).atualiza(bancoExistente);
                InfoMessage.atualizado();
                limparJanela();
            } else {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("banco_nao_encontrado"));
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void delete() {
        try {
            if (bancoSelecionada != null) {
                new RemoveDAO<Banco>(Banco.class).remove(bancoSelecionada, bancoSelecionada.getId());
                InfoMessage.removido();
                limparJanela();
            }
        } catch (DadoInvalidoException di) {
            di.print();
        } catch (ConstraintViolationException pe) {
            FatalMessage.print(pe.getMessage(), pe.getCause());
        }
    }

    public void limparJanela() {
        banco = new BancoBV();
        bancoSelecionada = null;
    }

    public void desfazer() {
        if (bancoSelecionada != null) {
            banco = new BancoBV(bancoSelecionada);
        }
    }

    public void selecionaBanco(SelectEvent e) {
        Banco b = (Banco) e.getObject();
        banco = new BancoBV(b);
        bancoSelecionada = b;
    }

    public BancoBV getBanco() {
        return banco;
    }

    public void setBanco(BancoBV banco) {
        this.banco = banco;
    }

    public Banco getBancoSelecionada() {
        return bancoSelecionada;
    }

    public void setBancoSelecionada(Banco bancoSelecionada) {
        this.bancoSelecionada = bancoSelecionada;
    }

}
