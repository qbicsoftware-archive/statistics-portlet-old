package life.qbic.presenter.tabs.workflows;


import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.PointClickListener;
import com.vaadin.addon.charts.model.*;
import com.vaadin.addon.charts.model.style.Color;
import life.qbic.model.view.charts.PieChartModel;
import life.qbic.portlet.StatisticsViewUI;
import life.qbic.presenter.MainPresenter;
import life.qbic.presenter.tabs.ATabPresenter;
import life.qbic.presenter.utils.Colors;
import life.qbic.presenter.utils.DataSorter;
import life.qbic.presenter.utils.LabelFormatter;
import life.qbic.view.TabView;
import life.qbic.view.tabs.charts.PieView;
import submodule.data.ChartConfig;
import submodule.lexica.ChartNames;
import submodule.lexica.CommonAbbr;
import submodule.lexica.Translator;

import java.util.*;

/**
 * @author fhanssen
 */
public class WorkflowUsagePresenter extends ATabPresenter<PieChartModel, PieView> {

    private ChartConfig workflowUsageConfig;

    public WorkflowUsagePresenter(MainPresenter mainPresenter) {
        super(mainPresenter, new PieView());

        extractData();

        addChartSettings();
        addChartData();
        addChartListener();
    }

    @Override
    public void extractData() {
        workflowUsageConfig = super.getMainPresenter().getMainConfig().getCharts()
                .get(ChartNames.Workflow_Execution_Counts.toString());
    }

    @Override
    public void addChartSettings() {

        PlotOptionsPie plot = new PlotOptionsPie();

        plot.setDataLabels(new DataLabels(true));

        Tooltip tooltip = new Tooltip();
        tooltip.setFormatter("this.point.name + ': <b>'+ this.y + '</b> times executed <br> Click to show available workflows'");

        Legend legend = new Legend();
        legend.setEnabled(false);

        super.setModel(new PieChartModel(super.getView().getConfiguration(), workflowUsageConfig.getSettings().getTitle(),
                workflowUsageConfig.getSettings().getSubtitle(), tooltip, legend, plot));
        logger.info("Settings were added to " + this.getClass() + " with chart title: " + super.getView().getConfiguration().getTitle().getText());
    }

    @Override
    public void addChartData() {

        //This is necessary to get from Object to required String arrays
        Object[] objectArray = workflowUsageConfig.getData().keySet().toArray(new Object[workflowUsageConfig.getData().keySet().size()]);
        String[] keySet = Arrays.asList(objectArray).toArray(new String[objectArray.length]);

        List<DataSorter> dataSorters = new ArrayList<>();

        //Actually adding of data
        for (String aKeySet : keySet) {
            for (int i = 0; i < workflowUsageConfig.getData().get(aKeySet).size(); i++) {
                String label = (String) workflowUsageConfig.getSettings().getxCategories().get(i);

                if(CommonAbbr.getList().contains(label)){
                    label = CommonAbbr.valueOf(label).toString();
                }else if(Translator.getList().contains(label)){
                    label = Translator.valueOf(label).getTranslation();
                }else{
                    label = LabelFormatter.generateCamelCase(label);
                }

                dataSorters.add(new DataSorter(label,
                        (int) workflowUsageConfig.getData().get(aKeySet).get(i)));
            }
        }

        Collections.sort(dataSorters);

        //Add data
        dataSorters.forEach(d -> this.getModel().addData(new DataSeriesItem(d.getName(), d.getCount())));

        logger.info("Data was added to a chart of " + this.getClass() + " with chart titel: " + workflowUsageConfig.getSettings().getTitle());

    }

    private void addChartListener() {
        ((Chart) super.getView().getComponent()).addPointClickListener((PointClickListener) event -> {
            logger.info("Chart of " + this.getClass() + " with chart titel: " + super.getView().getConfiguration().getTitle().getText() + " was clicked at " + super.getModel().getDataName(event));
            //TODO fix this when all i sproperly formatted
            AvailableWorkflowsPresenter p = new AvailableWorkflowsPresenter(super.getMainPresenter(),  super.getModel().getDataName(event).toUpperCase());
            p.addChart(super.getTabView(), "");
        });
    }

    @Override
    public void addChart(TabView tabView, String title) {

        //Set new tab
        super.setTabView(tabView);
        super.getTabView().addMainComponent();
        super.getMainPresenter().getMainView().addTabView(super.getTabView(), title);

        logger.info("Tab was added in " + this.getClass() + " for " + super.getView().getConfiguration().getTitle().getText());


    }

}
