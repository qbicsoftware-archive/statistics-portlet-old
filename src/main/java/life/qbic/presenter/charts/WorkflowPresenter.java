package life.qbic.presenter.charts;

import com.vaadin.server.ExternalResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import life.qbic.model.view.GridModel;
import life.qbic.model.components.GitHubLabels;
import life.qbic.model.data.ChartConfig;
import life.qbic.view.MainView;
import life.qbic.view.TabView;
import life.qbic.view.tabs.GridView;

import java.util.Arrays;

/**
 * @author fhanssen
 */
public class WorkflowPresenter extends AChartPresenter<GridModel, GridView> {

    public WorkflowPresenter(ChartConfig workflowConfig, MainView mainView) {
        super(workflowConfig, mainView, new GridView(3, 0, true, true));

        addChartSettings();
        addChartData();
        addChartListener();
    }

    @Override
    void addChartSettings() {
        model = new GridModel("title", "subtitle");
        logger.info("Settings were added to " + this.getClass() + " with chart title: " + this.model.getTitle());
    }

    @Override
    void addChartData() {

        //This is necessary to get from Object to required String arrays
        Object[] objectArray = chartConfig.getData().keySet().toArray(new Object[chartConfig.getData().keySet().size()]);
        String[] keySet = Arrays.asList(objectArray).toArray(new String[objectArray.length]);


        //Actually adding of data
        for (String aKeySet : keySet) {
            for (int i = 0; i < chartConfig.getData().get(aKeySet).size(); i++) {
                String[] title = ((String) chartConfig.getSettings().getxCategories().get(i)).split("/");
                model.addData(new GitHubLabels(title[title.length - 2].concat("/").concat(title[title.length - 1]),
                        (String) chartConfig.getSettings().getyCategories().get(i),
                        (int)(double) chartConfig.getData().get(aKeySet).get(i)));
            }
        }

        logger.info("Data was added to a chart of " + this.getClass() + " with chart titel: " + chartConfig.getSettings().getTitle());

    }

    @Override
    void addChartListener() {

    }

    @Override
    public void specifyView(TabView tabView, String title) {

        for (Object labels : model.getData()) {
            setGridItemLayout((GitHubLabels) labels);
        }

        //Set new Tab
        super.setTabView(tabView);
        super.tabView.addMainComponent();
        super.mainView.addTabView(super.tabView, title);

        logger.info("Tab was added in " + this.getClass() + " for " +  model.getTitle());

    }

    private void setGridItemLayout(GitHubLabels labels) {

        Panel panel = new Panel(labels.getTitle());
        panel.setSizeFull();

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
        view.addGridComponents(panel);

    }
}
