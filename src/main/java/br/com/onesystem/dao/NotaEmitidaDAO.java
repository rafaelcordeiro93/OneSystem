/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.dao;

import br.com.onesystem.domain.Condicional;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.FDadoInvalidoException;
import br.com.onesystem.valueobjects.EstadoDeNota;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.valueobjects.TipoLancamento;
import br.com.onesystem.valueobjects.TipoOperacao;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class NotaEmitidaDAO extends GenericDAO<NotaEmitida> {

    public NotaEmitidaDAO() {
        super(NotaEmitida.class);
        limpar();
    }

    public NotaEmitidaDAO porId(Long id) {
        where += " and notaEmitida.id = :pId ";
        parametros.put("pId", id);
        return this;
    }

    public NotaEmitidaDAO porEmissaoEntre(Date dataInicial, Date dataFinal) {
        where += " and notaEmitida.emissao between :pDataInicial and :pDataFinal ";
        parametros.put("pDataInicial", dataInicial);
        parametros.put("pDataFinal", dataFinal);
        return this;
    }

    public NotaEmitidaDAO porEstado(EstadoDeNota estado) {
        where += " and notaEmitida.estado = :pEstado ";
        parametros.put("pEstado", estado);
        return this;
    }

    public NotaEmitidaDAO porNaoCancelado() {
        where += " and notaEmitida.estado <> :pEstadoNaoCancelado ";
        parametros.put("pEstadoNaoCancelado", EstadoDeNota.CANCELADO);
        return this;
    }

    public NotaEmitidaDAO porTipoLancamento(TipoLancamento tipoLancamento) {
        where += " and notaEmitida.operacao.tipoNota = :pTipoLancamento";
        parametros.put("pTipoLancamento", tipoLancamento);
        return this;
    }

    public NotaEmitidaDAO porTipoOperacao(TipoOperacao tipoOperacao) {
        where += " and notaEmitida.operacao.tipoOperacao = :pTipoOperacao";
        parametros.put("pTipoOperacao", tipoOperacao);
        return this;
    }

    public NotaEmitidaDAO porOperacaoFinanceira(OperacaoFinanceira operacaoFinanceira) {
        where += " and notaEmitida.operacao.operacaoFinanceira = :pOperacaoFinanceira";
        parametros.put("pOperacaoFinanceira", operacaoFinanceira);
        return this;
    }

    public NotaEmitidaDAO porCondicional(Condicional condicional) {
        where += " and notaEmitida.condicional = :pCondicional ";
        parametros.put("pCondicional", condicional);
        return this;
    }

    public NotaEmitidaDAO porTiposDeOperacao(List<TipoOperacao> tiposDeOperacao) throws DadoInvalidoException {
        if (tiposDeOperacao != null && !tiposDeOperacao.isEmpty()) {
            where += " and notaEmitida.operacao.tipoOperacao in :pTiposDeOperacao ";
            parametros.put("pTiposDeOperacao", tiposDeOperacao);
        } else {
            throw new FDadoInvalidoException("Erro: Deve ser feito a validação de lista de tipos de operação não "
                    + "nula e não vazia antes de chamar o método porTiposDeOperacao a fim de não trazer resultados incorretos");
        }
        return this;
    }
    public NotaEmitidaDAO ordenaPorEmissao() {
        order += " order by notaEmitida.emissao";
        return this;
    }

}
