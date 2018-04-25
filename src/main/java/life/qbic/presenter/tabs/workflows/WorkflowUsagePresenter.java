//package life.qbic.presenter.tabs.workflows;
//
//
//import com.vaadin.addon.charts.Chart;
//import com.vaadin.addon.charts.PointClickListener;
//import com.vaadin.addon.charts.model.*;
//import com.vaadin.addon.charts.model.style.Color;
//import life.qbic.model.view.charts.PieChartModel;
//import life.qbic.portlet.StatisticsViewUI;
//import life.qbic.presenter.tabs.ATabPresenter;
//import life.qbic.presenter.utils.Colors;
//import life.qbic.view.TabView;
//import life.qbic.view.tabs.charts.PieView;
//import submodule.data.ChartConfig;
//import submodule.lexica.ChartNames;
//
//import java.util.Arrays;
//import java.util.Map;
//
///**
// * @author fhanssen
// */
//public class WorkflowUsagePresenter extends ATabPresenter<PieChartModel, PieView> {
//
//    private final Map<String, ChartConfig> availableWorkflows;
//
//    public WorkflowUsagePresenter(ChartConfig workflowConfig, StatisticsViewUI mainView, Map<String, ChartConfig> availableWorkflows) {
//        super(workflowConfig, mainView, new PieView());
//
//        this.availableWorkflows = availableWorkflows;
//
//        addChartSettings();
//        addChartData();
//        addChartListener();
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
//        tooltip.setFormatter("this.point.name + ': <b>'+ this.y + '</b> times executed <br> Click to show available workflows'");
//
//        Legend legend = new Legend();
//        legend.setEnabled(false);
//
//        super.setModel(new PieChartModel(super.getView().getConfiguration(),super.getChartConfig().getSettings().getTitle(),
//                super.getChartConfig().getSettings().getSubtitle(), tooltip, legend, plot));
//        logger.info("Settings were added to " + this.getClass() + " with chart title: " + super.getView().getConfiguration().getTitle().getText());
//    }
//
//    @Override
//    public void addChartData() {
//
//        //This is necessary to get from Object to required String arrays
//        Object[] objectArray = super.getChartConfig().getData().keySet().toArray(new Object[super.getChartConfig().getData().keySet().size()]);
//        String[] keySet = Arrays.asList(objectArray).toArray(new String[objectArray.length]);
//
//        Color[] innerColors = Arrays.copyOf(Colors.getSolidColors(), super.getChartConfig().getSettings().getxCategories().size());
//
//
//        //Actually adding of data
//        for (String aKeySet : keySet) {
//            for (int i = 0; i < super.getChartConfig().getData().get(aKeySet).size(); i++) {
//                this.getModel().addData(new DataSeries(new DataSeriesItem((String) super.getChartConfig().getSettings().getxCategories().get(i),
//                        (Number) super.getChartConfig().getData().get(aKeySet).get(i), innerColors[i % Colors.getSolidColors().length])));
//            }
//        }
//
//        logger.info("Data was added to a chart of " + this.getClass() + " with chart titel: " + super.getChartConfig().getSettings().getTitle());
//
//    }
//
//    @Override
//    public void addChartListener() {
//        ((Chart)super.getView().getComponent()).addPointClickListener((PointClickListener) event -> {
//            logger.info("Chart of "+ this.getClass() +" with chart titel: " + super.getView().getConfiguration().getTitle().getText() +" was clicked at " + super.getModel().getDataName(event));
//            if(availableWorkflows.keySet().contains(ChartNames.Available_Workflows_.toString().concat(super.getModel().getDataName(event)))) {
//                AvailableWorkflowsPresenter p = new AvailableWorkflowsPresenter(super.getMainView(), availableWorkflows.get(ChartNames.Available_Workflows_.toString().concat(super.getModel().getDataName(event))));
//
//                p.addCharts(super.getTabView(), "");
//            }
//
//        });
//    }
//
//    @Override
//    public void addCharts(TabView tabView, String title) {
//
//        //Set new tab
//        super.setTabView(tabView);
//        super.getTabView().addMainComponent();
//        super.getMainView().addTabView(super.getTabView(), title);
//
//        logger.info("Tab was added in " + this.getClass() + " for " +  super.getView().getConfiguration().getTitle().getText() );
//
//
//
//    }
//
//}
