package br.com.onesystem.services.impl;

import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.valueobjects.TipoOperacao;
import java.math.BigDecimal;
import java.util.Date;

public interface RelatorioContaAbertaImpl {

    public Long getId();
    
    public Pessoa getPessoa();
    
    public Date getEmissao();
    
    public String getOrigem();
    
    public Date getVencimento();
    
    public BigDecimal getValorBaixado();
    
    public BigDecimal getSaldo();
    
    public BigDecimal getValor();
    
    public Moeda getMoeda();
}
