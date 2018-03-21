package life.qbic.presenter.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public enum SuperKingdoms {
    //as found at: https://www.ncbi.nlm.nih.gov/Taxonomy/Browser/wwwtax.cgi
    Archae,
    Bacteria,
    Eukaryota,
    Viroids,
    Viruses;

    private static List<String> enumList = Arrays.asList(Stream.of(SuperKingdoms.values()).map(SuperKingdoms::name).toArray(String[]::new));

    public static List<String> getList(){
        return enumList;
    }
}
