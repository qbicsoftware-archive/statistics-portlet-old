package life.qbic.model.charts;

import com.vaadin.addon.charts.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author fhanssen
 * Example: https://demo.vaadin.com/charts/#BasicBar
 */
public class BarModel extends AModel {

    private final List<Series> series;
    private final XAxis xAxis;
    private final YAxis yAxis;

    public BarModel(Configuration configuration, String title, String subtitle,
                         Tooltip tooltip, Legend legend, AxisTitle xAxisTitle, AxisTitle yAxisTitle,PlotOptionsBar options){

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


}