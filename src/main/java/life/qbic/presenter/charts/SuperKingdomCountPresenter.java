package life.qbic.presenter.charts;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.*;
import com.vaadin.addon.charts.PointClickListener;
import com.vaadin.addon.charts.model.style.Color;
import life.qbic.model.data.ChartConfig;
import life.qbic.model.view.charts.PieChartModel;
import life.qbic.presenter.utils.Helper;
import life.qbic.presenter.utils.lexica.SuperKingdoms;
import life.qbic.view.MainView;
import life.qbic.view.TabView;
import life.qbic.view.tabs.charts.PieChartView;

import java.util.*;

/**
 * @author fhanssen
 */
public class SuperKingdomCountPresenter extends AChartPresenter<PieChartModel, PieChartView>{

    private final Map<String,ChartConfig> speciesConfig;
    private final Map<String,ChartConfig> genusConfig;
    private final ChartConfig speciesGenusMap;

    public SuperKingdomCountPresenter(MainView mainView,
                                      ChartConfig chartConfig,
                                      Map<String, ChartConfig> genusConfig,
                                      Map<String, ChartConfig> speciesConfig,
                                      ChartConfig speciesGenusMap){

        super(chartConfig,mainView, new PieChartView());
        this.speciesConfig = speciesConfig;
        this.genusConfig = genusConfig;
        this.speciesGenusMap = speciesGenusMap;

        addChartSettings();
        addChartData();
        addChartListener();
    }

    @Override
    void addChartSettings() {

        PlotOptionsPie plot = new PlotOptionsPie();

        plot.setDataLabels(new DataLabels(true));

        Tooltip tooltip = new Tooltip();
        tooltip.setFormatter("this.point.name + ': <b>'+ this.y + '</b> Samples'");

        Legend legend = new Legend();
        legend.setEnabled(false);

        this.model = new PieChartModel(this.view.getConfiguration(), chartConfig.getSettings().getTitle(),
                null, tooltip, legend, plot);

        logger.info("Settings were added to a chart of "+ this.getClass() +" with chart titel: " + this.view.getConfiguration().getTitle().getText());


    }

    @Override
    void addChartData() {

        //This is necessary to get from Object to required String arrays
        Object[] objectArray = chartConfig.getData().keySet().toArray(new Object[chartConfig.getData().keySet().size()]);
        String[] keySet = Arrays.asList(objectArray).toArray(new String[objectArray.length]);

        Color[] innerColors = Arrays.copyOf(Helper.colors, chartConfig.getSettings().getxCategories().size());
        //Actually adding of data
        for (String aKeySet : keySet) {
            for (int i = 0; i < chartConfig.getData().get(aKeySet).size(); i++) {
                model.addData(new DataSeries(new DataSeriesItem((String) chartConfig.getSettings().getxCategories().get(i),
                                                 (Number) chartConfig.getData().get(aKeySet).get(i), innerColors[i % Helper.colors.length])));
            }
        }

        logger.info("Data was added to a chart of SuperKingdomCountPresenter with chart titel: " + this.view.getConfiguration().getTitle().getText());

    }

    @Override
    void addChartListener(){
        ((Chart)view.getComponent()).addPointClickListener((PointClickListener) event -> {
            logger.info("Chart of SuperKingdomCountPresenter with chart titel: " + this.view.getConfiguration().getTitle().getText() +" was clicked at " + model.getDataName(event));
            if(SuperKingdoms.getList().contains(model.getDataName(event))) {
                GenusSpeciesCountPresenter p = new GenusSpeciesCountPresenter(mainView, genusConfig.get(model.getDataName(event).concat("_Genus")),
                        speciesConfig.get(model.getDataName(event).concat("_Species")), speciesGenusMap);

                p.specifyView(this.tabView, "");
            }

        });
    }

    @Override
    public void specifyView(TabView tabView, String title){

        //Set new tab
        super.setTabView(tabView);
        super.tabView.addMainComponent();
        super.mainView.addTabView(super.tabView, title);

        logger.info("Tab was added in " + this.getClass() + " for " +  this.view.getConfiguration().getTitle().getText() );


    }

}
