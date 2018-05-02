package life.qbic.presenter;



import life.qbic.logging.Log4j2Logger;
import life.qbic.logging.Logger;
import life.qbic.model.view.AModel;
import life.qbic.portal.liferayandvaadinhelpers.main.LiferayAndVaadinUtils;
import life.qbic.portlet.StatisticsViewUI;
import life.qbic.presenter.tabs.ATabPresenter;
import life.qbic.presenter.tabs.organisms.SuperKingdomCountPresenter;
import life.qbic.presenter.tabs.projects.ProjectTechColumnPresenter;
import life.qbic.presenter.tabs.samples.SampleTypeBarPresenter;
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
    private ATabPresenter sampleTypePresenter;
    private ATabPresenter projectPresenter;

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
        sampleTypePresenter = new SampleTypeBarPresenter(this);
        projectPresenter = new ProjectTechColumnPresenter(this);
    }

    void addCharts(){

        clear();
        //Careful: Order matters! Determines in which order tabs are displayed.
        superKingdomCountPresenter.addChart(new TabView((AView) superKingdomCountPresenter.getView(),
                (AModel) superKingdomCountPresenter.getModel()), "Organisms");
        sampleTypePresenter.addChart(new TabView((AView) sampleTypePresenter.getView(),
                (AModel) sampleTypePresenter.getModel()), "Samples");
        projectPresenter.addChart(new TabView((AView) projectPresenter.getView(),
                (AModel) projectPresenter.getModel()), "Projects");

    }

    void addReturnButtons(){
        addReturnButtonListener(superKingdomCountPresenter.getTabView());
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

    private void addReturnButtonListener(TabView tabView){
        tabView.getReturnButton().addClickListener(clickEvent -> {
            logger.info("Return button was pressed");
            tabView.addMainComponent();

        });
    }

}