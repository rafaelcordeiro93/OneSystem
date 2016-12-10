package br.com.onesystem.dao;

import br.com.onesystem.domain.Cambio;
import br.com.onesystem.domain.Cidade;
import br.com.onesystem.domain.Cidade;
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

public class CidadeDAO {

    private String consulta;
    private BundleUtil msg;
    private Map<String, Object> parametros;

    public CidadeDAO() {
        limpar();
    }

    private void limpar() {
        consulta = "";
        msg = new BundleUtil();
        parametros = new HashMap<String, Object>();
    }

    public CidadeDAO buscarCidades() {
        consulta += "select c from Cidade c where c.id > 0 ";
        return this;
    }

    public CidadeDAO porNome(Cidade cidade) {
        consulta += " and c.nome = :cNome ";
        parametros.put("cNome", cidade.getNome());
        return this;
    }

    public List<Cidade> listaDeResultados() {
        List<Cidade> resultado = new ArmazemDeRegistros<Cidade>(Cidade.class)
                .listaRegistrosDaConsulta(consulta, parametros);
        limpar();
        return resultado;
    }
}
