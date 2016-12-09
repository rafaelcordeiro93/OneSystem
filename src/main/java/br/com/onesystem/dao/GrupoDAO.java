package br.com.onesystem.dao;

import br.com.onesystem.domain.Cambio;
import br.com.onesystem.domain.Grupo;
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

public class GrupoDAO {

    private String consulta;
    private BundleUtil msg;
    private Map<String, Object> parametros;

    public GrupoDAO() {
        limpar();
    }

    private void limpar() {
        consulta = "";
        msg = new BundleUtil();
        parametros = new HashMap<String, Object>();
    }

    public GrupoDAO buscarGrupos() {
        consulta += "select g from Grupo g where g.id > 0 ";
        return this;
    }

    public GrupoDAO porNome(Grupo grupo) {
        consulta += " and g.nome = :pNome ";
        parametros.put("pNome", grupo.getNome());
        return this;
    }

    public List<Grupo> listaDeResultados() {
        List<Grupo> resultado = new ArmazemDeRegistros<Grupo>(Grupo.class)
                .listaRegistrosDaConsulta(consulta, parametros);
        limpar();
        return resultado;
    }
}
