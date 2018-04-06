package life.qbic.model.view;

import life.qbic.logging.Log4j2Logger;
import life.qbic.logging.Logger;
import life.qbic.model.components.AComponent;
import life.qbic.model.components.GitHubLabels;
import life.qbic.model.view.charts.AChartModel;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//TODO abstract GitHUbLabels
public class GridModel implements AModel<AComponent> {

    private static final Logger logger = new Log4j2Logger(GridModel.class);


    private final List<AComponent> data = new ArrayList<>();
    private final String title;
    private final String subtitle;

    public GridModel(String title, String subtitle){
        this.title = title;
        this.subtitle = subtitle;

        logger.info("New grid model for " + title + " was successfully created");

    }

    public List<AComponent> getData() {
        return data;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    @Override
    public void addData(AComponent... dataItem) {
        data.addAll(Arrays.asList(dataItem));
    }
}
