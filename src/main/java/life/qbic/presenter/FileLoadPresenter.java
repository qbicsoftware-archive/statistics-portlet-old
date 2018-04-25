package life.qbic.presenter;

import com.vaadin.ui.Label;
import com.vaadin.ui.Upload;
import life.qbic.io.YAMLParser;
import life.qbic.portal.liferayandvaadinhelpers.main.LiferayAndVaadinUtils;
import life.qbic.presenter.tabs.DummyChartPresenter;
import life.qbic.presenter.utils.CustomNotification;
import life.qbic.io.MyReceiver;
import life.qbic.view.TabView;
import submodule.data.MainConfig;

import java.io.IOException;

public class FileLoadPresenter {

    private final MainPresenter mainPresenter;

    public FileLoadPresenter(MainPresenter mainPresenter){
        this.mainPresenter = mainPresenter;

        try {
            setConfig(mainPresenter.getInputfilename());
            mainPresenter.addCharts();
        }catch(Exception e){
            configException(e);
        }
    }

    private void addUploadConfigFileOption(TabView tabView){

        MyReceiver receiver = new MyReceiver();
        Upload upload = new Upload(null, receiver);
        upload.setImmediate(true);
        upload.setButtonCaption("Select file");

        upload.addSucceededListener(receiver);

        upload.addFinishedListener(new Upload.FinishedListener() {
            @Override
            public void uploadFinished(Upload.FinishedEvent finishedEvent) {
                try {
                    setConfig(receiver.getMainConfig());
                    mainPresenter.addCharts();
                }catch(Exception e){
                   configException(e);
                }
            }
        });

        Label status = new Label("Please select a file to upload");
        tabView.getTab().addComponents(upload, status);

    }

    private void showDummyChart(){
        DummyChartPresenter dummyChartPresenter = new DummyChartPresenter(mainPresenter.getMainView());
        dummyChartPresenter.specifyView(new TabView(dummyChartPresenter.getView(), dummyChartPresenter.getModel()), "Dummy Chart");
        if(!LiferayAndVaadinUtils.isLiferayPortlet() || mainPresenter.isAdmin()){
            addUploadConfigFileOption(dummyChartPresenter.getTabView());
        }
    }

    public void setConfig(String filename) throws IOException {
       this.mainPresenter.setMainConfig(YAMLParser.parseConfig(filename));
    }

    public void setConfig(MainConfig mainConfig){
        this.mainPresenter.setMainConfig(mainConfig);
    }

    public void configException(Exception e){
        //logger.error("Parsing of YAML file failed. " + e);
        CustomNotification.error("Error",
                e.toString());
        showDummyChart();
        mainPresenter.setMainConfig(new MainConfig());
    }
}
