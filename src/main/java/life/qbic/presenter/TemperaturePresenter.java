package life.qbic.presenter;

import com.vaadin.addon.charts.model.*;
import life.qbic.model.BasicTimelineModel;
import life.qbic.view.BasicTimelineView;

public class TemperaturePresenter implements ChartPresenter<BasicTimelineModel, BasicTimelineView> {

    private final BasicTimelineView timelineView;
    private final AxisTitle yAxisTitle;
    private final Tooltip tooltip;
    private final Legend legend;
    private final BasicTimelineModel model;

    public TemperaturePresenter(){
        timelineView = new BasicTimelineView();

        yAxisTitle = new AxisTitle("Temperature (°C)");
        yAxisTitle.setAlign(VerticalAlign.MIDDLE);

        tooltip = new Tooltip();
        tooltip.setFormatter(
                "'<b>'+ this.series.name +'</b><br/>'+this.x +': '+ this.y +'°C'");

        legend = new Legend();
        legend.setLayout(LayoutDirection.VERTICAL);
        legend.setAlign(HorizontalAlign.RIGHT);
        legend.setVerticalAlign(VerticalAlign.TOP);
        legend.setX(-10d);
        legend.setY(100d);
        legend.setBorderWidth(0);

        PlotOptionsLine plotOptions = new PlotOptionsLine();
        plotOptions.getDataLabels().setEnabled(true);

        model = new BasicTimelineModel(timelineView.getConfiguration(), "Monthly Average Temperature", "Source: WorldClimate.com",
                null, yAxisTitle, tooltip, legend, plotOptions);

        //configuration.getChart().setMarginRight(130);
        //configuration.getChart().setMarginBottom(25);

        model.addXCategorie("Jan", "Feb", "Mar", "Apr",
                "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");

        model.setyMin(-5);


        ListSeries ls = new ListSeries();
        ls.setName("Tokyo");
        ls.setData(7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3,
                13.9, 9.6);
        model.addData(ls);
        ls = new ListSeries();
        ls.setName("New York");
        ls.setData(-0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1,
                8.6, 2.5);
        model.addData(ls);
        ls = new ListSeries();
        ls.setName("Berlin");
        ls.setData(-0.9, 0.6, 3.5, 8.4, 13.5, 17.0, 18.6, 17.9, 14.3, 9.0, 3.9,
                1.0);
        model.addData(ls);
        ls = new ListSeries();
        ls.setName("London");
        ls.setData(3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6,
                4.8);
        model.addData(ls);
    }

    @Override
    public BasicTimelineModel getModel() {
        return model;
    }

    @Override
    public BasicTimelineView getView() {
        return timelineView;
    }
}
