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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author Rafael Fernando Rauber
 */
@Entity
@SequenceGenerator(allocationSize = 1, initialValue = 1, name = "SEQ_GRUPODEMARGEM",
        sequenceName = "SEQ_GRUPODEMARGEM")
public class GrupoDeMargem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GRUPODEMARGEM")
    private Long id;
    @NotNull(message = "{nome_not_null}")
    @Length(min = 2, max = 60, message = "{nome_length}")
    @Column(length = 60, nullable = false)
    private String nome;
    @Min(value = 0, message = "{margem_min}")
    private BigDecimal margem;
    @Min(value = 0, message = "{custo_fixo_min}")
    private BigDecimal custoFixo;
    @Min(value = 0, message = "{frete_min}")
    private BigDecimal frete;
    @Min(value = 0, message = "{outros_custos_min}")
    private BigDecimal outrosCustos;
    @Min(value = 0, message = "{embalagem_min}")
    private BigDecimal embalagem;
    @OneToMany(mappedBy = "margem")
    private List<Item> itens;
    
    public GrupoDeMargem() {
    }

    public GrupoDeMargem(Long id, String nome, BigDecimal margem, BigDecimal custoFixo, BigDecimal frete, BigDecimal outrosCustos, BigDecimal embalagem) throws DadoInvalidoException {
        this.id = id;
        this.nome = nome;
        this.margem = margem;
        this.custoFixo = custoFixo;
        this.frete = frete;
        this.outrosCustos = outrosCustos;
        this.embalagem = embalagem;
        ehValido();        
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getMargem() {
        return margem;
    }

    public BigDecimal getCustoFixo() {
        return custoFixo;
    }

    public BigDecimal getFrete() {
        return frete;
    }

    public BigDecimal getOutrosCustos() {
        return outrosCustos;
    }

    public BigDecimal getEmbalagem() {
        return embalagem;
    }

    public List<Item> getItens() {
        return itens;
    }
    
    public final void ehValido() throws DadoInvalidoException {
        List<String> campos = Arrays.asList("nome","margem","custoFixo","frete","outrosCustos","embalagem");
        new ValidadorDeCampos<GrupoDeMargem>().valida(this, campos);
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof GrupoDeMargem)) {
            return false;
        }
        GrupoDeMargem outro = (GrupoDeMargem) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

    @Override
    public String toString() {
        return "GrupoDeMargem{" + "id=" + id + ", nome=" + nome + ", margem=" + margem + ", custoFixo=" + custoFixo + ", frete=" + frete + ", outrosCustos=" + outrosCustos + ", embalagem=" + embalagem + '}';
    }

}
