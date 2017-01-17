package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.FormaDeRecebimento;
import br.com.onesystem.domain.FormaDeRecebimentoOuPagamento;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.exception.DadoInvalidoException;
import java.math.BigDecimal;

/**
 *
 * @author Rafael
 */
public class FormaDeRecebimentoOuPagamentoBuilder {

    private Long id;
    private Moeda moeda;
    private FormaDeRecebimento formaDeRecebimento;
    private Integer parcelas;
    private BigDecimal dinheiro;
    private BigDecimal credito;
    private BigDecimal cheque;
    private BigDecimal cartao;
    private BigDecimal aFaturar;
    private NotaEmitida notaEmitida;

    public FormaDeRecebimentoOuPagamentoBuilder comID(Long ID) {
        this.id = ID;
        return this;
    }

    public FormaDeRecebimentoOuPagamentoBuilder comFormaDeRecebimento(FormaDeRecebimento formaDeRecebimento) {
        this.formaDeRecebimento = formaDeRecebimento;
        return this;
    }

    public FormaDeRecebimentoOuPagamentoBuilder comParcelas(Integer parcelas) {
        this.parcelas = parcelas;
        return this;
    }
    
    public FormaDeRecebimentoOuPagamentoBuilder comMoeda(Moeda moeda) {
        this.moeda = moeda;
        return this;
    }
    
    public FormaDeRecebimentoOuPagamentoBuilder comDinheiro(BigDecimal dinheiro) {
        this.dinheiro = dinheiro;
        return this;
    }
    
    public FormaDeRecebimentoOuPagamentoBuilder comCredito(BigDecimal credito) {
        this.credito = credito;
        return this;
    }
    
    public FormaDeRecebimentoOuPagamentoBuilder comCheque(BigDecimal cheque) {
        this.cheque = cheque;
        return this;
    }
    
    public FormaDeRecebimentoOuPagamentoBuilder comCartao(BigDecimal cartao) {
        this.cartao = cartao;
        return this;
    }
    
     public FormaDeRecebimentoOuPagamentoBuilder comAFaturar(BigDecimal aFaturar) {
        this.aFaturar = aFaturar;
        return this;
    }
     
     public FormaDeRecebimentoOuPagamentoBuilder comNotaEmitida(NotaEmitida notaEmitida){
         this.notaEmitida = notaEmitida;
         return this;
     }

    public FormaDeRecebimentoOuPagamento construir() throws DadoInvalidoException {
        return new FormaDeRecebimentoOuPagamento(id, formaDeRecebimento, parcelas, dinheiro, credito, cheque, cartao, aFaturar, notaEmitida, moeda);
    }

}
