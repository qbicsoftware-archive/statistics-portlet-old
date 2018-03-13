package life.qbic.presenter.charts;

import com.vaadin.addon.charts.model.*;
import com.vaadin.addon.charts.model.style.SolidColor;
import life.qbic.model.charts.BasicBarModel;
import life.qbic.model.data.ChartConfig;
import life.qbic.view.charts.BasicBarView;

public class HistoryPresenter implements ChartPresenter<BasicBarModel, BasicBarView> {

    private final BasicBarView barView;
    private  BasicBarModel model;
    private  final ChartConfig chartConfig;


    public HistoryPresenter(ChartConfig chartConfig){
        barView = new BasicBarView();
        this.chartConfig = chartConfig;
        addChartSettings();
        addChartData();
    }

    @Override
    public void addChartSettings() {
        AxisTitle yAxisTitle = new AxisTitle(chartConfig.getSettings().getyAxisTitle());
        yAxisTitle.setAlign(VerticalAlign.MIDDLE);

        Tooltip tooltip = new Tooltip();
        tooltip.setFormatter("this.series.name +': '+ this.y +' millions'");

        Legend legend = new Legend();
        legend.setLayout(LayoutDirection.VERTICAL);
        legend.setAlign(HorizontalAlign.RIGHT);
        legend.setVerticalAlign(VerticalAlign.TOP);
        legend.setX(-100);
        legend.setY(100);
        legend.setFloating(true);
        legend.setBorderWidth(1);
        legend.setBackgroundColor(new SolidColor("#FFFFFF"));
        legend.setShadow(true);

        PlotOptionsBar plot = new PlotOptionsBar();
        plot.setDataLabels(new DataLabels(true));

        model = new BasicBarModel(barView.getConfiguration(), chartConfig.getSettings().getTitle(),
                chartConfig.getSettings().getSubtitle(), new AxisTitle(chartConfig.getSettings().getxAxisTitle()), yAxisTitle, tooltip, legend, plot);

        model.addXCategorie(chartConfig.getSettings().getxCategories().toArray(new String[chartConfig.getSettings().getxCategories().size()]));
        model.setyMin(0);
    }

    @Override
    public void addChartData() {
        String[] keySet = chartConfig.getData().keySet().toArray(new String[chartConfig.getData().keySet().size()]);
        for (String aKeySet : keySet) {
            model.addData(new ListSeries("Year ".concat(aKeySet.toString()),
                    chartConfig.getData().get(aKeySet).toArray(new Number[chartConfig.getData().get(aKeySet).size()])));
        }
    }


    public BasicBarModel getModel() {
        return model;
    }

    public BasicBarView getView() {
        return barView;
    }
}
