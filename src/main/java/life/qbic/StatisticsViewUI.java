package life.qbic;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import life.qbic.logging.Log4j2Logger;
import life.qbic.logging.Logger;
import life.qbic.presenter.MainPresenter;
import life.qbic.view.MainView;

@Theme("mytheme")
@SuppressWarnings("serial")
@Widgetset("life.qbic.AppWidgetSet")
public class StatisticsViewUI extends UI {

    private static Logger logger = new Log4j2Logger(StatisticsViewUI.class);


    @Override
    protected void init(VaadinRequest request) {
        MainView mainView = new MainView(this);
        MainPresenter mainPresenter = new MainPresenter(mainView);
    }


}
