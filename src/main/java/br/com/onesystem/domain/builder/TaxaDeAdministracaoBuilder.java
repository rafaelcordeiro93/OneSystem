package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Cartao;
import br.com.onesystem.domain.TaxaDeAdministracao;
import br.com.onesystem.exception.DadoInvalidoException;
import java.math.BigDecimal;

/**
 *
 * @author Rafael
 */
public class TaxaDeAdministracaoBuilder {

    private Long id;
    private Integer numeroParcelas;
    private BigDecimal taxa;
    private Cartao cartao;

    public TaxaDeAdministracaoBuilder comID(Long ID) {
        this.id = ID;
        return this;
    }

    public TaxaDeAdministracaoBuilder comNumeroDeParcelas(Integer numeroParcelas) {
        this.numeroParcelas = numeroParcelas;
        return this;
    }

    public TaxaDeAdministracaoBuilder comTaxa(BigDecimal taxa) {
        this.taxa = taxa;
        return this;
    }
    
    public TaxaDeAdministracaoBuilder comCartao(Cartao cartao) {
        this.cartao = cartao;
        return this;
    }

    public TaxaDeAdministracao construir() throws DadoInvalidoException {
        return new TaxaDeAdministracao(id, numeroParcelas, taxa, cartao);
    }

}
