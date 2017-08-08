package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Coluna;
import br.com.onesystem.domain.ModeloDeRelatorio;
import br.com.onesystem.domain.builder.ModeloDeRelatorioBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.TipoRelatorio;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ModeloDeRelatorioBV implements Serializable {

    private Long id;
    private String nome;
    private List<Coluna> colunas = new ArrayList<>();
    private TipoRelatorio tipoRelatorio;

    public ModeloDeRelatorioBV(ModeloDeRelatorio modeloSelecionado) {
        this.id = modeloSelecionado.getId();
        this.nome = modeloSelecionado.getNome();
        this.colunas = modeloSelecionado.getColunasExibidas();
        this.tipoRelatorio = modeloSelecionado.getTipoRelatorio();
    }

    public ModeloDeRelatorioBV() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Coluna> getColunas() {
        return colunas;
    }

    public void setColunas(List<Coluna> colunas) {
        this.colunas = colunas;
    }

    public TipoRelatorio getTipoRelatorio() {
        return tipoRelatorio;
    }

    public void setTipoRelatorio(TipoRelatorio tipoRelatorio) {
        this.tipoRelatorio = tipoRelatorio;
    }

    public ModeloDeRelatorio construir() throws DadoInvalidoException {
        return new ModeloDeRelatorioBuilder().comNome(nome).comTipoRelatorio(tipoRelatorio).construir();
    }

    public ModeloDeRelatorio construirComID() throws DadoInvalidoException {
        return new ModeloDeRelatorioBuilder().comID(id).comNome(nome).comTipoRelatorio(tipoRelatorio).construir();
    }
}
