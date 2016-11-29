package br.com.onesystem.dao;

import br.com.onesystem.domain.Cambio;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.Estoque;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.valueobjects.OperacaoFisica;
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
    
    public EstoqueDAO wDeposito() {
        consulta += "where e from Estoque e ";
        return this;
    }
    
    

    public EstoqueDAO agrupadoPorDeposito() {
        consulta += "group by e.deposito";
        return this;
    }

    public EstoqueDAO agrupadoPorItem() {
        consulta += "group by e.item.nome";
        return this;
    }

    public EstoqueDAO orderByItem() {
        consulta += "order by e.item.nome ";
        return this;
    }

    public EstoqueDAO wbuscarTotalDeSaidas() {
        parametros.put("pSaldo", BigDecimal.ZERO);
        parametros.put("pOperacao", OperacaoFisica.SAIDA);
        consulta += "select sum(e.total) from Estoque e where e.saldo >= :psaldo and e.tipo == pOperacao ";
        return this;
    }
    
    public EstoqueDAO wbuscarTotalDeEntradas() {
        parametros.put("pSaldo", BigDecimal.ZERO);
        parametros.put("pOperacao", OperacaoFisica.ENTRADA);
        consulta += "select sum(e.total) from Estoque e where e.saldo >= :psaldo and e.tipo == pOperacao ";
        return this;
    }

    public BigDecimal buscarSaldoFinal() {

       // BigDecimal entradas
           //     = BigDecimal saidas
         //       = BigDecimal resultado = entradas.subtract(saidas);

      //  return resultado == null ? BigDecimal.ZERO : resultado;
            return null;
    }

    public BigDecimal resultadoSomaTotal() {
        BigDecimal resultado = new ArmazemDeRegistros<BigDecimal>(BigDecimal.class)
                .resultadoUnicoDaConsulta(consulta, parametros);
        limpar();
        return resultado == null ? BigDecimal.ZERO : resultado;
    }

    public List<Estoque> listaDeResultados() {
        List<Estoque> resultado = new ArmazemDeRegistros<Estoque>(Estoque.class)
                .listaRegistrosDaConsulta(consulta, parametros);
        limpar();
        return resultado;
    }

}
