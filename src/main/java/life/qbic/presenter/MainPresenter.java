package life.qbic.presenter;



import life.qbic.logging.Log4j2Logger;
import life.qbic.logging.Logger;
import life.qbic.model.view.AModel;
import life.qbic.portal.liferayandvaadinhelpers.main.LiferayAndVaadinUtils;
import life.qbic.portlet.StatisticsViewUI;
import life.qbic.presenter.tabs.ATabPresenter;
import life.qbic.presenter.tabs.organisms.SuperKingdomCountPresenter;
import life.qbic.presenter.utils.CustomNotification;
import life.qbic.view.TabView;
import life.qbic.view.tabs.AView;
import submodule.data.MainConfig;



/**
 * @author fhanssen
 * Handles input config and adds charts serially.
 * Handles button presses and listens to observabeLists in SubPresenters
 */
public class MainPresenter {

    private static final Logger logger = new Log4j2Logger(MainPresenter.class);

    private final StatisticsViewUI mainView;

    private  MainConfig mainConfig;

    private ATabPresenter superKingdomCountPresenter;

    public MainPresenter(StatisticsViewUI mainView, String defaultInputFilename) {
        this.mainView = mainView;
        this.mainConfig = new MainConfig();

        FileLoadPresenter fileLoadPresenter = new FileLoadPresenter(this);
        fileLoadPresenter.setChartsFromConfig(defaultInputFilename);

    }

    void setMainConfig(MainConfig mainConfig){
        this.mainConfig = mainConfig;
    }

    public StatisticsViewUI getMainView(){
        return mainView;
    }

    public void clear(){
        mainView.clearTabSheet();
    }

    boolean isAdmin(){

        try {
            for (com.liferay.portal.model.Role r : LiferayAndVaadinUtils.getUser().getRoles()) {
                if (r.getName().equals("Administrator")) {
                    return true;
                }
            }
        }catch(Exception e){
            logger.error("Could not determine whether logged in user is admin.");
            CustomNotification.error("Error", e.toString());
        }

        return false;
    }

    public MainConfig getMainConfig() {
        return mainConfig;
    }

    void addChildPresenter(){
        superKingdomCountPresenter = new SuperKingdomCountPresenter(this);
    }

    void addCharts(){

        clear();
        //Careful: Order matters! Determines in which order tabs are displayed.
        superKingdomCountPresenter.addChart(new TabView((AView) superKingdomCountPresenter.getView(),
                (AModel) superKingdomCountPresenter.getModel()), "Organisms");

    }

    void addReturnButtons(){
        addReturnButtonListener(superKingdomCountPresenter.getTabView());
    }





    private void addOrganismCountPie(){

//        //Get needed configs
//        Map<String, ChartConfig> speciesCharts = new HashMap<>();
//        Map<String, ChartConfig> genusCharts = new HashMap<>();
//
//        for(String chartName : ChartNames.getList()){
//            if(Kingdoms.getList().contains(chartName.split("_")[0])){
//                if(chartName.split("_")[1].equals("Species")) {
//                    speciesCharts.put(chartName, mainConfig.getCharts().get(chartName));
//                }else if (chartName.split("_")[1].equals("Genus")){
//                    genusCharts.put(chartName, mainConfig.getCharts().get(chartName));
//                }
//            }
//        }
//
//        //Create Presenter
//        SuperKingdomCountPresenter organismCountPiePresenter =
//                new SuperKingdomCountPresenter(this.mainView,
//                        mainConfig.getCharts().get(ChartNames.SuperKingdom.toString()),
//                        genusCharts,
//                        speciesCharts,
//                        mainConfig.getCharts().get(ChartNames.Species_Genus.toString()));
//
//
//        organismCountPiePresenter.addCharts( new TabView(organismCountPiePresenter.getView(),
//                        organismCountPiePresenter.getModel()),
//                "Organisms");
//        addReturnButtonListener(organismCountPiePresenter.getTabView());

        logger.info("Organism tab was added");


    }

//    private void addWorkflowGrid(){
//
//        Map<String, ChartConfig> availWorkflows = new HashMap<>();
//        mainConfig.getCharts().keySet().forEach(key ->{
//            if(key.startsWith(ChartNames.Available_Workflows_.toString())){
//                availWorkflows.put(key, mainConfig.getCharts().get(key));
//            }
//        });
//        WorkflowUsagePresenter workflowUsagePresenter = new WorkflowUsagePresenter(mainConfig.getCharts()
//                .get(ChartNames.Workflow_Execution_Counts.toString()),
//                mainView,
//                availWorkflows);
//        workflowUsagePresenter.addCharts(new TabView(workflowUsagePresenter.getView(), workflowUsagePresenter.getModel()),
//                "Workflows");
//
//        addReturnButtonListener(workflowUsagePresenter.getTabView());
//
//        logger.info("Workflow tab was added");
//
//    }
//
//    private void addSampleCountPie(){
//
//        //Create Presenter
//        SampleTypePresenter sampleTypePresenter =
//                new SampleTypePresenter(this.mainView,
//                        mainConfig.getCharts().get(ChartNames.Sample_Types.toString()));
//
//
//        sampleTypePresenter.addCharts( new TabView(sampleTypePresenter.getView(),
//                        sampleTypePresenter.getModel()),
//                "Sample Types");
//
//        logger.info("Sample tab was added");
//
//        //Create Presenter
//        SampleTypeBarPresenter sampleTypeBarPresenter =
//                new SampleTypeBarPresenter(this.mainView,
//                        mainConfig.getCharts().get(ChartNames.Sample_Types.toString()));
//
//
//        sampleTypeBarPresenter.addCharts( new TabView(sampleTypeBarPresenter.getView(),
//                        sampleTypeBarPresenter.getModel()),
//                "Sample Types");
//
//        logger.info("Sample tab was added");
//
//
//    }
//
//    private void addProjectCountsPie(){
//
//        //Create Presenter
//        ProjectTechnologiesPresenter projectTechnologiesPresenter =
//                new ProjectTechnologiesPresenter(this.mainView,
//                        mainConfig.getCharts().get(ChartNames.Projects_Technology.toString()));
//
//
//        projectTechnologiesPresenter.addCharts( new TabView(projectTechnologiesPresenter.getView(),
//                        projectTechnologiesPresenter.getModel()),
//                "Projects");
//
//        logger.info("Projects tab was added");
//
//        //Create Presenter
//        ProjectTechColumnPresenter projectTechColumnPresenter =
//                new ProjectTechColumnPresenter(this.mainView,
//                        mainConfig.getCharts().get(ChartNames.Projects_Technology.toString()));
//
//
//        projectTechColumnPresenter.addCharts( new TabView(projectTechColumnPresenter.getView(),
//                        projectTechColumnPresenter.getModel()),
//                "Projects");
//
//        logger.info("Projects tab was added");
//
//
//    }

    private void addReturnButtonListener(TabView tabView){
        tabView.getReturnButton().addClickListener(clickEvent -> {
            logger.info("Return button was pressed");
            tabView.addMainComponent();

        });
    }

}