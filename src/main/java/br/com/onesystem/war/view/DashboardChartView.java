package br.com.onesystem.war.view;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.primefaces.model.chart.BarChartModel;

@Named
public class DashboardChartView implements Serializable {

    private BarChartModel barModel;

    @PostConstruct
    public void init() {
    }

    public BarChartModel getBarModel() {
        return barModel;
    }

    public void setBarModel(BarChartModel barModel) {
        this.barModel = barModel;
    }

}
