package life.qbic.presenter.charts;

public interface ChartPresenter<T,V> {

    T getModel();

    V getView();
}
