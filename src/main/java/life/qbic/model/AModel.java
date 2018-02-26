package life.qbic.model;

import com.vaadin.addon.charts.model.*;

public abstract class AModel {

    protected final Configuration configuration;
    protected final XAxis xAxis;
    protected final YAxis yAxis;


    public AModel(Configuration configuration, String title, String subtitle,AxisTitle xAxisTitle, AxisTitle yAxisTitle,
                  Legend legend){

        this.configuration = configuration;
        this.configuration.setTitle(title);
        this.configuration.setSubTitle(subtitle);

        this.xAxis = configuration.getxAxis();
        this.xAxis.setTitle(xAxisTitle);

        this.yAxis = configuration.getyAxis();
        this.yAxis.setTitle(yAxisTitle);

        this.configuration.setLegend(legend);
    }

    public Configuration getConfiguration() {
        return configuration;
    }

}
