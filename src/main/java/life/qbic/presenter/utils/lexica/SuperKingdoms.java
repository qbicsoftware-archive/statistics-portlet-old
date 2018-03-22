package life.qbic.presenter.utils.lexica;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author fhanssen
 */
public enum SuperKingdoms {
    //as found at: https://www.ncbi.nlm.nih.gov/Taxonomy/Browser/wwwtax.cgi
    Archae("Archae"),
    Bacteria("Bacteria"),
    Eukaryota("Eukaryota"),
    Viroids("Viroids"),
    Viruses("Viruses");

    private static final List<String> enumList = Arrays.asList(Stream.of(SuperKingdoms.values()).map(SuperKingdoms::name).toArray(String[]::new));

    private final String kingdom;

    SuperKingdoms(String kingdom){
        this.kingdom = kingdom;
    }

    @Override
    public String toString(){
       return this.kingdom;
    }

    public static List<String> getList(){
        return enumList;
    }
}
