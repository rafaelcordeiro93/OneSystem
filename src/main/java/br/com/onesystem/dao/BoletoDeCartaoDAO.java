package br.com.onesystem.dao;

import br.com.onesystem.domain.BoletoDeCartao;
import br.com.onesystem.valueobjects.SituacaoDeCartao;

public class BoletoDeCartaoDAO extends GenericDAO<BoletoDeCartao> {

    public BoletoDeCartaoDAO() {
        super(BoletoDeCartao.class);
        limpar();
    }

    public BoletoDeCartaoDAO porNome(BoletoDeCartao boletoDeCartao) {
        where += " and cartao.nome = :bNome ";
        parametros.put("bNome", boletoDeCartao.getCartao().getNome());
        return this;
    }

    public BoletoDeCartaoDAO porSituacao(SituacaoDeCartao situacao) {
        where += "and situacao = :pSituacao";
        parametros.put("pSituacao", situacao);
        return this;
    }

    public BoletoDeCartaoDAO porId(Long id) {
        where += " and id = :bId ";
        parametros.put("bId", id);
        return this;
    }

}
