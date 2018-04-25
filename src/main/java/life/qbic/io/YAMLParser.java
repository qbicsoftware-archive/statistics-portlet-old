package life.qbic.io;

import life.qbic.logging.Log4j2Logger;
import life.qbic.logging.Logger;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import submodule.data.MainConfig;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author fhanssen
 * Read given config file. DON'T change the DumperOptions, unless you are absolutely sure of what you are doing.
 * This has to be synced with the Writer in the data portlet.
 */
public final class YAMLParser {

    private final static Logger logger = new Log4j2Logger(YAMLParser.class);


    public static MainConfig parseConfig(String inputFile) throws IOException{

        DumperOptions options = new DumperOptions();

        //this option needs to agree with the respective option set in the writer
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setPrettyFlow(true);
        Yaml yaml = new Yaml(options);

        return yaml.loadAs(new FileInputStream(inputFile), MainConfig.class);
    }

    public static MainConfig parseConfig(FileInputStream inputStream){

        DumperOptions options = new DumperOptions();

        //this option needs to agree with the respective option set in the writer
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setPrettyFlow(true);
        Yaml yaml = new Yaml(options);

        return yaml.loadAs(inputStream, MainConfig.class);
    }
}
