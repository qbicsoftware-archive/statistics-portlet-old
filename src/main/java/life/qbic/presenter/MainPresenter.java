package life.qbic.presenter;

import life.qbic.logging.Log4j2Logger;
import life.qbic.logging.Logger;
import life.qbic.presenter.charts.SuperKingdomCountPresenter;
import life.qbic.presenter.charts.WorkflowPresenter;
import life.qbic.io.YAMLParser;
import life.qbic.presenter.utils.lexica.ChartNames;
import life.qbic.presenter.utils.lexica.SuperKingdoms;
import life.qbic.view.MainView;
import life.qbic.view.TabView;
import submodule.data.ChartConfig;
import submodule.data.MainConfig;

import java.util.HashMap;
import java.util.Map;


/**
 * @author fhanssen
 * Handles input config and adds charts serially. Handles button presses and listens to observabeLists in SubPresenters
 */
public class MainPresenter {

    private static final Logger logger = new Log4j2Logger(MainPresenter.class);


    private final MainView mainView;
    private final MainConfig mainConfig;

    public MainPresenter(MainView mainView) {
        this.mainView = mainView;
        this.mainConfig = YAMLParser.parseConfig("/Users/qbic/Documents/QBiC/config.yaml");

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

        WorkflowPresenter workflowPresenter = new WorkflowPresenter(mainConfig.getCharts().get(ChartNames.Workflow.toString()), mainView);
        workflowPresenter.specifyView(new TabView(workflowPresenter.getView(), workflowPresenter.getModel()),
                                    "Workflows");

        logger.info("Workflow tab was added");

    }

    private void addReturnButtonListener(TabView tabView){
        tabView.getReturnButton().addClickListener(clickEvent -> {
            logger.info("Return button was pressed");
            tabView.addMainComponent();

        });
    }

}
