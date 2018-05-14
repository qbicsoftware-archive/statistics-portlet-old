package life.qbic.presenter.tabs.workflows;


import com.vaadin.server.ExternalResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import life.qbic.model.components.GitHubLabels;
import life.qbic.model.view.GridModel;
import life.qbic.presenter.MainPresenter;
import life.qbic.presenter.tabs.ATabPresenter;
import life.qbic.view.TabView;
import life.qbic.view.tabs.GridView;
import submodule.data.ChartConfig;

import java.util.Arrays;
/**
 * @author fhanssen
 */
public class AvailableWorkflowsPresenter extends ATabPresenter<GridModel, GridView> {

    private final String chartName;
    private ChartConfig chartConfig;

    public AvailableWorkflowsPresenter(MainPresenter mainPresenter,
                                       String chartName){
        super(mainPresenter, new GridView(2,0,true, true));

        this.chartName = chartName;
        extractData();

        addChartSettings();
        addChartData();
    }

    @Override
    public void extractData(){
        chartConfig = super.getMainPresenter().getMainConfig().getCharts()
                .get(chartName);
    }

    @Override
    public void addChartSettings() {
        super.setModel(new GridModel("title", "subtitle"));
        super.getView().getComponent().setStyleName("workflow");

        logger.info("Settings were added to " + this.getClass() + " with chart title: " + super.getModel().getTitle());
    }

    @Override
    public void addChartData() {

        //This is necessary to get from Object to required String arrays
        Object[] objectArray = chartConfig.getData().keySet().toArray(new Object[chartConfig.getData().keySet().size()]);
        String[] keySet = Arrays.asList(objectArray).toArray(new String[objectArray.length]);


        //Actually adding of data
        for (String aKeySet : keySet) {
            for (int i = 0; i < chartConfig.getData().get(aKeySet).size(); i++) {
                String[] title = ((String) chartConfig.getSettings().getxCategories().get(i)).split("/");
                super.getModel().addData(new GitHubLabels(title[title.length - 2].concat("/").concat(title[title.length - 1]),
                        (String) chartConfig.getSettings().getyCategories().get(i),
                        (int)(double) chartConfig.getData().get(aKeySet).get(i)));
            }
        }

        logger.info("Data was added to a chart of " + this.getClass() + " with chart titel: " + chartConfig.getSettings().getTitle());


    }

    @Override
    public void addChart(TabView tabView, String title) {

        super.getView().setRows(super.getModel().getData().size()/2 + 1);
        for (Object labels : super.getModel().getData()) {
            setGridItemLayout((GitHubLabels) labels);
        }

        logger.info("Tab was added in " + this.getClass() + " for " +  super.getModel().getTitle());

    }

    private void setGridItemLayout(GitHubLabels labels) {

        Panel panel = new Panel(labels.getTitle());
        panel.setHeight(100.0f, Sizeable.Unit.PERCENTAGE);

        panel.setStyleName("workflow"); //TODO check if this works, or if it has to added to the vertical layout
        Label star = new Label(FontAwesome.STAR_O.getHtml() + " " + labels.getCount(), ContentMode.HTML);

        Link link = new Link("",
                new ExternalResource(labels.getUrl()));
        link.setIcon(FontAwesome.valueOf("GITHUB_SQUARE"));

        HorizontalLayout horizontalLayout = new HorizontalLayout(link, star);
        horizontalLayout.setSpacing(true);
        horizontalLayout.setComponentAlignment(star, Alignment.TOP_RIGHT);
        horizontalLayout.setComponentAlignment(link, Alignment.TOP_RIGHT);

        VerticalLayout verticalLayout = new VerticalLayout(horizontalLayout, new Label(labels.getDescription(), ContentMode.TEXT));
        verticalLayout.setWidth(100, Sizeable.Unit.PERCENTAGE);
        verticalLayout.setMargin(true);

        panel.setContent(verticalLayout);
        super.getView().addGridComponents(panel);
    }

    String getChartName() {
        return chartName;
    }
}