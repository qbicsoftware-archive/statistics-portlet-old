package life.qbic.view.charts;

import com.vaadin.addon.charts.model.ChartType;
import life.qbic.view.AView;

/**
 * @author fhanssen
 */
public class BarView extends AChartView {

    public BarView(){
        super(ChartType.BAR);
    }
}
