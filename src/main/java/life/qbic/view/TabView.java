package life.qbic.view;


import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import life.qbic.logging.Log4j2Logger;
import life.qbic.logging.Logger;
import life.qbic.model.view.AModel;
import life.qbic.model.view.charts.AChartModel;
import life.qbic.view.tabs.charts.AChartView;
import life.qbic.view.tabs.AView;

/**
 * @author fhanssen
 */
public class TabView extends VerticalLayout {

    private static final Logger logger = new Log4j2Logger(TabView.class);

    private final Button returnButton = new Button("Return");
    private final AView mainView;
    private final AModel mainModel;
    private final Label title;

    public TabView(AView view, AModel model){
        this.mainView = view;
        this.mainModel = model;
        this.title = new Label("", ContentMode.HTML);
        title.setStyleName(ValoTheme.LABEL_BOLD);
        title.addStyleName(ValoTheme.LABEL_COLORED);
        title.addStyleName(ValoTheme.LABEL_HUGE);
        title.addStyleName(ValoTheme.LABEL_H1);
        title.setWidth(null);
        setMargin(true);
    }

    public Button getReturnButton() {
        return returnButton;
    }

    public void addMainComponent(){

        removeComponents();

        if(mainView instanceof AChartView) {
            ((AChartView) mainView).draw((AChartModel) mainModel);
        }

        addComponent(title);
        setComponentAlignment(title, Alignment.MIDDLE_CENTER);
        addComponent(mainView.getComponent());
        setComponentAlignment((mainView).getComponent(), Alignment.MIDDLE_CENTER);

        logger.info("Main component was added.");

    }

    private void removeComponents() {
        removeAllComponents();
        setTitle("");
        logger.info("All components were removed from tab.");
    }

    public void addSubComponent(AModel model, AView view){

        removeComponents();

        if(view instanceof AChartView) {
            ((AChartView)view).draw((AChartModel) model);
        }
        addComponents(title, view.getComponent(), returnButton);
        setComponentAlignment(title, Alignment.MIDDLE_CENTER);
        setComponentAlignment(returnButton, Alignment.TOP_RIGHT);

        logger.info("Sub-component and return button was added to tab.");

    }

    public void setTitle(String title) {
        this.title.setValue(title);
    }
}