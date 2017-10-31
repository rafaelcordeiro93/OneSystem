package br.com.onesystem.dao;

import br.com.onesystem.domain.Condicional;
import br.com.onesystem.domain.ContaDeEstoque;
import br.com.onesystem.domain.Estoque;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.Nota;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.domain.NotaRecebida;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.FDadoInvalidoException;
import br.com.onesystem.valueobjects.EstadoDeCondicional;
import br.com.onesystem.valueobjects.EstadoDeNota;
import br.com.onesystem.valueobjects.OperacaoFisica;
import br.com.onesystem.valueobjects.TipoLancamento;
import br.com.onesystem.valueobjects.TipoOperacao;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EstoqueDAO extends GenericDAO<Estoque> {

    public EstoqueDAO() {
        super(Estoque.class);
    }

    public EstoqueDAO porItem(Item item) {
        where += " and estoque.item = :pItem";
        parametros.put("pItem", item);
        return this;
    }
    
    public EstoqueDAO porIdItem(Long id) {
        where += " and estoque.item.id = :pIdItem";
        parametros.put("pIdItem", id);
        return this;
    }

    public EstoqueDAO porContaDeEstoque(ContaDeEstoque contaDeEstoque) {
        where += " and estoque.operacaoDeEstoque.contaDeEstoque = :pContaDeEstoque";
        parametros.put("pContaDeEstoque", contaDeEstoque);
        return this;
    }

    public EstoqueDAO porEstoqueAlterado() {
        where += " and estoque.operacaoDeEstoque.operacaoFisica <> :pOperacaoFisica";
        parametros.put("pOperacaoFisica", OperacaoFisica.SEM_ALTERACAO);
        return this;
    }

    public EstoqueDAO porNaoCancelado() {
        join += " left join estoque.itemDeNota.nota as nt left join estoque.itemDeCondicional.condicional as cd ";
        where += " and ((estoque.itemDeNota is not null and nt.estado <> :pEstado) or (estoque.itemDeCondicional is not null and cd.estado <> :pEstadoCondicional)"
                + " or (estoque.ajusteDeEstoque is not null and estoque.ajusteDeEstoque.custo <> :pZero)) ";
        parametros.put("pEstado", EstadoDeNota.CANCELADO);
        parametros.put("pZero", BigDecimal.ZERO);
        parametros.put("pEstadoCondicional", EstadoDeCondicional.CANCELADO);
        return this;
    }
    
    public EstoqueDAO porNaoSerItemCondicional(){
        where += " and estoque.itemDeCondicional is null ";
        return this;
    }

    public EstoqueDAO porCancelado() {
        join += " left join estoque.itemDeNota.nota as nt left join estoque.itemDeCondicional.condicional as cd ";
        where += " and (nt.estado = :pEstado or cd.estado = :pEstadoCondicional)";
        parametros.put("pEstado", EstadoDeNota.CANCELADO);
        parametros.put("pEstadoCondicional", EstadoDeCondicional.CANCELADO);
        return this;
    }

    public EstoqueDAO porNota(Nota nota) {
        where += " and estoque.itemDeNota.nota = :pNota";
        parametros.put("pNota", nota);
        return this;
    }

    public EstoqueDAO porCondicional(Condicional condicional) {
        where += " and estoque.itemDeCondicional.condicional = :pCondicional";
        parametros.put("pCondicional", condicional);
        return this;
    }

    public EstoqueDAO porNotasEmitidas(List<NotaEmitida> notasEmitidas) throws DadoInvalidoException {
        if (notasEmitidas != null && !notasEmitidas.isEmpty()) {
            where += " and estoque.itemDeNota.nota in :pNotas ";
            parametros.put("pNotas", notasEmitidas);
        } else {
            throw new FDadoInvalidoException("Erro: Deve ser feito a validação de lista de notas emitida não "
                    + "nula e não vazia antes de chamar o método porNotasEmitidas a fim de não trazer resultados incorretos");
        }
        return this;
    }

    public EstoqueDAO porNotasRecebidas(List<NotaRecebida> notasRecebidas) throws DadoInvalidoException {
        if (notasRecebidas != null && !notasRecebidas.isEmpty()) {
            where += " and estoque.itemDeNota.nota in :pNotas ";
            parametros.put("pNotas", notasRecebidas);
        } else {
            throw new FDadoInvalidoException("Erro: Deve ser feito a validação de lista de notas recebidas não "
                    + "nula e não vazia antes de chamar o método porNotasRecebidas a fim de não trazer resultados incorretos");
        }
        return this;
    }

    public EstoqueDAO porNotaDeOrigem(Nota notaDeOrigem) {
        where += " and estoque.itemDeNota.nota.notaDeOrigem = :pNotaDeOrigem";
        parametros.put("pNotaDeOrigem", notaDeOrigem);
        return this;
    }

    public EstoqueDAO porTipoDeOperacaoDeNota(TipoOperacao tipoOperacao) {
        where += " and estoque.itemDeNota.nota.operacao.tipoOperacao = :pTipoOperacao";
        parametros.put("pTipoOperacao", tipoOperacao);
        return this;
    }

    public EstoqueDAO porTipoDeLancamentoDeNota(TipoLancamento tipoLancamento) {
        where += " and estoque.itemDeNota.nota.operacao.tipoNota = :pTipoLancamento";
        parametros.put("pTipoLancamento", tipoLancamento);
        return this;
    }

    public EstoqueDAO ateEmissao(Date emissao) {
        if (emissao != null) {
            where += " and estoque.emissao <= :pEmissao ";
            parametros.put("pEmissao", emissao);
        }
        return this;
    }
    
    public EstoqueDAO orderByDescEmissao(){
        order = " order by estoque.emissao desc";
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

}
