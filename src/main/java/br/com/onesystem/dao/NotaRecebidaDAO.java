/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.dao;

import br.com.onesystem.domain.Condicional;
import br.com.onesystem.domain.NotaRecebida;
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
public class NotaRecebidaDAO extends GenericDAO<NotaRecebida> {

    public NotaRecebidaDAO() {
        super(NotaRecebida.class);
        limpar();
    }

    public NotaRecebidaDAO porId(Long id) {
        where += " and notaRecebida.id = :pId ";
        parametros.put("pId", id);
        return this;
    }

    public NotaRecebidaDAO porEmissaoEntre(Date dataInicial, Date dataFinal) {
        where += " and notaRecebida.emissao between :pDataInicial and :pDataFinal ";
        parametros.put("pDataInicial", dataInicial);
        parametros.put("pDataFinal", dataFinal);
        return this;
    }

    public NotaRecebidaDAO porEstado(EstadoDeNota estado) {
        where += " and notaRecebida.estado = :pEstado ";
        parametros.put("pEstado", estado);
        return this;
    }

    public NotaRecebidaDAO porNaoCancelado() {
        where += " and notaRecebida.estado <> :pEstadoNaoCancelado ";
        parametros.put("pEstadoNaoCancelado", EstadoDeNota.CANCELADO);
        return this;
    }

    public NotaRecebidaDAO porTipoLancamento(TipoLancamento tipoLancamento) {
        where += " and notaRecebida.operacao.tipoNota = :pTipoLancamento";
        parametros.put("pTipoLancamento", tipoLancamento);
        return this;
    }

    public NotaRecebidaDAO porTipoOperacao(TipoOperacao tipoOperacao) {
        where += " and notaRecebida.operacao.tipoOperacao = :pTipoOperacao";
        parametros.put("pTipoOperacao", tipoOperacao);
        return this;
    }

    public NotaRecebidaDAO porOperacaoFinanceira(OperacaoFinanceira operacaoFinanceira) {
        where += " and notaRecebida.operacao.operacaoFinanceira = :pOperacaoFinanceira";
        parametros.put("pOperacaoFinanceira", operacaoFinanceira);
        return this;
    }

    public NotaRecebidaDAO porCondicional(Condicional condicional) {
        where += " and notaRecebida.condicional = :pCondicional ";
        parametros.put("pCondicional", condicional);
        return this;
    }

    public NotaRecebidaDAO porTiposDeOperacao(List<TipoOperacao> tiposDeOperacao) throws DadoInvalidoException {
        if (tiposDeOperacao != null && !tiposDeOperacao.isEmpty()) {
            where += " and notaRecebida.operacao.tipoOperacao in :pTiposDeOperacao ";
            parametros.put("pTiposDeOperacao", tiposDeOperacao);
        } else {
            throw new FDadoInvalidoException("Erro: Deve ser feito a validação de lista de tipos de operação não "
                    + "nula e não vazia antes de chamar o método porTiposDeOperacao a fim de não trazer resultados incorretos");
        }
        return this;
    }

    public NotaRecebidaDAO porPessoa(Pessoa pessoa) {
        where += " and notaRecebida.pessoa = :pPessoa";
        parametros.put("pPessoa", pessoa);
        return this;
    }

    public NotaRecebidaDAO porAFaturarMaiorZero() {
        where += " and notaRecebida.aFaturar > :pZero ";
        parametros.put("pZero", BigDecimal.ZERO);
        return this;
    }

    public NotaRecebidaDAO porConhecimentoDeFreteNaoInformado() {
        where += " and notaRecebida.conhecimentoDeFrete = null ";
        return this;
    }

    public NotaRecebidaDAO porSemFaturaRecebida() {
        where += " and notaRecebida.faturaRecebida = null ";
        return this;
    }

    public NotaRecebidaDAO ordenaPorEmissao() {
        order += " order by notaRecebida.emissao";
        return this;
    }

}
