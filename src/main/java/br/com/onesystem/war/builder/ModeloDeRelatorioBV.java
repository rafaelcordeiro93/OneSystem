package br.com.onesystem.war.builder;

import br.com.onesystem.domain.ModeloDeRelatorio;
import br.com.onesystem.domain.builder.TemplateRelatoriosBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.TipoRelatorio;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ModeloDeRelatorioBV implements Serializable {

    private Long id;
    private String nome;
    private List<String> listaDeCampos = new ArrayList<String>();
    private TipoRelatorio tipoRelatorio;

    public ModeloDeRelatorioBV(ModeloDeRelatorio modeloSelecionado) {
        this.id = modeloSelecionado.getId();
        this.nome = modeloSelecionado.getNome();
        this.listaDeCampos = modeloSelecionado.getKey();
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

    public List<String> getListaDeCampos() {
        return listaDeCampos;
    }

    public void setListaDeCampos(List<String> listaDeCampos) {
        this.listaDeCampos = listaDeCampos;
    }

    public TipoRelatorio getTipoRelatorio() {
        return tipoRelatorio;
    }

    public void setTipoRelatorio(TipoRelatorio tipoRelatorio) {
        this.tipoRelatorio = tipoRelatorio;
    }

    public ModeloDeRelatorio construir() throws DadoInvalidoException {
        return new TemplateRelatoriosBuilder().comNome(nome).comlistaDeCampos(listaDeCampos).comTipoRelatorio(tipoRelatorio).construir();
    }

    public ModeloDeRelatorio construirComID() throws DadoInvalidoException {
        return new TemplateRelatoriosBuilder().comID(id).comNome(nome).comlistaDeCampos(listaDeCampos).comTipoRelatorio(tipoRelatorio).construir();
    }
}
