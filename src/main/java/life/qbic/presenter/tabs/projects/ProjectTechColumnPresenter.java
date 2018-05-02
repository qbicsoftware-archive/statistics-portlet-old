package life.qbic.presenter.tabs.projects;


import com.vaadin.addon.charts.model.*;
import com.vaadin.addon.charts.model.style.Color;
import life.qbic.model.view.charts.ColumnModel;
import life.qbic.presenter.MainPresenter;
import life.qbic.presenter.tabs.ATabPresenter;
import life.qbic.presenter.utils.Colors;
import life.qbic.presenter.utils.DataSorter;
import life.qbic.view.TabView;
import life.qbic.view.tabs.charts.ColumnView;
import submodule.data.ChartConfig;
import submodule.lexica.ChartNames;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author fhanssen
 */
public class ProjectTechColumnPresenter extends ATabPresenter<ColumnModel, ColumnView> {

    ChartConfig projectConfig;

    public ProjectTechColumnPresenter(MainPresenter mainPresenter){
        super(mainPresenter, new ColumnView());

        extractData();

        addChartSettings();
        addChartData();
    }

    @Override
    public void extractData(){
        projectConfig = super.getMainPresenter().getMainConfig().getCharts()
                .get(ChartNames.Projects_Technology.toString());
    }

    @Override
    public void addChartSettings() {
        PlotOptionsColumn plot = new PlotOptionsColumn();

        plot.setDataLabels(new DataLabels(true));

        Tooltip tooltip = new Tooltip();
        tooltip.setFormatter("this.point.name + ': <b>'+ this.y + '</b> Projects'");

        Legend legend = new Legend();
        legend.setEnabled(false);


        super.setModel(new ColumnModel(super.getView().getConfiguration(), projectConfig.getSettings().getTitle(),
                null, tooltip, legend, new AxisTitle(projectConfig.getSettings().getxAxisTitle()),
                new AxisTitle(projectConfig.getSettings().getyAxisTitle()), plot));

        super.getModel().setXAxisType(AxisType.CATEGORY);
        logger.info("Settings were added to a chart of "+ this.getClass() +" with chart titel: " + super.getView().getConfiguration().getTitle().getText());

    }

    @Override
    public void addChartData() {

        //This is necessary to get from Object to required String arrays
        Object[] objectArray = projectConfig.getData().keySet().toArray(new Object[projectConfig.getData().keySet().size()]);
        String[] keySet = Arrays.asList(objectArray).toArray(new String[objectArray.length]);

        //Color[] innerColors = Arrays.copyOf(Colors.getSolidColors(), projectConfig.getSettings().getxCategories().size());
        //Actually adding of data

        List<DataSorter> dataSorterList = new ArrayList<>();

        DataSeries series = new DataSeries();
        for (String aKeySet : keySet) {
            for (int i = 0; i < projectConfig.getData().get(aKeySet).size(); i++) {
                dataSorterList.add(new DataSorter((String) projectConfig.getSettings().getxCategories().get(i),
                        (int)projectConfig.getData().get(aKeySet).get(i)));

            }
        }
        Collections.sort(dataSorterList);
        dataSorterList.forEach(d -> series.add(new DataSeriesItem(d.getName(), d.getCount())));

        super.getModel().addData(series);

    }


    @Override
    public void addChart(TabView tabView, String title) {
        //Set new tab
        super.setTabView(tabView);
        super.getTabView().addMainComponent();
        super.getMainPresenter().getMainView().addTabView(super.getTabView(), title);

        logger.info("Tab was added in " + this.getClass() + " for " +  super.getView().getConfiguration().getTitle().getText() );

    }
}