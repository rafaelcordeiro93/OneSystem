/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.ValidadorDeCampos;
import br.com.onesystem.valueobjects.TipoImpressao;
import br.com.onesystem.valueobjects.TipoLayout;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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
 * @author Rafael
 */
@Entity
@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "SEQ_LAYOUTDEIMPRESSAO",
        sequenceName = "SEQ_LAYOUTDEIMPRESSAO")
public class LayoutDeImpressao implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_LAYOUTDEIMPRESSAO", strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull(message = "{tipoLayout_not_null}")
    @Enumerated(EnumType.STRING)
    private TipoLayout tipoLayout;
    @Length(max = 255, min = 0, message = "{layoutGrafico_lenght}")
    @Column(length = 255)
    private String layoutGrafico;
    private String layoutTexto;
    @NotNull(message = "{layoutGraficoEhPadrao_not_null}")
    private boolean layoutGraficoEhPadrao;
    @NotNull(message = "{tipoImpressao_not_null}")
    @Enumerated(EnumType.STRING)
    private TipoImpressao tipoImpressao;

    public LayoutDeImpressao() {
    }

    public LayoutDeImpressao(Long id, TipoLayout tipoLayout, String layoutGrafico, String layoutTexto, boolean layoutGraficoEhPadrao, TipoImpressao tipoImpressao) throws DadoInvalidoException {
        this.id = id;
        this.tipoLayout = tipoLayout;
        this.layoutGrafico = layoutGrafico;
        this.layoutTexto = layoutTexto;
        this.layoutGraficoEhPadrao = layoutGraficoEhPadrao;
        this.tipoImpressao = tipoImpressao;
        ehValido();
    }

    private void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("tipoLayout", "layoutGrafico", "layoutGraficoEhPadrao", "tipoImpressao");
        new ValidadorDeCampos<LayoutDeImpressao>().valida(this, campos);
    }

    public Long getId() {
        return id;
    }

    public TipoLayout getTipoLayout() {
        return tipoLayout;
    }

    public String getLayoutGrafico() {
        return layoutGrafico;
    }

    public String getLayoutTexto() {
        return layoutTexto;
    }

    public boolean isLayoutGraficoEhPadrao() {
        return layoutGraficoEhPadrao;
    }

    public TipoImpressao getTipoImpressao() {
        return tipoImpressao;
    }
    
    @Override
    public String toString() {
        return "LayoutDeImpressao{" + "id=" + id + ", tipoLayout=" + tipoLayout + ", layoutGrafico=" + layoutGrafico + ", layoutTexto=" + layoutTexto + ", layoutGraficoEhPadrao=" + layoutGraficoEhPadrao + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
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
        final LayoutDeImpressao other = (LayoutDeImpressao) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
