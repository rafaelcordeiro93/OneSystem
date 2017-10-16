package br.com.onesystem.dao;

import br.com.onesystem.domain.Operacao;
import br.com.onesystem.valueobjects.TipoLancamento;
import br.com.onesystem.valueobjects.TipoOperacao;

public class OperacaoDAO extends GenericDAO<Operacao> {

    public OperacaoDAO() {
        super(Operacao.class);
    }

    public OperacaoDAO porTipoDeLancamento(TipoLancamento tipoLancamento) {
        where += " and operacao.tipoNota = :oTipoNota ";
        parametros.put("oTipoNota", tipoLancamento);
        return this;
    }

    public OperacaoDAO porTipoDeOperacao(TipoOperacao tipoOperacao) {
        where += " and operacao.tipoOperacao = :oTipoOperacao ";
        parametros.put("oTipoOperacao", tipoOperacao);
        return this;
    }

    public OperacaoDAO porId(Long id) {
        where += " and operacao.id = :oId ";
        parametros.put("oId", id);
        return this;
    }

    public OperacaoDAO porNome(String nome) {
        where += " and operacao.nome = :oNome ";
        parametros.put("oNome", nome);
        return this;
    }
    
    public OperacaoDAO orderByID(){
        order = "order by operacao.id";
        return this;
    }
            

}
