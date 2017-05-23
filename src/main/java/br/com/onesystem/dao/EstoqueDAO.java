package br.com.onesystem.dao;

import br.com.onesystem.domain.Condicional;
import br.com.onesystem.domain.ContaDeEstoque;
import br.com.onesystem.domain.Estoque;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.Nota;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.exception.impl.FDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.valueobjects.EstadoDeCondicional;
import br.com.onesystem.valueobjects.EstadoDeNota;
import br.com.onesystem.valueobjects.OperacaoFisica;
import br.com.onesystem.valueobjects.TipoLancamento;
import br.com.onesystem.valueobjects.TipoOperacao;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EstoqueDAO {

    private String query;
    private String where;
    private String join;
    private BundleUtil msg;
    private Map<String, Object> parametros;

    public EstoqueDAO() {
        limpar();
    }

    private void limpar() {
        query = " select e from Estoque e ";
        join = " ";
        where = " where e.id != 0 ";
        msg = new BundleUtil();
        parametros = new HashMap<String, Object>();
    }

    public EstoqueDAO porItem(Item item) {
        where += " and e.item = :pItem";
        parametros.put("pItem", item);
        return this;
    }

    public EstoqueDAO porContaDeEstoque(ContaDeEstoque contaDeEstoque) {
        where += " and e.operacaoDeEstoque.contaDeEstoque = :pContaDeEstoque";
        parametros.put("pContaDeEstoque", contaDeEstoque);
        return this;
    }

    public EstoqueDAO porEstoqueAlterado() {
        where += " and e.operacaoDeEstoque.operacaoFisica <> :pOperacaoFisica";
        parametros.put("pOperacaoFisica", OperacaoFisica.SEM_ALTERACAO);
        return this;
    }

    public EstoqueDAO porNaoCancelado() {
        join += " left join e.itemDeNota.nota as nt left join e.itemDeCondicional.condicional as cd ";
        where += " and (nt.estado <> :pEstado or cd.estado <> :pEstadoCondicional)";
        parametros.put("pEstado", EstadoDeNota.CANCELADO);
        parametros.put("pEstadoCondicional", EstadoDeCondicional.CANCELADO);
        return this;
    }

    public EstoqueDAO porCancelado() {
        join += " left join e.itemDeNota.nota as nt left join e.itemDeCondicional.condicional as cd ";
        where += " and (nt.estado = :pEstado or cd.estado = :pEstadoCondicional)";
        parametros.put("pEstado", EstadoDeNota.CANCELADO);
        parametros.put("pEstadoCondicional", EstadoDeCondicional.CANCELADO);
        return this;
    }

    public EstoqueDAO porNota(Nota nota) {
        where += " and e.itemDeNota.nota = :pNota";
        parametros.put("pNota", nota);
        return this;
    }

    public EstoqueDAO porCondicional(Condicional condicional) {
        where += " and e.itemDeCondicional.condicional = :pCondicional";
        parametros.put("pCondicional", condicional);
        return this;
    }

    public EstoqueDAO porNotasEmitidas(List<NotaEmitida> notasEmitidas) throws DadoInvalidoException {
        if (notasEmitidas != null && !notasEmitidas.isEmpty()) {
            where += " and e.itemDeNota.nota in :pNotas ";
            parametros.put("pNotas", notasEmitidas);
        } else {
            throw new FDadoInvalidoException("Erro: Deve ser feito a validação de lista de notas emitida não "
                    + "nula e não vazia antes de chamar o método porNotasEmitidas a fim de não trazer resultados incorretos");
        }
        return this;
    }

    public EstoqueDAO porNotaDeOrigem(Nota notaDeOrigem) {
        where += " and e.itemDeNota.nota.notaDeOrigem = :pNotaDeOrigem";
        parametros.put("pNotaDeOrigem", notaDeOrigem);
        return this;
    }

    public EstoqueDAO porTipoDeOperacaoDeNota(TipoOperacao tipoOperacao) {
        where += " and e.itemDeNota.nota.operacao.tipoOperacao = :pTipoOperacao";
        parametros.put("pTipoOperacao", tipoOperacao);
        return this;
    }

    public EstoqueDAO porTipoDeLancamentoDeNota(TipoLancamento tipoLancamento) {
        where += " and e.itemDeNota.nota.operacao.tipoNota = :pTipoLancamento";
        parametros.put("pTipoLancamento", tipoLancamento);
        return this;
    }

    public EstoqueDAO porEmissao(Date emissao) {
        if (emissao != null) {
            Calendar dataAtual = getDataComHoraFimdoDia(emissao);
            where += " and e.emissao <= :pEmissao ";
            parametros.put("pEmissao", dataAtual.getTime());
        }
        return this;
    }

    public String getConsulta() {
        return query + join + where;
    }

    private Calendar getDataComHoraFimdoDia(Date emissao) {
        Calendar dataAtual = Calendar.getInstance();
        dataAtual.setTime(emissao);
        dataAtual.set(Calendar.HOUR_OF_DAY, 23);
        dataAtual.set(Calendar.MINUTE, 59);
        dataAtual.set(Calendar.SECOND, 59);
        return dataAtual;
    }

    public List<Estoque> listaResultados() {
        List<Estoque> resultado = new ArmazemDeRegistros<Estoque>(Estoque.class)
                .listaRegistrosDaConsulta(getConsulta(), parametros);
        limpar();
        return resultado;
    }

}
