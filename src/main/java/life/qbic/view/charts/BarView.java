package life.qbic.view.charts;

import com.vaadin.addon.charts.model.ChartType;
import life.qbic.view.charts.AView;

/**
 * @author fhanssen
 */
public class BarView extends AView {

    public BarView(){
        super(ChartType.BAR);
    }
}