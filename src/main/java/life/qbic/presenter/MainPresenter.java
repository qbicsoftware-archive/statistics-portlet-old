package life.qbic.presenter;

import com.vaadin.server.ExternalResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.server.Sizeable;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import javafx.collections.ListChangeListener;
import life.qbic.logging.Log4j2Logger;
import life.qbic.logging.Logger;
import life.qbic.model.AModel;
import life.qbic.model.components.GitHubLabels;
import life.qbic.model.data.ChartConfig;
import life.qbic.model.data.MainConfig;
import life.qbic.presenter.charts.AChartPresenter;
import life.qbic.presenter.charts.SuperKingdomCountPresenter;
import life.qbic.presenter.charts.WorkflowPresenter;
import life.qbic.presenter.utils.lexica.ChartNames;
import life.qbic.presenter.utils.lexica.SuperKingdoms;
import life.qbic.utils.YAMLParser;
import life.qbic.view.MainView;
import life.qbic.view.AView;
import life.qbic.view.TabView;
import life.qbic.view.charts.AChartView;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author fhanssen
 * Handles input onfig and adds charts serially. Handles button presses and listens to observabeLists in SubPresenters
 */
public class MainPresenter {

    static Logger logger = new Log4j2Logger(MainPresenter.class);


    private final MainView mainView;
    private final MainConfig mainConfig;

    public MainPresenter(MainView mainView) {
        this.mainView = mainView;
        this.mainConfig = YAMLParser.parseConfig("/Users/qbic/Documents/QBiC/statistics-data-retrieval-openbis/config.yaml");

        addCharts();
    }

    //Careful: Order matters! Determines in which order tabs are displayed.
    private void addCharts(){
        addOrganismCountPie();
        addWorkflowGrid();
        //TODO 5: Add your method to add a new chart doing the following things: 1) Get your ChartConfigs, 2) Create a new AChartPresenter, 3) Set a new Tab 4) Add Button and SubChartsListener, 5) Add Tab to mainView
    }

    private void addOrganismCountPie(){

        //Get needed configs
        Map<String, ChartConfig> speciesCharts = new HashMap<>();
        Map<String, ChartConfig> genusCharts = new HashMap<>();

        for(String chartName : ChartNames.getList()){
            if(SuperKingdoms.getList().contains(chartName.split("_")[0])){
                if(chartName.split("_")[1].equals("Species")) {
                    speciesCharts.put(chartName, mainConfig.getCharts().get(chartName));
                }else if (chartName.split("_")[1].equals("Genus")){
                    genusCharts.put(chartName, mainConfig.getCharts().get(chartName));
                }
            }
        }

        //Create Presenter
        SuperKingdomCountPresenter organismCountPiePresenter =
                new SuperKingdomCountPresenter(mainConfig.getCharts().get(ChartNames.SuperKingdom.toString()),
                                                        genusCharts,
                                                        speciesCharts,
                                                        mainConfig.getCharts().get(ChartNames.Species_Genus.toString()));

        //Set new tab
        TabView domainCountTab = new TabView(organismCountPiePresenter.getView(),
                                             organismCountPiePresenter.getModel());

        domainCountTab.addMainChart();

        //Add Listener
        addListenerForSubcharts(domainCountTab, organismCountPiePresenter);
        addReturnButtonListener(domainCountTab);

        //Add Tab to MainView
        this.mainView.addChart(domainCountTab, "Organisms");
        logger.info("Organism tab was added");


    }

    private void addWorkflowGrid(){

        WorkflowPresenter workflowPresenter = new WorkflowPresenter(mainConfig.getCharts().get(ChartNames.Workflow.toString()));
        for(Object l : workflowPresenter.getModel().getData()){
            Panel t = new Panel(((GitHubLabels) l).getTitle());
            VerticalLayout v = new VerticalLayout();
            t.setContent(v);
            v.setWidth(200, Sizeable.Unit.PERCENTAGE);
            v.setMargin(true);

            Label star = new Label(FontAwesome.STAR_O.getHtml() + " " + ((GitHubLabels) l).getCount(), ContentMode.HTML);
            t.setSizeFull();
            Link link = new Link("",
                    new ExternalResource(((GitHubLabels) l).getUrl()));
            link.setIcon(FontAwesome.valueOf("GITHUB_SQUARE"));
            HorizontalLayout h = new HorizontalLayout();
            h.addComponents(link, star);
            h.setSpacing(true);
            v.addComponent(h);

            v.addComponent(new Label(((GitHubLabels) l).getDescription(), ContentMode.TEXT));


            workflowPresenter.getView().addGridComponents(t);
        }
        TabView githubView = new TabView(workflowPresenter.getView(), workflowPresenter.getModel());
        githubView.addGrid();
        this.mainView.addChart(githubView, "Available Workflows");

    }

    private void addReturnButtonListener(TabView tabView){
        tabView.getReturnButton().addClickListener(clickEvent -> {
            logger.info("Return button was pressed");
            tabView.addMainChart();

        });
    }

    private void addListenerForSubcharts(TabView tabView, AChartPresenter presenter){
        presenter.getSubCharts().addListener((ListChangeListener<? super AChartPresenter<AModel, AView>>) c -> {
            while(c.next()){
                c.getAddedSubList().forEach(l -> tabView.addSubChart(l.getModel(), l.getView()));
                c.getRemoved().forEach(l -> tabView.removeChart(((AChartView)l.getView()).getChart()));
            }
            logger.info("ObservableList of " + presenter.getClass() + " changed");
        });
    }
}
