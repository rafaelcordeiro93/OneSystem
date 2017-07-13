package br.com.onesystem.war.view;

import br.com.onesystem.domain.DespesaProvisionada;
import br.com.onesystem.domain.ReceitaProvisionada;
import br.com.onesystem.domain.Titulo;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.war.service.DespesaProvisionadaService;
import br.com.onesystem.war.service.ReceitaProvisionadaService;
import br.com.onesystem.war.service.TituloService;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;

import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.LineChartModel;

@Named
@RequestScoped
public class DashboardContasView implements Serializable {

    private BundleUtil msg = new BundleUtil();
    private BigDecimal maiorValorTotal = BigDecimal.ZERO;
    private BarChartModel contasAPagar = new BarChartModel();
    private BarChartModel contasAReceber = new BarChartModel();
    private LineChartModel comparativoDeContas = new LineChartModel();
    private List<DespesaProvisionada> despesasAPagar;
    private List<ReceitaProvisionada> receitasAReceber;
    private List<Titulo> titulos;
    private SortedMap<String, BigDecimal> despesasAPagarTotal = new TreeMap<String, BigDecimal>();
    private SortedMap<String, BigDecimal> titulosAPagarTotal = new TreeMap<String, BigDecimal>();
    private SortedMap<String, BigDecimal> receitasAReceberTotal = new TreeMap<String, BigDecimal>();
    private SortedMap<String, BigDecimal> titulosAReceberTotal = new TreeMap<String, BigDecimal>();
    private SortedMap<String, BigDecimal> contasAPagarTotal = new TreeMap<String, BigDecimal>();
    private SortedMap<String, BigDecimal> contasAReceberTotal = new TreeMap<String, BigDecimal>();

    @PostConstruct
    public void init() {
        criarGraficoDeVendas();
    }

    public void criarGraficoDeVendas() {
        Date hoje = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date proximosSeisMeses = Date.from(LocalDate.now().plusMonths(5).atStartOfDay(ZoneId.systemDefault()).toInstant());

        for (int i = 0; i <= 5; i++) {
            String month = msg.getLabel(LocalDate.now().plusMonths(i).getMonth().toString());
            Integer monthValue = LocalDate.now().plusMonths(i).getMonthValue();
            despesasAPagarTotal.put(monthValue + '-' + month, BigDecimal.ZERO);
            receitasAReceberTotal.put(monthValue + '-' + month, BigDecimal.ZERO);
            titulosAPagarTotal.put(monthValue + '-' + month, BigDecimal.ZERO);
            titulosAReceberTotal.put(monthValue + '-' + month, BigDecimal.ZERO);
        }

        despesasAPagar = new DespesaProvisionadaService().buscarDespesaProvisionadasAPagarComVencimentoEntre(hoje, proximosSeisMeses);
        receitasAReceber = new ReceitaProvisionadaService().buscarReceitaProvisionadasAReceberComVencimentoEntre(hoje, proximosSeisMeses);

        titulos = new TituloService().buscarTitulosComVencimentoEntre(hoje, proximosSeisMeses);

        formataDadosDeContasAPagar();
        criarModeloDeComparativoDeContas();
        criarModeloDeContasAPagar();
        criarModeloDeContasAReceber();
    }

