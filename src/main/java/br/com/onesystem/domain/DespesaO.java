/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.valueobjects.OperacaoFinanceira;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author rauber
 */
public abstract class DespesaO {
    
    private Long id;
    
    private String emissao;
    
    private Pessoa pessoa;
    
    private TipoDespesa tipoDespesa;
    
    private Cotacao cotacao;
    
    private String historico;
    
    private List<Baixa> baixas;
    
    private OperacaoFinanceira operacaoFinanceira;
    
    private BigDecimal valor;
}
