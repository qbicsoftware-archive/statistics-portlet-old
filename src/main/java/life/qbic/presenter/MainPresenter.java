package life.qbic.presenter;

import life.qbic.presenter.charts.HistoryPresenter;
import life.qbic.presenter.charts.TemperaturePresenter;
import life.qbic.view.MainView;

public class MainPresenter {

    private final MainView mainView;

    public MainPresenter(MainView mainView) {
        this.mainView = mainView;

        addHistoryPlot();
        addTimelinePlot();
    }

    public void addHistoryPlot() {
        HistoryPresenter historyPresenter = new HistoryPresenter();
        this.mainView.addBarPlot(historyPresenter.getView(), historyPresenter.getModel(), "Barplot Example");
    }

    private void addTimelinePlot(){
        TemperaturePresenter temperaturPresenter = new TemperaturePresenter();
        this.mainView.addTimelinePlot(temperaturPresenter.getView(), temperaturPresenter.getModel(), "Timeline Example");
    }
}
