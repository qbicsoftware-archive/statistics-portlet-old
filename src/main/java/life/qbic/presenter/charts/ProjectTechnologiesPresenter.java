package life.qbic.presenter.charts;

import life.qbic.model.view.charts.PieChartModel;
import life.qbic.view.MainView;
import life.qbic.view.TabView;
import life.qbic.view.tabs.charts.PieChartView;
import submodule.data.ChartConfig;

public class ProjectTechnologiesPresenter extends AChartPresenter<PieChartModel, PieChartView> {

    public ProjectTechnologiesPresenter(ChartConfig projectsConfig, MainView mainView){
        super(projectsConfig,mainView, new PieChartView());

        addChartSettings();
        addChartData();
        addChartListener();
    }

    @Override
    void addChartSettings() {

    }

    @Override
    void addChartData() {

    }

    @Override
    void addChartListener() {

    }

    @Override
    public void specifyView(TabView tabView, String title) {

    }
}
