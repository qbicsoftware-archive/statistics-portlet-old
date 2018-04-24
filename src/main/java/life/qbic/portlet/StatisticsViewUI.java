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

    private  TabSheet tabSheet;

    @Override
    protected Layout getPortletContent(final VaadinRequest request) {
        // TODO: build the content for your portlet, this is just some sample code
        logger.info("Generating content for portlet statistics-portlet");

        final StringBuilder builder = new StringBuilder();
        if (LiferayAndVaadinUtils.isLiferayPortlet()) {
            logger.info("User: " + LiferayAndVaadinUtils.getUser().getScreenName());
        } else {
            builder.append("You are currently in a local testing mode. No Liferay Portlet context found.<br>");            
        }


        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        setContent(layout);
        this.tabSheet = new TabSheet();
        layout.addComponent(tabSheet);

        try {
            MainPresenter mainPresenter = new MainPresenter(this);
        }catch(Exception e){
            e.printStackTrace();
            logger.error("Portlet failed due to: " + e.getMessage());
        }

        return layout;
    }

    public void addTabView(TabView tabView, String title){

        this.tabSheet.addTab(tabView.getTab()).setCaption(title);
        logger.info("A new tab with titel " + title +" was added.");
    }
}
