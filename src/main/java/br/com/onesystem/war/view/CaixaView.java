package br.com.onesystem.war.view;

import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.CaixaDAO;
import br.com.onesystem.dao.UsuarioDAO;
import br.com.onesystem.domain.Caixa;
import br.com.onesystem.domain.Configuracao;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.util.SessionUtil;
import br.com.onesystem.war.builder.CaixaBV;
import br.com.onesystem.war.service.ConfiguracaoService;
import br.com.onesystem.war.service.CotacaoService;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import br.com.onesystem.war.util.UsuarioLogadoUtil;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped
public class CaixaView extends BasicMBImpl<Caixa, CaixaBV> implements Serializable {

    private String estado = "FECHADO";
    private Configuracao configuracao;
    private List<Cotacao> cotacaoLista;

    @Inject
    private ConfiguracaoService configuracaoService;

    @Inject
    private CotacaoService serviceCotacao;

    @PostConstruct
    public void init() {
        limparJanela();
        iniciarConfiguracoes();
        try {
            populaCampos();//TEM QUE ESTAR LOGADO COM USUARIO VALIDO PARA FUNCIONAR
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    @Override
    public void limparJanela() {
        e = new CaixaBV();
        estado = null;
    }

    private void iniciarConfiguracoes() {
        try {
            configuracao = configuracaoService.buscar();
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    private void inicializaCotacoes() {
        cotacaoLista = serviceCotacao.buscarCotacoesDoDiaAtual();
    }

    private void buscaUsuarioDaSessao() {
        e.setUsuario(new UsuarioDAO().buscarUsuarios().porEmailString(new UsuarioLogadoUtil().getEmailUsuario()).resultado());
    }

    private void adicionaCotacaoInicial() {
        inicializaCotacoes();
        e.setCotacao(cotacaoLista);
    }

    private void populaCampos() throws DadoInvalidoException {
        try {
            e = new CaixaBV(new CaixaDAO().buscarCaixas().porUsuario(new UsuarioLogadoUtil().getEmailUsuario()).porUltimoAberto().resultado());
            if (e.getId() != null) {
                alteraEstadoCaixa();
                return;
            } else if (e.getId() == null) {
                buscaUsuarioDaSessao();
                adicionaCotacaoInicial();
            }
        } catch (EDadoInvalidoException die) {
            die.print();
        }
    }

    public void add() {
        try {
            buscaUsuarioDaSessao();
            adicionaCotacaoInicial();
            addNoBanco(e.construir());
            populaCampos();
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    @Override
    public void selecionar(SelectEvent event) {
        Caixa obj = (Caixa) event.getObject();
        if (obj instanceof Caixa) {
            e = new CaixaBV(obj);
            alteraEstadoCaixa();
        }
    }

    public void alteraEstadoCaixa() {
        if (e.getId() != null && e.getFechamento() == null) {
            estado = new BundleUtil().getLabel("Aberto");
        } else {
            estado = new BundleUtil().getLabel("Fechado");
        }
    }

    public void fecharCaixa() throws DadoInvalidoException {
        try {
            Caixa ca = (Caixa) SessionUtil.getObject("caixa", FacesContext.getCurrentInstance());
            if (ca.getId().equals(e.getId())) {
                SessionUtil.remove("caixa", FacesContext.getCurrentInstance());
            }
            Caixa c = e.construirComID();
            c.fecharCaixa();
            new AtualizaDAO<>().atualiza(c);
            InfoMessage.atualizado();
            limparJanela();
            populaCampos();

        } catch (EDadoInvalidoException die) {
            die.print();
        }
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Configuracao getConfiguracao() {
        return configuracao;
    }

    public void setConfiguracao(Configuracao configuracao) {
        this.configuracao = configuracao;
    }

    public List<Cotacao> getCotacaoLista() {
        return cotacaoLista;
    }

    public void setCotacaoLista(List<Cotacao> cotacaoLista) {
        this.cotacaoLista = cotacaoLista;
    }

    public CotacaoService getServiceCotacao() {
        return serviceCotacao;
    }

    public void setServiceCotacao(CotacaoService serviceCotacao) {
        this.serviceCotacao = serviceCotacao;
    }

    public ConfiguracaoService getConfiguracaoService() {
        return configuracaoService;
    }

    public void setConfiguracaoService(ConfiguracaoService configuracaoService) {
        this.configuracaoService = configuracaoService;
    }

}
