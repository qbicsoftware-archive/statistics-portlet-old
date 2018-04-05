package life.qbic.presenter.charts;

import life.qbic.model.GridModel;
import life.qbic.model.components.GitHubLabels;
import life.qbic.model.data.ChartConfig;
import life.qbic.view.GridView;

import java.util.Arrays;

public class WorkflowPresenter extends AChartPresenter<GridModel, GridView> {


    public WorkflowPresenter(ChartConfig workflowConfig) {
        super(workflowConfig, new GridView());

        addChartSettings();
        addChartData();
        addChartListener();
    }

    @Override
    void addChartSettings() {
        model = new GridModel("title", "subtitle");
        //logger.info("Settings were added to a chart of WorkflowPresenter with chart titel: " + this.view.getConfiguration().getTitle().getText());
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
                model.addData(new GitHubLabels(title[title.length-2].concat("/").concat(title[title.length-1]),
                        (String) chartConfig.getSettings().getyCategories().get(i),
                        (int) chartConfig.getData().get(aKeySet).get(i),
                        (String) chartConfig.getSettings().getxCategories().get(i)));
            }
        }

        logger.info("Data was added to a chart of WorkflowPresenter with chart titel: ");

    }

    @Override
    void addChartListener() {

    }
}
