package br.com.onesystem.dao;

import br.com.onesystem.domain.Cambio;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.Titulo;
import br.com.onesystem.reportTemplate.SomaSaldoDeTituloPorMoedaReportTemplate;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoedaDAO {

    private String consulta;
    private BundleUtil msg;
    private Map<String, Object> parametros;

    public MoedaDAO() {
        limpar();
    }

    private void limpar() {
        consulta = "";
        msg = new BundleUtil();
        parametros = new HashMap<String, Object>();
    }

    public MoedaDAO buscarMoedas() {
        consulta += "select g from Moeda g where g.id > 0 ";
        return this;
    }

    public MoedaDAO porNome(Moeda moeda) {
        consulta += " and g.nome = :pNome ";
        parametros.put("pNome", moeda.getNome());
        return this;
    }
    
    public MoedaDAO porSigla(Moeda moeda) {
        consulta += " and g.sigla = :pSigla ";
        parametros.put("pSigla", moeda.getSigla());
        return this;
    }

    public List<Moeda> listaDeResultados() {
        List<Moeda> resultado = new ArmazemDeRegistros<Moeda>(Moeda.class)
                .listaRegistrosDaConsulta(consulta, parametros);
        limpar();
        return resultado;
    }
}
