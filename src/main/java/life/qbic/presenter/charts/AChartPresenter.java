package life.qbic.presenter.charts;

import life.qbic.model.data.ChartConfig;


public abstract class AChartPresenter<T,V> {

    final ChartConfig chartConfig;
    final V view;
    T model;

    AChartPresenter(ChartConfig chartConfig, V view){
        this.view = view;
        this.chartConfig = chartConfig;

    }

    public T getModel(){
        return model;
    }

    public V getView(){
        return view;
    }

    abstract void addChartSettings();

    abstract void addChartData();

    abstract void addChartListener();
}
