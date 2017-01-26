package br.com.onesystem.dao;

import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;

public class PessoaDAO {

    private String consulta;
    private BundleUtil msg;
    private Map<String, Object> parametros;

    public PessoaDAO() {
        limpar();
    }

    private void limpar() {
        consulta = "";
        msg = new BundleUtil();
        parametros = new HashMap<String, Object>();
    }

    public PessoaDAO buscarPessoas() {
        consulta += "select p from Pessoa p where p.id > 0 ";
        return this;
    }

    public PessoaDAO porNome(Pessoa pessoa) {
        consulta += " and p.nome = :pNome ";
        parametros.put("pNome", pessoa.getNome());
        return this;
    }

    public PessoaDAO porId(Long id) {
        consulta += " and p.id = :pId ";
        parametros.put("pId", id);
        return this;
    }

    public List<Pessoa> listaDeResultados() {
        List<Pessoa> resultado = new ArmazemDeRegistros<Pessoa>(Pessoa.class)
                .listaRegistrosDaConsulta(consulta, parametros);
        limpar();
        return resultado;
    }

    public Pessoa resultado() throws DadoInvalidoException {
        try {
            Pessoa resultado = new ArmazemDeRegistros<Pessoa>(Pessoa.class)
                    .resultadoUnicoDaConsulta(consulta, parametros);
            limpar();
            return resultado;
        } catch (NoResultException nre) {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("registro_nao_encontrado"));
        }
    }
}
