package life.qbic.view;

import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import life.qbic.main.StatisticsViewUI;
import life.qbic.logging.Log4j2Logger;
import life.qbic.logging.Logger;

/**
 * @author fhanssen
 */
public class MainView {

    private static Logger logger = new Log4j2Logger(MainView.class);

    private final TabSheet tabSheet;

    public MainView(StatisticsViewUI statisticsViewUI){
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        statisticsViewUI.setContent(layout);


        this.tabSheet = new TabSheet();
        layout.addComponent(tabSheet);
    }

    public void addTabView(TabView tabView, String title){

        this.tabSheet.addTab(tabView.getTab()).setCaption(title);
        logger.info("A new tab with titel " + title +" was added.");
    }
}
