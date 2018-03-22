package life.qbic.presenter.utils.lexica;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public enum ChartNames {
    Domain("Domain"),
    Eukaryota_Genus("Eukaryota_Genus"),
    Bacteria_Genus("Bacteria_Genus"),
    Viruses_Genus("Viruses_Genus"),
    Eukaryota_Species("Eukaryota_Species"),
    Bacteria_Species("Bacteria_Species"),
    Viruses_Species("Viruses_Species"),
    Species_Genus("Species_Genus");



    private final String chartName;

    private static final List<String> enumList = Arrays.asList(Stream.of(ChartNames.values()).map(ChartNames::name).toArray(String[]::new));


    ChartNames(String chartName){
        this.chartName = chartName;
    }

    @Override
    public String toString(){
        return this.chartName;
    }

    public static List<String> getList(){
        return enumList;
    }
}