    private void formataDadosDeContasAPagar() {
        // ----------------- Despesas Provisionadas ----------------
        for (DespesaProvisionada dp : despesasAPagar) {
            //Busca o mês referente
            String month = msg.getLabel(dp.getVencimento().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getMonth().toString());
            Integer monthValue = dp.getVencimento().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getMonthValue();
            //Adiciona o valor da despesa no mês
            try {
                despesasAPagarTotal.put(monthValue + '-' + month, despesasAPagarTotal.get(month).add(dp.getSaldo()));
            } catch (NullPointerException npe) {
                despesasAPagarTotal.put(monthValue + '-' + month, dp.getSaldo());
            }
            //Atualiza o maior valor total
            if (maiorValorTotal.compareTo(dp.getSaldo()) < 0) {
                maiorValorTotal = dp.getSaldo();
            }
        }

        // ----------------- Receitas Provisionadas ----------------
        for (ReceitaProvisionada rp : receitasAReceber) {
            //Busca o mês referente
            String month = msg.getLabel(rp.getVencimento().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getMonth().toString());
            Integer monthValue = rp.getVencimento().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getMonthValue();
            //Adiciona o valor da receita no mês
            try {
                receitasAReceberTotal.put(monthValue + '-' + month, receitasAReceberTotal.get(month).add(rp.getSaldo()));
            } catch (NullPointerException npe) {
                receitasAReceberTotal.put(monthValue + '-' + month, rp.getSaldo());
            }
            //Atualiza o maior valor total
            if (maiorValorTotal.compareTo(rp.getSaldo()) < 0) {
                maiorValorTotal = rp.getSaldo();
            }
        }

        // ------------------ Titulos ------------------
        for (Titulo t : titulos) {
            //Busca o mês referente
            String month = msg.getLabel(t.getVencimento().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getMonth().toString());
            Integer monthValue = t.getVencimento().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getMonthValue();

            //Adiciona o valor da despesa no mês
            try {
                if (t.getOperacaoFinanceira() == OperacaoFinanceira.SAIDA) {
                    titulosAPagarTotal.put(monthValue + '-' + month, titulosAPagarTotal.get(month).add(t.getSaldo()));
                } else if (t.getOperacaoFinanceira() == OperacaoFinanceira.ENTRADA) {
                    titulosAReceberTotal.put(monthValue + '-' + month, titulosAPagarTotal.get(month).add(t.getSaldo()));
                }
            } catch (NullPointerException npe) {
                if (t.getOperacaoFinanceira() == OperacaoFinanceira.SAIDA) {
                    titulosAPagarTotal.put(monthValue + '-' + month, t.getSaldo());
                } else if (t.getOperacaoFinanceira() == OperacaoFinanceira.ENTRADA) {
                    titulosAReceberTotal.put(monthValue + '-' + month, t.getSaldo());
                }
            }
            //Atualiza o maior valor total
            if (maiorValorTotal.compareTo(t.getSaldo()) < 0) {
                maiorValorTotal = t.getSaldo();
            }
        }

    }

    private void criarModeloDeContasAPagar() {
        Axis yAxis = contasAPagar.getAxis(AxisType.Y);

        contasAPagar = inicializaModeloDeContaAPagar();
        contasAPagar.setExtender("skinBar");
        contasAPagar.setTitle("Contas a Pagar");
        contasAPagar.setAnimate(true);
        contasAPagar.setStacked(true);
        contasAPagar.setLegendPosition("ne");
        contasAPagar.setShowPointLabels(true);
        contasAPagar.getAxes().put(AxisType.X, new CategoryAxis("Meses"));

        yAxis = contasAPagar.getAxis(AxisType.Y);
        yAxis.setLabel("Total");
        yAxis.setMin(0);
        yAxis.setMax(maiorValorTotal.add(new BigDecimal(5000)));
    }

    private BarChartModel inicializaModeloDeContaAPagar() {
        BarChartModel model = new BarChartModel();

        ChartSeries despesasProvisionadasSerie = new ChartSeries();
        despesasProvisionadasSerie.setLabel(msg.getLabel("Despesas_Provisionadas"));
        despesasAPagarTotal.forEach((k, v) -> despesasProvisionadasSerie.set(k.substring(2, k.length()), v));

        ChartSeries titulosAPagarSerie = new ChartSeries();
        titulosAPagarSerie.setLabel(msg.getLabel("Titulos"));
        titulosAPagarTotal.forEach((k, v) -> titulosAPagarSerie.set(k.substring(2, k.length()), v));

        model.addSeries(despesasProvisionadasSerie);
        model.addSeries(titulosAPagarSerie);

        return model;
    }

