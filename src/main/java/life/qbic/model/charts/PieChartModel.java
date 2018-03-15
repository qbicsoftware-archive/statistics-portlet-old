package life.qbic.model.charts;

import com.vaadin.addon.charts.model.*;

import java.util.Arrays;

public class PieChartModel extends AModel{

    private final DataSeries series;

    public PieChartModel(Configuration configuration, String title, String subtitle, AxisTitle xAxisTitle, AxisTitle yAxisTitle, Tooltip tooltip, Legend legend, PlotOptionsPie options) {
        super(configuration, title, subtitle, xAxisTitle, yAxisTitle, tooltip, legend);

        this.configuration.setPlotOptions(options);
        this.series = new DataSeries();
        this.configuration.setSeries(series);
    }


    public void addData(DataSeriesItem... dataSeries){
        Arrays.stream(dataSeries).forEach(this.series::add);
    }

    public void clearData(){
        this.series.clear();
    }


}
