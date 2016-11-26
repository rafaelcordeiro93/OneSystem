/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.onesystem.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author Rafael
 */
@Entity
@SequenceGenerator(sequenceName = "SEQ_OPERACAO", initialValue = 1, allocationSize = 1,
        name = "SEQ_OPERACAO")
public class Operacao implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_OPERACAO", strategy = GenerationType.SEQUENCE)
    private Long id;
    
    @NotNull(message = "{nome_not_null}")
    @Length(min = 4, max = 80, message = "{nome_lenght}")
    private String nome;
    
    public Operacao() {
    }
    
}