    private void criarModeloDeContasAReceber() {
        Axis yAxis = contasAPagar.getAxis(AxisType.Y);

        contasAReceber = inicializaModeloDeContaAReceber();
        contasAReceber.setExtender("skinBar");
        contasAReceber.setTitle("Contas a Receber");
        contasAReceber.setAnimate(true);
        contasAReceber.setStacked(true);
        contasAReceber.setLegendPosition("ne");
        contasAReceber.setShowPointLabels(true);
        contasAReceber.getAxes().put(AxisType.X, new CategoryAxis("Meses"));

        yAxis = contasAPagar.getAxis(AxisType.Y);
        yAxis.setLabel("Total");
        yAxis.setMin(0);
        yAxis.setMax(maiorValorTotal.add(new BigDecimal(5000)));
    }

    private BarChartModel inicializaModeloDeContaAReceber() {
        BarChartModel model = new BarChartModel();

        ChartSeries receitassProvisionadasSerie = new ChartSeries();
        receitassProvisionadasSerie.setLabel(msg.getLabel("Receitas_Provisionadas"));
        receitasAReceberTotal.forEach((k, v) -> receitassProvisionadasSerie.set(k.substring(2, k.length()), v));

        ChartSeries titulosAReceberSerie = new ChartSeries();
        titulosAReceberSerie.setLabel(msg.getLabel("Titulos"));
        titulosAReceberTotal.forEach((k, v) -> titulosAReceberSerie.set(k.substring(2, k.length()), v));

        model.addSeries(receitassProvisionadasSerie);
        model.addSeries(titulosAReceberSerie);

        return model;
    }

    private void criarModeloDeComparativoDeContas() {
        Axis yAxis = contasAPagar.getAxis(AxisType.Y);

        comparativoDeContas = inicializaModeloDeComparativoDeContas();
        comparativoDeContas.setExtender("skinLine");
        comparativoDeContas.setTitle("Comparativo de Contas");
        comparativoDeContas.setAnimate(true);
        comparativoDeContas.setLegendPosition("ne");
        comparativoDeContas.setShowPointLabels(true);
        comparativoDeContas.getAxes().put(AxisType.X, new CategoryAxis("Meses"));

        yAxis = contasAPagar.getAxis(AxisType.Y);
        yAxis.setLabel("Total");
        yAxis.setMin(0);
        yAxis.setMax(maiorValorTotal.add(new BigDecimal(5000)));
    }

    private LineChartModel inicializaModeloDeComparativoDeContas() {
        LineChartModel model = new LineChartModel();

        titulosAPagarTotal.forEach((k, v) -> contasAPagarTotal.put(k, v.add(despesasAPagarTotal.get(k))));
        titulosAReceberTotal.forEach((k, v) -> contasAReceberTotal.put(k, v.add(receitasAReceberTotal.get(k))));

        ChartSeries contasAPagarSerie = new ChartSeries();
        contasAPagarSerie.setLabel(msg.getLabel("Contas_A_Pagar"));
        contasAPagarTotal.forEach((k, v) -> contasAPagarSerie.set(k.substring(2, k.length()), v));

        ChartSeries contasAReceberSerie = new ChartSeries();
        contasAReceberSerie.setLabel(msg.getLabel("Contas_A_Receber"));
        contasAReceberTotal.forEach((k, v) -> contasAReceberSerie.set(k.substring(2, k.length()), v));

        model.addSeries(contasAReceberSerie);
        model.addSeries(contasAPagarSerie);

        return model;
    }

    public BarChartModel getContasAPagar() {
        return contasAPagar;
    }

    public void setContasAPagar(BarChartModel contasAPagar) {
        this.contasAPagar = contasAPagar;
    }

    public BarChartModel getContasAReceber() {
        return contasAReceber;
    }

    public void setContasAReceber(BarChartModel contasAReceber) {
        this.contasAReceber = contasAReceber;
    }

    public LineChartModel getComparativoDeContas() {
        return comparativoDeContas;
    }

    public void setComparativoDeContas(LineChartModel comparativoDeContas) {
        this.comparativoDeContas = comparativoDeContas;
    }

}
