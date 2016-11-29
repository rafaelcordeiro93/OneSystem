/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.valueobjects.OperacaoFisica;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Rafael
 */
@Entity
@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "SEQ_CONTADEESTOQUE",
        sequenceName = "SEQ_CONTADEESTOQUE")
public class ContaDeEstoque implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_CONTADEESTOQUE", strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull(message = "{operacao_not_null}")
    @ManyToOne
    private Operacao operacao;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{operacao_fisica_not_null}")
    private OperacaoFisica operacaoFisica;
        
    
    
    
}
