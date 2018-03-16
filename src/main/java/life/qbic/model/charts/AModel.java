package life.qbic.model.charts;

import com.vaadin.addon.charts.model.*;

public abstract class AModel {

    final Configuration configuration;

    AModel(Configuration configuration, String title, String subtitle,
           Tooltip tooltip, Legend legend){

        this.configuration = configuration;
        this.configuration.setTitle(title);
        this.configuration.setSubTitle(subtitle);

        this.configuration.setTooltip(tooltip);

        this.configuration.setLegend(legend);
    }

    public Configuration getConfiguration() {
        return configuration;
    }

}
