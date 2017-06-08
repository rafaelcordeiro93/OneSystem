/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.util.FormatFisrtCaracterUpperCase;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author Rauber
 */
@Entity
@SequenceGenerator(name = "SEQ_UNIDADEMEDIDAMERCADORIA", sequenceName = "SEQ_UNIDADEMEDIDAMERCADORIA",
        initialValue = 1, allocationSize = 1)
public class UnidadeMedidaItem implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_UNIDADEMEDIDAMERCADORIA", strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull(message = "{sigla_not_null}")
    @Length(min = 1, max = 4, message = "{sigla_length}")
    @Column(length = 3, nullable = false, unique = true)
    private String sigla;
    @NotNull(message = "{nome_not_null}")
    @Length(min = 3, max = 60, message = "{nome_lenght}")
    @Column(nullable = false, length = 60)
    private String nome;
    @NotNull(message = "{casa_decimal_not_null}")
    @Max(value = 5, message = "{casa_decimal_max}")
    @Min(value = 0, message = "{casa_decimal_min}")
    @Column(nullable = false)
    private Integer casasDecimais;
    @OneToMany(mappedBy = "unidadeDeMedida")
    private List<Item> itens;

    public UnidadeMedidaItem() {
    }

    public UnidadeMedidaItem(Long id, String nome, String sigla, Integer casasDecimais) throws DadoInvalidoException {
        this.id = id;
        this.nome = FormatFisrtCaracterUpperCase.format(nome);        
        this.sigla = sigla.toUpperCase();
        this.casasDecimais = casasDecimais;
        ehValido();
    }

    public Long getId() {
        return id;
    }

    public String getSigla() {
        return sigla;
    }

    public String getNome() {
        return nome;
    }

    public Integer getCasasDecimais() {
        return casasDecimais;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UnidadeMedidaItem other = (UnidadeMedidaItem) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "UnidadeMedidaItem"
                + " - Unidade de Medida: " + sigla
                + " - Nome: " + nome;
    }

    private void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("nome", "sigla", "casasDecimais");
        new ValidadorDeCampos<UnidadeMedidaItem>().valida(this, campos);
    }
}
