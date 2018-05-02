package life.qbic.presenter.tabs.samples;

import com.vaadin.addon.charts.model.*;
import com.vaadin.addon.charts.model.style.Color;
import life.qbic.model.view.charts.BarModel;
import life.qbic.portlet.StatisticsViewUI;
import life.qbic.presenter.MainPresenter;
import life.qbic.presenter.tabs.ATabPresenter;
import life.qbic.presenter.utils.Colors;
import life.qbic.presenter.utils.DataSorter;
import life.qbic.presenter.utils.LabelFormatter;
import life.qbic.view.TabView;
import life.qbic.view.tabs.charts.BarView;
import submodule.data.ChartConfig;
import submodule.lexica.ChartNames;
import submodule.lexica.CommonAbbr;
import submodule.lexica.Translator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author fhanssen
 */
public class SampleTypeBarPresenter extends ATabPresenter<BarModel, BarView> {

    private ChartConfig sampleConfig;

    public SampleTypeBarPresenter(MainPresenter mainPresenter){
        super(mainPresenter, new BarView());

        extractData();

        addChartSettings();
        addChartData();
    }

    @Override
    public void extractData(){
        sampleConfig = super.getMainPresenter().getMainConfig().getCharts().get(ChartNames.Sample_Types.toString());
    }

    @Override
    public void addChartSettings() {
        PlotOptionsBar plot = new PlotOptionsBar();

        plot.setDataLabels(new DataLabels(true));

        Tooltip tooltip = new Tooltip();
        tooltip.setFormatter("this.point.name + ': <b>'+ this.y + '</b> Samples'");

        Legend legend = new Legend();
        legend.setEnabled(false);

        super.setModel(new BarModel(super.getView().getConfiguration(), sampleConfig.getSettings().getTitle(),
                null, tooltip, legend, new AxisTitle(sampleConfig.getSettings().getxAxisTitle()),
                new AxisTitle(sampleConfig.getSettings().getyAxisTitle()),
                plot));

        super.getModel().setXAxisType(AxisType.CATEGORY);
        logger.info("Settings were added to a chart of "+ this.getClass() +" with chart titel: " + super.getView().getConfiguration().getTitle().getText());

    }

    @Override
    public void addChartData() {
        //This is necessary to get from Object to required String arrays
        Object[] objectArray = sampleConfig.getData().keySet().toArray(new Object[sampleConfig.getData().keySet().size()]);
        String[] keySet = Arrays.asList(objectArray).toArray(new String[objectArray.length]);

        List<DataSorter> dataSorterList = new ArrayList<>();
        //Actually adding of data
       DataSeries series = new DataSeries();
        for (String aKeySet : keySet) {
            for (int i = 0; i < sampleConfig.getData().get(aKeySet).size(); i++) {

                String label = (String) sampleConfig.getSettings().getxCategories().get(i);

                if(! CommonAbbr.getList().contains(label)){
                    if(!Translator.getList().contains(label)) {
                        if (label.contains("_")) {
                            label = LabelFormatter.firstLowerCaseRestUpperCase(label.replace("_", ""));
                        } else {
                            label = LabelFormatter.generateCamelCase(label);
                        }
                    }else{
                        label = Translator.valueOf(label).getTranslation();
                    }
                }
                dataSorterList.add(new DataSorter(label,
                        (int)sampleConfig.getData().get(aKeySet).get(i)));
            }
        }
        Collections.sort(dataSorterList);

        dataSorterList.forEach(d -> series.add(new DataSeriesItem(d.getName(), d.getCount())));

        super.getModel().addData(series);
        logger.info("Data was added to a chart of " + this.getClass() + " with chart titel: " + super.getView().getConfiguration().getTitle().getText());

    }

    @Override
    public void addChart(TabView tabView, String title) {
        //Set new tab
        super.setTabView(tabView);
        super.getTabView().addMainComponent();
        super.getMainPresenter().getMainView().addTabView(super.getTabView(), title);

        logger.info("Tab was added in " + this.getClass() + " for " +  super.getView().getConfiguration().getTitle().getText() );
    }
}