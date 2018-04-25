//package life.qbic.presenter.tabs.samples;
//
//
//import com.vaadin.addon.charts.model.*;
//import com.vaadin.addon.charts.model.style.Color;
//import life.qbic.model.view.charts.PieChartModel;
//import life.qbic.portlet.StatisticsViewUI;
//import life.qbic.presenter.tabs.ATabPresenter;
//import life.qbic.presenter.utils.Colors;
//import life.qbic.view.TabView;
//import life.qbic.view.tabs.charts.PieView;
//import submodule.data.ChartConfig;
//
//import java.util.Arrays;
///**
// * @author fhanssen
// */
//public class SampleTypePresenter extends ATabPresenter<PieChartModel, PieView> {
//
//    public SampleTypePresenter(StatisticsViewUI mainView, ChartConfig chartConfig){
//        super( chartConfig, mainView, new PieView());
//
//        addChartSettings();
//        addChartData();
//        addChartListener();
//
//    }
//
//    @Override
//    public void addChartSettings() {
//
//        PlotOptionsPie plot = new PlotOptionsPie();
//
//        plot.setDataLabels(new DataLabels(true));
//
//        Tooltip tooltip = new Tooltip();
//        tooltip.setFormatter("this.point.name + ': <b>'+ this.y + '</b> Samples'");
//
//        Legend legend = new Legend();
//        legend.setEnabled(false);
//
//        super.setModel(new PieChartModel(super.getView().getConfiguration(), super.getChartConfig().getSettings().getTitle(),
//                null, tooltip, legend, plot));
//
//        logger.info("Settings were added to a chart of "+ this.getClass() +" with chart titel: " + super.getView().getConfiguration().getTitle().getText());
//
//
//
//    }
//
//    @Override
//    public void addChartData() {
//        //This is necessary to get from Object to required String arrays
//        Object[] objectArray = super.getChartConfig().getData().keySet().toArray(new Object[super.getChartConfig().getData().keySet().size()]);
//        String[] keySet = Arrays.asList(objectArray).toArray(new String[objectArray.length]);
//
//        Color[] innerColors = Arrays.copyOf(Colors.getSolidColors(), super.getChartConfig().getSettings().getxCategories().size());
//        //Actually adding of data
//        for (String aKeySet : keySet) {
//            for (int i = 0; i < super.getChartConfig().getData().get(aKeySet).size(); i++) {
//                super.getModel().addData(new DataSeries(new DataSeriesItem((String) super.getChartConfig().getSettings().getxCategories().get(i),
//                        (Number) super.getChartConfig().getData().get(aKeySet).get(i), innerColors[i % Colors.getSolidColors().length])));
//            }
//        }
//
//        logger.info("Data was added to a chart of " + this.getClass() + " with chart titel: " + super.getView().getConfiguration().getTitle().getText());
//
//
//    }
//
//    @Override
//    public void addChartListener() {
//
//    }
//
//    @Override
//    public void addCharts(TabView tabView, String title) {
//        //Set new tab
//        super.setTabView(tabView);
//        super.getTabView().addMainComponent();
//        super.getMainView().addTabView(super.getTabView(), title);
//
//        logger.info("Tab was added in " + this.getClass() + " for " +  super.getView().getConfiguration().getTitle().getText() );
//
//    }
//}
