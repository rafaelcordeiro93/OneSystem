package br.com.onesystem.services;

import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.valueobjects.TipoFormaPagRec;
import br.com.onesystem.valueobjects.TipoOperacao;
import br.com.onesystem.valueobjects.UnidadeFinanceira;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Rafael-Pc
 */
public interface Movimento {

    public Long getIdOrigem();
    
    public String getOrigem();

    public BigDecimal getValorBaixado();
    
    public Date getVencimento();
    
    public Date getUltimoPagamento();
}
