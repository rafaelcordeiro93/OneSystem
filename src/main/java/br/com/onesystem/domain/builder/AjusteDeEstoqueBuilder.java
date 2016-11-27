package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.AjusteDeEstoque;
import br.com.onesystem.domain.Banco;
import br.com.onesystem.domain.Deposito;
import br.com.onesystem.domain.Item;
import br.com.onesystem.exception.DadoInvalidoException;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Rafael
 */
public class AjusteDeEstoqueBuilder {

    private Long id;
    private String observacao;
    private Deposito deposito;
    private Item item;
    private BigDecimal quantidade;
    private Date emissao;

    public AjusteDeEstoqueBuilder comID(Long ID) {
        this.id = ID;
        return this;
    }

    public AjusteDeEstoqueBuilder comObservacao(String observacao) {
        this.observacao = observacao;
        return this;
    }
    
    public AjusteDeEstoqueBuilder comDeposito(Deposito deposito){
        this.deposito = deposito;
        return this;
    }

    public AjusteDeEstoqueBuilder comItem(Item item){
        this.item = item;
        return this;
    }
    
    public AjusteDeEstoqueBuilder comQuantidade(BigDecimal quantidade){
        this.quantidade = quantidade;
        return this;
    }
    
    public AjusteDeEstoqueBuilder comEmissao(Date emissao){
        this.emissao = emissao;
        return this;
    }
  
    public AjusteDeEstoque construir() throws DadoInvalidoException {
        return new AjusteDeEstoque(id, observacao, item, quantidade, deposito,emissao);
    }

}
