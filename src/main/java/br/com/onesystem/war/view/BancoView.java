package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.Banco;
import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.war.builder.BancoBV;
import br.com.onesystem.war.service.BancoService;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.hibernate.exception.ConstraintViolationException;

@ManagedBean
@ViewScoped
public class BancoView implements Serializable {

    private boolean panel;
    private BancoBV banco;
    private Banco bancoSelecionada;
    private List<Banco> bancoLista;
    private List<Banco> bancosFiltradas;

    @ManagedProperty("#{bancoService}")
    private BancoService service;

    @PostConstruct
    public void init() {
        limparJanela();
        panel = false;
        bancoLista = service.buscarBancos();
    }

    public void add() {
        try {
            Banco novoRegistro = banco.construir();
            new AdicionaDAO<Banco>().adiciona(novoRegistro);
            bancoLista.add(novoRegistro);
            InfoMessage.print(new BundleUtil().getMessage("banco_adicionado")
                    + " " + novoRegistro.getNome());
            limparJanela();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void update() {
        try {
            Banco bancoExistente = banco.construirComID();
            if (bancoExistente.getId() != null) {
                new AtualizaDAO<Banco>(Banco.class).atualiza(bancoExistente);
                bancoLista.set(bancoLista.indexOf(bancoExistente),
                        bancoExistente);
                if (bancosFiltradas != null && bancosFiltradas.contains(bancoExistente)) {
                    bancosFiltradas.set(bancosFiltradas.indexOf(bancoExistente), bancoExistente);
                }
                InfoMessage.print("¡Banco '" + bancoExistente.getNome() + "' cambiado con éxito!");
                limparJanela();
            } else {
                throw new EDadoInvalidoException("!La banco no se encontra registrada!");
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void delete() {
        try {
            if (bancoLista != null && bancoLista.contains(bancoSelecionada)) {
                new RemoveDAO<Banco>(Banco.class).remove(bancoSelecionada, bancoSelecionada.getId());
                bancoLista.remove(bancoSelecionada);
                if (bancosFiltradas != null && bancosFiltradas.contains(bancoSelecionada)) {
                    bancosFiltradas.remove(bancoSelecionada);
                }
                InfoMessage.print("Banco '" + this.banco.getNome() + "' eliminada con éxito!");
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
        bancoSelecionada = new Banco();
    }

    public void abrirEdicao() {
        limparJanela();
        panel = true;
    }

    public void abrirEdicaoComDados() {
        panel = true;
        banco = new BancoBV(bancoSelecionada);
    }

    public void fecharEdicao() {
        panel = false;
    }

    public void desfazer() {
        if (bancoSelecionada != null) {
            banco = new BancoBV(bancoSelecionada);
        }
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

    public List<Banco> getBancoLista() {
        return bancoLista;
    }

    public void setBancoLista(List<Banco> bancoLista) {
        this.bancoLista = bancoLista;
    }

    public List<Banco> getBancosFiltradas() {
        return bancosFiltradas;
    }

    public void setBancosFiltradas(List<Banco> bancosFiltradas) {
        this.bancosFiltradas = bancosFiltradas;
    }

    public boolean isPanel() {
        return panel;
    }

    public void setPanel(boolean panel) {
        this.panel = panel;
    }

    public BancoService getService() {
        return service;
    }

    public void setService(BancoService service) {
        this.service = service;
    }

}
