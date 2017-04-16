package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.CartaoDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.Cartao;
import br.com.onesystem.domain.Configuracao;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.Despesa;
import br.com.onesystem.domain.TaxaDeAdministracao;
import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.war.builder.CartaoBV;
import br.com.onesystem.war.builder.TaxaDeAdministracaoBV;
import br.com.onesystem.war.service.ConfiguracaoService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceException;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class CartaoView implements Serializable {

    private CartaoBV cartao;
    private Cartao cartaoSelecionada;
    private TaxaDeAdministracaoBV taxa;
    private TaxaDeAdministracao taxaSelecionado;
    private List<TaxaDeAdministracao> listaTaxaDeletada;
    private Configuracao configuracao;

    @Inject
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
            Cartao novoRegistro = cartao.construir();
            preparaParaInclusaoDeTaxa(novoRegistro);
            new AdicionaDAO<Cartao>().adiciona(novoRegistro);
            InfoMessage.adicionado();
            limparJanela();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    private void preparaParaInclusaoDeTaxa(Cartao novoRegistro) {
        for (TaxaDeAdministracao t : novoRegistro.getTaxaDeAdministracao()) {
            t.preparaInclusao(novoRegistro);
        }
    }

    public void update() {
        try {

            if (cartaoSelecionada != null) {
                Cartao cartaoExistente = cartao.construirComID();
                preparaParaInclusaoDeTaxa(cartaoExistente);
                new AtualizaDAO<>().atualiza(cartaoExistente);
                deletarTaxas();
                InfoMessage.atualizado();
                limparJanela();
            } else {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("cartao_nao_encontrado"));
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    private void deletarTaxas() throws PersistenceException, DadoInvalidoException {
        for (TaxaDeAdministracao lista : listaTaxaDeletada) {
            new RemoveDAO<>().remove(lista, lista.getId());
        }
    }

    public void delete() {
        try {
            if (cartaoSelecionada != null) {
                new RemoveDAO<>().remove(cartaoSelecionada, cartaoSelecionada.getId());
                InfoMessage.removido();
                limparJanela();
            }
        } catch (DadoInvalidoException di) {
            di.print();
        } catch (ConstraintViolationException pe) {
            FatalMessage.print(pe.getMessage(), pe.getCause());
        }
    }

    public void selecionaConta(SelectEvent event) {
        Conta contaSelecionado = (Conta) event.getObject();
        cartao.setConta(contaSelecionado);
    }

    public void selecionaDespesa(SelectEvent event) {
        Despesa despesaSelecionado = (Despesa) event.getObject();
        cartao.setDespesa(despesaSelecionado);
    }

    public void selecionaJuros(SelectEvent event) {
        Despesa despesaSelecionado = (Despesa) event.getObject();
        cartao.setJuros(despesaSelecionado);
    }

    public void selecionaCartao(SelectEvent e) {
        Cartao a = (Cartao) e.getObject();
        cartao = new CartaoBV(a);
        cartaoSelecionada = a;
    }

    public void buscaPorId() {
        Long id = cartao.getId();
        if (id != null) {
            try {
                CartaoDAO dao = new CartaoDAO();
                Cartao c = dao.buscarCartaos().porId(id).resultado();
                cartaoSelecionada = c;
                cartao = new CartaoBV(cartaoSelecionada);
            } catch (DadoInvalidoException die) {
                limparJanela();
                cartao.setId(id);
                die.print();
            }
        }
    }

    public void selecionaTaxa(SelectEvent event) {
        this.taxaSelecionado = (TaxaDeAdministracao) event.getObject();
        this.taxa = new TaxaDeAdministracaoBV(taxaSelecionado);
    }

    public void addTaxaNaLista() {
        try {
            taxa.setId(retornarCodigo());
            for (TaxaDeAdministracao lista : cartao.getTaxaDeAdministracao()) {
                if (taxa.getNumeroDias().equals(lista.getNumeroDias())) {
                    throw new EDadoInvalidoException(new BundleUtil().getMessage("Parcela_Ja_Existe"));
                }
            }
            cartao.getTaxaDeAdministracao().add(taxa.construirComID());
            limparTaxaDeAdministracao();
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void updateTaxaNaLista() {
        try {
            if (taxaSelecionado != null) {

                cartao.getTaxaDeAdministracao().set(cartao.getTaxaDeAdministracao().indexOf(taxaSelecionado),
                        taxa.construirComID());
                limparTaxaDeAdministracao();
            }
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void deleteTaxaNaLista() {
        if (taxaSelecionado != null) {
            listaTaxaDeletada.add(taxaSelecionado);
            cartao.getTaxaDeAdministracao().remove(taxaSelecionado);
            limparTaxaDeAdministracao();
        }
    }

    public void limparTaxaDeAdministracao() {
        taxa = new TaxaDeAdministracaoBV();
        taxaSelecionado = null;
    }

    private Long retornarCodigo() {
        Long id = (long) 1;
        if (!cartao.getTaxaDeAdministracao().isEmpty()) {
            for (TaxaDeAdministracao dp : cartao.getTaxaDeAdministracao()) {
                if (dp.getId() >= id) {
                    id = dp.getId() + 1;
                }
            }
        }
        return id;
    }

    public void limparJanela() {
        cartao = new CartaoBV();
        taxa = new TaxaDeAdministracaoBV();
        listaTaxaDeletada = new ArrayList<TaxaDeAdministracao>();
        cartaoSelecionada = null;
    }

    public void desfazer() {
        if (cartaoSelecionada != null) {
            cartao = new CartaoBV(cartaoSelecionada);
        }
    }

    public CartaoBV getCartao() {
        return cartao;
    }

    public void setCartao(CartaoBV cartao) {
        this.cartao = cartao;
    }

    public Cartao getCartaoSelecionada() {
        return cartaoSelecionada;
    }

    public void setCartaoSelecionada(Cartao cartaoSelecionada) {
        this.cartaoSelecionada = cartaoSelecionada;
    }

    public TaxaDeAdministracaoBV getTaxa() {
        return taxa;
    }

    public void setTaxa(TaxaDeAdministracaoBV taxa) {
        this.taxa = taxa;
    }

    public TaxaDeAdministracao getTaxaSelecionado() {
        return taxaSelecionado;
    }

    public void setTaxaSelecionado(TaxaDeAdministracao taxaSelecionado) {
        this.taxaSelecionado = taxaSelecionado;
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
