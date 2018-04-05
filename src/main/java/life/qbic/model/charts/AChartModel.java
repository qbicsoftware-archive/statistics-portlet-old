package life.qbic.model.charts;

import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.Legend;
import com.vaadin.addon.charts.model.Tooltip;
import life.qbic.logging.Log4j2Logger;
import life.qbic.logging.Logger;
import life.qbic.model.AModel;

public abstract class AChartModel extends AModel{

    private static Logger logger = new Log4j2Logger(AChartModel.class);

    final Configuration configuration;

    AChartModel(Configuration configuration, String title, String subtitle,
           Tooltip tooltip, Legend legend){

        super(title, subtitle);

        this.configuration = configuration;
        this.configuration.setTitle(title);
        this.configuration.setSubTitle(subtitle);

        this.configuration.setTooltip(tooltip);

        this.configuration.setLegend(legend);

        logger.info("New chart model for " + title + " was successfully created");
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    //TODO 2: If your chart TYPE does not exists yet, extend this class
}
