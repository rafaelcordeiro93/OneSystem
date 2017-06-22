package br.com.onesystem.war.view;

import br.com.onesystem.dao.NotaEmitidaDAO;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.valueobjects.EstadoDeNota;
import br.com.onesystem.war.service.NotaEmitidaService;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.LineChartModel;

@Named
@ViewScoped
public class DashboardChartView implements Serializable {

    private List<NotaEmitida> notasEmitidas;
    private LineChartModel vendas;

    @PostConstruct
    public void init() {

        Date ultimoDiaDoAno = Date.from(LocalDate.now().with(TemporalAdjusters.lastDayOfYear()).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date primeiroDiaDoAnoAnterior = Date.from(LocalDate.now().minusYears(1).with(TemporalAdjusters.firstDayOfYear()).atStartOfDay(ZoneId.systemDefault()).toInstant());

        List<NotaEmitida> notas = new NotaEmitidaDAO().porEmissaoEntre(primeiroDiaDoAnoAnterior, ultimoDiaDoAno).porNaoCancelado().listaDeResultados();

        
    }

}
