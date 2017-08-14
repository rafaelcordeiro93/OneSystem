package br.com.onesystem.dao;

import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.ItemDeNota;
import br.com.onesystem.domain.Nota;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.domain.NotaRecebida;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.FDadoInvalidoException;
import br.com.onesystem.valueobjects.EstadoDeNota;
import java.util.List;

public class ItemDeNotaDAO extends GenericDAO<ItemDeNota> {

    public ItemDeNotaDAO() {
        super(ItemDeNota.class);
    }

    public ItemDeNotaDAO consultaItemEmitido() {
        query = "select itemDeNota from ItemDeNota itemDeNota";
        where = "where TYPE(itemDeNota.nota.class) = :pTypeNotaEmitida";
        parametros.put("pTypeNotaEmitida", NotaEmitida.class);
        return this;
    }

    public ItemDeNotaDAO consultaItemRecebido() {
        query = "select itemDeNota from ItemDeNota itemDeNota ";
        where = "where TYPE(itemDeNota.nota.class) = :pTypeNotaRecebida";
        parametros.put("pTypeNotaRecebida", NotaRecebida.class);
        return this;
    }

    public ItemDeNotaDAO buscaMediaDeCusto() {
        query = "select avg(itemDeNota.unitario) from ItemDeNota itemDeNota ";
        return this;
    }

    public ItemDeNotaDAO porItem(Item item) {
        if (item != null) {
            where += " and itemDeNota.item = :pItem ";
            parametros.put("pItem", item);
        }
        return this;
    }

    public ItemDeNotaDAO porNota(Nota nota) {
        where += " and itemDeNota.nota = :pNota ";
        parametros.put("pNota", nota);
        return this;
    }

    public ItemDeNotaDAO porNotasEmitidas(List<NotaEmitida> notasEmitidas) throws DadoInvalidoException {
        if (notasEmitidas != null && !notasEmitidas.isEmpty()) {
            where += " and itemDeNota.nota in :pNotas ";
            parametros.put("pNotas", notasEmitidas);
        } else {
            throw new FDadoInvalidoException("Erro: Deve ser feito a validação de lista de notas emitida não "
                    + "nula e não vazia antes de chamar o método porNotasEmitidas a fim de não trazer resultados incorretos");
        }
        return this;
    }

    public ItemDeNotaDAO porNaoCancelado() {
        where += " and itemDeNota.nota.estado <> :pEstado ";
        parametros.put("pEstado", EstadoDeNota.CANCELADO);
        return this;
    }

}
