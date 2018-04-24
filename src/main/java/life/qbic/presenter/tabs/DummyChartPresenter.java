package life.qbic.presenter.tabs;

import com.vaadin.addon.charts.model.*;
import life.qbic.model.view.charts.PieChartModel;
import life.qbic.portlet.StatisticsViewUI;
import life.qbic.view.TabView;
import life.qbic.view.tabs.charts.PieView;
import submodule.data.ChartConfig;


public class DummyChartPresenter extends ATabPresenter<PieChartModel, PieView> {

    public DummyChartPresenter(StatisticsViewUI statisticsViewUI){
        super(new ChartConfig(), statisticsViewUI, new PieView());

        addChartSettings();
        addChartData();
    }


    @Override
    public void addChartSettings() {
        PlotOptionsPie plot = new PlotOptionsPie();

        plot.setDataLabels(new DataLabels(true));

        Tooltip tooltip = new Tooltip();
        tooltip.setFormatter("this.point.name + ': <b>'+ this.y + '</b> Samples'");

        Legend legend = new Legend();
        legend.setEnabled(false);

        this.setModel(new PieChartModel(this.getView().getConfiguration(), "Dummy Chart",
                null, tooltip, legend, plot));

        logger.info("Settings were added to a chart of "+ this.getClass() +" with chart titel: " + this.getView().getConfiguration().getTitle().getText());

    }

    @Override
    public void addChartData() {

            for (int i = 0; i < 5; i++) {
                this.getModel().addData(new DataSeries(new DataSeriesItem("Genomes".concat(String.valueOf(i)), i)));
            }


        logger.info("Data was added to a chart of  " + this.getClass() + "  with chart titel: " + this.getView().getConfiguration().getTitle().getText());

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

        logger.info("Tab was added in " + this.getClass() + " for " +  this.getView().getConfiguration().getTitle().getText() );

    }
}
