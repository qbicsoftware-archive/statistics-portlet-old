package life.qbic.model.charts;

import com.vaadin.addon.charts.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BasicBarModel extends AModel {

    private final List<Series> series;

    public BasicBarModel(Configuration configuration, String title, String subtitle, AxisTitle xAxisTitle, AxisTitle yAxisTitle,
                         Tooltip tooltip, Legend legend, PlotOptionsBar options){

        super(configuration, title, subtitle, xAxisTitle, yAxisTitle, legend);

        this.configuration.setTooltip(tooltip);

        PlotOptionsBar options1 = options;
        options1.setDataLabels(new DataLabels(true));
        this.configuration.setPlotOptions(options);

        this.series = new ArrayList<>();
        this.configuration.setSeries(series);

        this.configuration.disableCredits();

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
