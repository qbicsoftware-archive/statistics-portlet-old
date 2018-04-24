package life.qbic.presenter.tabs.organisms;


import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.*;
import com.vaadin.addon.charts.PointClickListener;
import com.vaadin.addon.charts.model.style.Color;
import life.qbic.model.view.charts.PieChartModel;
import life.qbic.portlet.StatisticsViewUI;
import life.qbic.presenter.tabs.ATabPresenter;
import life.qbic.presenter.utils.Colors;
import life.qbic.view.TabView;
import life.qbic.view.tabs.charts.PieView;
import submodule.data.ChartConfig;
import submodule.lexica.Kingdoms;

import java.util.*;

/**
 * @author fhanssen
 */
public class SuperKingdomCountPresenter extends ATabPresenter<PieChartModel, PieView> {

    private final Map<String, ChartConfig> speciesConfig;
    private final Map<String,ChartConfig> genusConfig;
    private final ChartConfig speciesGenusMap;

    public SuperKingdomCountPresenter(StatisticsViewUI mainView,
                                      ChartConfig chartConfig,
                                      Map<String, ChartConfig> genusConfig,
                                      Map<String, ChartConfig> speciesConfig,
                                      ChartConfig speciesGenusMap){

        super(chartConfig,mainView, new PieView());
        this.speciesConfig = speciesConfig;
        this.genusConfig = genusConfig;
        this.speciesGenusMap = speciesGenusMap;

        addChartSettings();
        addChartData();
        addChartListener();
    }

    @Override
    public void addChartSettings() {

        PlotOptionsPie plot = new PlotOptionsPie();

        plot.setDataLabels(new DataLabels(true));

        Tooltip tooltip = new Tooltip();
        tooltip.setFormatter("this.point.name + ': <b>'+ this.y + '</b> Samples'");

        Legend legend = new Legend();
        legend.setEnabled(false);

        this.setModel(new PieChartModel(this.getView().getConfiguration(), this.getChartConfig().getSettings().getTitle(),
                null, tooltip, legend, plot));

        logger.info("Settings were added to a chart of "+ this.getClass() +" with chart titel: " + this.getView().getConfiguration().getTitle().getText());


    }

    @Override
    public void addChartData() {

        //This is necessary to get from Object to required String arrays
        Object[] objectArray = this.getChartConfig().getData().keySet().toArray(new Object[this.getChartConfig().getData().keySet().size()]);
        String[] keySet = Arrays.asList(objectArray).toArray(new String[objectArray.length]);

        Color[] innerColors = Arrays.copyOf(Colors.getSolidColors(), this.getChartConfig().getSettings().getxCategories().size());
        //Actually adding of data
        for (String aKeySet : keySet) {
            for (int i = 0; i < this.getChartConfig().getData().get(aKeySet).size(); i++) {
                this.getModel().addData(new DataSeries(new DataSeriesItem((String) this.getChartConfig().getSettings().getxCategories().get(i),
                        (Number) this.getChartConfig().getData().get(aKeySet).get(i), innerColors[i % Colors.getSolidColors().length])));
            }
        }

        logger.info("Data was added to a chart of  " + this.getClass() + "  with chart titel: " + this.getView().getConfiguration().getTitle().getText());

    }

    @Override
    public void addChartListener(){
        ((Chart)getView().getComponent()).addPointClickListener((PointClickListener) event -> {
            logger.info("Chart of "+ this.getClass() +" with chart titel: " + this.getView().getConfiguration().getTitle().getText() +" was clicked at " + this.getModel().getDataName(event));
            if(Kingdoms.getList().contains(this.getModel().getDataName(event))) {
                GenusSpeciesCountPresenter p = new GenusSpeciesCountPresenter(this.getMainView(), genusConfig.get(this.getModel().getDataName(event).concat("_Genus")),
                        speciesConfig.get(this.getModel().getDataName(event).concat("_Species")), speciesGenusMap);

                p.specifyView(this.getTabView(), "");
            }

        });
    }

    @Override
    public void specifyView(TabView tabView, String title){

        //Set new tab
        super.setTabView(tabView);
        super.getTabView().addMainComponent();
        super.getMainView().addTabView(super.getTabView(), title);

        logger.info("Tab was added in " + this.getClass() + " for " +  this.getView().getConfiguration().getTitle().getText() );


    }

}