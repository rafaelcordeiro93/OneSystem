package br.com.onesystem.dao;

import br.com.onesystem.domain.Cartao;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;

public class CartaoDAO {

    private String consulta;
    private BundleUtil msg;
    private Map<String, Object> parametros;

    public CartaoDAO() {
        limpar();
    }

    private void limpar() {
        consulta = "";
        msg = new BundleUtil();
        parametros = new HashMap<String, Object>();
    }

    public CartaoDAO buscarCartaos() {
        consulta += "select b from Cartao b where b.id > 0 ";
        return this;
    }

    public CartaoDAO porNome(Cartao cartao) {
        consulta += " and b.nome = :bNome ";
        parametros.put("bNome", cartao.getNome());
        return this;
    }

    public CartaoDAO porId(Long id) {
        consulta += " and b.id = :bId ";
        parametros.put("bId", id);
        return this;
    }

    public List<Cartao> listaDeResultados() {
        List<Cartao> resultado = new ArmazemDeRegistros<Cartao>(Cartao.class)
                .listaRegistrosDaConsulta(consulta, parametros);
        limpar();
        return resultado;
    }
    
     public Cartao resultado() throws DadoInvalidoException {
        try {
            Cartao resultado = new ArmazemDeRegistros<Cartao>(Cartao.class)
                    .resultadoUnicoDaConsulta(consulta, parametros);        
            limpar();
            return resultado;
        } catch (NoResultException nre) {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("registro_nao_encontrado"));
        }
    }
}
