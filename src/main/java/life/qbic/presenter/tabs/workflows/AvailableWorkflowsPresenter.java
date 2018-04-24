package life.qbic.presenter.tabs.workflows;


import com.vaadin.server.ExternalResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import life.qbic.model.components.GitHubLabels;
import life.qbic.model.view.GridModel;
import life.qbic.portlet.StatisticsViewUI;
import life.qbic.presenter.tabs.ATabPresenter;
import life.qbic.view.TabView;
import life.qbic.view.tabs.GridView;
import submodule.data.ChartConfig;

import java.util.Arrays;
/**
 * @author fhanssen
 */
public class AvailableWorkflowsPresenter extends ATabPresenter<GridModel, GridView> {

    public AvailableWorkflowsPresenter(StatisticsViewUI mainView, ChartConfig chartConfig){
        super(chartConfig, mainView, new GridView(3,0,true, true));

        addChartSettings();
        addChartData();
        addChartListener();
    }

    @Override
    public void addChartSettings() {
        super.setModel(new GridModel("title", "subtitle"));
        logger.info("Settings were added to " + this.getClass() + " with chart title: " + super.getModel().getTitle());
    }

    @Override
    public void addChartData() {

        //This is necessary to get from Object to required String arrays
        Object[] objectArray = super.getChartConfig().getData().keySet().toArray(new Object[super.getChartConfig().getData().keySet().size()]);
        String[] keySet = Arrays.asList(objectArray).toArray(new String[objectArray.length]);


        //Actually adding of data
        for (String aKeySet : keySet) {
            for (int i = 0; i < super.getChartConfig().getData().get(aKeySet).size(); i++) {
                String[] title = ((String) super.getChartConfig().getSettings().getxCategories().get(i)).split("/");
                super.getModel().addData(new GitHubLabels(title[title.length - 2].concat("/").concat(title[title.length - 1]),
                        (String) super.getChartConfig().getSettings().getyCategories().get(i),
                        (int)(double) super.getChartConfig().getData().get(aKeySet).get(i)));
            }
        }

        logger.info("Data was added to a chart of " + this.getClass() + " with chart titel: " + super.getChartConfig().getSettings().getTitle());


    }

    @Override
    public void addChartListener() {

    }

    @Override
    public void specifyView(TabView tabView, String title) {

        super.getView().setRows(super.getModel().getData().size()/3 + 1);
        for (Object labels : super.getModel().getData()) {
            setGridItemLayout((GitHubLabels) labels);
        }


        tabView.addSubComponent(super.getModel(), super.getView());

        logger.info("Tab was added in " + this.getClass() + " for " +  super.getModel().getTitle());

    }

    private void setGridItemLayout(GitHubLabels labels) {

        Panel panel = new Panel(labels.getTitle());


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
}