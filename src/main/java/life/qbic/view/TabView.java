package life.qbic.view;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;
import life.qbic.logging.Log4j2Logger;
import life.qbic.logging.Logger;
import life.qbic.model.view.AModel;
import life.qbic.model.view.charts.AChartModel;
import life.qbic.view.tabs.charts.AChartView;
import life.qbic.view.tabs.AView;

/**
 * @author fhanssen
 */
public class TabView {

    private static final Logger logger = new Log4j2Logger(TabView.class);


    private final VerticalLayout charts = new VerticalLayout();
    private final Button returnButton = new Button("Return");
    private final AView mainView;
    private final AModel mainModel;
    private final VerticalLayout barTab = new VerticalLayout();


    public TabView(AView view, AModel model){
        this.mainView = view;
        this.mainModel = model;

        barTab.addComponent(charts);
    }

    public VerticalLayout getTab() {
        return barTab;
    }

    public Button getReturnButton() {
        return returnButton;
    }

    public void addMainComponent(){
        charts.removeAllComponents();
        logger.info("All components were removed from tab.");

        if(mainView instanceof AChartView) {
            ((AChartView) mainView).draw((AChartModel) mainModel);
        }

        charts.addComponent(mainView.getComponent());
        charts.setComponentAlignment((mainView).getComponent(), Alignment.MIDDLE_CENTER);

        logger.info("Main component was added.");

    }


    public void addSubComponent(AModel model, AView view){
        charts.removeAllComponents();
        logger.info("Components were removed from tab.");

        if(view instanceof AChartView) {
            ((AChartView)view).draw((AChartModel) model);
        }
        charts.addComponents(view.getComponent(), returnButton);
        charts.setComponentAlignment(returnButton, Alignment.TOP_RIGHT);

        logger.info("Sub-component and return button was added to tab.");

    }
}
