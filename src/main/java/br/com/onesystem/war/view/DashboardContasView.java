package br.com.onesystem.war.view;

import br.com.onesystem.domain.DespesaProvisionada;
import br.com.onesystem.domain.Titulo;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.war.service.DespesaProvisionadaService;
import br.com.onesystem.war.service.TituloService;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.HashMap;
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
public class DashboardContasView implements Serializable {

    private BundleUtil msg = new BundleUtil();
    private BigDecimal maiorValorTotal = BigDecimal.ZERO;
    private BarChartModel contas = new BarChartModel();
    private List<DespesaProvisionada> despesasAPagar;
    private List<Titulo> titulosAPagar;
    private HashMap<String, BigDecimal> despesasAPagarTotal = new HashMap<String, BigDecimal>();
    private HashMap<String, BigDecimal> titulosAPagarTotal = new HashMap<String, BigDecimal>();

    @PostConstruct
    public void init() {
        criarGraficoDeVendas();
    }

    private void criarGraficoDeVendas() {
        Date hoje = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date proximosSeisMeses = Date.from(LocalDate.now().plusMonths(5).atStartOfDay(ZoneId.systemDefault()).toInstant());

        for (int i = 0; i <= 5; i++) {
            String month = msg.getLabel(LocalDate.now().plusMonths(i).getMonth().toString());
            despesasAPagarTotal.put(month, BigDecimal.ZERO);
            titulosAPagarTotal.put(month, BigDecimal.ZERO);
        }

        titulosAPagar = new TituloService().buscarTitulosAPagarComVencimentoEntre(hoje, proximosSeisMeses);
        despesasAPagar = new DespesaProvisionadaService().buscarDespesaProvisionadasAPagarComVencimentoEntre(hoje, proximosSeisMeses);

        formataDadosDeContasAPagar();
        criarModeloDeBarra();
    }

    private void formataDadosDeContasAPagar() {
        // ----------------- Despesas Provisionadas ----------------
        for (DespesaProvisionada dp : despesasAPagar) {
            //Busca o mês referente
            String month = msg.getLabel(dp.getVencimento().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getMonth().toString());
            //Adiciona o valor da despesa no mês
            try {
                despesasAPagarTotal.put(month, despesasAPagarTotal.get(month).add(dp.getSaldo()));
            } catch (NullPointerException npe) {
                despesasAPagarTotal.put(month, dp.getSaldo());
            }
            //Atualiza o maior valor total
            if (maiorValorTotal.compareTo(dp.getSaldo()) < 0) {
                maiorValorTotal = dp.getSaldo();
            }
        }

        // ------------------ Titulos A Pagar  ------------------
        for (Titulo t : titulosAPagar) {
            //Busca o mês referente
            String month = msg.getLabel(t.getVencimento().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getMonth().toString());
            //Adiciona o valor da despesa no mês
            try {
                titulosAPagarTotal.put(month, titulosAPagarTotal.get(month).add(t.getSaldo()));
            } catch (NullPointerException npe) {
                titulosAPagarTotal.put(month, t.getSaldo());
            }
            //Atualiza o maior valor total
            if (maiorValorTotal.compareTo(t.getSaldo()) < 0) {
                maiorValorTotal = t.getSaldo();
            }
        }
    }

    private void criarModeloDeBarra() {
        Axis yAxis = contas.getAxis(AxisType.Y);

        contas = inicializaModeloDeBarra();
        contas.setExtender("skinBar");
        contas.setTitle("Contas a Pagas");
        contas.setAnimate(true);
        contas.setStacked(true);
        contas.setLegendPosition("ne");
        contas.setShowPointLabels(true);
        contas.getAxes().put(AxisType.X, new CategoryAxis("Meses"));

        yAxis = contas.getAxis(AxisType.Y);
        yAxis.setLabel("Total");
        yAxis.setMin(0);
        yAxis.setMax(maiorValorTotal.add(new BigDecimal(5000)));
    }

    private BarChartModel inicializaModeloDeBarra() {
        BarChartModel model = new BarChartModel();

        ChartSeries despesasProvisionadasSerie = new ChartSeries();
        despesasProvisionadasSerie.setLabel(msg.getLabel("Despesas_Provisionadas"));
        despesasAPagarTotal.forEach((k, v) -> despesasProvisionadasSerie.set(k, v));

        ChartSeries titulosAPagarSerie = new ChartSeries();
        titulosAPagarSerie.setLabel(msg.getLabel("Titulos"));
        titulosAPagarTotal.forEach((k, v) -> titulosAPagarSerie.set(k, v));

        model.addSeries(despesasProvisionadasSerie);
        model.addSeries(titulosAPagarSerie);

        return model;
    }

    public BarChartModel getContas() {
        return contas;
    }

    public void setContas(BarChartModel contas) {
        this.contas = contas;
    }

}
