package life.qbic.presenter.charts;

import com.vaadin.addon.charts.model.*;
import com.vaadin.addon.charts.PointClickEvent;
import com.vaadin.addon.charts.PointClickListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import life.qbic.model.charts.PieChartModel;
import life.qbic.model.data.ChartConfig;
import life.qbic.view.charts.PieChartView;

import java.util.*;

public class DomainCountPieChartPresenter extends AChartPresenter<PieChartModel, PieChartView>{

    //TODO ObservableLists are javafx constructs, can this be avoided?
    private final ObservableList<AChartPresenter<PieChartModel, PieChartView>> list = FXCollections.observableArrayList();
    private final Map<String,ChartConfig> subCharts;

    public DomainCountPieChartPresenter(ChartConfig chartConfig, Map<String, ChartConfig> subCharts){
        super(chartConfig, new PieChartView());
        addChartListener();
        this.subCharts = subCharts;
    }

    @Override
    public void addChartSettings() {

        PlotOptionsPie plot = new PlotOptionsPie();
        DataLabels labels = new DataLabels(true);
        labels.setAlign(HorizontalAlign.LEFT);
        labels.setSoftConnector(true);
        plot.setDataLabels(labels);

        Tooltip tooltip = new Tooltip();
        tooltip.setFormatter("this.point.name + ': <b>'+ this.y + '</b> Samples'");

        Legend legend = new Legend();
        legend.setEnabled(false);

        this.model = new PieChartModel(this.view.getConfiguration(), chartConfig.getSettings().getTitle(),
                 null, tooltip, legend, plot);

    }

    @Override
    public void addChartData() {
        Object[] objectArray = chartConfig.getData().keySet().toArray(new Object[chartConfig.getData().keySet().size()]);
        String[] keySet = Arrays.asList(objectArray).toArray(new String[objectArray.length]);
        Arrays.sort(keySet);
        for (String aKeySet : keySet) {
            for (int i = 0; i < chartConfig.getData().get(aKeySet).size(); i++) {
                model.addData(new DataSeriesItem((String) chartConfig.getSettings().getxCategories().get(i),
                                                 (Number) chartConfig.getData().get(aKeySet).get(i)));
            }
        }
    }

    private void addChartListener(){

        view.getChart().addPointClickListener(new PointClickListener() {
            @Override
            public void onClick(PointClickEvent event) {
                list.clear();
                //TODO avoid hard coding of any names
                if(model.getDataName(event).equals("Bacteria") || model.getDataName(event).equals("Viruses")|| model.getDataName(event).equals("Eukaryota")) {
                    list.add(new DomainCountPieChartPresenter(subCharts.get(model.getDataName(event)), new HashMap<>()));
                }
            }
        });
    }

    public ObservableList<AChartPresenter<PieChartModel, PieChartView>> getList() {
        return list;
    }

}
