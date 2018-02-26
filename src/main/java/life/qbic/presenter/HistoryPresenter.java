package life.qbic.presenter;

import com.vaadin.addon.charts.model.*;
import com.vaadin.addon.charts.model.style.SolidColor;
import life.qbic.model.BasicBarModel;
import life.qbic.view.BasicBarView;

public class HistoryPresenter implements ChartPresenter<BasicBarModel, BasicBarView> {

    private final BasicBarView barView;
    private final AxisTitle yAxisTitle;
    private final Tooltip tooltip;
    private final Legend legend;
    private final BasicBarModel model;


    public HistoryPresenter(String description){
        barView = new BasicBarView();

        yAxisTitle = new AxisTitle("Population (millions)");
        yAxisTitle.setAlign(VerticalAlign.MIDDLE);

        tooltip = new Tooltip();
        tooltip.setFormatter("this.series.name +': '+ this.y +' millions'");

        legend = new Legend();
        legend.setLayout(LayoutDirection.VERTICAL);
        legend.setAlign(HorizontalAlign.RIGHT);
        legend.setVerticalAlign(VerticalAlign.TOP);
        legend.setX(-100);
        legend.setY(100);
        legend.setFloating(true);
        legend.setBorderWidth(1);
        legend.setBackgroundColor(new SolidColor("#FFFFFF"));
        legend.setShadow(true);

        PlotOptionsBar plot = new PlotOptionsBar();
        plot.setDataLabels(new DataLabels(true));

        model = new BasicBarModel(description, barView.getConfiguration(), "Historic World Population by Region",
                "Source: Wikipedia.org", null, yAxisTitle, tooltip, legend, plot);

        model.addXCategorie("Africa", "America", "Asia", "Europe", "Oceania");
        model.setyMin(0);
        model.addData(new ListSeries("Year 1800", 107, 31, 635, 203, 2),
                new ListSeries("Year 1900", 133, 156, 947, 408, 6),
                new ListSeries("Year 2008", 973, 914, 4054, 732, 34));


    }

    public BasicBarModel getModel() {
        return model;
    }

    public BasicBarView getView() {
        return barView;
    }
}
