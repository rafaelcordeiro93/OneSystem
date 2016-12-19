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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author Rafael Fernando Rauber
 */
@Entity
@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "SEQ_COMISSAO",
        sequenceName = "SEQ_COMISSAO")
public class Comissao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_COMISSAO")
    private Long id;
    @NotNull(message = "{nome_not_null}")
    @Length(max = 60, min = 4, message = "{nome_length}")
    @Column(nullable = false, length = 60)
    private String nome;
    private BigDecimal comissaoVendedor;
    @Min(value = 0, message = "{comissao_representante_min}")
    private BigDecimal comissaoRepresentante;
    @OneToMany(mappedBy = "comissao")
    private List<Item> itens;

    public Comissao() {
    }

    public Comissao(Long id, String nome, BigDecimal comissaoVendedor,
            BigDecimal comissaoRepresentante) throws DadoInvalidoException {
        this.id = id;
        this.nome = nome;
        this.comissaoVendedor = comissaoVendedor;
        this.comissaoRepresentante = comissaoRepresentante;
        ehValido();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getComissaoVendedor() {
        if(comissaoVendedor == null){
            return BigDecimal.ZERO;
        }
        return comissaoVendedor;
    }

    public BigDecimal getComissaoRepresentante() {
        if(comissaoRepresentante == null){
            return BigDecimal.ZERO;
        }
        return comissaoRepresentante;
    }

    private void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("nome", "comissaoVendedor", "comissaoRepresentante");
        new ValidadorDeCampos<Comissao>().valida(this, campos);
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof Comissao)) {
            return false;
        }
        Comissao outro = (Comissao) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

    @Override
    public String toString() {
        return "Comissao{" + "id=" + id + ", nome=" + nome + ", tipoComissao=" + comissaoVendedor + ", comissaoRepresentante=" + comissaoRepresentante + '}';
    }

}
