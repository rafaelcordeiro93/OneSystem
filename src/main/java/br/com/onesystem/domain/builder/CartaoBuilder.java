package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.Cartao;
import br.com.onesystem.domain.TipoDespesa;
import br.com.onesystem.domain.TaxaDeAdministracao;
import br.com.onesystem.exception.DadoInvalidoException;
import java.util.List;

/**
 *
 * @author Rafael
 */
public class CartaoBuilder {

    private Long id;
    private String nome;
    private Conta conta;
    private TipoDespesa despesa;
    private TipoDespesa juros;
    private List<TaxaDeAdministracao> taxaDeAdministracao;

    public CartaoBuilder comID(Long ID) {
        this.id = ID;
        return this;
    }

    public CartaoBuilder comNome(String nome) {
        this.nome = nome;
        return this;
    }

    public CartaoBuilder comConta(Conta conta) {
        this.conta = conta;
        return this;
    }

    public CartaoBuilder comDespesa(TipoDespesa despesa) {
        this.despesa = despesa;
        return this;
    }

    public CartaoBuilder comJuros(TipoDespesa juros) {
        this.juros = juros;
        return this;
    }
    
    public CartaoBuilder comTaxaDeAdministracao(List<TaxaDeAdministracao> taxaDeAdministracao) {
        this.taxaDeAdministracao = taxaDeAdministracao;
        return this;
    }

    public Cartao construir() throws DadoInvalidoException {
        return new Cartao(id, nome, conta, despesa, juros, taxaDeAdministracao);
    }

}
