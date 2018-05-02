package life.qbic.presenter.tabs.organisms;


import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.*;
import com.vaadin.addon.charts.PointClickListener;
import life.qbic.model.view.charts.PieChartModel;
import life.qbic.presenter.MainPresenter;
import life.qbic.presenter.tabs.ATabPresenter;
import life.qbic.presenter.utils.DataSorter;
import life.qbic.presenter.utils.LabelFormatter;
import life.qbic.view.TabView;
import life.qbic.view.tabs.charts.PieView;
import submodule.data.ChartConfig;
import submodule.lexica.ChartNames;
import submodule.lexica.Kingdoms;

import java.util.*;

/**
 * @author fhanssen
 */
public class SuperKingdomCountPresenter extends ATabPresenter<PieChartModel, PieView> {

    private ChartConfig kingdomConfig;

    public SuperKingdomCountPresenter(MainPresenter mainPresenter){

        super(mainPresenter, new PieView());

        extractData();

        addChartSettings();
        addChartData();
        addChartListener();
    }

    @Override
    public void extractData(){
        kingdomConfig = super.getMainPresenter().getMainConfig().getCharts()
                .get(ChartNames.SuperKingdom.toString());
    }

    @Override
    public void addChartSettings() {

        PlotOptionsPie plot = new PlotOptionsPie();

        plot.setDataLabels(new DataLabels(true));

        Tooltip tooltip = new Tooltip();
        tooltip.setFormatter("this.point.name + ': <b>'+ this.y + '</b> Samples'");

        Legend legend = new Legend();
        legend.setEnabled(false);

        this.setModel(new PieChartModel(this.getView().getConfiguration(), kingdomConfig.getSettings().getTitle(),
                null, tooltip, legend, plot));

        logger.info("Settings were added to a chart of "+ this.getClass() +" with chart titel: " + this.getView().getConfiguration().getTitle().getText());


    }

    @Override
    public void addChartData() {

        //This is necessary to get from Object to required String arrays
        Object[] objectArray = kingdomConfig.getData().keySet().toArray(new Object[kingdomConfig.getData().keySet().size()]);
        String[] keySet = Arrays.asList(objectArray).toArray(new String[objectArray.length]);

        List<DataSorter> dataSorters = new ArrayList<>();

        //Retrieve and Sort data
        for (String aKeySet : keySet) {
            for (int i = 0; i <kingdomConfig.getData().get(aKeySet).size(); i++) {
                dataSorters.add(new DataSorter(LabelFormatter.generateCamelCase((String) kingdomConfig.getSettings().getxCategories().get(i)),
                        (int)kingdomConfig.getData().get(aKeySet).get(i)));

            }
        }

        Collections.sort(dataSorters);

        //Add data
        dataSorters.forEach(d -> this.getModel().addData(new DataSeriesItem(d.getName(), d.getCount())));

        logger.info("Data was added to a chart of  " + this.getClass() + "  with chart titel: " + this.getView().getConfiguration().getTitle().getText());

    }


    private void addChartListener(){
        ((Chart)getView().getComponent()).addPointClickListener((PointClickListener) event -> {

            logger.info("Chart of "+ this.getClass() +" with chart titel: " +
                        this.getView().getConfiguration().getTitle().getText() +
                    " was clicked at " + this.getModel().getDataName(event));

            if(Kingdoms.getList().contains(this.getModel().getDataName(event))) {
                GenusSpeciesCountPresenter p =
                        new GenusSpeciesCountPresenter(super.getMainPresenter(), this.getModel().getDataName(event));

                p.addChart(this.getTabView(), "");
            }

        });
    }

    @Override
    public void addChart(TabView tabView, String title){
        //Set new tab
        super.setTabView(tabView);
        super.getTabView().addMainComponent();
        super.getMainPresenter().getMainView().addTabView(super.getTabView(), title);

        logger.info("Tab was added in " + this.getClass() + " for " +  this.getView().getConfiguration().getTitle().getText() );

    }

}