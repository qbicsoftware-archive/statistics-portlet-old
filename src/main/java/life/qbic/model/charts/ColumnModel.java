package life.qbic.model.charts;

import com.vaadin.addon.charts.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ColumnModel  extends AModel {

    private final List<Series> series;

    public ColumnModel(Configuration configuration, String title, String subtitle, AxisTitle xAxisTitle, AxisTitle yAxisTitle,
                         Tooltip tooltip, Legend legend, PlotOptionsColumn options){

        super(configuration, title, subtitle, xAxisTitle, yAxisTitle, tooltip, legend);

        this.configuration.setPlotOptions(options);
        this.series = new ArrayList<>();
        this.configuration.setSeries(series);

    }

    public void addXCategorie(String... cat){
        xAxis.setCategories(cat);
    }

    public void setyMin(int yMin){
        yAxis.setMin(yMin);
    }

    public void addData(ListSeries... listSeries){
        Arrays.stream(listSeries).forEach(this.series::add);
    }

    public void clearData(){
        this.series.clear();
    }

}
