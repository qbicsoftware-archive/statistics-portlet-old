package life.qbic.presenter.tabs.organisms;


import com.vaadin.addon.charts.model.*;
import com.vaadin.addon.charts.model.style.SolidColor;
import life.qbic.model.view.charts.PieChartModel;
import life.qbic.presenter.MainPresenter;
import life.qbic.presenter.tabs.ATabPresenter;
import life.qbic.presenter.utils.Colors;
import life.qbic.presenter.utils.LabelFormatter;
import life.qbic.view.TabView;
import life.qbic.view.tabs.charts.PieView;
import submodule.data.ChartConfig;
import submodule.lexica.ChartNames;

import java.util.*;

/**
 * @author fhanssen
 */
public class GenusSpeciesCountPresenter extends ATabPresenter<PieChartModel, PieView> {

    private ChartConfig speciesConfig;
    private Map<String, List<String>> genusSpeciesMap;
    private ChartConfig genusConfig;
    private PlotOptionsPie innerPieOptions;
    private PlotOptionsPie outerPieOptions;
    private final String kingdom;

    public GenusSpeciesCountPresenter(MainPresenter mainPresenter,
                                      String kingdom) {

        super(mainPresenter, new PieView());

        this.kingdom = kingdom;
        extractData();
        addChartSettings();
        addChartData();
    }

    @Override
    public void extractData(){
        speciesConfig = super.getMainPresenter().getMainConfig().getCharts().get(kingdom.concat("_Species"));
        genusConfig = super.getMainPresenter().getMainConfig().getCharts().get(kingdom.concat("_Genus"));
        genusSpeciesMap = generateGenusToSpeciesMap(super.getMainPresenter().getMainConfig().getCharts().get(ChartNames.Species_Genus.toString()));
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
    public void addChartSettings() {
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
        outerPieOptions.getDataLabels().setFormatter("function() {var text = ''; for (i = 0; i < 2; i++) {" +
                " text += ' ' + this.point.name.split(' ')[i] } for (i = 2; i < this.point.name.split(' ').length; i++) {"+
                " text += ' ' + this.point.name.split(' ')[i].italics() + ' ' }return text;}");

        Tooltip tooltip = new Tooltip();
        tooltip.setValueSuffix("");

        this.getView().getConfiguration().addyAxis(new YAxis());
        this.setModel(new PieChartModel(this.getView().getConfiguration(), genusConfig.getSettings().getTitle(),
                null, tooltip, null, new PlotOptionsPie()));

        logger.info("Settings were added to a chart of "+ this.getClass() + " with chart titel: " + this.getView().getConfiguration().getTitle().getText());
    }

    @Override
    public void addChartData() {


        //Add data for Genus
        DataSeries innerSeries = new DataSeries("Samples");
        innerSeries.setPlotOptions(innerPieOptions);

        Object[] genusData =  this.genusConfig.getData().keySet().toArray(new Object[ this.genusConfig.getData().keySet().size()]);
        Number[] innerValues = new Number[ this.genusConfig.getSettings().getxCategories().size()];
        String[] innerNames = new String[ this.genusConfig.getSettings().getxCategories().size()];

        for (Object dataCategory : genusData) {
            for (int i = 0; i <  this.genusConfig.getData().get(dataCategory).size(); i++) {
                innerNames[i] = LabelFormatter.firstUpperRestLowerCase((String)  this.genusConfig.getSettings().getxCategories().get(i));
                innerValues[i] = (Number)  this.genusConfig.getData().get(dataCategory).get(i);
            }
        }

        innerSeries.setData(innerNames, innerValues, Arrays.copyOf(Colors.getSolidColors(),  this.genusConfig.getSettings().getxCategories().size()));

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
                    outerItems[counter] = new DataSeriesItem(LabelFormatter.firstUpperRestLowerCase(s), getSpeciesCount(s, (String)dataCategory), Colors.getRandomOphaque(Colors.getSolidColors()[i % Colors.getSolidColors().length]));
                    counter++;
                }
            }
        }

        outerSeries.setData(Arrays.asList(outerItems));

        this.getModel().addDonatPieData(innerSeries, outerSeries);

        logger.info("Data was added to a chart of " + this.getClass() +" with chart titel: " + this.getView().getConfiguration().getTitle().getText());

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
    public void addChart(TabView tabView, String title){

        //Add to existing tab
        tabView.addSubComponent(this.getModel(), this.getView());

        logger.info("View was added in " + this.getClass() + " for " +  this.getView().getConfiguration().getTitle().getText() );
    }
}