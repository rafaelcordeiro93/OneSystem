package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.dao.ItemDeNotaDAO;
import br.com.onesystem.domain.Condicional;
import br.com.onesystem.domain.ConfiguracaoEstoque;
import br.com.onesystem.domain.ItemDeCondicional;
import br.com.onesystem.domain.ItemDeNota;
import br.com.onesystem.exception.DadoInvalidoException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class ItemDeNotaService implements Serializable {

    public List<ItemDeNota> buscarItensEmitidos() {
        return new ArmazemDeRegistros<ItemDeNota>(ItemDeNota.class).listaTodosOsRegistros();
    }

    public BigDecimal buscaQuantidadeFaturadaPor(ItemDeCondicional item, Condicional condicional) throws DadoInvalidoException {
        ConfiguracaoEstoqueService serv = new ConfiguracaoEstoqueService();
        ConfiguracaoEstoque conf = serv.buscar();

        BigDecimal saldo = BigDecimal.ZERO;
        
        if (condicional.getNotasEmitidas() != null && !condicional.getNotasEmitidas().isEmpty()) {
            List<ItemDeNota> itensDeNotas = new ItemDeNotaDAO().buscarItens().porNotasEmitidas(condicional.getNotasEmitidas()).porItem(item.getItem())
                    .porNaoCancelado().listaDeResultados();
            saldo = itensDeNotas.stream().map(ItemDeNota::getQuantidade).reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        return saldo;
    }

}
