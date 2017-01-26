package br.com.onesystem.dao;

import br.com.onesystem.domain.Moeda;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;

public class MoedaDAO {

    private String consulta;
    private BundleUtil msg;
    private Map<String, Object> parametros;

    public MoedaDAO() {
        limpar();
    }

    private void limpar() {
        consulta = "";
        msg = new BundleUtil();
        parametros = new HashMap<String, Object>();
    }

    public MoedaDAO buscarMoedas() {
        consulta += "select m from Moeda m where m.id > 0 ";
        return this;
    }

    public MoedaDAO porNome(Moeda moeda) {
        consulta += " and m.nome = :mNome ";
        parametros.put("mNome", moeda.getNome());
        return this;
    }

    public MoedaDAO porSigla(Moeda moeda) {
        consulta += " and m.sigla = :mSigla ";
        parametros.put("mSigla", moeda.getSigla());
        return this;
    }

    public MoedaDAO porId(Long id) {
        consulta += " and m.id = :mId ";
        parametros.put("mId", id);
        return this;
    }

    public List<Moeda> listaDeResultados() {
        List<Moeda> resultado = new ArmazemDeRegistros<Moeda>(Moeda.class)
                .listaRegistrosDaConsulta(consulta, parametros);
        limpar();
        return resultado;
    }

    public Moeda resultado() throws DadoInvalidoException {
        try {
            Moeda resultado = new ArmazemDeRegistros<Moeda>(Moeda.class)
                    .resultadoUnicoDaConsulta(consulta, parametros);
            limpar();
            return resultado;
        } catch (NoResultException nre) {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("registro_nao_encontrado"));
        }
    }

}
