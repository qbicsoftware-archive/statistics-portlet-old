package life.qbic.view;

import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import life.qbic.StatisticsViewUI;
import life.qbic.logging.Log4j2Logger;
import life.qbic.logging.Logger;
import life.qbic.model.BasicBarModel;

public class MainView {

    //Presenter shoudl then be created for each individual chart to allow customization
    private static Logger logger = new Log4j2Logger(StatisticsViewUI.class);

    private final VerticalLayout layout;
    private final TabSheet tabSheet;

    public MainView(StatisticsViewUI statisticsViewUI){
        layout = new VerticalLayout();
        layout.setMargin(true);
        statisticsViewUI.setContent(layout);

        this.tabSheet = new TabSheet();
        this.layout.addComponent(tabSheet);

    }

    public void addBarPlot(BasicBarView barView, BasicBarModel model){
        VerticalLayout barTab = new VerticalLayout();
        barView.draw(model);
        barTab.addComponent(barView.getChart());
        this.tabSheet.addTab(barTab).setCaption(model.getDescription());
    }

}
