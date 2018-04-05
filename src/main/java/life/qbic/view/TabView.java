package life.qbic.view;

import com.vaadin.addon.charts.Chart;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;
import life.qbic.logging.Log4j2Logger;
import life.qbic.logging.Logger;
import life.qbic.model.AModel;
import life.qbic.model.charts.AChartModel;
import life.qbic.view.charts.AChartView;

/**
 * @author fhanssen
 */
public class TabView {

    private static Logger logger = new Log4j2Logger(TabView.class);


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

    public void addMainChart(){
        charts.removeAllComponents();
        logger.info("Charts were removed from tab.");

        ((AChartView)mainView).draw((AChartModel) mainModel);
        charts.addComponent(((AChartView)mainView).getChart());
        logger.info("Main Chart was added.");
    }

    public void addGrid(){
        charts.removeAllComponents();
        charts.addComponent(((GridView)mainView).getGridLayout());
        charts.setComponentAlignment(((GridView)mainView).getGridLayout(), Alignment.MIDDLE_CENTER);

    }

    public void removeChart(Chart chart){
        charts.removeComponent(chart);
        logger.info("Chart was removed from tab.");

    }

    public void addSubChart(AModel model, AView view){
        charts.removeAllComponents();
        logger.info("Charts were removed from tab.");
        ((AChartView)mainView).draw((AChartModel) model);
        charts.addComponents(((AChartView)view).getChart(), returnButton);
        charts.setComponentAlignment(returnButton, Alignment.TOP_RIGHT);
        logger.info("Subchart and return button was added to tab.");

    }
}
