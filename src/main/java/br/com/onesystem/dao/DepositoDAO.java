package br.com.onesystem.dao;

import br.com.onesystem.domain.Cambio;
import br.com.onesystem.domain.Deposito;
import br.com.onesystem.domain.Deposito;
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

public class DepositoDAO {

    private String consulta;
    private BundleUtil msg;
    private Map<String, Object> parametros;

    public DepositoDAO() {
        limpar();
    }

    private void limpar() {
        consulta = "";
        msg = new BundleUtil();
        parametros = new HashMap<String, Object>();
    }

    public DepositoDAO buscarDepositos() {
        consulta += "select d from Deposito d where d.id > 0 ";
        return this;
    }

    public DepositoDAO porNome(Deposito deposito) {
        consulta += " and d.nome = :dNome ";
        parametros.put("dNome", deposito.getNome());
        return this;
    }

    public List<Deposito> listaDeResultados() {
        List<Deposito> resultado = new ArmazemDeRegistros<Deposito>(Deposito.class)
                .listaRegistrosDaConsulta(consulta, parametros);
        limpar();
        return resultado;
    }
}
