package life.qbic.view.tabs;

import com.vaadin.ui.Component;

/**
 * @author fhanssen
 * This abstract class holds the chart. When
 * adding a new chart type (such as Scatter Plot or so) a new chart view needs be added. A comprehensive
 * guide on Vaadin charts can be found here: https://demo.vaadin.com/charts/
 */
public abstract class AView {

    protected AView(){
    }

    abstract public Component getComponent();
    //TODO 3: If your Chart TYPE does not exist extend this class

}