package life.qbic.presenter;

import com.vaadin.ui.Label;
import com.vaadin.ui.Upload;
import life.qbic.io.YAMLParser;
import life.qbic.logging.Log4j2Logger;
import life.qbic.portal.liferayandvaadinhelpers.main.LiferayAndVaadinUtils;
import life.qbic.presenter.tabs.DummyChartPresenter;
import life.qbic.presenter.utils.CustomNotification;
import life.qbic.io.MyReceiver;
import life.qbic.view.TabView;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author fhanssen
 */

public class FileLoadPresenter {

    private static final Log4j2Logger logger = new Log4j2Logger(FileLoadPresenter.class);

    private final MainPresenter mainPresenter;
    private final MyReceiver receiver = new MyReceiver();
    private final Upload upload = new Upload("Upload file", receiver);
    private final DummyChartPresenter dummyChartPresenter;

    public FileLoadPresenter(MainPresenter mainPresenter){

        this.mainPresenter = mainPresenter;
        this.upload.setImmediate(true);
        this.dummyChartPresenter = new DummyChartPresenter(mainPresenter);


    }

    private void addUploadConfigFileOption(TabView tabView){

        //tabView.addComponents(upload, new Label("Please select a file to upload"));
        upload.addSucceededListener((Upload.SucceededListener) succeededEvent ->
                setChartsFromConfig(receiver.getFile(), receiver.getFilename()));
    }

    private void showDummyChart(){
        dummyChartPresenter.addChart(new TabView(dummyChartPresenter.getView(),
                                                    dummyChartPresenter.getModel()), "Dummy Chart");
    }

    void setChartsFromConfig(String filename){
        try {
            this.mainPresenter.setMainConfig(YAMLParser.parseConfig(filename));
            logger.info("Finished parsing");
            mainPresenter.addChildPresenter();
            mainPresenter.addCharts();
        }catch(Exception e){
            handleConfigParseError(e);
        }
    }

    private void setChartsFromConfig(File file, String inputFilename){

        try {
            this.mainPresenter.setMainConfig(YAMLParser.parseConfig(new FileInputStream(file), inputFilename));
            logger.info("Finished parsing.");
            mainPresenter.addChildPresenter();
            mainPresenter.addCharts();
        }catch(Exception e){
            handleConfigParseError(e);
        }
    }

    private void handleConfigParseError(Exception e){

        logger.error("Parsing of YAML file failed: " + e);
        CustomNotification.error("Error", e.toString());

        mainPresenter.clear();
        showDummyChart();

        if(!LiferayAndVaadinUtils.isLiferayPortlet() || mainPresenter.isAdmin()){
            addUploadConfigFileOption(dummyChartPresenter.getTabView());
        }
    }


}
