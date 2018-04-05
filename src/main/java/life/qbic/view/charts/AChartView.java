package life.qbic.view.charts;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.ui.GridLayout;
import life.qbic.model.charts.AChartModel;
import life.qbic.view.AView;

public abstract class AChartView extends AView{

    private final Chart chart;
    private final GridLayout gridLayout;

    public AChartView(ChartType type){
        super();
        this.chart = new Chart(type);
        this.gridLayout = new GridLayout(4,3);
    }

    public Configuration getConfiguration(){
        return this.chart.getConfiguration();
    }

    public void draw(AChartModel model){
        chart.drawChart(model.getConfiguration());
    }

    public Chart getChart() {
        return chart;
    }

    public GridLayout getGrid(){
        return gridLayout;
    }

    //TODO 3: If your Chart TYPE does not exist extend this class
}
