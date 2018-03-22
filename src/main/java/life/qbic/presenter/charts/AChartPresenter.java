package life.qbic.presenter.charts;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import life.qbic.model.data.ChartConfig;

/**
 * @author fhanssen
 * Abstract class holds fields and methods that a ChartPresenter generally needs. When extending it, just use your required
 * model and view type.
 */
public abstract class AChartPresenter<T,V> {

    final ChartConfig chartConfig;
    final V view;
    T model;
    //TODO ObservableLists are javafx constructs, can this be avoided? -> think about this some more
    final ObservableList<AChartPresenter> subCharts = FXCollections.observableArrayList();

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

    public ObservableList<AChartPresenter> getSubCharts() {
        return subCharts;
    }

    abstract void addChartSettings();

    abstract void addChartData();

    abstract void addChartListener();

    //TODO 4: Extend this class in order to create a new presenter. As examples you can look at the SuperKingdomCountPresenter or GenusSpeciesCountPresenter
}
