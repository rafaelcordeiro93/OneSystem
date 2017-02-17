/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.util.BundleUtil;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

@Entity
@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "SEQ_COLUNA",
        sequenceName = "SEQ_COLUNA")
public class Coluna implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_COLUNA")
    private Long id;
    @NotNull(message = "{key_not_null}")
    
    private String key;
    private String nome;
    @ManyToOne
    private ModeloDeRelatorio modelo;

    public Coluna() {
    }

    public Coluna(Long id, String key, ModeloDeRelatorio modelo) throws DadoInvalidoException {
        this.id = id;
        this.key = key;
        this.nome = new BundleUtil().getLabel(key);
        this.modelo = modelo;
        ehValido();
    }

    public final void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("key");
        new ValidadorDeCampos<Coluna>().valida(this, campos);
    }

    public Long getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getNome() {
        return nome;
    }

    public ModeloDeRelatorio getModelo() {
        return modelo;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Coluna other = (Coluna) obj;
        if (!Objects.equals(this.key, other.key)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Coluna{" + "id=" + id + ", key=" + key + ", nome=" + nome + ", modelo=" + modelo + '}';
    }

}
