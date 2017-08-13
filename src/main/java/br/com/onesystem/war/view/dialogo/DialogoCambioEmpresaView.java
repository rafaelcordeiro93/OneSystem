package br.com.onesystem.war.view.dialogo;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.ContaDAO;
import br.com.onesystem.dao.CotacaoDAO;
import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.CambioEmpresa;
import br.com.onesystem.domain.TipoDespesa;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.Model;
import br.com.onesystem.util.ModelList;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.war.builder.BaixaBV;
import br.com.onesystem.war.builder.CambioEmpresaBV;
import br.com.onesystem.war.service.ConfiguracaoService;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.math.BigDecimal;
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
public class DialogoCambioEmpresaView extends BasicMBImpl<CambioEmpresa, CambioEmpresaBV> implements Serializable {

    private BaixaBV baixaBV;
    private ModelList<Baixa> baixas;
    private Model baixaSelecionada;
    private Cotacao cotacaoPadrao;
    private List<Conta> contaComCotacaoEmpresa;
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
        e = new CambioEmpresaBV();
        baixas = new ModelList<>();
        limparBaixa();
    }

    public void inicializar() {
        try {
            e.setEmissao(new Date());
            cotacaoPadrao = new CotacaoDAO().porMoeda(serviceConf.buscar().getMoedaPadrao()).naMaiorEmissao(e.getEmissao()).porCotacaoEmpresa().resultado();
            cotacaoEmpresaLista = new CotacaoDAO().naMaiorEmissao(e.getEmissao()).porCotacaoEmpresa().listaDeResultados();
            contaComCotacaoEmpresa = new ContaDAO().buscarContaW().semBanco().ePorMoedas(cotacaoEmpresaLista.stream().map(c -> c.getConta().getMoeda()).collect(Collectors.toList())).listaDeResultados();
        } catch (DadoInvalidoException die) {
        }
    }

    public void abrirDialogo() {
        exibeNaTela();
    }

    private void exibeNaTela() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("resizable", false);
        opcoes.put("width", 720);
        opcoes.put("draggable", true);
        opcoes.put("height", 475);
        opcoes.put("closable", false);
        opcoes.put("contentWidth", "100%");
        opcoes.put("contentHeight", "100%");
        opcoes.put("headerElement", "customheader");

        RequestContext.getCurrentInstance().openDialog("/dialogo/dialogoCambioEmpresa", opcoes, null);
    }

    @Override
    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        if (obj instanceof TipoDespesa) {
            baixaBV.setDespesa((TipoDespesa) obj);
        } else if (obj instanceof Model) {
            baixaSelecionada = (Model) obj;
            baixaBV = new BaixaBV((Baixa) baixaSelecionada.getObject());
        }
    }

    public void cambiar() {
        try {
            t = e.construir();
            t.geraBaixaDeSaque(e.getCotacaoDeOrigem(), e.getCotacaoDeDestino());
            baixas.forEach(b -> t.adiciona((Baixa) b.getObject()));

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

    public void addBaixa() {
        try {
            if (baixaBV.getDespesa() != null) {
                if (baixaBV.getValor() != null && baixaBV.getValor().compareTo(BigDecimal.ZERO) > 0) {
                    baixaBV.setEmissao(e.getEmissao());
                    baixaBV.setNaturezaFinanceira(OperacaoFinanceira.SAIDA);
                    baixaBV.setCotacao(e.getCotacaoDeOrigem());
                    baixas.add(baixaBV.construirComID());
                    limparBaixa();
                } else {
                    throw new EDadoInvalidoException(new BundleUtil().getMessage("valor_not_null"));
                }
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void deleteBaixa() {
        if (baixas != null && baixaSelecionada != null) {
            baixas.remove(baixaSelecionada);
            limparBaixa();
        }
    }

    public void atualizaBaixa() {
        if (baixas != null && baixaSelecionada != null) {
            try {
                baixaSelecionada.setObject(baixaBV.construirComID());
                baixas.set(baixaSelecionada);
                limparBaixa();
            } catch (DadoInvalidoException ex) {
                ex.print();
            }
        }
    }

    public void limparBaixa() {
        baixaBV = new BaixaBV();
        baixaSelecionada = null;
    }

    public void selecionaCotacaoDeOrigemConformeConta() {
        if (e.getOrigem() != null) {
            e.setCotacaoDeOrigem(cotacaoEmpresaLista.stream().filter(c -> c.getConta().equals(e.getOrigem())).findFirst().get());
        }
    }

    public void selecionaCotacaoDeDestinoConformeConta() {
        if (e.getDestino() != null) {
            e.setCotacaoDeDestino(cotacaoEmpresaLista.stream().filter(c -> c.getConta().equals(e.getDestino())).findFirst().get());
        }
    }

    public Cotacao getCotacaoPadrao() {
        return cotacaoPadrao;
    }

    public void setCotacaoPadrao(Cotacao cotacaoPadrao) {
        this.cotacaoPadrao = cotacaoPadrao;
    }

    public List<Conta> getContaComCotacaoEmpresa() {
        return contaComCotacaoEmpresa;
    }

    public void setContaComCotacaoEmpresa(List<Conta> contaComCotacaoEmpresa) {
        this.contaComCotacaoEmpresa = contaComCotacaoEmpresa;
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

    public BaixaBV getBaixaBV() {
        return baixaBV;
    }

    public void setBaixaBV(BaixaBV baixaBV) {
        this.baixaBV = baixaBV;
    }

    public ModelList<Baixa> getBaixas() {
        return baixas;
    }

    public void setBaixas(ModelList<Baixa> baixas) {
        this.baixas = baixas;
    }

    public Model getBaixaSelecionada() {
        return baixaSelecionada;
    }

    public void setBaixaSelecionada(Model baixaSelecionada) {
        this.baixaSelecionada = baixaSelecionada;
    }

}
