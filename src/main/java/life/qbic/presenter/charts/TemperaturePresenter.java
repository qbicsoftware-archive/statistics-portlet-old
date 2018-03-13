package life.qbic.presenter.charts;

import com.vaadin.addon.charts.model.*;
import life.qbic.model.charts.BasicTimelineModel;
import life.qbic.model.data.ChartConfig;
import life.qbic.view.charts.BasicTimelineView;

public class TemperaturePresenter implements ChartPresenter<BasicTimelineModel, BasicTimelineView> {

    private final BasicTimelineView timelineView;
    private BasicTimelineModel model;
    private final ChartConfig chartConfig;

    public TemperaturePresenter(ChartConfig chartConfig){
        timelineView = new BasicTimelineView();
        this.chartConfig = chartConfig;
        addChartSettings();
        addChartData();
    }

    @Override
    public void addChartSettings(){
        AxisTitle yAxisTitle = new AxisTitle(chartConfig.getSettings().getyAxisTitle());
        yAxisTitle.setAlign(VerticalAlign.MIDDLE);

        Tooltip tooltip = new Tooltip();
        tooltip.setFormatter(
                "'<b>'+ this.series.name +'</b><br/>'+this.x +': '+ this.y +'°C'");

        Legend legend = new Legend();
        legend.setLayout(LayoutDirection.VERTICAL);
        legend.setAlign(HorizontalAlign.RIGHT);
        legend.setVerticalAlign(VerticalAlign.TOP);
        legend.setX(-10d);
        legend.setY(100d);
        legend.setBorderWidth(0);

        PlotOptionsLine plotOptions = new PlotOptionsLine();
        plotOptions.getDataLabels().setEnabled(true);

        model = new BasicTimelineModel(timelineView.getConfiguration(),
                                        chartConfig.getSettings().getTitle(),
                                        chartConfig.getSettings().getSubtitle(),
                                        new AxisTitle(chartConfig.getSettings().getxAxisTitle()),
                yAxisTitle,
                tooltip,
                legend,
                                        plotOptions);

        model.addXCategorie(chartConfig.getSettings().getxCategories().toArray(new String[chartConfig.getSettings().getxCategories().size()]));

        model.setyMin(-5);

    }

    @Override
    public void addChartData(){
        String[] keySet = chartConfig.getData().keySet().toArray(new String[chartConfig.getData().keySet().size()]);

        for (String aKeySet : keySet)
            model.addData(new ListSeries(aKeySet,
                    chartConfig.getData().get(aKeySet).toArray(new Double[chartConfig.getData().get(aKeySet).size()])));
    }

    @Override
    public BasicTimelineModel getModel() {
        return model;
    }

    @Override
    public BasicTimelineView getView() {
        return timelineView;
    }
}
