//package life.qbic.presenter.charts;
//
//import com.vaadin.addon.charts.model.*;
//import life.qbic.model.charts.BasicLineModel;
//import life.qbic.model.data.ChartConfig;
//import life.qbic.view.charts.BasicLineView;
//
//import java.util.Arrays;
//
//public class TemperaturePresenter implements ChartPresenter<BasicLineModel, BasicLineView> {
//
//    private final BasicLineView timelineView;
//    private BasicLineModel model;
//    private final ChartConfig chartConfig;
//
//    public TemperaturePresenter(ChartConfig chartConfig){
//        timelineView = new BasicLineView();
//        this.chartConfig = chartConfig;
//        addChartSettings();
//        addChartData();
//    }
//
//    @Override
//    public void addChartSettings(){
//        AxisTitle yAxisTitle = new AxisTitle(chartConfig.getSettings().getyAxisTitle());
//        yAxisTitle.setAlign(VerticalAlign.MIDDLE);
//
//        Tooltip tooltip = new Tooltip();
//        tooltip.setFormatter(
//                "'<b>'+ this.series.name +'</b><br/>'+this.x +': '+ this.y +'°C'");
//
//        Legend legend = new Legend();
//        legend.setLayout(LayoutDirection.VERTICAL);
//        legend.setAlign(HorizontalAlign.RIGHT);
//        legend.setVerticalAlign(VerticalAlign.TOP);
//        legend.setX(-10d);
//        legend.setY(100d);
//        legend.setBorderWidth(0);
//
//        PlotOptionsLine plotOptions = new PlotOptionsLine();
//        plotOptions.getDataLabels().setEnabled(true);
//
//        model = new BasicLineModel(timelineView.getConfiguration(),
//                                        chartConfig.getSettings().getTitle(),
//                                        chartConfig.getSettings().getSubtitle(),
//                                        new AxisTitle(chartConfig.getSettings().getxAxisTitle()),
//                yAxisTitle,
//                tooltip,
//                legend,
//                                        plotOptions);
//
//        model.addXCategorie(chartConfig.getSettings().getxCategories().toArray(new String[chartConfig.getSettings().getxCategories().size()]));
//
//        model.setyMin(-5);
//
//    }
//
//    @Override
//    public void addChartData(){
//        Object[] objectArray = chartConfig.getData().keySet().toArray(new Object[chartConfig.getData().keySet().size()]);
//        String[] keySet = Arrays.copyOf(objectArray, objectArray.length, String[].class);
//
//        for (String aKeySet : keySet)
//            model.addData(new ListSeries(String.valueOf(aKeySet),
//                    chartConfig.getData().get(aKeySet).toArray(new Integer[chartConfig.getData().get(aKeySet).size()])));
//    }
//
//    @Override
//    public BasicLineModel getModel() {
//        return model;
//    }
//
//    @Override
//    public BasicLineView getView() {
//        return timelineView;
//    }
//}
