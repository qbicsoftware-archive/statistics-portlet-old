package life.qbic.presenter;

import life.qbic.model.data.MainConfig;
import life.qbic.presenter.charts.OrganismCountPieChartPresenter;
import life.qbic.presenter.charts.OrganismCountPresenter;
import life.qbic.utils.YAMLParser;
import life.qbic.view.MainView;

public class MainPresenter {

    private final MainView mainView;
    private final MainConfig mainConfig;

    public MainPresenter(MainView mainView) {
        this.mainView = mainView;
        this.mainConfig = YAMLParser.parseConfig("/Users/qbic/Documents/QBiC/statistics-data-retrieval-openbis/config.yaml");


        addOrganismCount();
        addOrganismCountPie();
    }

    private void addOrganismCount(){
        OrganismCountPresenter organismCountPresenter = new OrganismCountPresenter(mainConfig.getCharts().get("organismCount"));
        this.mainView.addColumnPlot(organismCountPresenter.getView(), organismCountPresenter.getModel(), "Organism Count");
    }

    private void addOrganismCountPie(){
        OrganismCountPieChartPresenter organismCountPiePresenter = new OrganismCountPieChartPresenter(mainConfig.getCharts().get("organismCount"));
        this.mainView.addPieChart(organismCountPiePresenter.getView(), organismCountPiePresenter.getModel(), "Organism Pie");
    }

}
