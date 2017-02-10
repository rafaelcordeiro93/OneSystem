package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.ModeloDeRelatorio;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.TipoRelatorio;
import java.util.List;

/**
 *
 * @author Rafael
 */
public class TemplateRelatoriosBuilder {

    private Long id;
    private String nome;
    private List<String> listaDeCampos;
    private TipoRelatorio tipoRelatorio;

    public TemplateRelatoriosBuilder comID(Long ID) {
        this.id = ID;
        return this;
    }

    public TemplateRelatoriosBuilder comNome(String nome) {
        this.nome = nome;
        return this;
    }

    public TemplateRelatoriosBuilder comlistaDeCampos(List<String> listaDeCampos) {
        this.listaDeCampos = listaDeCampos;
        return this;
    }

    public TemplateRelatoriosBuilder comTipoRelatorio(TipoRelatorio tipoRelatorio) {
        this.tipoRelatorio = tipoRelatorio;
        return this;
    }

    public ModeloDeRelatorio construir() throws DadoInvalidoException {
        return new ModeloDeRelatorio(id, nome, listaDeCampos, tipoRelatorio);
    }

}
