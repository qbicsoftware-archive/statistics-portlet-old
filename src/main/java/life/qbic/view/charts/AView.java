package life.qbic.view.charts;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.ui.Component;
import life.qbic.model.charts.AModel;

public abstract class AView {

    private final Chart chart;

    AView(ChartType type){
        this.chart = new Chart(type);
    }

    public Configuration getConfiguration(){
        return this.chart.getConfiguration();
    }

    public void draw(AModel model){
        chart.drawChart(model.getConfiguration());
    }

    public Component getChart() {
        return chart;
    }

}
