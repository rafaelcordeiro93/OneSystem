package br.com.onesystem.dao;

import br.com.onesystem.domain.Operacao;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.valueobjects.TipoLancamento;
import br.com.onesystem.valueobjects.TipoOperacao;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;

public class OperacaoDAO {

    private String consulta;
    private BundleUtil msg;
    private Map<String, Object> parametros;

    public OperacaoDAO() {
        limpar();
    }

    private void limpar() {
        consulta = "";
        msg = new BundleUtil();
        parametros = new HashMap<String, Object>();
    }

    public OperacaoDAO buscarOperacao() {
        consulta += "select o from Operacao o where o.id > 0 ";
        return this;
    }

    public OperacaoDAO porTipoDeLancamento(TipoLancamento tipoLancamento) {
        consulta += " and o.tipoNota = :oTipoNota ";
        parametros.put("oTipoNota", tipoLancamento);
        return this;
    }

    public OperacaoDAO porTipoDeOperacao(TipoOperacao tipoOperacao) {
        consulta += " and o.tipoOperacao = :oTipoOperacao ";
        parametros.put("oTipoOperacao", tipoOperacao);
        return this;
    }

    public OperacaoDAO porId(Long id) {
        consulta += " and o.id = :oId ";
        parametros.put("oId", id);
        return this;
    }

    public OperacaoDAO porNome(Operacao operacao) {
        consulta += " and o.nome = :oNome ";
        parametros.put("oNome", operacao.getNome());
        return this;
    }

    public List<Operacao> listaDeResultados() {
        List<Operacao> resultado = new ArmazemDeRegistros<Operacao>(Operacao.class)
                .listaRegistrosDaConsulta(consulta, parametros);
        limpar();
        return resultado;
    }

    public Operacao resultado() throws DadoInvalidoException {
        try {
            Operacao resultado = new ArmazemDeRegistros<Operacao>(Operacao.class)
                    .resultadoUnicoDaConsulta(consulta, parametros);
            limpar();
            return resultado;
        } catch (NoResultException nre) {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("registro_nao_encontrado"));
        }
    }

}
