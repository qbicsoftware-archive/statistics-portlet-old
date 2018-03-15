package life.qbic.model.charts;

import com.vaadin.addon.charts.model.*;

public abstract class AModel {

    final Configuration configuration;
    final XAxis xAxis;
    final YAxis yAxis;


    AModel(Configuration configuration, String title, String subtitle, AxisTitle xAxisTitle, AxisTitle yAxisTitle,
           Tooltip tooltip, Legend legend){

        this.configuration = configuration;
        this.configuration.setTitle(title);
        this.configuration.setSubTitle(subtitle);

        this.xAxis = configuration.getxAxis();
        this.xAxis.setTitle(xAxisTitle);

        this.yAxis = configuration.getyAxis();
        this.yAxis.setTitle(yAxisTitle);

        this.configuration.setTooltip(tooltip);

        this.configuration.setLegend(legend);
    }

    public Configuration getConfiguration() {
        return configuration;
    }

}
