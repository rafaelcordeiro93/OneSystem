package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Cartao;
import br.com.onesystem.domain.TaxaDeAdministracao;
import br.com.onesystem.domain.builder.TaxaDeAdministracaoBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import java.io.Serializable;
import java.math.BigDecimal;

public class TaxaDeAdministracaoBV implements Serializable {

  private Long id;
    private Integer numeroParcelas;
    private BigDecimal taxa;
    private Cartao cartao;
    

    public TaxaDeAdministracaoBV(TaxaDeAdministracao taxaDeAdministracaoSelecionada) {
        this.id = taxaDeAdministracaoSelecionada.getId();
        this.numeroParcelas = taxaDeAdministracaoSelecionada.getNumeroParcelas();
        this.taxa = taxaDeAdministracaoSelecionada.getTaxa();
        this.cartao = taxaDeAdministracaoSelecionada.getCartao();
    
    }

    public TaxaDeAdministracaoBV() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumeroParcelas() {
        return numeroParcelas;
    }

    public void setNumeroParcelas(Integer numeroParcelas) {
        this.numeroParcelas = numeroParcelas;
    }

    public BigDecimal getTaxa() {
        return taxa;
    }

    public void setTaxa(BigDecimal taxa) {
        this.taxa = taxa;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public void setCartao(Cartao cartao) {
        this.cartao = cartao;
    }

    
    public TaxaDeAdministracao construir() throws DadoInvalidoException {
        return new TaxaDeAdministracaoBuilder().comNumeroDeParcelas(numeroParcelas).comTaxa(taxa).comCartao(cartao).construir();
    }

    public TaxaDeAdministracao construirComID() throws DadoInvalidoException {
        return new TaxaDeAdministracaoBuilder().comID(id).comNumeroDeParcelas(numeroParcelas).comTaxa(taxa).comCartao(cartao).construir();
    }
}
