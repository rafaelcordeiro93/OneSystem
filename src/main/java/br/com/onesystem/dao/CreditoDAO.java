package br.com.onesystem.dao;

import br.com.onesystem.domain.Credito;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreditoDAO {

    private String consulta;
    private BundleUtil msg;
    private Map<String, Object> parametros;

    public CreditoDAO() {
        limpar();
    }

    private void limpar() {
        consulta = "";
        msg = new BundleUtil();
        parametros = new HashMap<String, Object>();
    }

    public CreditoDAO buscarCreditos() {
        consulta += "select c from Credito c where c.id > 0 ";
        return this;
    }
    
    public CreditoDAO porPessoa(Pessoa pessoa){
        consulta += " and c.pessoa = :pPessoa";
        parametros.put("pPessoa", pessoa);
        return this;
    }
    
    public List<Credito> listaDeResultados() {
        List<Credito> resultado = new ArmazemDeRegistros<Credito>(Credito.class)
                .listaRegistrosDaConsulta(consulta, parametros);
        limpar();
        return resultado;
    }

}
