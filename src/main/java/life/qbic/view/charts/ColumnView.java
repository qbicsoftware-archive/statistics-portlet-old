package life.qbic.view.charts;

import com.vaadin.addon.charts.model.ChartType;
import life.qbic.view.AView;

/**
 * @author fhanssen
 */
public class ColumnView extends AChartView {

    public ColumnView(){
        super(ChartType.COLUMN);
    }
}
