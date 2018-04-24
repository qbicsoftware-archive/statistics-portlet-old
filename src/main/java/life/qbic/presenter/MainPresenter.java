package life.qbic.presenter;


import com.vaadin.data.util.filter.Not;
import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import life.qbic.logging.Log4j2Logger;
import life.qbic.logging.Logger;
import life.qbic.io.YAMLParser;
import life.qbic.portlet.StatisticsViewUI;
import life.qbic.presenter.tabs.organisms.SuperKingdomCountPresenter;
import life.qbic.presenter.tabs.projects.ProjectTechColumnPresenter;
import life.qbic.presenter.tabs.projects.ProjectTechnologiesPresenter;
import life.qbic.presenter.tabs.samples.SampleTypeBarPresenter;
import life.qbic.presenter.tabs.samples.SampleTypePresenter;
import life.qbic.presenter.tabs.workflows.WorkflowUsagePresenter;
import life.qbic.presenter.utils.CustomNotification;
import life.qbic.view.TabView;
import submodule.data.ChartConfig;
import submodule.data.MainConfig;
import submodule.lexica.ChartNames;
import submodule.lexica.Kingdoms;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * @author fhanssen
 * Handles input config and adds charts serially. Handles button presses and listens to observabeLists in SubPresenters
 */
public class MainPresenter {

    private static final Logger logger = new Log4j2Logger(MainPresenter.class);

    private final StatisticsViewUI mainView;
    private  MainConfig mainConfig;

    public MainPresenter(StatisticsViewUI mainView) {
        this.mainView = mainView;

        try {
            this.mainConfig = YAMLParser.parseConfig("/Users/qbic/Documents/QBiC/config.yaml");
        }catch(IOException e){
            logger.error("Parsing of YAML file failed. " + e);
            CustomNotification.error("File not Found",
                                  "Config file with data could not be parsed");
            this.mainConfig = new MainConfig();
        }

    }

    //Careful: Order matters! Determines in which order tabs are displayed.
    public void addCharts(){
        addOrganismCountPie();
        addWorkflowGrid();
        addSampleCountPie();
        addProjectCountsPie();
        //TODO 5: Add your method to add a new chart doing the following things: 1) Get your ChartConfigs, 2) Create a new AChartPresenter, 3) Set a new Tab 4) Add Button and SubChartsListener, 5) Add Tab to mainView
    }

    private void addOrganismCountPie(){

        //Get needed configs
        Map<String, ChartConfig> speciesCharts = new HashMap<>();
        Map<String, ChartConfig> genusCharts = new HashMap<>();

        for(String chartName : ChartNames.getList()){
            if(Kingdoms.getList().contains(chartName.split("_")[0])){
                if(chartName.split("_")[1].equals("Species")) {
                    speciesCharts.put(chartName, mainConfig.getCharts().get(chartName));
                }else if (chartName.split("_")[1].equals("Genus")){
                    genusCharts.put(chartName, mainConfig.getCharts().get(chartName));
                }
            }
        }

        //Create Presenter
        SuperKingdomCountPresenter organismCountPiePresenter =
                new SuperKingdomCountPresenter(this.mainView,
                        mainConfig.getCharts().get(ChartNames.SuperKingdom.toString()),
                        genusCharts,
                        speciesCharts,
                        mainConfig.getCharts().get(ChartNames.Species_Genus.toString()));


        organismCountPiePresenter.specifyView( new TabView(organismCountPiePresenter.getView(),
                        organismCountPiePresenter.getModel()),
                "Organisms");
        addReturnButtonListener(organismCountPiePresenter.getTabView());

        logger.info("Organism tab was added");


    }

    private void addWorkflowGrid(){

        Map<String, ChartConfig> availWorkflows = new HashMap<>();
        mainConfig.getCharts().keySet().forEach(key ->{
            if(key.startsWith(ChartNames.Available_Workflows_.toString())){
                availWorkflows.put(key, mainConfig.getCharts().get(key));
            }
        });
        WorkflowUsagePresenter workflowUsagePresenter = new WorkflowUsagePresenter(mainConfig.getCharts()
                .get(ChartNames.Workflow_Execution_Counts.toString()),
                mainView,
                availWorkflows);
        workflowUsagePresenter.specifyView(new TabView(workflowUsagePresenter.getView(), workflowUsagePresenter.getModel()),
                "Workflows");

        addReturnButtonListener(workflowUsagePresenter.getTabView());

        logger.info("Workflow tab was added");

    }

    private void addSampleCountPie(){

        //Create Presenter
        SampleTypePresenter sampleTypePresenter =
                new SampleTypePresenter(this.mainView,
                        mainConfig.getCharts().get(ChartNames.Sample_Types.toString()));


        sampleTypePresenter.specifyView( new TabView(sampleTypePresenter.getView(),
                        sampleTypePresenter.getModel()),
                "Sample Types");

        logger.info("Sample tab was added");

        //Create Presenter
        SampleTypeBarPresenter sampleTypeBarPresenter =
                new SampleTypeBarPresenter(this.mainView,
                        mainConfig.getCharts().get(ChartNames.Sample_Types.toString()));


        sampleTypeBarPresenter.specifyView( new TabView(sampleTypeBarPresenter.getView(),
                        sampleTypeBarPresenter.getModel()),
                "Sample Types");

        logger.info("Sample tab was added");


    }

    private void addProjectCountsPie(){

        //Create Presenter
        ProjectTechnologiesPresenter projectTechnologiesPresenter =
                new ProjectTechnologiesPresenter(this.mainView,
                        mainConfig.getCharts().get(ChartNames.Projects_Technology.toString()));


        projectTechnologiesPresenter.specifyView( new TabView(projectTechnologiesPresenter.getView(),
                        projectTechnologiesPresenter.getModel()),
                "Projects");

        logger.info("Projects tab was added");

        //Create Presenter
        ProjectTechColumnPresenter projectTechColumnPresenter =
                new ProjectTechColumnPresenter(this.mainView,
                        mainConfig.getCharts().get(ChartNames.Projects_Technology.toString()));


        projectTechColumnPresenter.specifyView( new TabView(projectTechColumnPresenter.getView(),
                        projectTechColumnPresenter.getModel()),
                "Projects");

        logger.info("Projects tab was added");


    }

    private void addReturnButtonListener(TabView tabView){
        tabView.getReturnButton().addClickListener(clickEvent -> {
            logger.info("Return button was pressed");
            tabView.addMainComponent();

        });
    }

}