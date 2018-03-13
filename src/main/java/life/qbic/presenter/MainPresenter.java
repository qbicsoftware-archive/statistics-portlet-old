package life.qbic.presenter;

import life.qbic.model.data.MainConfig;
import life.qbic.presenter.charts.HistoryPresenter;
import life.qbic.presenter.charts.TemperaturePresenter;
import life.qbic.utils.YAMLParser;
import life.qbic.view.MainView;

public class MainPresenter {

    private final MainView mainView;
    private final MainConfig mainConfig;

    public MainPresenter(MainView mainView) {
        this.mainView = mainView;
        this.mainConfig = YAMLParser.parseConfig("/Users/qbic/Documents/QBiC/statistics-data-retrieval-openbis/config.yaml");

        addHistoryPlot();
        addTimelinePlot();
    }

    private void addHistoryPlot() {
        HistoryPresenter historyPresenter = new HistoryPresenter(mainConfig.getCharts().get("basicbarmodel"));
        this.mainView.addBarPlot(historyPresenter.getView(), historyPresenter.getModel(), "Barplot Example");
    }

    private void addTimelinePlot(){
        TemperaturePresenter temperaturPresenter = new TemperaturePresenter(mainConfig.getCharts().get("temperatur"));
        this.mainView.addTimelinePlot(temperaturPresenter.getView(), temperaturPresenter.getModel(), "Timeline Example");
    }
}
