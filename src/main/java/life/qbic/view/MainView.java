package life.qbic.view;

import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import life.qbic.StatisticsViewUI;
import life.qbic.logging.Log4j2Logger;
import life.qbic.logging.Logger;
import life.qbic.model.charts.ColumnModel;
import life.qbic.model.charts.PieChartModel;
import life.qbic.view.charts.ColumnView;
import life.qbic.view.charts.PieChartView;

public class MainView {

    private static Logger logger = new Log4j2Logger(StatisticsViewUI.class);

    private final TabSheet tabSheet;

    public MainView(StatisticsViewUI statisticsViewUI){
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        statisticsViewUI.setContent(layout);

        this.tabSheet = new TabSheet();
        layout.addComponent(tabSheet);

    }

//    public void addBarPlot(BasicBarView barView, BasicBarModel model, String title){
//        VerticalLayout barTab = new VerticalLayout();
//        barView.draw(model);
//        barTab.addComponent(barView.getChart());
//        this.tabSheet.addTab(barTab).setCaption(title);
//    }
//
//    public void addTimelinePlot(BasicLineView timelineView, BasicLineModel model, String title){
//        VerticalLayout barTab = new VerticalLayout();
//        timelineView.draw(model);
//        barTab.addComponent(timelineView.getChart());
//        this.tabSheet.addTab(barTab).setCaption(title);
//    }

    public void addColumnPlot(ColumnView columnView, ColumnModel columnModel, String title){
        VerticalLayout barTab = new VerticalLayout();
        columnView.draw(columnModel);
        barTab.addComponent(columnView.getChart());
        this.tabSheet.addTab(barTab).setCaption(title);
    }

    public void addPieChart(PieChartView pieChartView, PieChartModel pieChartModel, String title){
        VerticalLayout barTab = new VerticalLayout();
        pieChartView.draw(pieChartModel);
        barTab.addComponent(pieChartView.getChart());
        this.tabSheet.addTab(barTab).setCaption(title);
    }
}
