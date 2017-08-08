package br.com.onesystem.war.view.dialogo;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.ContaDAO;
import br.com.onesystem.dao.CotacaoDAO;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.DepositoBancario;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.TipoLancamentoBancario;
import br.com.onesystem.war.builder.DepositoBancarioBV;
import br.com.onesystem.war.service.ConfiguracaoService;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@Named
@ViewScoped
public class DialogoDepositoBancarioView extends BasicMBImpl<DepositoBancario, DepositoBancarioBV> implements Serializable {

    private Cotacao cotacaoPadrao;
    private List<Conta> contaComCotacaoBancaria;
    private List<Conta> contaComCotacaoEmpresa;
    private List<Cotacao> cotacaoBancariaLista;
    private List<Cotacao> cotacaoEmpresaLista;

    @Inject
    private ConfiguracaoService serviceConf;

    @PostConstruct
    public void init() {
        limparJanela();
        inicializar();
    }

    @Override
    public void limparJanela() {
        t = null;
        e = new DepositoBancarioBV();
        e.setTipoLancamentoBancario(TipoLancamentoBancario.LANCAMENTO);
    }

    public void inicializar() {
        try {
            e.setEmissao(new Date());
            cotacaoPadrao = new CotacaoDAO().porMoeda(serviceConf.buscar().getMoedaPadrao()).naMaiorEmissao(e.getEmissao()).porCotacaoEmpresa().resultado();
            cotacaoEmpresaLista = new CotacaoDAO().naMaiorEmissao(e.getEmissao()).porCotacaoEmpresa().listaDeResultados();
            cotacaoBancariaLista = new CotacaoDAO().naUltimaEmissao(e.getEmissao()).porCotacaoBancaria().listaDeResultados();
            contaComCotacaoEmpresa = new ContaDAO().buscarContaW().semBanco().ePorMoedas(cotacaoEmpresaLista.stream().map(c -> c.getConta().getMoeda()).collect(Collectors.toList())).listaDeResultados();
            contaComCotacaoBancaria = new ContaDAO().buscarContaW().comBanco().ePorMoedas(cotacaoBancariaLista.stream().map(c -> c.getConta().getMoeda()).collect(Collectors.toList())).listaDeResultados();
        } catch (DadoInvalidoException die) {
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
        opcoes.put("height", 475);
        opcoes.put("closable", false);
        opcoes.put("contentWidth", "100%");
        opcoes.put("contentHeight", "100%");
        opcoes.put("headerElement", "customheader");

        RequestContext.getCurrentInstance().openDialog("/dialogo/dialogoDepositoBancario", opcoes, null);
    }

    @Override
    public void selecionar(SelectEvent event) {
    }

    public void depositar() {
        try {
            t = e.construir();
            t.geraBaixaDeDeposito(e.getCotacaoDeOrigem(), e.getCotacaoDeDestino());
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

    public void selecionaCotacaoDeOrigemConformeConta() {
        if (e.getOrigem() != null) {
            e.setCotacaoDeOrigem(cotacaoEmpresaLista.stream().filter(c -> c.getConta().equals(e.getOrigem())).findFirst().get());
        }
    }

    public void selecionaCotacaoDeDestinoConformeConta() {
        if (e.getDestino() != null) {
            e.setCotacaoDeDestino(cotacaoBancariaLista.stream().filter(c -> c.getConta().equals(e.getDestino())).findFirst().get());
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

    public List<Conta> getContaComCotacaoEmpresa() {
        return contaComCotacaoEmpresa;
    }

    public void setContaComCotacaoEmpresa(List<Conta> contaComCotacaoEmpresa) {
        this.contaComCotacaoEmpresa = contaComCotacaoEmpresa;
    }

    public List<Cotacao> getCotacaoBancariaLista() {
        return cotacaoBancariaLista;
    }

    public void setCotacaoBancariaLista(List<Cotacao> cotacaoBancariaLista) {
        this.cotacaoBancariaLista = cotacaoBancariaLista;
    }

    public List<Cotacao> getCotacaoEmpresaLista() {
        return cotacaoEmpresaLista;
    }

    public void setCotacaoEmpresaLista(List<Cotacao> cotacaoEmpresaLista) {
        this.cotacaoEmpresaLista = cotacaoEmpresaLista;
    }

    public ConfiguracaoService getServiceConf() {
        return serviceConf;
    }

    public void setServiceConf(ConfiguracaoService serviceConf) {
        this.serviceConf = serviceConf;
    }

}
