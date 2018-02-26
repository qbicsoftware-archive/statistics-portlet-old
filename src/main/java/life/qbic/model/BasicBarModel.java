package life.qbic.model;

import com.vaadin.addon.charts.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BasicBarModel {

    private final List<Series> series;
    private final Configuration configuration;
    private final XAxis xAxis;
    private final YAxis yAxis;
    private final PlotOptionsBar options;
    private final String description;

    public BasicBarModel(String description, Configuration configuration, String title, String subtitle, AxisTitle xAxisTitle, AxisTitle yAxisTitle,
                         Tooltip tooltip, Legend legend, PlotOptionsBar options){

        this.description = description;

        this.configuration = configuration;

        this.configuration.setTitle(title);
        this.configuration.setSubTitle(subtitle);

        this.xAxis = new XAxis();
        this.xAxis.setTitle(xAxisTitle);
        this.configuration.addxAxis(xAxis);

        this.yAxis = new YAxis();
        this.yAxis.setTitle(yAxisTitle);
        this.configuration.addyAxis(yAxis);

        this.configuration.setTooltip(tooltip);

        this.configuration.setLegend(legend);

        this.options = options;
        this.options.setDataLabels(new DataLabels(true));
        this.configuration.setPlotOptions(options);

        this.series = new ArrayList<>();
        this.configuration.setSeries(series);

        this.configuration.disableCredits();

    }

    public String getDescription() {
        return description;
    }

    public void addXCategorie(String... cat){
        xAxis.setCategories(cat);
    }

    public void setyMin(int yMin){
        this.yAxis.setMin(yMin);
    }

    public void addData(ListSeries... listSeries){
        Arrays.stream(listSeries).forEach(l -> this.series.add(l));
    }

    public void clearData(){
        this.series.clear();
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}
