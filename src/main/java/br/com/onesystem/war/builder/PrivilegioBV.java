package br.com.onesystem.war.builder;

import br.com.onesystem.domain.GrupoDePrivilegio;
import br.com.onesystem.domain.Janela;
import br.com.onesystem.domain.Privilegio;
import java.io.Serializable;

public class PrivilegioBV implements Serializable {

    private Long id;
    private Janela janela;
    private boolean consultar = false;
    private boolean adicionar = false;
    private boolean alterar = false;
    private boolean remover = false;
    private GrupoDePrivilegio grupoPrivilegio;

    public PrivilegioBV() {
    }

    public PrivilegioBV(Privilegio privilegio) {
        this.id = privilegio.getId();
        this.janela = privilegio.getJanela();
        this.consultar = privilegio.isConsultar();
        this.adicionar = privilegio.isAdicionar();
        this.alterar = privilegio.isAlterar();
        this.remover = privilegio.isRemover();
        this.grupoPrivilegio = privilegio.getGrupoPrivilegio();
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

   
    public Privilegio construir() {
        return new Privilegio(id, janela, consultar, adicionar, alterar, remover, grupoPrivilegio);
    }

    public Privilegio construirComId() {
        return new Privilegio(id, janela, consultar, adicionar, alterar, remover, grupoPrivilegio);
    }

}
