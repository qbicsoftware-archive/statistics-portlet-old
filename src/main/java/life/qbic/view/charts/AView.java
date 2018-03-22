package life.qbic.view.charts;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import life.qbic.model.charts.AModel;

/**
 * @author fhanssen
 * This abstract class holds the chart. When
 * adding a new chart type (such as Scatter Plot or so) a new chart view needs be added. A comprehensive
 * guide on Vaadin charts can be found here: https://demo.vaadin.com/charts/
 */
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

    public Chart getChart() {
        return chart;
    }

    //TODO 5: If your Chart TYPE does not exist extend this chart

}
