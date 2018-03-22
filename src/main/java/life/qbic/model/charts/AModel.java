package life.qbic.model.charts;

import com.vaadin.addon.charts.model.*;
import life.qbic.logging.Log4j2Logger;
import life.qbic.logging.Logger;
import life.qbic.main.StatisticsViewUI;

/**
 * @author fhanssen
 * This abstract class holds all fields and methods needed for a general Chart, mainly the configuration file. When
 * adding a new chart type (such as Scatter Plot or so) a new chart model needs be added. A comprehensive
 * guide on Vaadin charts can be found here: https://demo.vaadin.com/charts/
 */
public abstract class AModel {

    private static Logger logger = new Log4j2Logger(AModel.class);

    final Configuration configuration;

    AModel(Configuration configuration, String title, String subtitle,
           Tooltip tooltip, Legend legend){

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
