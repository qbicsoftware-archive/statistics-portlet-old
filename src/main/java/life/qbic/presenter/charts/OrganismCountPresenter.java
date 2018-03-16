//package life.qbic.presenter.charts;
//
//import com.vaadin.addon.charts.model.*;
//import life.qbic.model.charts.ColumnModel;
//import life.qbic.model.data.ChartConfig;
//import life.qbic.view.charts.ColumnView;
//
//import java.util.Arrays;
//
//public class OrganismCountPresenter extends AChartPresenter<ColumnModel, ColumnView>{
//
//    public OrganismCountPresenter(ChartConfig chartConfig){
//       super(chartConfig, new ColumnView());
//    }
//
//    @Override
//    public void addChartSettings() {
//
//        AxisTitle yAxisTitle = new AxisTitle("Count");
//        yAxisTitle.setAlign(VerticalAlign.MIDDLE);
//
//        Tooltip tooltip = new Tooltip();
//
//        PlotOptionsColumn plot = new PlotOptionsColumn();
//        plot.setDataLabels(new DataLabels(true));
//
//        Legend legend = new Legend();
//        legend.setEnabled(false);
//
//        model = new ColumnModel(view.getConfiguration(), "Domain Count",
//                null, tooltip, legend,new AxisTitle("Domain"), yAxisTitle,  plot);
//
//        model.addXCategorie(chartConfig.getSettings().getxCategories().toArray(new String[chartConfig.getSettings().getxCategories().size()]));
//
//    }
//
//    @Override
//    public void addChartData() {
//        Object[] objectArray = chartConfig.getData().keySet().toArray(new Object[chartConfig.getData().keySet().size()]);
//        String[] keySet = Arrays.asList(objectArray).toArray(new String[objectArray.length]);
//        Arrays.sort(keySet);
//        for (String aKeySet : keySet) {
//            model.addData(new ListSeries("1",
//                    chartConfig.getData().get(aKeySet).toArray(new Number[chartConfig.getData().get(aKeySet).size()])));
//        }
//    }
//
//}
