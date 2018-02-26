package life.qbic.presenter;

import life.qbic.view.MainView;

public class MainPresenter {

    private final MainView mainView;

    public MainPresenter(MainView mainView) {
        this.mainView = mainView;

        addHistoryPlot("number 1");
        addHistoryPlot("number 2");
    }

    public void addHistoryPlot(String description) {
        HistoryPresenter historyPresenter = new HistoryPresenter(description);
        this.mainView.addBarPlot(historyPresenter.getView(), historyPresenter.getModel());
    }
}
