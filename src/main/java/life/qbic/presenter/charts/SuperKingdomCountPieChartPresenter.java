package life.qbic.presenter.charts;

import com.vaadin.addon.charts.model.*;
import com.vaadin.addon.charts.PointClickListener;
import com.vaadin.addon.charts.model.style.Color;
import com.vaadin.addon.charts.themes.ValoLightTheme;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import life.qbic.model.charts.PieChartModel;
import life.qbic.model.data.ChartConfig;
import life.qbic.presenter.utils.Helper;
import life.qbic.presenter.utils.SuperKingdoms;
import life.qbic.view.charts.PieChartView;

import java.util.*;

public class SuperKingdomCountPieChartPresenter extends AChartPresenter<PieChartModel, PieChartView>{

    //TODO ObservableLists are javafx constructs, can this be avoided? -> think about this some more
    private final ObservableList<AChartPresenter<PieChartModel, PieChartView>> list = FXCollections.observableArrayList();
    private final Map<String,ChartConfig> speciesConfig;
    private final Map<String,ChartConfig> genusConfig;
    private final ChartConfig speciesGenusMap;


    public SuperKingdomCountPieChartPresenter(ChartConfig chartConfig, Map<String, ChartConfig> genusConfig, Map<String, ChartConfig> speciesConfig, ChartConfig speciesGenusMap){
        super(chartConfig, new PieChartView());
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

        this.model = new PieChartModel(this.view.getConfiguration(), chartConfig.getSettings().getTitle(),
                null, tooltip, legend, plot);

    }

    @Override
    public void addChartData() {

        //This is necessary to get from Object to required String arrays
        Object[] objectArray = chartConfig.getData().keySet().toArray(new Object[chartConfig.getData().keySet().size()]);
        String[] keySet = Arrays.asList(objectArray).toArray(new String[objectArray.length]);

        Color[] innerColors = Arrays.copyOf(Helper.colors, chartConfig.getSettings().getxCategories().size());
        //Actually adding of data
        for (String aKeySet : keySet) {
            for (int i = 0; i < chartConfig.getData().get(aKeySet).size(); i++) {
                model.addData(new DataSeriesItem((String) chartConfig.getSettings().getxCategories().get(i),
                                                 (Number) chartConfig.getData().get(aKeySet).get(i), innerColors[i % Helper.colors.length]));
            }
        }
    }

    @Override
    public void addChartListener(){
        view.getChart().addPointClickListener((PointClickListener) event -> {
            list.clear();
            if(SuperKingdoms.getList().contains(model.getDataName(event))) {
                list.add(new GenusSpeciesCountDonutPieChartPresenter(genusConfig.get(model.getDataName(event).concat("_Genus")),
                                                                    speciesConfig.get(model.getDataName(event).concat("_Species")), speciesGenusMap));
            }
        });
    }

    public ObservableList<AChartPresenter<PieChartModel, PieChartView>> getList() {
        return list;
    }

}
