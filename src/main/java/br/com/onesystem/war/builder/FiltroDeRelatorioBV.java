package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Coluna;
import br.com.onesystem.domain.FiltroDeRelatorio;
import br.com.onesystem.domain.ModeloDeRelatorio;
import br.com.onesystem.domain.ParametroDeFiltroDeRelatorio;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.TipoDeBusca;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FiltroDeRelatorioBV implements Serializable {

    private Long id;
    private Coluna coluna;
    private List<ParametroDeFiltroDeRelatorio> parametros = new ArrayList<>();
    private TipoDeBusca tipoDaBusca;
    private Date filtroDeData;
    private ModeloDeRelatorio modelo;

    public FiltroDeRelatorioBV() {
    }

    public FiltroDeRelatorioBV(FiltroDeRelatorio f) {
        this.id = f.getId();
        this.coluna = f.getColuna();
        this.parametros = f.getParametros();
        this.tipoDaBusca = f.getTipoDaBusca();
        this.filtroDeData = f.getFiltroDeData();
        this.modelo = f.getModelo();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Coluna getColuna() {
        return coluna;
    }

    public void setColuna(Coluna coluna) {
        this.coluna = coluna;
    }

    public List<ParametroDeFiltroDeRelatorio> getParametros() {
        return parametros;
    }

    public void setParametros(List<ParametroDeFiltroDeRelatorio> parametros) {
        this.parametros = parametros;
    }

    public TipoDeBusca getTipoDaBusca() {
        return tipoDaBusca;
    }

    public void setTipoDaBusca(TipoDeBusca tipoDaBusca) {
        this.tipoDaBusca = tipoDaBusca;
    }

    public Date getFiltroDeData() {
        return filtroDeData;
    }

    public void setFiltroDeData(Date filtroDeData) {
        this.filtroDeData = filtroDeData;
    }

    public ModeloDeRelatorio getModelo() {
        return modelo;
    }

    public void setModelo(ModeloDeRelatorio modelo) {
        this.modelo = modelo;
    }

    public FiltroDeRelatorio construir() {
        return new FiltroDeRelatorio(null, coluna, tipoDaBusca, filtroDeData, modelo, parametros);
    }

    public FiltroDeRelatorio construirComID() throws DadoInvalidoException {
        return new FiltroDeRelatorio(id, coluna, tipoDaBusca, filtroDeData, modelo, parametros);
    }

}
