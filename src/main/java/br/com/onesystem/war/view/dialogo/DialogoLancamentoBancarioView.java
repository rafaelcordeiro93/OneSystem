package br.com.onesystem.war.view.dialogo;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.ContaDAO;
import br.com.onesystem.dao.CotacaoDAO;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.Filial;
import br.com.onesystem.domain.LancamentoBancario;
import br.com.onesystem.domain.TipoDespesa;
import br.com.onesystem.domain.TipoReceita;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.util.SessionUtil;
import br.com.onesystem.valueobjects.TipoLancamentoBancario;
import br.com.onesystem.war.builder.LancamentoBancarioBV;
import br.com.onesystem.war.service.ConfiguracaoService;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@Named
@ViewScoped
public class DialogoLancamentoBancarioView extends BasicMBImpl<LancamentoBancario, LancamentoBancarioBV> implements Serializable {

    private Cotacao cotacaoPadrao;
    private List<Conta> contaComCotacaoBancaria;
    private List<Cotacao> cotacaoBancariaLista;
    private Boolean yesNoRadio;

    @Inject
    private ConfiguracaoService serviceConf;

    @PostConstruct
    public void init() {
        limparJanela();
        inicializar();
    }

    @Override
    public void limparJanela() {
        try{
        t = null;
        e = new LancamentoBancarioBV();
        e.setFilial((Filial) SessionUtil.getObject("filial", FacesContext.getCurrentInstance()));
        yesNoRadio = true;
        }catch(DadoInvalidoException die){
            die.print();
        }
    }

    public void inicializar() {
        try {
            e.setEmissao(new Date());
            e.setTipoLancamentoBancario(TipoLancamentoBancario.LANCAMENTO);
            cotacaoPadrao = new CotacaoDAO().porMoeda(serviceConf.buscar().getMoedaPadrao()).naMaiorEmissao(e.getEmissao()).porCotacaoEmpresa().resultado();
            cotacaoBancariaLista = new CotacaoDAO().naUltimaEmissao(e.getEmissao()).porCotacaoBancaria().listaDeResultados();
            contaComCotacaoBancaria = new ContaDAO().buscarContaW().comBanco().listaDeResultados();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void abrirDialogo() {
        exibeNaTela();
    }

    private void exibeNaTela() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("resizable", false);
        opcoes.put("width", 400);
        opcoes.put("draggable", true);
        opcoes.put("height", 520);
        opcoes.put("closable", false);
        opcoes.put("contentWidth", "100%");
        opcoes.put("contentHeight", "100%");
        opcoes.put("headerElement", "customheader");
        RequestContext.getCurrentInstance().openDialog("/dialogo/dialogoLancamentoBancario", opcoes, null);
    }

    @Override
    public void selecionar(SelectEvent event) {
        Object obj = (Object) event.getObject();
        if (obj instanceof TipoReceita) {
            e.setReceita((TipoReceita) obj);
        } else if (obj instanceof TipoDespesa) {
            e.setDespesa((TipoDespesa) obj);
        }
    }

    public void lancar() {
        try {
            t = e.construir();
            t.geraBaixaDeLancamento(e.getCotacaoDeConta());
            new AdicionaDAO<>().adiciona(t);
            RequestContext.getCurrentInstance().closeDialog(t);
            limparJanela();
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void fechar() {
        limparJanela();
        RequestContext.getCurrentInstance().closeDialog(null);
    }

    public void selecionaCotacaoDeConta() {
        if (e.getConta() != null) {
            e.setCotacaoDeConta(cotacaoBancariaLista.stream().filter(c -> c.getConta().equals(e.getConta())).findFirst().get());
        }
    }

    public Cotacao getCotacaoPadrao() {
        return cotacaoPadrao;
    }

    public void setCotacaoPadrao(Cotacao cotacaoPadrao) {
        this.cotacaoPadrao = cotacaoPadrao;
    }

    public List<Conta> getContaComCotacaoBancaria() {
        return contaComCotacaoBancaria;
    }

    public void setContaComCotacaoBancaria(List<Conta> contaComCotacaoBancaria) {
        this.contaComCotacaoBancaria = contaComCotacaoBancaria;
    }

    public List<Cotacao> getCotacaoBancariaLista() {
        return cotacaoBancariaLista;
    }

    public void setCotacaoBancariaLista(List<Cotacao> cotacaoBancariaLista) {
        this.cotacaoBancariaLista = cotacaoBancariaLista;
    }

    public ConfiguracaoService getServiceConf() {
        return serviceConf;
    }

    public void setServiceConf(ConfiguracaoService serviceConf) {
        this.serviceConf = serviceConf;
    }

    public Boolean getYesNoRadio() {
        return yesNoRadio;
    }

    public void setYesNoRadio(Boolean yesNoRadio) {
        this.yesNoRadio = yesNoRadio;
    }

}
