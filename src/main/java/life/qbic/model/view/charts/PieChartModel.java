package life.qbic.model.view.charts;

import com.vaadin.addon.charts.model.*;
import com.vaadin.addon.charts.PointClickEvent;

import java.util.Arrays;


/**
 * @author fhanssen
 * Example: https://demo.vaadin.com/charts/#PieChart , https://demo.vaadin.com/charts/#DonutChart
 */
public class PieChartModel extends AChartModel<DataSeries> {

    private final DataSeries series;

    public PieChartModel(Configuration configuration, String title, String subtitle, Tooltip tooltip, Legend legend, PlotOptionsPie options) {
        super(configuration, title, subtitle, tooltip, legend,options);

        series = new DataSeries();
        super.configuration.setSeries(series);
    }


    public void addData(DataSeries... dataSeries){
        Arrays.stream(dataSeries).forEach(dataSerie -> dataSerie.getData().forEach(series::add));
    }

    public void addDonatPieData(DataSeries... dataSeries){
        configuration.setSeries(dataSeries);
    }


    public void clearData(){
        series.clear();
    }

    public String getDataName(PointClickEvent event){
        return series.get(event.getPointIndex()).getName();
    }

}
