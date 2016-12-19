package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Comissao;
import br.com.onesystem.exception.DadoInvalidoException;
import java.math.BigDecimal;

/**
 *
 * @author Rafael
 */
public class ComissaoBuilder {

    private Long id;
    private String nome;
    private BigDecimal comissaoVendedor;
    private BigDecimal comissaoRepresentante;


    public ComissaoBuilder comID(Long ID) {
        this.id = ID;
        return this;
    }

    public ComissaoBuilder comNome(String nome) {
        this.nome = nome;
        return this;
    }
    
    public ComissaoBuilder comComissaoVendedor(BigDecimal comissaoVendedor){
        this.comissaoVendedor = comissaoVendedor;
        return this;
    }
    
    public ComissaoBuilder comComissaoRepresentante(BigDecimal comissaoRepresentante){
        this.comissaoRepresentante = comissaoRepresentante;
        return this;
    }

      public Comissao construir() throws DadoInvalidoException {
        return new Comissao(id, nome, comissaoVendedor, comissaoRepresentante);
    }

}
