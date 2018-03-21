package life.qbic.presenter;

import javafx.collections.ListChangeListener;
import life.qbic.model.charts.PieChartModel;
import life.qbic.model.data.ChartConfig;
import life.qbic.model.data.MainConfig;
import life.qbic.presenter.charts.AChartPresenter;
import life.qbic.presenter.charts.SuperKingdomCountPieChartPresenter;
import life.qbic.utils.YAMLParser;
import life.qbic.view.MainView;
import life.qbic.view.charts.PieChartView;
import life.qbic.view.TabView;

import java.util.HashMap;
import java.util.Map;

public class MainPresenter {

    //TODO when more charts are added there is prob an obvious way of how to add an level of abstraction,
    //TODO as likely most of them have subcharts and return buttons etc

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
    }

    private void addOrganismCountPie(){
        Map<String, ChartConfig> speciesCharts = new HashMap<>();

        //TODO avoid hard coding these names
        speciesCharts.put("Eukaryota_Species", mainConfig.getCharts().get("Eukaryota_Species"));
        speciesCharts.put("Bacteria_Species", mainConfig.getCharts().get("Bacteria_Species"));
        speciesCharts.put("Viruses_Species", mainConfig.getCharts().get("Viruses_Species"));

        Map<String, ChartConfig> genusCharts = new HashMap<>();
        genusCharts.put("Eukaryota_Genus", mainConfig.getCharts().get("Eukaryota_Genus"));
        genusCharts.put("Bacteria_Genus", mainConfig.getCharts().get("Bacteria_Genus"));
        genusCharts.put("Viruses_Genus", mainConfig.getCharts().get("Viruses_Genus"));

        //hard coding this name is unavoidable
        SuperKingdomCountPieChartPresenter organismCountPiePresenter =
                new SuperKingdomCountPieChartPresenter(mainConfig.getCharts().get("Domain"), genusCharts, speciesCharts, mainConfig.getCharts().get("OrganismGenus"));

        TabView domainCountTab = new TabView(organismCountPiePresenter.getView(), organismCountPiePresenter.getModel());

        this.mainView.addChart(domainCountTab, "Organisms");

        organismCountPiePresenter.getList().addListener((ListChangeListener<? super AChartPresenter<PieChartModel,PieChartView>>) c -> {
            while(c.next()){
                c.getAddedSubList().forEach(l -> domainCountTab.addSubChart(l.getModel(), l.getView()));
                c.getRemoved().forEach(l -> domainCountTab.removeChart(l.getView().getChart()));
            }
        });
        domainCountTab.getReturnButton().addClickListener(clickEvent -> domainCountTab.addMainChart());
    }

}
