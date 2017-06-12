package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.Margem;
import br.com.onesystem.exception.DadoInvalidoException;
import java.math.BigDecimal;

/**
 *
 * @author Rafael
 */
public class GrupoDeMargemBuilder {

    private Long ID;
    private String nome;
    private BigDecimal margem;
    private BigDecimal custoFixo;
    private BigDecimal frete;
    private BigDecimal outrosCustos;
    private BigDecimal embalagem;

    public GrupoDeMargemBuilder comID(Long ID) {
        this.ID = ID;
        return this;
    }

    public GrupoDeMargemBuilder comNome(String nombre) {
        this.nome = nombre;
        return this;
    }

    public GrupoDeMargemBuilder comMargem(BigDecimal margem) {
        this.margem = margem;
        return this;
    }
    
    public GrupoDeMargemBuilder comCustoFixo(BigDecimal custoFixo) {
        this.custoFixo = custoFixo;
        return this;
    }
    
    public GrupoDeMargemBuilder comFrete(BigDecimal frete) {
        this.frete = frete;
        return this;
    }
    
    public GrupoDeMargemBuilder comOutrosCustos(BigDecimal outrosCustos) {
        this.outrosCustos = outrosCustos;
        return this;
    }
    
    public GrupoDeMargemBuilder comEmbalagem(BigDecimal embalagem) {
        this.embalagem = embalagem;
        return this;
    }
  
    public Margem construir() throws DadoInvalidoException {
        return new Margem(ID, nome,margem, custoFixo, frete, outrosCustos, embalagem);
    }

}
