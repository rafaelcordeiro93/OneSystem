package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.BoletoDeCartao;
import br.com.onesystem.domain.Cartao;
import br.com.onesystem.domain.Configuracao;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.war.builder.BoletoDeCartaoBV;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.valueobjects.TipoSituacao;
import br.com.onesystem.war.service.ConfiguracaoService;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.SelectEvent;

@ManagedBean
@ViewScoped
public class BoletoDeCartaoView implements Serializable {

    private BoletoDeCartaoBV boletoDeCartao;
    private BoletoDeCartao boletoDeCartaoSelecionada;
    private Configuracao configuracao;

    @ManagedProperty("#{configuracaoService}")
    private ConfiguracaoService serviceConfigurcao;

    @PostConstruct
    public void init() {
        limparJanela();
        inicializarConfiguracoes();
    }

    private void inicializarConfiguracoes() {
        try {
            configuracao = serviceConfigurcao.buscar();
            if (configuracao.getMoedaPadrao() == null) {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("Configuracao_nao_definida"));
            }
        } catch (EDadoInvalidoException ex) {
            ex.print();
        }
    }

    public void add() {
        try {
            BoletoDeCartao novoRegistro = boletoDeCartao.construir();
            System.out.println(novoRegistro);
            new AdicionaDAO<BoletoDeCartao>().adiciona(novoRegistro);
            InfoMessage.adicionado();
            limparJanela();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void update() {
        try {

            if (boletoDeCartaoSelecionada != null) {
                BoletoDeCartao boletoDeCartaoExistente = boletoDeCartao.construirComID();
                new AtualizaDAO<BoletoDeCartao>(BoletoDeCartao.class).atualiza(boletoDeCartaoExistente);
                InfoMessage.atualizado();
                limparJanela();
            } else {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("boleto_de_cartao_nao_encontrado"));
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void delete() {
        try {
            if (boletoDeCartaoSelecionada != null) {
                new RemoveDAO<BoletoDeCartao>(BoletoDeCartao.class).remove(boletoDeCartaoSelecionada, boletoDeCartaoSelecionada.getId());
                InfoMessage.removido();
                limparJanela();
            }
        } catch (DadoInvalidoException di) {
            di.print();
        } catch (ConstraintViolationException pe) {
            FatalMessage.print(pe.getMessage(), pe.getCause());
        }
    }

    public void selecionaVenda(SelectEvent event) {
        NotaEmitida notaSelecionado = (NotaEmitida) event.getObject();
        boletoDeCartao.setVenda(notaSelecionado);
    }

    public void selecionaCartao(SelectEvent event) {
        Cartao cartaoSelecionado = (Cartao) event.getObject();
        boletoDeCartao.setCartao(cartaoSelecionado);
    }

    public void selecionaBoletoDeCartao(SelectEvent e) {
        BoletoDeCartao a = (BoletoDeCartao) e.getObject();
        boletoDeCartao = new BoletoDeCartaoBV(a);
        boletoDeCartaoSelecionada = a;
    }

    public List<TipoSituacao> getTipoSituacao() {
        return Arrays.asList(TipoSituacao.values());
    }

    public void limparJanela() {
        boletoDeCartao = new BoletoDeCartaoBV();
        boletoDeCartaoSelecionada = null;
    }

    public void desfazer() {
        if (boletoDeCartaoSelecionada != null) {
            boletoDeCartao = new BoletoDeCartaoBV(boletoDeCartaoSelecionada);
        }
    }

    public BoletoDeCartaoBV getBoletoDeCartao() {
        return boletoDeCartao;
    }

    public void setBoletoDeCartao(BoletoDeCartaoBV boletoDeCartao) {
        this.boletoDeCartao = boletoDeCartao;
    }

    public BoletoDeCartao getBoletoDeCartaoSelecionada() {
        return boletoDeCartaoSelecionada;
    }

    public void setBoletoDeCartaoSelecionada(BoletoDeCartao boletoDeCartaoSelecionada) {
        this.boletoDeCartaoSelecionada = boletoDeCartaoSelecionada;
    }

    public Configuracao getConfiguracao() {
        return configuracao;
    }

    public void setConfiguracao(Configuracao configuracao) {
        this.configuracao = configuracao;
    }

    public ConfiguracaoService getServiceConfigurcao() {
        return serviceConfigurcao;
    }

    public void setServiceConfigurcao(ConfiguracaoService serviceConfigurcao) {
        this.serviceConfigurcao = serviceConfigurcao;
    }

}
