package br.com.onesystem.dao;

import br.com.onesystem.domain.Banco;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;

public class BancoDAO {

    private String consulta;
    private BundleUtil msg;
    private Map<String, Object> parametros;

    public BancoDAO() {
        limpar();
    }

    private void limpar() {
        consulta = "";
        msg = new BundleUtil();
        parametros = new HashMap<String, Object>();
    }

    public BancoDAO buscarBancos() {
        consulta += "select b from Banco b where b.id > 0 ";
        return this;
    }

    public BancoDAO porNome(Banco banco) {
        consulta += " and b.nome = :bNome ";
        parametros.put("bNome", banco.getNome());
        return this;
    }

    public BancoDAO porId(Long id) {
        consulta += " and b.id = :bId ";
        parametros.put("bId", id);
        return this;
    }

    public List<Banco> listaDeResultados() {
        List<Banco> resultado = new ArmazemDeRegistros<Banco>(Banco.class)
                .listaRegistrosDaConsulta(consulta, parametros);
        limpar();
        return resultado;
    }
    
     public Banco resultado() throws DadoInvalidoException {
        try {
            Banco resultado = new ArmazemDeRegistros<Banco>(Banco.class)
                    .resultadoUnicoDaConsulta(consulta, parametros);        
            limpar();
            return resultado;
        } catch (NoResultException nre) {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("registro_nao_encontrado"));
        }
    }
}
