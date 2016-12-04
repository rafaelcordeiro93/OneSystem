/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Rafael Fernando Rauber
 */
@Entity
@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "SEQ_TABELADECOMISSAO",
        sequenceName = "SEQ_TABELADECOMISSAO")
public class TabelaDeComissao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TABELADECOMISSAO")
    private Long id;
    @NotNull
    @Min(value = 0)
    @Column(nullable = false)
    private BigDecimal porcentagemDeDesconto;
    @NotNull
    @Min(value = 0)
    @Column(nullable = false)
    private BigDecimal porcentagemDeComissao;

    public TabelaDeComissao(Long id, BigDecimal porcentagemDeDesconto, BigDecimal porcentagemDeComissao) throws DadoInvalidoException {
        this.id = id;
        this.porcentagemDeDesconto = porcentagemDeDesconto;
        this.porcentagemDeComissao = porcentagemDeComissao;
        ehValido();
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getPorcentagemDeDesconto() {
        return porcentagemDeDesconto;
    }

    public BigDecimal getPorcentagemDeComissao() {
        return porcentagemDeComissao;
    }
    
     private void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("nome", "tipoComissao");
        new ValidadorDeCampos<TabelaDeComissao>().valida(this, campos);
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof TabelaDeComissao)) {
            return false;
        }
        TabelaDeComissao outro = (TabelaDeComissao) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

    @Override
    public String toString() {
        return "TabelaDeComissao{" + "id=" + id + ", porcentagemDeDesconto=" + porcentagemDeDesconto + ", porcentagemDeComissao=" + porcentagemDeComissao + '}';
    }
   
}
