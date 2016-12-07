/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.domain.GrupoDeMargem;
import br.com.onesystem.domain.IVA;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author Rafael Fernando Rauber
 */
@Entity
@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "SEQ_GRUPOFISCAL",
        sequenceName = "SEQ_GRUPOFISCAL")
public class GrupoFiscal implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GRUPOFISCAL")
    private Long id;
    @NotNull(message = "{nome_not_null}")
    @Length(min = 4, max = 120, message = "{nome_lenght}")
    @Column(nullable = false, length = 120)
    private String nome;
    @NotNull(message = "{IVA_not_null}")
    @ManyToOne(optional = false)
    private IVA iva;

    public GrupoFiscal() {
    }

    public GrupoFiscal(Long id, String nome, IVA iva) throws DadoInvalidoException {
        this.id = id;
        this.nome = nome;
        this.iva = iva;
        ehValido();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public IVA getIva() {
        return iva;
    }

    @Override
    public String toString() {
        return "GrupoFiscal{" + "id=" + id + ", nome=" + nome + ", iva=" + iva + '}';
    }

    public final void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("nome", "iva");
        new ValidadorDeCampos<GrupoFiscal>().valida(this, campos);
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof GrupoFiscal)) {
            return false;
        }
        GrupoFiscal outro = (GrupoFiscal) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

}
