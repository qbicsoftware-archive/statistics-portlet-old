package life.qbic.presenter.charts;

import com.vaadin.addon.charts.model.*;
import com.vaadin.addon.charts.model.style.SolidColor;
import life.qbic.model.charts.PieChartModel;
import life.qbic.model.data.ChartConfig;
import life.qbic.presenter.utils.Helper;
import life.qbic.view.charts.PieChartView;

import java.util.*;

class GenusSpeciesCountDonutPieChartPresenter extends AChartPresenter<PieChartModel, PieChartView>{

    private final ChartConfig speciesConfig;
    private final Map<String, List<String>> genusSpeciesMap;
    private PlotOptionsPie innerPieOptions;
    private PlotOptionsPie outerPieOptions;

    GenusSpeciesCountDonutPieChartPresenter(ChartConfig genusConfig, ChartConfig speciesConfig, ChartConfig speciesGenusMap) {
        super(genusConfig, new PieChartView());
        this.speciesConfig = speciesConfig;
        this.genusSpeciesMap = generateGenusToSpeciesMap(speciesGenusMap);

        addChartSettings();
        addChartData();
        addChartListener();

    }

    private Map<String, List<String>> generateGenusToSpeciesMap(ChartConfig speciesGenusConfig){
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
        innerPieOptions = new PlotOptionsPie();
        innerPieOptions.setSize("237px");
        innerPieOptions.setDataLabels(new DataLabels());
        innerPieOptions.getDataLabels().setFormatter("this.percentage >  10 ? this.point.name : null");
        innerPieOptions.getDataLabels().setColor(new SolidColor(255,255,255));
        innerPieOptions.getDataLabels().setDistance(-30);

        outerPieOptions = new PlotOptionsPie();
        outerPieOptions.setInnerSize("237px");
        outerPieOptions.setSize("318px");
        outerPieOptions.setDataLabels(new DataLabels());
        outerPieOptions.getDataLabels().setFormatter("this.point.name");

        Tooltip tooltip = new Tooltip();
        tooltip.setValueSuffix("");

        this.view.getConfiguration().addyAxis(new YAxis());
        this.model = new PieChartModel(this.view.getConfiguration(), chartConfig.getSettings().getTitle(),
                null, tooltip, null, new PlotOptionsPie());
    }

    @Override
    void addChartData() {


        //Add data for Genus
        DataSeries innerSeries = new DataSeries("Samples");
        innerSeries.setPlotOptions(innerPieOptions);

        Object[] genusData = chartConfig.getData().keySet().toArray(new Object[chartConfig.getData().keySet().size()]);
        Number[] innerValues = new Number[chartConfig.getSettings().getxCategories().size()];
        String[] innerNames = new String[chartConfig.getSettings().getxCategories().size()];

        for (Object dataCategory : genusData) {
            for (int i = 0; i < chartConfig.getData().get(dataCategory).size(); i++) {
                innerNames[i] = (String) chartConfig.getSettings().getxCategories().get(i);
                innerValues[i] = (Number) chartConfig.getData().get(dataCategory).get(i);
            }
        }

        innerSeries.setData(innerNames, innerValues, Arrays.copyOf(Helper.colors, chartConfig.getSettings().getxCategories().size()));

        //Add data for Species
        DataSeries outerSeries = new DataSeries("Samples");
        outerSeries.setPlotOptions(outerPieOptions);

        Object[] speciesData = speciesConfig.getData().keySet().toArray(new Object[speciesConfig.getData().keySet().size()]);

        DataSeriesItem[] outerItems = new DataSeriesItem[0];
        //Actually adding of data
        for (Object dataCategory : speciesData) {
            outerItems = new DataSeriesItem[speciesConfig.getData().get(dataCategory).size()];
            int counter = 0;
            for (int i = 0; i < innerNames.length; i++) {
                List<String> speciesPerGenus = genusSpeciesMap.get(innerNames[i]);
                for(String s : speciesPerGenus) {
                    outerItems[counter] = new DataSeriesItem(s, getSpeciesCount(s, (String)dataCategory), Helper.getRandomOphaque(Helper.colors[i % Helper.colors.length]));
                    counter++;
                }
            }
        }

        outerSeries.setData(Arrays.asList(outerItems));

        model.addDonatPieData(innerSeries, outerSeries);
    }

    private Number getSpeciesCount(String species, String dataKey){
        Number value = 0;
        for(int j = 0; j < speciesConfig.getSettings().getxCategories().size(); j++){
            if(speciesConfig.getSettings().getxCategories().get(j).equals(species)){
                value = (Number) speciesConfig.getData().get(dataKey).get(j);
                break;
            }
        }
        return value;
    }

    @Override
    void addChartListener(){

    }
}
