
import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.ArmazemDeRegistros;
import br.com.onesystem.dao.EstoqueDAO;
import br.com.onesystem.domain.ConfiguracaoEstoque;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.Deposito;
import br.com.onesystem.domain.GrupoFiscal;
import br.com.onesystem.domain.IVA;
import br.com.onesystem.domain.Estoque;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.TipoDespesa;
import br.com.onesystem.domain.TipoReceita;
import br.com.onesystem.domain.UnidadeMedidaItem;
import br.com.onesystem.domain.builder.ConfiguracaoContabilBuilder;
import br.com.onesystem.dao.NotaEmitidaDAO;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.TipoItem;
import br.com.onesystem.war.builder.ConfiguracaoContabilBV;
import br.com.onesystem.war.service.ConfiguracaoContabilService;
import java.math.BigDecimal;
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
    }

}
