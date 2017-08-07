package br.com.onesystem.dao;

import br.com.onesystem.domain.Cambio;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.Estoque;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.PrecoDeItem;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.valueobjects.OperacaoFisica;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrecoDeItemDAO {

    private String consulta;
    private BundleUtil msg;
    private Map<String, Object> parametros;

    public PrecoDeItemDAO() {
        limpar();
    }

    private void limpar() {
        consulta = "";
        msg = new BundleUtil();
        parametros = new HashMap<String, Object>();
    }

    public PrecoDeItemDAO buscarPrecos() {
        consulta += "select p from PrecoDeItem p where p.id != 0 ";
        return this;
    }

    public PrecoDeItemDAO porItem(Item item) {
        consulta += " and p.item = :pItem ";
        parametros.put("pItem", item);
        return this;
    }

    public PrecoDeItemDAO eEmissao(Date emissao) {
        consulta += " and p.emissao = :pEmissao ";
        parametros.put("pEmissao", emissao);
        return this;
    }

    public PrecoDeItemDAO eNaoExpirado() {
        consulta += " and p.dataDeExpiracao >= :pDataDeExpiracao ";
        parametros.put("pDataDeExpiracao", new Date());
        return this;
    }

    public PrecoDeItemDAO ordenadoPorEmissaoDecrescente() {
        consulta += " order by emissao desc ";
        return this;
    }

    public List<PrecoDeItem> listaDeResultados() {
        List<PrecoDeItem> resultado = new ArmazemDeRegistros<PrecoDeItem>(PrecoDeItem.class)
                .listaRegistrosDaConsulta(consulta, parametros);
        limpar();
        return resultado;
    }

}
