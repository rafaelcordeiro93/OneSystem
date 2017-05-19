package br.com.onesystem.dao;

import br.com.onesystem.domain.Operacao;
import br.com.onesystem.domain.OperacaoDeEstoque;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.valueobjects.TipoLancamento;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;

public class OperacaoDeEstoqueDAO {

    private String consulta;
    private BundleUtil msg;
    private Map<String, Object> parametros;

    public OperacaoDeEstoqueDAO() {
        limpar();
    }

    private void limpar() {
        consulta = "";
        msg = new BundleUtil();
        parametros = new HashMap<String, Object>();
    }

    public OperacaoDeEstoqueDAO buscarOperacaoDeEstoque() {
        consulta += "select o from OperacaoDeEstoque o where o.id > 0 ";
        return this;
    }

    public OperacaoDeEstoqueDAO porOperacao(Operacao operacao) {
        consulta += " and o.operacao = :oOperacao ";
        parametros.put("oOperacao", operacao);
        return this;
    }

    public List<OperacaoDeEstoque> listaDeResultados() {
        List<OperacaoDeEstoque> resultado = new ArmazemDeRegistros<OperacaoDeEstoque>(OperacaoDeEstoque.class)
                .listaRegistrosDaConsulta(consulta, parametros);
        limpar();
        return resultado;
    }

    public OperacaoDeEstoque resultado() throws DadoInvalidoException {
        try {
            OperacaoDeEstoque resultado = new ArmazemDeRegistros<OperacaoDeEstoque>(OperacaoDeEstoque.class)
                    .resultadoUnicoDaConsulta(consulta, parametros);
            limpar();
            return resultado;
        } catch (NoResultException nre) {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("registro_nao_encontrado"));
        }
    }

}
