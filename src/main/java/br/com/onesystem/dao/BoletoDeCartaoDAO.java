package br.com.onesystem.dao;

import br.com.onesystem.domain.BoletoDeCartao;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.valueobjects.SituacaoDeCartao;
import br.com.onesystem.valueobjects.SituacaoDeCheque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;

public class BoletoDeCartaoDAO {

    private String consulta;
    private BundleUtil msg;
    private Map<String, Object> parametros;

    public BoletoDeCartaoDAO() {
        limpar();
    }

    private void limpar() {
        consulta = "";
        msg = new BundleUtil();
        parametros = new HashMap<String, Object>();
    }

    public BoletoDeCartaoDAO buscarBoletosDeCartao() {
        consulta += "select b from BoletoDeCartao b where b.id > 0 ";
        return this;
    }

    public BoletoDeCartaoDAO porNome(BoletoDeCartao boletoDeCartao) {
        consulta += " and b.nome = :bNome ";
        parametros.put("bNome", boletoDeCartao.getCartao().getNome());
        return this;
    }

     public BoletoDeCartaoDAO porSituacao(SituacaoDeCartao situacao){
        consulta += "and b.situacao = :pSituacao";
        parametros.put("pSituacao", situacao);
        return this;
    }
    
    public BoletoDeCartaoDAO porId(Long id) {
        consulta += " and b.id = :bId ";
        parametros.put("bId", id);
        return this;
    }

    public List<BoletoDeCartao> listaDeResultados() {
        List<BoletoDeCartao> resultado = new ArmazemDeRegistros<BoletoDeCartao>(BoletoDeCartao.class)
                .listaRegistrosDaConsulta(consulta, parametros);
        limpar();
        return resultado;
    }

    public BoletoDeCartao resultado() throws DadoInvalidoException {
        try {
            BoletoDeCartao resultado = new ArmazemDeRegistros<BoletoDeCartao>(BoletoDeCartao.class)
                    .resultadoUnicoDaConsulta(consulta, parametros);
            limpar();
            return resultado;
        } catch (NoResultException nre) {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("registro_nao_encontrado"));
        }
    }
}
