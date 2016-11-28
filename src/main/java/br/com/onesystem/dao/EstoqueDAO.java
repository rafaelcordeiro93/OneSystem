package br.com.onesystem.dao;

import br.com.onesystem.domain.Cambio;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.Estoque;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.valueobjects.UnidadeFinanceira;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EstoqueDAO {

    private String consulta;
    private BundleUtil msg;
    private Map<String, Object> parametros;

    public EstoqueDAO() {
        limpar();
    }

    private void limpar() {
        consulta = "";
        msg = new BundleUtil();
        parametros = new HashMap<String, Object>();
    }

    public EstoqueDAO buscarEstoques() {
        consulta += "select e from Estoque e ";
        return this;
    }


    public EstoqueDAO agrupadoPorItem() {
        consulta += "group by e.item.nome";
        return this;
    }
    
    public EstoqueDAO orderByItem(){
        consulta += "order by e.item.nome ";
        return this;
    }

    public List<Estoque> listaDeResultados() {
        List<Estoque> resultado = new ArmazemDeRegistros<Estoque>(Estoque.class)
                .listaRegistrosDaConsulta(consulta, parametros);
        limpar();
        return resultado;
    }

    
}
