package br.com.onesystem.dao;

import br.com.onesystem.domain.Cheque;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.valueobjects.SituacaoDeCheque;
import br.com.onesystem.valueobjects.TipoLancamento;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;

public class ChequeDAO {

    private String consulta;
    private BundleUtil msg;
    private Map<String, Object> parametros;

    public ChequeDAO() {
        limpar();
    }

    private void limpar() {
        consulta = "";
        msg = new BundleUtil();
        parametros = new HashMap<String, Object>();
    }

    public ChequeDAO buscarCheques() {
        consulta += "select b from Cheque b where b.id > 0 ";
        return this;
    }

    public ChequeDAO porNome(Cheque cheque) {
        consulta += " and b.nome = :bNome ";
        parametros.put("bNome", cheque.getEmitente());
        return this;
    }

    public ChequeDAO porEmissao(Date data) {
        consulta += " and b.emissao = :bEmissao ";
        parametros.put("bEmissao", data);
        return this;
    }

    public ChequeDAO porSituacao(SituacaoDeCheque situacaoDeCheque) {
        consulta += " and b.tipoSituacao = :pSituacao";
        parametros.put("pSituacao", situacaoDeCheque);
        return this;
    }

    public ChequeDAO porTipoLancamento(TipoLancamento tipoLancamento) {
        consulta += " and b.tipoLancamento = :pTipoLancamento";
        parametros.put("pTipoLancamento", tipoLancamento);
        return this;
    }

    public ChequeDAO porId(Long id) {
        consulta += " and b.id = :bId ";
        parametros.put("bId", id);
        return this;
    }

    public List<Cheque> listaDeResultados() {
        List<Cheque> resultado = new ArmazemDeRegistros<Cheque>(Cheque.class)
                .listaRegistrosDaConsulta(consulta, parametros);
        limpar();
        return resultado;
    }

    public Cheque resultado() throws DadoInvalidoException {
        try {
            Cheque resultado = new ArmazemDeRegistros<Cheque>(Cheque.class)
                    .resultadoUnicoDaConsulta(consulta, parametros);
            limpar();
            return resultado;
        } catch (NoResultException nre) {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("registro_nao_encontrado"));
        }
    }
}
