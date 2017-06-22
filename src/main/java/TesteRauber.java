
import br.com.onesystem.dao.NotaEmitidaDAO;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.EstadoDeNota;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
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

        Date ultimoDiaDoAno = Date.from(LocalDate.now().with(TemporalAdjusters.lastDayOfYear()).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date primeiroDiaDoAnoAnterior = Date.from(LocalDate.now().minusYears(1).with(TemporalAdjusters.firstDayOfYear()).atStartOfDay(ZoneId.systemDefault()).toInstant());

        List<NotaEmitida> notas = new NotaEmitidaDAO().porEmissaoEntre(primeiroDiaDoAnoAnterior, ultimoDiaDoAno).porNaoCancelado().listaDeResultados();

        
        notas.forEach(System.out::println);
    }

}
