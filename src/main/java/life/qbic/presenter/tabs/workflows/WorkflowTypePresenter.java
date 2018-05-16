package life.qbic.presenter.tabs.workflows;

import com.vaadin.ui.Accordion;
import com.vaadin.ui.VerticalLayout;
import life.qbic.model.view.GridModel;
import life.qbic.presenter.MainPresenter;
import life.qbic.presenter.tabs.ATabPresenter;
import life.qbic.view.TabView;
import life.qbic.view.tabs.GridView;
import submodule.lexica.ChartNames;
import submodule.lexica.Translator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fhanssen
 */
public class WorkflowTypePresenter extends ATabPresenter<GridModel, GridView> {

    private final String workflowType;
    private final List<String> subTypes;
    private final List<AvailableWorkflowsPresenter> availableWorkflowsPresenters;

    private final Accordion accordion = new Accordion();

    public WorkflowTypePresenter(MainPresenter mainPresenter, String workflowType) {
        super(mainPresenter, new GridView(1, 2, true, true));

        this.workflowType = workflowType;
        this.subTypes = new ArrayList<>();
        this.availableWorkflowsPresenters = new ArrayList<>();

        extractData();
        addChartSettings();
        addChartData();

    }

    @Override
    public void extractData() {
        String subType = Translator.getOriginal(workflowType);
        if (subType.isEmpty()) {
            subType = workflowType;

        }

        final String s = subType;
        getMainPresenter().getMainConfig().getCharts().keySet().forEach(n -> {
            if (n.contains(ChartNames.Available_Workflows_.toString()) && n.contains(s)) {
                subTypes.add(n);
            }
        });

    }

    @Override
    public void addChartSettings() {
        super.setModel(new GridModel("title", "subtitle"));
        logger.info("Settings were added to " + this.getClass() + " with chart title: " + super.getModel().getTitle());

    }

    @Override
    public void addChartData() {
        subTypes.forEach(s -> {
            AvailableWorkflowsPresenter ap = new AvailableWorkflowsPresenter(getMainPresenter(), s);
            availableWorkflowsPresenters.add(ap);
        });
        logger.info("Data was added to a chart of " + this.getClass());

    }

    @Override
    public void addChart(TabView tabView, String title) {

        for (AvailableWorkflowsPresenter a : availableWorkflowsPresenters) {
            a.addChart(tabView, "");
            setGridItemLayout(a.getView(), a.getChartName());
        }

        super.getView().addGridComponents(accordion);
        tabView.addSubComponent(super.getModel(), super.getView());
        tabView.setTitle(title);
        this.accordion.setSelectedTab(this.accordion.getComponentCount()-1);

        addReturnButtonListener(tabView);
        logger.info("Tab was added in " + this.getClass() + " for " + super.getModel().getTitle());
    }

    private void setGridItemLayout(GridView view, String chartName) {
        final VerticalLayout layout = new VerticalLayout(view.getComponent());
        String title = chartName.split("_")[chartName.split("_").length - 1];
        if (!Translator.getTranslation(title).isEmpty()) {
            title = Translator.getTranslation(title);
        }
        accordion.addTab(layout, title);

    }
}
