package br.com.onesystem.war.view;

import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.war.service.NotaEmitidaService;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;

import org.primefaces.model.chart.BarChartModel;

@Named
@RequestScoped
public class DashboardVendaView implements Serializable {

    private BundleUtil msg = new BundleUtil();
    private BigDecimal maiorValorTotal = BigDecimal.ZERO;
    private List<NotaEmitida> notasEmitidas;
    private BarChartModel vendas = new BarChartModel();
    private BarChartModel vendasPorVendedor = new BarChartModel();
    private BigDecimal[] pTotal = new BigDecimal[12];
    private BigDecimal[] uTotal = new BigDecimal[12];
    private HashMap<String, BigDecimal> vendedorTotal = new LinkedHashMap<String, BigDecimal>();

    @PostConstruct
    public void init() {
        criarGraficoDeVendas();
    }

    private void criarGraficoDeVendas() {
        try {

            Date ultimoDiaDoAno = Date.from(LocalDate.now().with(TemporalAdjusters.lastDayOfYear()).atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date primeiroDiaDoAnoAnterior = Date.from(LocalDate.now().minusYears(1).with(TemporalAdjusters.firstDayOfYear()).atStartOfDay(ZoneId.systemDefault()).toInstant());

            notasEmitidas = new NotaEmitidaService().buscarVendasDoPeriodo(primeiroDiaDoAnoAnterior, ultimoDiaDoAno);

            formataDadosDeVendas();
            criarModeloDeBarra();
            criaModeloDeVendasPorVendedor();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    private void formataDadosDeVendas() {
        Month mesAtual = LocalDate.now().getMonth();
        for (NotaEmitida n : notasEmitidas) {
            LocalDate data = n.getEmissao().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (data.getYear() == LocalDate.now().getYear()) {
                uTotal[data.getMonthValue() - 1] = n.getTotalNota();
                if (data.getMonth() == mesAtual) {
                    try {
                        vendedorTotal.put(n.getUsuario().getPessoa().getPrimeiroNomeEComecoDoSobrenome(), vendedorTotal.get(n.getUsuario().getPessoa().getPrimeiroNomeEComecoDoSobrenome()).add(n.getTotalNota()));
                    } catch (NullPointerException npe) {
                        vendedorTotal.put(n.getUsuario().getPessoa().getPrimeiroNomeEComecoDoSobrenome(), n.getTotalNota());
                    }
                }
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
        Axis xAxis = vendas.getAxis(AxisType.X);

        vendas = inicializaModeloDeBarra();
        vendas.setExtender("skinBar");
        vendas.setTitle(msg.getLabel("Vendas"));
        vendas.setAnimate(true);
        vendas.setLegendPosition("ne");
        vendas.setShowPointLabels(true);
        vendas.getAxes().put(AxisType.X, new CategoryAxis(msg.getLabel("Meses")));
        vendas.getAxes().get(AxisType.X).setTickAngle(-50);

        xAxis = vendas.getAxis(AxisType.Y);
        xAxis.setLabel(msg.getLabel("Meses"));
        xAxis.setMin(0);
        xAxis.setTickAngle(-50);
        xAxis.setMax(maiorValorTotal.add(new BigDecimal(5000)));

        yAxis = vendas.getAxis(AxisType.Y);
        yAxis.setLabel(msg.getLabel("Total"));
        yAxis.setMin(0);
        yAxis.setMax(maiorValorTotal.add(new BigDecimal(5000)));
    }

    private BarChartModel inicializaModeloDeBarra() {
        BarChartModel model = new BarChartModel();
        LocalDate pAno = LocalDate.now().minusYears(1);
        LocalDate uAno = LocalDate.now();

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

    private void criaModeloDeVendasPorVendedor() {
        Axis yAxis = vendasPorVendedor.getAxis(AxisType.Y);

        vendasPorVendedor = inicializaModeloDeVendasPorVendedor();
        vendasPorVendedor.setExtender("skinBar");
        vendasPorVendedor.setTitle(msg.getLabel("Vendas_por_Vendedor_no_Mes"));
        vendasPorVendedor.setAnimate(true);
        vendasPorVendedor.setLegendPosition("ne");
        vendasPorVendedor.setShowPointLabels(true);
        vendasPorVendedor.getAxes().put(AxisType.X, new CategoryAxis(msg.getLabel("Vendedores")));

        yAxis = vendasPorVendedor.getAxis(AxisType.Y);
        yAxis.setLabel(msg.getLabel("Total_Por_Vendedor"));
        yAxis.setMin(0);
        yAxis.setMax(maiorValorTotal.add(new BigDecimal(5000)));
    }

    private BarChartModel inicializaModeloDeVendasPorVendedor() {
        BarChartModel model = new BarChartModel();

        vendedorTotal.entrySet().stream().sorted(Map.Entry.comparingByValue((BigDecimal v1, BigDecimal v2) -> v2.compareTo(v1) )).limit(10).forEach(e -> {
            ChartSeries c = new ChartSeries(e.getKey());
            c.set("", e.getValue());
            model.addSeries(c);
        });
        return model;
    }

    public BarChartModel getVendas() {
        return vendas;
    }

    public void setVendas(BarChartModel vendas) {
        this.vendas = vendas;
    }

    public BarChartModel getVendasPorVendedor() {
        return vendasPorVendedor;
    }

    public void setVendasPorVendedor(BarChartModel vendasPorVendedor) {
        this.vendasPorVendedor = vendasPorVendedor;
    }

}
