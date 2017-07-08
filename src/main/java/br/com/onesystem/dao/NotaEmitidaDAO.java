/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.dao;

import br.com.onesystem.domain.Condicional;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.exception.impl.FDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.valueobjects.EstadoDeNota;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.valueobjects.TipoLancamento;
import br.com.onesystem.valueobjects.TipoOperacao;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.persistence.NoResultException;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class NotaEmitidaDAO extends GenericDAO<NotaEmitida> {

    public NotaEmitidaDAO() {
        limpar();
    }

    protected void limpar() {
        query = " select n from NotaEmitida n ";
        join = " ";
        where = " where n.id != 0 ";
        order = " ";
        group = " ";
        msg = new BundleUtil();
        parametros = new HashMap<String, Object>();
    }

    public NotaEmitidaDAO porId(Long id) {
        where += " and n.id = :pId ";
        parametros.put("pId", id);
        return this;
    }

    public NotaEmitidaDAO porEmissaoEntre(Date dataInicial, Date dataFinal) {
        where += " and n.emissao between :pDataInicial and :pDataFinal ";
        parametros.put("pDataInicial", dataInicial);
        parametros.put("pDataFinal", dataFinal);
        return this;
    }

    public NotaEmitidaDAO porEstado(EstadoDeNota estado) {
        where += " and n.estado = :pEstado ";
        parametros.put("pEstado", estado);
        return this;
    }

    public NotaEmitidaDAO porNaoCancelado() {
        where += " and n.estado <> :pEstadoNaoCancelado ";
        parametros.put("pEstadoNaoCancelado", EstadoDeNota.CANCELADO);
        return this;
    }

    public NotaEmitidaDAO porTipoLancamento(TipoLancamento tipoLancamento) {
        where += " and n.operacao.tipoNota = :pTipoLancamento";
        parametros.put("pTipoLancamento", tipoLancamento);
        return this;
    }

    public NotaEmitidaDAO porTipoOperacao(TipoOperacao tipoOperacao) {
        where += " and n.operacao.tipoOperacao = :pTipoOperacao";
        parametros.put("pTipoOperacao", tipoOperacao);
        return this;
    }

    public NotaEmitidaDAO porOperacaoFinanceira(OperacaoFinanceira operacaoFinanceira) {
        where += " and n.operacao.operacaoFinanceira = :pOperacaoFinanceira";
        parametros.put("pOperacaoFinanceira", operacaoFinanceira);
        return this;
    }

    public NotaEmitidaDAO porCondicional(Condicional condicional) {
        where += " and n.condicional = :pCondicional ";
        parametros.put("pCondicional", condicional);
        return this;
    }

    public NotaEmitidaDAO porTiposDeOperacao(List<TipoOperacao> tiposDeOperacao) throws DadoInvalidoException {
        if (tiposDeOperacao != null && !tiposDeOperacao.isEmpty()) {
            where += " and n.operacao.tipoOperacao in :pTiposDeOperacao ";
            parametros.put("pTiposDeOperacao", tiposDeOperacao);
        } else {
            throw new FDadoInvalidoException("Erro: Deve ser feito a validação de lista de tipos de operação não "
                    + "nula e não vazia antes de chamar o método porTiposDeOperacao a fim de não trazer resultados incorretos");
        }
        return this;
    }
    
    public NotaEmitidaDAO ordenaPorEmissao(){
        order += " order by n.emissao";
        return this;
    }

    public List<NotaEmitida> listaDeResultados() {
        List<NotaEmitida> resultado = new ArmazemDeRegistros<>(NotaEmitida.class)
                .listaRegistrosDaConsulta(getConsulta(), parametros);
        limpar();
        return resultado;
    }

    public NotaEmitida resultado() throws DadoInvalidoException {
        try {
            NotaEmitida resultado = new ArmazemDeRegistros<NotaEmitida>(NotaEmitida.class)
                    .resultadoUnicoDaConsulta(where, parametros);
            limpar();
            return resultado;
        } catch (NoResultException nre) {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("registro_nao_encontrado"));
        }
    }

}
