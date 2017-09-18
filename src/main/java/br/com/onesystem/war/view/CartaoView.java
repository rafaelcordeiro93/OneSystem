package br.com.onesystem.war.view;

import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.Cartao;
import br.com.onesystem.domain.Configuracao;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.TipoDespesa;
import br.com.onesystem.domain.TaxaDeAdministracao;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.war.builder.CartaoBV;
import br.com.onesystem.war.builder.TaxaDeAdministracaoBV;
import br.com.onesystem.war.service.ConfiguracaoService;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceException;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class CartaoView extends BasicMBImpl<Cartao, CartaoBV> implements Serializable {

    // private CartaoBV cartao;
    // private Cartao cartaoSelecionada;
    private TaxaDeAdministracaoBV taxa;
    private TaxaDeAdministracao taxaSelecionado;
    private List<TaxaDeAdministracao> listaTaxaDeletada;

    @Inject
    private Configuracao configuracao;

    @Inject
    private ConfiguracaoService serviceConfigurcao;

    @Inject
    private RemoveDAO<TaxaDeAdministracao> removeDAO;

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
            Cartao novoRegistro = e.construir();
            preparaParaInclusaoDeTaxa(novoRegistro);
            addNoBanco(novoRegistro);
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
            Cartao cartaoExistente = e.construirComID();
            preparaParaInclusaoDeTaxa(cartaoExistente);
            updateNoBanco(cartaoExistente);
            deletarTaxas();
        } catch (DadoInvalidoException ex) {
            ex.print();
        }

    }

    private void deletarTaxas() throws PersistenceException, DadoInvalidoException {
        for (TaxaDeAdministracao lista : listaTaxaDeletada) {
            removeDAO.remove(lista, lista.getId());
        }
    }

    @Override
    public void limparJanela() {
        e = new CartaoBV();
        taxa = new TaxaDeAdministracaoBV();
        taxaSelecionado = null;
        listaTaxaDeletada = new ArrayList<TaxaDeAdministracao>();
    }

    @Override
    public void selecionar(SelectEvent event) {
        Object obj = (Object) event.getObject();
        String idComponent = event.getComponent().getId();
        if (obj instanceof Cartao) {
            e = new CartaoBV((Cartao) obj);
        } else if (obj instanceof Conta) {
            e.setConta((Conta) obj);
        } else if (obj instanceof TipoDespesa && "despesasId-search".equals(idComponent)) {
            e.setDespesa((TipoDespesa) obj);
        } else if (obj instanceof TipoDespesa && "jurosId-search".equals(idComponent)) {
            e.setJuros((TipoDespesa) obj);
        }
    }

    public void selecionaTaxa(SelectEvent event) {
        this.taxaSelecionado = (TaxaDeAdministracao) event.getObject();
        this.taxa = new TaxaDeAdministracaoBV(taxaSelecionado);
    }

    public void addTaxaNaLista() {
        try {
            taxa.setId(retornarCodigo());
            for (TaxaDeAdministracao lista : e.getTaxaDeAdministracao()) {
                if (taxa.getNumeroDias().equals(lista.getNumeroDias())) {
                    throw new EDadoInvalidoException(new BundleUtil().getMessage("Parcela_Ja_Existe"));
                }
            }
            e.getTaxaDeAdministracao().add(taxa.construirComID());
            limparTaxaDeAdministracao();
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void updateTaxaNaLista() {
        try {
            if (taxaSelecionado != null) {
                e.getTaxaDeAdministracao().set(e.getTaxaDeAdministracao().indexOf(taxaSelecionado), taxa.construirComID());
                limparTaxaDeAdministracao();
            }
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void deleteTaxaNaLista() {
        if (taxaSelecionado != null) {
            listaTaxaDeletada.add(taxaSelecionado);
            e.getTaxaDeAdministracao().remove(taxaSelecionado);
            limparTaxaDeAdministracao();
        }
    }

    public void limparTaxaDeAdministracao() {
        taxa = new TaxaDeAdministracaoBV();
        taxaSelecionado = null;
    }

    private Long retornarCodigo() {
        Long id = (long) 1;
        if (!e.getTaxaDeAdministracao().isEmpty()) {
            for (TaxaDeAdministracao dp : e.getTaxaDeAdministracao()) {
                if (dp.getId() >= id) {
                    id = dp.getId() + 1;
                }
            }
        }
        return id;
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
