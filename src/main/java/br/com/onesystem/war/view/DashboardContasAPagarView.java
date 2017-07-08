package br.com.onesystem.war.view;

import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.war.service.DespesaProvisionadaService;
import br.com.onesystem.war.service.NotaEmitidaService;
import br.com.onesystem.war.service.TituloService;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;

import org.primefaces.model.chart.BarChartModel;

@Named
@ViewScoped
public class DashboardContasAPagarView implements Serializable {

    private BigDecimal maiorValorTotal = BigDecimal.ZERO;
    private List<NotaEmitida> notasEmitidas;
    private BarChartModel vendas = new BarChartModel();
    private BigDecimal[] pTotal = new BigDecimal[12];
    private BigDecimal[] uTotal = new BigDecimal[12];

    @PostConstruct
    public void init() {
        criarGraficoDeVendas();
    }

    private void criarGraficoDeVendas() {
        try {

            Date hoje = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date proximosSeisMeses = Date.from(LocalDate.now().plusMonths(6).with(TemporalAdjusters.firstDayOfYear()).atStartOfDay(ZoneId.systemDefault()).toInstant());
            
            new TituloService().buscarTitulosAPagarComVencimentoEntre(hoje, proximosSeisMeses);
            new DespesaProvisionadaService().buscarDespesaProvisionadasAPagarComVencimentoEntre(hoje, proximosSeisMeses);

            notasEmitidas = new NotaEmitidaService().buscarVendasDoPeriodo(proximosSeisMeses, hoje);

            formataDadosDeVendas();
            criarModeloDeBarra();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    private void formataDadosDeVendas() {
        for (NotaEmitida n : notasEmitidas) {
            LocalDate data = n.getEmissao().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (data.getYear() == LocalDate.now().getYear()) {
                uTotal[data.getMonthValue() - 1] = n.getTotalNota();
            } else {
                pTotal[data.getMonthValue() - 1] = n.getTotalNota();
            }
            if (maiorValorTotal.compareTo(n.getTotalNota()) < 0) {
                maiorValorTotal = n.getTotalNota();
            }
        }
    }

    private void criarModeloDeBarra() {
        Axis yAxis = vendas.getAxis(AxisType.Y);

        vendas = inicializaModeloDeBarra();
        vendas.setExtender("skinBar");
        vendas.setTitle("Vendas");
        vendas.setAnimate(true);
        vendas.setLegendPosition("ne");
        vendas.setShowPointLabels(true);
        vendas.getAxes().put(AxisType.X, new CategoryAxis("Meses"));

        yAxis = vendas.getAxis(AxisType.Y);
        yAxis.setLabel("Total");
        yAxis.setMin(0);
        yAxis.setMax(maiorValorTotal.add(new BigDecimal(5000)));
    }

    private BarChartModel inicializaModeloDeBarra() {
        BarChartModel model = new BarChartModel();
        LocalDate pAno = LocalDate.now().minusYears(1);
        LocalDate uAno = LocalDate.now();

        String pAnoAbreviado = String.valueOf(pAno.getYear()).substring(2, 4);
        String uAnoAbreviado = String.valueOf(uAno.getYear()).substring(2, 4);

        ChartSeries pAnoSerie = new ChartSeries();
        pAnoSerie.setLabel(String.valueOf(pAno.getYear()));
        pAnoSerie.set("01", pTotal[0]);
        pAnoSerie.set("02", pTotal[1]);
        pAnoSerie.set("03", pTotal[2]);
        pAnoSerie.set("04", pTotal[3]);
        pAnoSerie.set("05", pTotal[4]);
        pAnoSerie.set("06", pTotal[5]);
        pAnoSerie.set("07", pTotal[6]);
        pAnoSerie.set("08", pTotal[7]);
        pAnoSerie.set("09", pTotal[8]);
        pAnoSerie.set("10", pTotal[9]);
        pAnoSerie.set("11", pTotal[10]);
        pAnoSerie.set("12", pTotal[11]);

        ChartSeries uAnoSerie = new ChartSeries();
        uAnoSerie.setLabel(String.valueOf(uAno.getYear()));
        uAnoSerie.set("01", uTotal[0]);
        uAnoSerie.set("02", uTotal[1]);
        uAnoSerie.set("03", uTotal[2]);
        uAnoSerie.set("04", uTotal[3]);
        uAnoSerie.set("05", uTotal[4]);
        uAnoSerie.set("06", uTotal[5]);
        uAnoSerie.set("07", uTotal[6]);
        uAnoSerie.set("08", uTotal[7]);
        uAnoSerie.set("09", uTotal[8]);
        uAnoSerie.set("10", uTotal[9]);
        uAnoSerie.set("11", uTotal[10]);
        uAnoSerie.set("12", uTotal[11]);

        model.addSeries(pAnoSerie);
        model.addSeries(uAnoSerie);

        return model;
    }

    public BarChartModel getVendas() {
        return vendas;
    }

    public void setVendas(BarChartModel vendas) {
        this.vendas = vendas;
    }

}
