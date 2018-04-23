package life.qbic.presenter.charts;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.PointClickListener;
import com.vaadin.addon.charts.model.*;
import com.vaadin.addon.charts.model.style.Color;
import life.qbic.model.view.charts.PieChartModel;
import life.qbic.presenter.utils.Colors;
import life.qbic.view.MainView;
import life.qbic.view.TabView;
import life.qbic.view.tabs.charts.PieView;
import submodule.data.ChartConfig;
import submodule.lexica.ChartNames;

import java.util.Arrays;
import java.util.Map;

/**
 * @author fhanssen
 */
public class WorkflowUsagePresenter extends AChartPresenter<PieChartModel, PieView> {

    private final Map<String, ChartConfig> availableWorkflows;

    public WorkflowUsagePresenter(ChartConfig workflowConfig, MainView mainView, Map<String, ChartConfig> availableWorkflows) {
        super(workflowConfig, mainView, new PieView());

        this.availableWorkflows = availableWorkflows;

        addChartSettings();
        addChartData();
        addChartListener();
    }

    @Override
    void addChartSettings() {

        PlotOptionsPie plot = new PlotOptionsPie();

        plot.setDataLabels(new DataLabels(true));

        Tooltip tooltip = new Tooltip();
        tooltip.setFormatter("this.point.name + ': <b>'+ this.y + '</b> times executed <br> Click to show available workflows'");

        Legend legend = new Legend();
        legend.setEnabled(false);

        model = new PieChartModel(this.view.getConfiguration(),chartConfig.getSettings().getTitle(),
                chartConfig.getSettings().getSubtitle(), tooltip, legend, plot);
        logger.info("Settings were added to " + this.getClass() + " with chart title: " + this.view.getConfiguration().getTitle().getText());
    }

    @Override
    void addChartData() {

        //This is necessary to get from Object to required String arrays
        Object[] objectArray = chartConfig.getData().keySet().toArray(new Object[chartConfig.getData().keySet().size()]);
        String[] keySet = Arrays.asList(objectArray).toArray(new String[objectArray.length]);

        Color[] innerColors = Arrays.copyOf(Colors.getSolidColors(), chartConfig.getSettings().getxCategories().size());


        //Actually adding of data
        for (String aKeySet : keySet) {
            for (int i = 0; i < chartConfig.getData().get(aKeySet).size(); i++) {
                model.addData(new DataSeries(new DataSeriesItem((String) chartConfig.getSettings().getxCategories().get(i),
                        (Number) chartConfig.getData().get(aKeySet).get(i), innerColors[i % Colors.getSolidColors().length])));
            }
        }

        logger.info("Data was added to a chart of " + this.getClass() + " with chart titel: " + chartConfig.getSettings().getTitle());

    }

    @Override
    void addChartListener() {
        ((Chart)view.getComponent()).addPointClickListener((PointClickListener) event -> {
            logger.info("Chart of "+ this.getClass() +" with chart titel: " + this.view.getConfiguration().getTitle().getText() +" was clicked at " + model.getDataName(event));
            if(availableWorkflows.keySet().contains(ChartNames.Available_Workflows_.toString().concat(model.getDataName(event)))) {
                AvailableWorkflowPresenter p = new AvailableWorkflowPresenter(mainView, availableWorkflows.get(ChartNames.Available_Workflows_.toString().concat(model.getDataName(event))));

                p.specifyView(this.tabView, "");
            }

        });
    }

    @Override
    public void specifyView(TabView tabView, String title) {

        //Set new tab
        super.setTabView(tabView);
        super.tabView.addMainComponent();
        super.mainView.addTabView(super.tabView, title);

        logger.info("Tab was added in " + this.getClass() + " for " +  this.view.getConfiguration().getTitle().getText() );



    }

}