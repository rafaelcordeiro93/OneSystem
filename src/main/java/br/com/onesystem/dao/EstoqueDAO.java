package br.com.onesystem.dao;

import br.com.onesystem.domain.ContaDeEstoque;
import br.com.onesystem.domain.Estoque;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.Nota;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.valueobjects.TipoLancamento;
import br.com.onesystem.valueobjects.TipoOperacao;
import java.util.Calendar;
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
        consulta += "select e from Estoque e where e.id != 0 ";
        return this;
    }

    public EstoqueDAO porItem(Item item) {
        consulta += " and e.item = :pItem";
        parametros.put("pItem", item);
        return this;
    }

    public EstoqueDAO porContaDeEstoque(ContaDeEstoque contaDeEstoque) {
        consulta += " and e.operacaoDeEstoque.contaDeEstoque = :pContaDeEstoque";
        parametros.put("pContaDeEstoque", contaDeEstoque);
        return this;
    }

    public EstoqueDAO porNota(Nota nota) {
        consulta += " and e.itemDeNota.nota = :pNota";
        parametros.put("pNota", nota);
        return this;
    }
    
    public EstoqueDAO porNotaDeOrigem(Nota notaDeOrigem) {
        consulta += " and e.itemDeNota.nota.notaDeOrigem = :pNotaDeOrigem";
        parametros.put("pNotaDeOrigem", notaDeOrigem);
        return this;
    }
    
       public EstoqueDAO porTipoDeOperacaoDeNota(TipoOperacao tipoOperacao) {
        consulta += " and e.itemDeNota.nota.operacao.tipoOperacao = :pTipoOperacao";
        parametros.put("pTipoOperacao", tipoOperacao);
        return this;
    }

    public EstoqueDAO porTipoDeLancamentoDeNota(TipoLancamento tipoLancamento) {
        consulta += " and e.itemDeNota.nota.operacao.tipoNota = :pTipoLancamento";
        parametros.put("pTipoLancamento", tipoLancamento);
        return this;
    }

    public EstoqueDAO porEmissao(Date emissao) {
        if (emissao != null) {
            Calendar dataAtual = getDataComHoraFimdoDia(emissao);
            consulta += " and e.emissao <= :pEmissao ";
            parametros.put("pEmissao", dataAtual.getTime());
        }
        return this;
    }

    private Calendar getDataComHoraFimdoDia(Date emissao) {
        Calendar dataAtual = Calendar.getInstance();
        dataAtual.setTime(emissao);
        dataAtual.set(Calendar.HOUR_OF_DAY, 23);
        dataAtual.set(Calendar.MINUTE, 59);
        dataAtual.set(Calendar.SECOND, 59);
        return dataAtual;
    }

    public List<Estoque> listaDeResultados() {
        List<Estoque> resultado = new ArmazemDeRegistros<Estoque>(Estoque.class)
                .listaRegistrosDaConsulta(consulta, parametros);
        limpar();
        return resultado;
    }

}
