package br.com.onesystem.dao;

import br.com.onesystem.domain.Cambio;
import br.com.onesystem.domain.IVA;
import br.com.onesystem.domain.IVA;
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

public class IvaDAO {

    private String consulta;
    private BundleUtil msg;
    private Map<String, Object> parametros;

    public IvaDAO() {
        limpar();
    }

    private void limpar() {
        consulta = "";
        msg = new BundleUtil();
        parametros = new HashMap<String, Object>();
    }

    public IvaDAO buscarIVA() {
        consulta += "select d from IVA d where d.id > 0 ";
        return this;
    }

    public IvaDAO porNome(IVA deposito) {
        consulta += " and d.nome = :dNome ";
        parametros.put("dNome", deposito.getNome());
        return this;
    }

    public List<IVA> listaDeResultados() {
        List<IVA> resultado = new ArmazemDeRegistros<IVA>(IVA.class)
                .listaRegistrosDaConsulta(consulta, parametros);
        limpar();
        return resultado;
    }
}
