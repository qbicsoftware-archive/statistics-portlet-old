package life.qbic.presenter.charts;

import com.vaadin.addon.charts.model.*;
import com.vaadin.addon.charts.model.style.SolidColor;
import life.qbic.model.charts.PieChartModel;
import life.qbic.model.data.ChartConfig;
import life.qbic.presenter.utils.Helper;
import life.qbic.view.charts.PieChartView;

import java.util.*;

public class GenusSpeciesCountDonutPieChartPresenter extends AChartPresenter<PieChartModel, PieChartView>{

    private final ChartConfig speciesConfig;
    private final Map<String, List<String>> genusSpeciesMap;

    public GenusSpeciesCountDonutPieChartPresenter(ChartConfig genusConfig, ChartConfig speciesConfig, ChartConfig speciesGenusMap) {
        super(genusConfig, new PieChartView());
        this.speciesConfig = speciesConfig;
        this.genusSpeciesMap = generateGenusSpeciesMap(speciesGenusMap);

        addChartSettings();
        addChartData();
        addChartListener();

    }


    private Map<String, List<String>> generateGenusSpeciesMap(ChartConfig speciesGenusConfig){
        Map<String, List<String>> result = new HashMap<>();

        Object[] objectArray = speciesGenusConfig.getData().keySet().toArray(new Object[speciesGenusConfig.getData().keySet().size()]);
        String[] keySet = Arrays.asList(objectArray).toArray(new String[objectArray.length]);

        for (String aKeySet : keySet) {
            for (int i = 0; i < speciesGenusConfig.getData().get(aKeySet).size(); i++) {
                List<String> list = new ArrayList<>();
                if(result.containsKey(speciesGenusConfig.getData().get(aKeySet).get(i))) {
                    list = result.get(speciesGenusConfig.getData().get(aKeySet).get(i));
                }
                list.add((String) speciesGenusConfig.getSettings().getxCategories().get(i));
                result.put((String)speciesGenusConfig.getData().get(aKeySet).get(i), list);
            }
        }

        return result;
    }

    @Override
    void addChartSettings() {
        PlotOptionsPie plot = new PlotOptionsPie();
        plot.setShadow(false);

        Tooltip tooltip = new Tooltip();
        tooltip.setValueSuffix("");

        this.view.getConfiguration().addyAxis(new YAxis());
        this.model = new PieChartModel(this.view.getConfiguration(), chartConfig.getSettings().getTitle(),
                null, tooltip, null, plot);
    }

    //TODO clean up
    @Override
    void addChartData() {

        DataSeries innerSeries = new DataSeries();
        innerSeries.setName("Samples");
        PlotOptionsPie innerPieOptions = new PlotOptionsPie();

        innerSeries.setPlotOptions(innerPieOptions);

        innerPieOptions.setSize("237px");
        innerPieOptions.setDataLabels(new DataLabels());
        innerPieOptions.getDataLabels().setFormatter("this.percentage >  10 ? this.point.name : null");
        innerPieOptions.getDataLabels().setColor(new SolidColor(255,255,255));
        innerPieOptions.getDataLabels().setDistance(-30);


        Object[] objectArray = chartConfig.getData().keySet().toArray(new Object[chartConfig.getData().keySet().size()]);
        String[] keySet = Arrays.asList(objectArray).toArray(new String[objectArray.length]);


        Number[] innerValues = new Number[chartConfig.getSettings().getxCategories().size()];
        String[] innerNames = new String[chartConfig.getSettings().getxCategories().size()];
        for (String aKeySet : keySet) {
            for (int i = 0; i < chartConfig.getData().get(aKeySet).size(); i++) {
                innerNames[i] = (String) chartConfig.getSettings().getxCategories().get(i);
                innerValues[i] = (Number) chartConfig.getData().get(aKeySet).get(i);
            }
        }

        innerSeries.setData(innerNames, innerValues, Arrays.copyOf(Helper.colors, chartConfig.getSettings().getxCategories().size()));

        DataSeries outerSeries = new DataSeries();
        outerSeries.setName("Samples");

        PlotOptionsPie outerSeriesOptions = new PlotOptionsPie();
        outerSeries.setPlotOptions(outerSeriesOptions);

        outerSeriesOptions.setInnerSize("237px");
        outerSeriesOptions.setSize("318px");

        outerSeriesOptions.setDataLabels(new DataLabels());
        outerSeriesOptions
                .getDataLabels()
                .setFormatter(
                        "this.point.name");

        //This is necessary to get from Object to required String arrays
        Object[] objectArray1 = speciesConfig.getData().keySet().toArray(new Object[speciesConfig.getData().keySet().size()]);
        String[] keySet1 = Arrays.asList(objectArray).toArray(new String[objectArray1.length]);

        DataSeriesItem[] outerItems = new DataSeriesItem[0];
        //Actually adding of data
        for (String aKeySet : keySet1) {
            outerItems = new DataSeriesItem[speciesConfig.getData().get(aKeySet).size()];
            int counter = 0;
            for (int i = 0; i < innerNames.length; i++) {
                List<String> species = genusSpeciesMap.get(innerNames[i]);

                for(String s : species) {
                    Number value = 0;
                    for(int j = 0; j < speciesConfig.getSettings().getxCategories().size(); j++){
                        if(speciesConfig.getSettings().getxCategories().get(j).equals(s)){
                            value = (Number) speciesConfig.getData().get(aKeySet).get(j);
                            break;
                        }
                    }
                    outerItems[counter] = new DataSeriesItem(s, value, Helper.getRandomOphaque(Helper.colors[i % Helper.colors.length]));
                    counter++;
                }
            }
        }

        outerSeries.setData(Arrays.asList(outerItems));
        model.addDonatPieData(innerSeries, outerSeries);
    }

    @Override
    void addChartListener(){

    }
}
