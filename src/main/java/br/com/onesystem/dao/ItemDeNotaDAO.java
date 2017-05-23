package br.com.onesystem.dao;

import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.ItemDeNota;
import br.com.onesystem.domain.Nota;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.exception.impl.FDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.valueobjects.EstadoDeNota;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;

public class ItemDeNotaDAO {

    private String consulta;
    private BundleUtil msg;
    private Map<String, Object> parametros;

    public ItemDeNotaDAO() {
        limpar();
    }

    private void limpar() {
        consulta = "";
        msg = new BundleUtil();
        parametros = new HashMap<String, Object>();
    }

    public ItemDeNotaDAO buscarItens() {
        consulta += "select i from ItemDeNota i where i.id > 0 ";
        return this;
    }

    public ItemDeNotaDAO porItem(Item item) {
        if (item != null) {
            consulta += " and i.item = :pItem ";
            parametros.put("pItem", item);
        }
        return this;
    }

    public ItemDeNotaDAO porNota(Nota nota) {
        consulta += " and i.nota = :pNota ";
        parametros.put("pNota", nota);
        return this;
    }

    public ItemDeNotaDAO porNotasEmitidas(List<NotaEmitida> notasEmitidas) throws DadoInvalidoException {
        if (notasEmitidas != null && !notasEmitidas.isEmpty()) {
            consulta += " and i.nota in :pNotas ";
            parametros.put("pNotas", notasEmitidas);
        } else {
            throw new FDadoInvalidoException("Erro: Deve ser feito a validação de lista de notas emitida não "
                    + "nula e não vazia antes de chamar o método porNotasEmitidas a fim de não trazer resultados incorretos");
        }
        return this;
    }

    public ItemDeNotaDAO porNaoCancelado() {
        consulta += " and i.nota.estado <> :pEstado ";
        parametros.put("pEstado", EstadoDeNota.CANCELADO);
        return this;
    }

    public List<ItemDeNota> listaDeResultados() {
        List<ItemDeNota> resultado = new ArmazemDeRegistros<ItemDeNota>(ItemDeNota.class)
                .listaRegistrosDaConsulta(consulta, parametros);
        limpar();
        return resultado;
    }

    public String getConsulta() {
        return consulta;
    }

    public ItemDeNota resultado() throws DadoInvalidoException {
        try {
            ItemDeNota resultado = new ArmazemDeRegistros<ItemDeNota>(ItemDeNota.class)
                    .resultadoUnicoDaConsulta(consulta, parametros);
            limpar();
            return resultado;
        } catch (NoResultException nre) {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("registro_nao_encontrado"));
        }
    }

}
