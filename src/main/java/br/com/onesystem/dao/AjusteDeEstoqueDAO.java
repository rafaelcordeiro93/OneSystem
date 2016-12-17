package br.com.onesystem.dao;

import br.com.onesystem.domain.AjusteDeEstoque;
import br.com.onesystem.domain.Cambio;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.Estoque;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.valueobjects.OperacaoFisica;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AjusteDeEstoqueDAO {

    private String consulta;
    private BundleUtil msg;
    private Map<String, Object> parametros;

    public AjusteDeEstoqueDAO() {
        limpar();
    }

    private void limpar() {
        consulta = "";
        msg = new BundleUtil();
        parametros = new HashMap<String, Object>();
    }

    public AjusteDeEstoqueDAO buscarAjustesDeEstoque() {
        consulta += "select a from AjusteDeEstoque a where a.id != 0 ";
        return this;
    }

    public AjusteDeEstoqueDAO porItem(Item item) {
        consulta += " and a.item = :pItem ";
        parametros.put("pItem", item);
        return this;
    }

    public AjusteDeEstoqueDAO ordenadoPorEmissaoDescrescente(){
        consulta += " order by a.emissao desc ";
        return this;
    }    
    
    public List<AjusteDeEstoque> listaDeResultados() {
        List<AjusteDeEstoque> resultado = new ArmazemDeRegistros<AjusteDeEstoque>(AjusteDeEstoque.class)
                .listaRegistrosDaConsulta(consulta, parametros);
        limpar();
        return resultado;
    }

}
