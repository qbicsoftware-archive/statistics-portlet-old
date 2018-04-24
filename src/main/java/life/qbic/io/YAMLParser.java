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


    public static MainConfig parseConfig(String inputFile) {

        DumperOptions options = new DumperOptions();

        //this option needs to agree with the respective option set in the writer
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setPrettyFlow(true);
        Yaml yaml = new Yaml(options);

        MainConfig mainConfig;
        try{
            mainConfig = yaml.loadAs(new FileInputStream(inputFile), MainConfig.class);
            logger.info("Successfully parsed YAML config file");

        }catch(IOException e){
            e.printStackTrace();
            logger.error("Parsing of YAML file failed. Reason: " + e.getMessage());

            mainConfig = new MainConfig();
        }

        return mainConfig;
    }
}
