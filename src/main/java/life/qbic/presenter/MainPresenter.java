package life.qbic.presenter;

import javafx.collections.ListChangeListener;
import life.qbic.model.charts.AModel;
import life.qbic.model.data.ChartConfig;
import life.qbic.model.data.MainConfig;
import life.qbic.presenter.charts.AChartPresenter;
import life.qbic.presenter.charts.SuperKingdomCountPresenter;
import life.qbic.presenter.utils.lexica.ChartNames;
import life.qbic.presenter.utils.lexica.SuperKingdoms;
import life.qbic.utils.YAMLParser;
import life.qbic.view.MainView;
import life.qbic.view.charts.AView;
import life.qbic.view.TabView;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fhanssen
 * HAndles input onfig and adds charts serially. Handles button presses and listens to observabeLists in SubPresenters
 */
public class MainPresenter {

    private final MainView mainView;
    private final MainConfig mainConfig;

    public MainPresenter(MainView mainView) {
        this.mainView = mainView;
        this.mainConfig = YAMLParser.parseConfig("/Users/qbic/Documents/QBiC/statistics-data-retrieval-openbis/config.yaml");

        addCharts();
    }

    //Careful: Order matters! Determines in which order tabs are displayed.
    private void addCharts(){
        addOrganismCountPie();
        //TODO 5: Add your method to add a new chart doing the following things: 1) Get your ChartConfigs, 2) Create a new AChartPresenter, 3) Set a new Tab 4) Add Button and SubChartsListener, 5) Add Tab to mainView
    }

    private void addOrganismCountPie(){

        //Get needed configs
        Map<String, ChartConfig> speciesCharts = new HashMap<>();
        Map<String, ChartConfig> genusCharts = new HashMap<>();

        for(String chartName : ChartNames.getList()){
            if(SuperKingdoms.getList().contains(chartName.split("_")[0])){
                if(chartName.split("_")[1].equals("Species")) {
                    speciesCharts.put(chartName, mainConfig.getCharts().get(chartName));
                }else if (chartName.split("_")[1].equals("Genus")){
                    genusCharts.put(chartName, mainConfig.getCharts().get(chartName));
                }
            }
        }

        //Create Presenter
        SuperKingdomCountPresenter organismCountPiePresenter =
                new SuperKingdomCountPresenter(mainConfig.getCharts().get(ChartNames.SuperKingdom.toString()),
                                                        genusCharts,
                                                        speciesCharts,
                                                        mainConfig.getCharts().get(ChartNames.Species_Genus.toString()));

        //Set new tab
        TabView domainCountTab = new TabView(organismCountPiePresenter.getView(),
                                             organismCountPiePresenter.getModel());

        //Add Listener
        addListenerForSubcharts(domainCountTab, organismCountPiePresenter);
        addReturnButtonListener(domainCountTab);

        //Add Tab to MainView
        this.mainView.addChart(domainCountTab, "Organisms");

    }

    private void addReturnButtonListener(TabView tabView){
        tabView.getReturnButton().addClickListener(clickEvent -> tabView.addMainChart());
    }

    private void addListenerForSubcharts(TabView tabView, AChartPresenter presenter){
        presenter.getSubCharts().addListener((ListChangeListener<? super AChartPresenter<AModel, AView>>) c -> {
            while(c.next()){
                c.getAddedSubList().forEach(l -> tabView.addSubChart(l.getModel(), l.getView()));
                c.getRemoved().forEach(l -> tabView.removeChart(l.getView().getChart()));
            }
        });
    }
}
