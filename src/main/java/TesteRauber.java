
import br.com.onesystem.dao.NotaEmitidaDAO;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.valueobjects.TipoLancamento;
import br.com.onesystem.valueobjects.TipoOperacao;
import br.com.onesystem.war.service.PessoaService;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Rafael Fernando Rauber
 */
public class TesteRauber {

    public static void main(String[] args) throws DadoInvalidoException {

        Date primeiroDiaDoAnoAnterior = Date.from(LocalDate.now().minusYears(1).with(TemporalAdjusters.firstDayOfYear()).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date agora = new Date();

        NotaEmitidaDAO dao = new NotaEmitidaDAO();
        List<NotaEmitida> notas = dao.porEmissaoEntre(primeiroDiaDoAnoAnterior, agora).porNaoCancelado().porTipoLancamento(TipoLancamento.EMITIDA).porOperacaoFinanceira(OperacaoFinanceira.ENTRADA)
                .porTiposDeOperacao(Arrays.asList(TipoOperacao.VENDA, TipoOperacao.VENDA_ENTREGA_FUTURA, TipoOperacao.VENDA_IMOBILIZADO)).ordenaPorEmissao().listaDeResultados();

        for (NotaEmitida n : notas) {
            LocalDate data = n.getEmissao().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            data.getMonth();
        }

    }

}
