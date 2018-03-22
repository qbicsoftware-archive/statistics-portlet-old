package life.qbic.view;

import com.vaadin.addon.charts.Chart;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;
import life.qbic.model.charts.AModel;
import life.qbic.view.charts.AView;

/**
 * @author fhanssen
 */
public class TabView {

    private final VerticalLayout charts = new VerticalLayout();
    private final Button returnButton = new Button("Return");
    private final AView mainView;
    private final AModel mainModel;
    private final VerticalLayout barTab = new VerticalLayout();


    public TabView(AView view, AModel model){
        this.mainView = view;
        this.mainModel = model;

        addMainChart();
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
        mainView.draw(mainModel);
        charts.addComponent(mainView.getChart());
    }

    public void removeChart(Chart chart){
        charts.removeComponent(chart);
    }

    public void addSubChart(AModel model, AView view){
        charts.removeAllComponents();
        view.draw(model);
        charts.addComponents(view.getChart(), returnButton);
        charts.setComponentAlignment(returnButton, Alignment.TOP_RIGHT);
    }
}
