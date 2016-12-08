/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.CharacterType;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.valueobjects.TipoComissao;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author Rafael Fernando Rauber
 */
@Entity
@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "SEQ_GRUPODECOMISSAO",
        sequenceName = "SEQ_GRUPODECOMISSAO")
public class GrupoDeComissao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GRUPODECOMISSAO")
    private Long id;
    @NotNull(message = "{nome_not_null}")
    @Length(max = 60, min = 4, message = "{nome_length}")
    @Column(nullable = false, length = 60)
    private String nome;
    @NotNull(message = "{tipo_comissao_not_null}")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoComissao tipoComissao;

    public GrupoDeComissao(Long id, String nome, TipoComissao tipoComissao) throws DadoInvalidoException {
        this.id = id;
        this.nome = nome;
        this.tipoComissao = tipoComissao;
        ehValido();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public TipoComissao getTipoComissao() {
        return tipoComissao;
    }
    
    private void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("nome", "tipoComissao");
        new ValidadorDeCampos<GrupoDeComissao>().valida(this, campos);
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof GrupoDeComissao)) {
            return false;
        }
        GrupoDeComissao outro = (GrupoDeComissao) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

    @Override
    public String toString() {
        return "GrupoDeComissao{" + "id=" + id + ", nome=" + nome + '}';
    }

}
