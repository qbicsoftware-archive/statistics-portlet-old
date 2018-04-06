package life.qbic.presenter.charts;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import life.qbic.logging.Log4j2Logger;
import life.qbic.logging.Logger;
import life.qbic.model.data.ChartConfig;
import life.qbic.model.view.AModel;
import life.qbic.view.MainView;
import life.qbic.view.TabView;
import life.qbic.view.tabs.AView;

/**
 * @author fhanssen
 * Abstract class holds fields and methods that a ChartPresenter generally needs. When extending it, just use your required
 * model and view type.
 */
public abstract class AChartPresenter<T,V> {

    static final Logger logger = new Log4j2Logger(AChartPresenter.class);


    final ChartConfig chartConfig;
    final V view;
    final MainView mainView;
    T model;
    TabView tabView;

    AChartPresenter(ChartConfig chartConfig, MainView mainView,  V view){
        this.view = view;
        this.chartConfig = chartConfig;
        this.mainView = mainView;

    }

    public T getModel(){
        return model;
    }

    public V getView(){
        return view;
    }

    void setTabView(TabView temp){
        this.tabView = temp;
    }

    public TabView getTabView() {
        return tabView;
    }

    abstract void addChartSettings();

    abstract void addChartData();

    abstract void addChartListener();

    abstract public void specifyView(TabView tabView, String title);

    //TODO 4: Extend this class in order to create a new presenter. As examples you can look at the SuperKingdomCountPresenter or GenusSpeciesCountPresenter
}
