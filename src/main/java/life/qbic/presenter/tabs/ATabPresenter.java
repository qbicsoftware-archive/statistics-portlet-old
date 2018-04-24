package life.qbic.presenter.tabs;


import life.qbic.logging.Log4j2Logger;
import life.qbic.logging.Logger;
import life.qbic.portlet.StatisticsViewUI;
import life.qbic.view.TabView;
import submodule.data.ChartConfig;

/**
 * @author fhanssen
 * Abstract class holds fields and methods that a ChartPresenter generally needs. When extending it, just use your required
 * model and view type.
 */
public abstract class ATabPresenter<T,V> {

    public static final Logger logger = new Log4j2Logger(ATabPresenter.class);

    private final ChartConfig chartConfig;
    private final V view;
    private final StatisticsViewUI mainView;
    private T model;
    private TabView tabView;

    public ATabPresenter(ChartConfig chartConfig, StatisticsViewUI mainView,  V view){
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

    public void setModel(T model){
        this.model = model;
    }

    protected ChartConfig getChartConfig(){
        return chartConfig;
    }

    protected void setTabView(TabView temp){
        this.tabView = temp;
    }

    public TabView getTabView() {
        return tabView;
    }

    protected StatisticsViewUI getMainView() {
        return mainView;
    }

    abstract public void addChartSettings();

    abstract public void addChartData();

    abstract public void addChartListener();

    abstract public void specifyView(TabView tabView, String title);

    //TODO 4: Extend this class in order to create a new presenter. As examples you can look at the SuperKingdomCountPresenter or GenusSpeciesCountPresenter
}