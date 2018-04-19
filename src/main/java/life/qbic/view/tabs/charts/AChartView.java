package life.qbic.view.tabs.charts;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.ui.Component;
import life.qbic.model.view.charts.AChartModel;
import life.qbic.view.tabs.AView;

/**
 * @author fhanssen
 * This abstract class holds the chart. When
 * adding a new chart type (such as Scatter Plot or so) a new chart view needs be added. A comprehensive
 * guide on Vaadin charts can be found here: https://demo.vaadin.com/charts/
 */
public abstract class AChartView extends AView{

    private final Chart chart;

    AChartView(ChartType type){
        super();
        this.chart = new Chart(type);
    }

    public Configuration getConfiguration(){
        return this.chart.getConfiguration();
    }

    public void draw(AChartModel model){
        chart.drawChart(model.getConfiguration());
    }

    @Override
    public Component getComponent() {
        return chart;
    }


    //TODO 3: If your Chart TYPE does not exist extend this class
}