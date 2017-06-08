package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Cartao;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.TipoDespesa;
import br.com.onesystem.domain.TaxaDeAdministracao;
import br.com.onesystem.domain.builder.CartaoBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.BuilderView;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CartaoBV implements Serializable, BuilderView<Cartao> {

    private Long id;
    private String nome;
    private Conta conta; 
    private TipoDespesa despesa;
    private TipoDespesa juros;
   private List<TaxaDeAdministracao> taxaDeAdministracao = new ArrayList<TaxaDeAdministracao>();

    public CartaoBV(Cartao cartaoSelecionada) {
        this.id = cartaoSelecionada.getId();
        this.nome = cartaoSelecionada.getNome();
        this.conta = cartaoSelecionada.getConta();
        this.despesa = cartaoSelecionada.getDespesa();
        this.juros = cartaoSelecionada.getJuros();
        this.taxaDeAdministracao = cartaoSelecionada.getTaxaDeAdministracao();

    }

    public CartaoBV() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public TipoDespesa getDespesa() {
        return despesa;
    }

    public void setDespesa(TipoDespesa despesa) {
        this.despesa = despesa;
    }

    public TipoDespesa getJuros() {
        return juros;
    }

    public void setJuros(TipoDespesa juros) {
        this.juros = juros;
    }

    public List<TaxaDeAdministracao> getTaxaDeAdministracao() {
        return taxaDeAdministracao;
    }

    public void setTaxaDeAdministracao(List<TaxaDeAdministracao> taxaDeAdministracao) {
        this.taxaDeAdministracao = taxaDeAdministracao;
    }

    public Cartao construir() throws DadoInvalidoException {
        return new CartaoBuilder().comNome(nome).comConta(conta).comDespesa(despesa).comJuros(juros).comTaxaDeAdministracao(taxaDeAdministracao).construir();
    }

    public Cartao construirComID() throws DadoInvalidoException {
        return new CartaoBuilder().comID(id).comNome(nome).comConta(conta).comDespesa(despesa).comJuros(juros).comTaxaDeAdministracao(taxaDeAdministracao).construir();
    }
}
