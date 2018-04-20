package life.qbic.presenter.charts;

import com.vaadin.addon.charts.model.*;
import com.vaadin.addon.charts.model.style.Color;
import life.qbic.model.view.charts.ColumnModel;
import life.qbic.presenter.utils.Helper;
import life.qbic.view.MainView;
import life.qbic.view.TabView;
import life.qbic.view.tabs.charts.ColumnView;
import submodule.data.ChartConfig;

import java.util.Arrays;

public class ProjectTechColumnPresenter extends AChartPresenter <ColumnModel, ColumnView> {


    public ProjectTechColumnPresenter(MainView mainView, ChartConfig chartConfig){
        super(chartConfig, mainView, new ColumnView());

        addChartSettings();
        addChartData();
        addChartListener();
    }

    @Override
    void addChartSettings() {
        PlotOptionsColumn plot = new PlotOptionsColumn();

        plot.setDataLabels(new DataLabels(true));

        Tooltip tooltip = new Tooltip();
        tooltip.setFormatter("this.point.name + ': <b>'+ this.y + '</b> Projects'");

        Legend legend = new Legend();
        legend.setEnabled(false);


        this.model = new ColumnModel(this.view.getConfiguration(), chartConfig.getSettings().getTitle(),
                null, tooltip, legend, new AxisTitle(chartConfig.getSettings().getxAxisTitle()), new AxisTitle(chartConfig.getSettings().getyAxisTitle()), plot);

        this.model.setXAxisType(AxisType.CATEGORY);
        logger.info("Settings were added to a chart of "+ this.getClass() +" with chart titel: " + this.view.getConfiguration().getTitle().getText());

    }

    @Override
    void addChartData() {

        //This is necessary to get from Object to required String arrays
        Object[] objectArray = chartConfig.getData().keySet().toArray(new Object[chartConfig.getData().keySet().size()]);
        String[] keySet = Arrays.asList(objectArray).toArray(new String[objectArray.length]);

        Color[] innerColors = Arrays.copyOf(Helper.colors, chartConfig.getSettings().getxCategories().size());
        //Actually adding of data

        DataSeries series = new DataSeries();
        for (String aKeySet : keySet) {
            for (int i = 0; i < chartConfig.getData().get(aKeySet).size(); i++) {
                series.add(new DataSeriesItem((String) chartConfig.getSettings().getxCategories().get(i),
                        (Number) chartConfig.getData().get(aKeySet).get(i), innerColors[i % Helper.colors.length]));
            }
        }

        model.addXCategorie(chartConfig.getSettings().getxCategories().toArray(new String[0]));
        model.addData(series);

    }

    @Override
    void addChartListener() {

    }

    @Override
    public void specifyView(TabView tabView, String title) {
        //Set new tab
        super.setTabView(tabView);
        super.tabView.addMainComponent();
        super.mainView.addTabView(super.tabView, title);

        logger.info("Tab was added in " + this.getClass() + " for " +  this.view.getConfiguration().getTitle().getText() );

    }
}
