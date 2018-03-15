package life.qbic.presenter.charts;

import com.vaadin.addon.charts.model.*;
import life.qbic.model.charts.PieChartModel;
import life.qbic.model.data.ChartConfig;
import life.qbic.view.charts.PieChartView;

import java.util.Arrays;

public class OrganismCountPieChartPresenter extends AChartPresenter<PieChartModel, PieChartView>{

    public OrganismCountPieChartPresenter(ChartConfig chartConfig){
        super(chartConfig, new PieChartView());
    }

    @Override
    public void addChartSettings() {

        PlotOptionsPie plot = new PlotOptionsPie();
        plot.setDataLabels(new DataLabels(true));

        Tooltip tooltip = new Tooltip();
        tooltip.setFormatter("'<b>'+ this.y + '</b> Samples'");

        Legend legend = new Legend();
        legend.setEnabled(false);

        this.model = new PieChartModel(this.view.getConfiguration(), chartConfig.getSettings().getTitle(),
                chartConfig.getSettings().getSubtitle(), null, null, tooltip, legend, plot);

    }

    @Override
    public void addChartData() {
        Object[] objectArray = chartConfig.getData().keySet().toArray(new Object[chartConfig.getData().keySet().size()]);
        String[] keySet = Arrays.asList(objectArray).toArray(new String[objectArray.length]);
        Arrays.sort(keySet);
        for (String aKeySet : keySet) {
            for (int i = 0; i < chartConfig.getData().get(aKeySet).size(); i++) {
                model.addData(new DataSeriesItem((String)chartConfig.getSettings().getxCategories().get(i), (Number) chartConfig.getData().get(aKeySet).get(i)));
            }
        }
    }
}
