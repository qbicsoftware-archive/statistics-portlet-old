package life.qbic.presenter.tabs;


import life.qbic.logging.Log4j2Logger;
import life.qbic.logging.Logger;
import life.qbic.presenter.MainPresenter;
import life.qbic.view.TabView;
import life.qbic.view.tabs.AView;


/**
 * @author fhanssen
 * Abstract class holds fields and methods that a ChartPresenter generally needs. When extending it, just use your required
 * model and view type.
 */
public abstract class ATabPresenter<T, V> {

    public static final Logger logger = new Log4j2Logger(ATabPresenter.class);

    private final V view;
    private final MainPresenter mainPresenter;
    private TabView tabView;
    private T model;

    public ATabPresenter(MainPresenter mainPresenter, V view){
        this.view = view;
        this.mainPresenter = mainPresenter;
    }

    public T getModel(){
        return model;
    }

    public V getView(){
        return view;
    }

    protected MainPresenter getMainPresenter() {
        return mainPresenter;
    }

    public void setModel(T model){
        this.model = model;
    }

    protected void setTabView(TabView temp){
        this.tabView = temp;
    }

    public TabView getTabView() {
        return tabView;
    }

    abstract public void extractData();

    abstract public void addChartSettings();

    abstract public void addChartData();

    abstract public void addChart(TabView tabView, String title);

    //TODO 4: Extend this class in order to create a new presenter. As examples you can look at the SuperKingdomCountPresenter or GenusSpeciesCountPresenter
}