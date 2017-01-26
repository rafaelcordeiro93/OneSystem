package br.com.onesystem.dao;

import br.com.onesystem.domain.Cidade;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;

public class CidadeDAO {

    private String consulta;
    private BundleUtil msg;
    private Map<String, Object> parametros;

    public CidadeDAO() {
        limpar();
    }

    private void limpar() {
        consulta = "";
        msg = new BundleUtil();
        parametros = new HashMap<String, Object>();
    }

    public CidadeDAO buscarCidades() {
        consulta += "select c from Cidade c where c.id > 0 ";
        return this;
    }

    public CidadeDAO porNome(Cidade cidade) {
        consulta += " and c.nome = :cNome ";
        parametros.put("cNome", cidade.getNome());
        return this;
    }

    public CidadeDAO porId(Long id) {
        consulta += " and c.id = :cId ";
        parametros.put("cId", id);
        return this;
    }

    public List<Cidade> listaDeResultados() {
        List<Cidade> resultado = new ArmazemDeRegistros<Cidade>(Cidade.class)
                .listaRegistrosDaConsulta(consulta, parametros);
        limpar();
        return resultado;
    }
    
     public Cidade resultado() throws DadoInvalidoException {
        try {
            Cidade resultado = new ArmazemDeRegistros<Cidade>(Cidade.class)
                    .resultadoUnicoDaConsulta(consulta, parametros);        
            limpar();
            return resultado;
        } catch (NoResultException nre) {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("registro_nao_encontrado"));
        }
    }
}
