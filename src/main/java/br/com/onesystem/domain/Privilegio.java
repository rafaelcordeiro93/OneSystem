/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author Rafael-Pc
 */
@Entity
@SequenceGenerator(initialValue = 1, allocationSize = 1, sequenceName = "SEQ_PRIVILEGIO", name = "SEQ_PRIVILEGIO")
public class Privilegio implements Serializable {

    @Id
    @GeneratedValue(generator = "SEQ_PRIVILEGIO", strategy = GenerationType.SEQUENCE)
    private Long id;
    @OneToOne
    private Janela janela;
    private boolean consultar = false;
    private boolean adicionar = false;
    private boolean alterar = false;
    private boolean remover = false;
    @ManyToOne(optional = false)
    private GrupoDePrivilegio grupoPrivilegio;

    public Privilegio() {
    }

    public Privilegio(Long id, Janela janela, boolean consultar, boolean adicionar, boolean alterar, boolean remover,
            GrupoDePrivilegio grupoPrivilegio) {
        this.id = id;
        this.janela = janela;
        this.consultar = consultar;
        this.adicionar = adicionar;
        this.alterar = alterar;
        this.remover = remover;
        this.grupoPrivilegio = grupoPrivilegio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Janela getJanela() {
        return janela;
    }

    public void setJanela(Janela janela) {
        this.janela = janela;
    }

    public boolean isConsultar() {
        return consultar;
    }

    public void setConsultar(boolean consultar) {
        this.consultar = consultar;
    }

    public boolean isAdicionar() {
        return adicionar;
    }

    public void setAdicionar(boolean adicionar) {
        this.adicionar = adicionar;
    }

    public boolean isAlterar() {
        return alterar;
    }

    public void setAlterar(boolean alterar) {
        this.alterar = alterar;
    }

    public boolean isRemover() {
        return remover;
    }

    public void setRemover(boolean remover) {
        this.remover = remover;
    }

    public GrupoDePrivilegio getGrupoPrivilegio() {
        return grupoPrivilegio;
    }

    public void setGrupoPrivilegio(GrupoDePrivilegio grupoPrivilegio) {
        this.grupoPrivilegio = grupoPrivilegio;
    }

    @Override
    public String toString() {
        return "Privilegio{" + "id=" + id + ", janela=" + janela + ", consultar=" + consultar + ", adicionar=" + adicionar + ", alterar=" + alterar + ", remover=" + remover + ", grupoPrivilegio=" + grupoPrivilegio + '}';
    }
    
    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof Privilegio)) {
            return false;
        }
        Privilegio outro = (Privilegio) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.getId());
    }
}
