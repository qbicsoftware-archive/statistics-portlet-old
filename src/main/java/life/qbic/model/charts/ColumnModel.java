package life.qbic.model.charts;

import com.vaadin.addon.charts.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ColumnModel  extends AModel {

    private final List<Series> series;
    private final XAxis xAxis;
    private final YAxis yAxis;

    public ColumnModel(Configuration configuration, String title, String subtitle,
                         Tooltip tooltip, Legend legend, AxisTitle xAxisTitle, AxisTitle yAxisTitle, PlotOptionsColumn options){

        super(configuration, title, subtitle, tooltip, legend);

        this.configuration.setPlotOptions(options);
        this.series = new ArrayList<>();
        this.configuration.setSeries(series);
        xAxis = this.configuration.getxAxis();
        yAxis = this.configuration.getyAxis();
        xAxis.setTitle(xAxisTitle);
        yAxis.setTitle(yAxisTitle);

    }

    public void addXCategorie(String... cat){
        xAxis.setCategories(cat);
    }

    public void setyMin(int yMin){
        yAxis.setMin(yMin);
    }

    public void addData(ListSeries... listSeries){
        this.series.addAll(Arrays.asList(listSeries));
    }

    public void clearData(){
        this.series.clear();
    }

    @Override
    public Configuration getConfiguration() {
        return super.getConfiguration();
    }

}
