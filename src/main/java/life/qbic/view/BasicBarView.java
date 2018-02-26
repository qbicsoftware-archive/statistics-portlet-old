package life.qbic.view;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.ui.Component;
import life.qbic.model.BasicBarModel;

public class BasicBarView {

    private final Chart chart;

    public BasicBarView(){
        this.chart = new Chart(ChartType.BAR);
    }

    public Configuration getConfiguration(){
        return this.chart.getConfiguration();
    }

    public void draw(BasicBarModel model){
        chart.drawChart(model.getConfiguration());
    }

    public Component getChart() {
        return chart;
    }

}
