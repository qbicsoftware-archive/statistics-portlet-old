package life.qbic.view;

import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import life.qbic.StatisticsViewUI;
import life.qbic.logging.Log4j2Logger;
import life.qbic.logging.Logger;
import life.qbic.model.charts.BasicBarModel;
import life.qbic.model.charts.BasicLineModel;
import life.qbic.view.charts.BasicBarView;
import life.qbic.view.charts.BasicLineView;

public class MainView {

    //Presenter should then be created for each individual chart to allow customization
    private static Logger logger = new Log4j2Logger(StatisticsViewUI.class);

    private final TabSheet tabSheet;

    public MainView(StatisticsViewUI statisticsViewUI){
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        statisticsViewUI.setContent(layout);

        this.tabSheet = new TabSheet();
        layout.addComponent(tabSheet);

    }

    public void addBarPlot(BasicBarView barView, BasicBarModel model, String title){
        VerticalLayout barTab = new VerticalLayout();
        barView.draw(model);
        barTab.addComponent(barView.getChart());
        this.tabSheet.addTab(barTab).setCaption(title);
    }

    public void addTimelinePlot(BasicLineView timelineView, BasicLineModel model, String title){
        VerticalLayout barTab = new VerticalLayout();
        timelineView.draw(model);
        barTab.addComponent(timelineView.getChart());
        this.tabSheet.addTab(barTab).setCaption(title);
    }

}
