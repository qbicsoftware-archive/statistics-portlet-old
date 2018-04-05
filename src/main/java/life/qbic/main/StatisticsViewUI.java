package life.qbic.main;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import life.qbic.logging.Log4j2Logger;
import life.qbic.logging.Logger;
import life.qbic.portal.liferayandvaadinhelpers.main.LiferayAndVaadinUtils;
import life.qbic.presenter.MainPresenter;
import life.qbic.view.MainView;

/**
 * @author fhanssen
 * Functions as Main class by creating Vaadin UI.
 */

@Theme("mytheme")
@SuppressWarnings("serial")
@Widgetset("life.qbic.AppWidgetSet")
public class StatisticsViewUI extends UI {

    private static Logger logger = new Log4j2Logger(StatisticsViewUI.class);


    @Override
    protected void init(VaadinRequest request) {

        if(LiferayAndVaadinUtils.isLiferayPortlet()){
            logger.info("User: " + LiferayAndVaadinUtils.getUser().getScreenName());
        }else{
            logger.info("Not in liferay environment");
        }
        MainView mainView = new MainView(this);
        try {
            MainPresenter mainPresenter = new MainPresenter(mainView);
        }catch(Exception e){
            e.printStackTrace();
            logger.error("Portlet failed due to: " + e.getMessage());
        }
    }


}
