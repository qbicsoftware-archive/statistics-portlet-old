package life.qbic.portlet;


import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import life.qbic.logging.Log4j2Logger;
import life.qbic.logging.Logger;
import life.qbic.portal.liferayandvaadinhelpers.main.LiferayAndVaadinUtils;
import life.qbic.presenter.MainPresenter;
import life.qbic.view.TabView;

@Theme("mytheme")
@SuppressWarnings("serial")
@Widgetset("life.qbic.AppWidgetSet")
public class StatisticsViewUI extends QBiCPortletUI {

    private static final Logger logger = new Log4j2Logger(StatisticsViewUI.class);

    private final TabSheet tabSheet = new TabSheet();
    private final VerticalLayout layout = new VerticalLayout();

    @Override
    protected Layout getPortletContent(final VaadinRequest request) {

        logger.info("Generating content for portlet statistics-portlet");

        if (LiferayAndVaadinUtils.isLiferayPortlet()) {
            logger.info("User: " + LiferayAndVaadinUtils.getUser().getScreenName());
        } else {
           logger.info("You are currently in a local testing mode. No Liferay Portlet context found.");
        }

        layout.setMargin(true);
        setContent(layout);

        layout.addComponent(tabSheet);

        try {
            MainPresenter mainPresenter = new MainPresenter(this,  "/config.yaml" );
          
        }catch(Exception e){
            logger.error("Portlet failed due to: " + e.toString());
        }

        return layout;
    }

    public void addTabView(TabView tabView, String title){
        this.tabSheet.addTab(tabView).setCaption(title);
        logger.info("A new tab with titel " + title +" was added.");
    }

    public VerticalLayout getLayout() {
        return layout;
    }

    public void clearTabSheet(){
        tabSheet.removeAllComponents();
    }
}
