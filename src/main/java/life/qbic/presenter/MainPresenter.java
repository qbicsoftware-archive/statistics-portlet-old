package life.qbic.presenter;

import javafx.collections.ListChangeListener;
import life.qbic.model.charts.PieChartModel;
import life.qbic.model.data.ChartConfig;
import life.qbic.model.data.MainConfig;
import life.qbic.presenter.charts.AChartPresenter;
import life.qbic.presenter.charts.DomainCountPieChartPresenter;
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

//    private void addOrganismCount(){
//        AChartPresenter<ColumnModel,ColumnView> organismCountPresenter = new OrganismCountPresenter(mainConfig.getCharts().get("domainCount"));
//        this.mainView.addColumnPlot(organismCountPresenter.getView(), organismCountPresenter.getModel(), "Domain Count");
//    }

    private void addOrganismCountPie(){
        Map<String, ChartConfig> subCharts = new HashMap<>();

        //TODO avoid hard coding these names
        subCharts.put("Eukaryota", mainConfig.getCharts().get("Eukaryota"));
        subCharts.put("Bacteria", mainConfig.getCharts().get("Bacteria"));
        subCharts.put("Viruses", mainConfig.getCharts().get("Viruses"));

        //hard coding this name is unavoidable
        DomainCountPieChartPresenter organismCountPiePresenter =
                new DomainCountPieChartPresenter(mainConfig.getCharts().get("domainCount"), subCharts);

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
