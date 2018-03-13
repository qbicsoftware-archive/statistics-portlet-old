package life.qbic.presenter.charts;

interface ChartPresenter<T,V> {

    void addChartSettings();

    void addChartData();

    T getModel();

    V getView();
}
