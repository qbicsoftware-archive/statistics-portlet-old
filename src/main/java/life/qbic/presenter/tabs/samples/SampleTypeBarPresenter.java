package life.qbic.presenter.tabs.samples;

import com.vaadin.addon.charts.model.*;
import com.vaadin.addon.charts.model.style.Color;
import life.qbic.model.view.charts.BarModel;
import life.qbic.portlet.StatisticsViewUI;
import life.qbic.presenter.tabs.ATabPresenter;
import life.qbic.presenter.utils.Colors;
import life.qbic.view.TabView;
import life.qbic.view.tabs.charts.BarView;
import submodule.data.ChartConfig;

import java.util.Arrays;
/**
 * @author fhanssen
 */
public class SampleTypeBarPresenter extends ATabPresenter<BarModel, BarView> {

    public SampleTypeBarPresenter(StatisticsViewUI mainView, ChartConfig chartConfig){
        super(chartConfig, mainView, new BarView());

        addChartSettings();
        addChartData();
    }

    @Override
    public void addChartSettings() {
        PlotOptionsBar plot = new PlotOptionsBar();

        plot.setDataLabels(new DataLabels(true));

        Tooltip tooltip = new Tooltip();
        tooltip.setFormatter("this.point.name + ': <b>'+ this.y + '</b> Samples'");

        Legend legend = new Legend();
        legend.setEnabled(false);

        super.setModel(new BarModel(super.getView().getConfiguration(), super.getChartConfig().getSettings().getTitle(),
                null, tooltip, legend, new AxisTitle(super.getChartConfig().getSettings().getxAxisTitle()), new AxisTitle(super.getChartConfig().getSettings().getyAxisTitle()),
                plot));

        super.getModel().setXAxisType(AxisType.CATEGORY);
        logger.info("Settings were added to a chart of "+ this.getClass() +" with chart titel: " + super.getView().getConfiguration().getTitle().getText());

    }

    @Override
    public void addChartData() {
        //This is necessary to get from Object to required String arrays
        Object[] objectArray = super.getChartConfig().getData().keySet().toArray(new Object[super.getChartConfig().getData().keySet().size()]);
        String[] keySet = Arrays.asList(objectArray).toArray(new String[objectArray.length]);

        Color[] innerColors = Arrays.copyOf(Colors.getSolidColors(), super.getChartConfig().getSettings().getxCategories().size());
        //Actually adding of data

        DataSeries series = new DataSeries();
        for (String aKeySet : keySet) {
            for (int i = 0; i < super.getChartConfig().getData().get(aKeySet).size(); i++) {
                series.add(new DataSeriesItem((String) super.getChartConfig().getSettings().getxCategories().get(i),
                        (Number) super.getChartConfig().getData().get(aKeySet).get(i), innerColors[i % Colors.getSolidColors().length]));
            }
        }
        super.getModel().addXCategorie(super.getChartConfig().getSettings().getxCategories().toArray(new String[0]));
        super.getModel().addData(series);
        logger.info("Data was added to a chart of " + this.getClass() + " with chart titel: " + super.getView().getConfiguration().getTitle().getText());

    }

    @Override
    public void addChartListener() {

    }

    @Override
    public void specifyView(TabView tabView, String title) {
        //Set new tab
        super.setTabView(tabView);
        super.getTabView().addMainComponent();
        super.getMainView().addTabView(super.getTabView(), title);

        logger.info("Tab was added in " + this.getClass() + " for " +  super.getView().getConfiguration().getTitle().getText() );
    }
}