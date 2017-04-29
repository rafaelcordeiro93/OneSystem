/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.valueobjects.EstadoDeOrcamento;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author Rafael Fernando Rauber
 */
@Entity
@SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "SEQ_HISTORICO_ORCAMENTO",
        name = "SEQ_HISTORICO_ORCAMENTO")
public class HistoricoDeOrcamento implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_HISTORICO_ORCAMENTO", strategy = GenerationType.SEQUENCE)
    private Long id;
    @Enumerated(EnumType.STRING)
    private EstadoDeOrcamento estado;
    @Length(max = 1000, min = 0, message = "{historico_lenght}")
    @Column(length = 1000)
    private String historico;
    @ManyToOne
    private Usuario usuario;
    @ManyToOne
    private Orcamento orcamento;

    public HistoricoDeOrcamento() {
    }

    public HistoricoDeOrcamento(Long id, EstadoDeOrcamento estado, String historico, Usuario usuario, Orcamento orcamento) {
        this.id = id;
        this.estado = estado;
        this.historico = historico;
        this.usuario = usuario;
        this.orcamento = orcamento;
    }

    public Long getId() {
        return id;
    }

    public EstadoDeOrcamento getEstado() {
        return estado;
    }

    public String getHistorico() {
        return historico;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Orcamento getOrcamento() {
        return orcamento;
    }

}
