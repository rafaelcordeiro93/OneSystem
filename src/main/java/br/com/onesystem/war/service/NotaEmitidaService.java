package br.com.onesystem.war.service;

import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.dao.NotaEmitidaDAO;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.valueobjects.TipoLancamento;
import br.com.onesystem.valueobjects.TipoOperacao;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class NotaEmitidaService implements Serializable {

    public List<NotaEmitida> buscarNotasEmitidas() {
        return new ArmazemDeRegistros<>(NotaEmitida.class).listaTodosOsRegistros();
    }
 
    public List<NotaEmitida> buscarVendasDoPeriodo(Date dataInicial, Date dataFinal) throws DadoInvalidoException {
        return new NotaEmitidaDAO().porEmissaoEntre(dataInicial, dataFinal).porNaoCancelado().porTipoLancamento(TipoLancamento.EMITIDA).porOperacaoFinanceira(OperacaoFinanceira.ENTRADA)
                .porTiposDeOperacao(Arrays.asList(TipoOperacao.VENDA, TipoOperacao.VENDA_ENTREGA_FUTURA, TipoOperacao.VENDA_IMOBILIZADO)).ordenaPorEmissao().listaDeResultados();
    }

}
