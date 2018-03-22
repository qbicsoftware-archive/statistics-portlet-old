package life.qbic.utils;

import life.qbic.model.data.MainConfig;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author fhanssen
 * Read given config file. DON'T change the DumperOptions, unless you are absolutely sure of what you are doing.
 * This has to be synced with the Writer in the data portlet.
 */
public final class YAMLParser {

    public static MainConfig parseConfig(String inputFile){

        DumperOptions options = new DumperOptions();

        //this option needs to agree with the respective option set in the writer
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setPrettyFlow(true);
        Yaml yaml = new Yaml(options);

        MainConfig mainConfig;
        try{
            mainConfig = yaml.loadAs(new FileInputStream(inputFile), MainConfig.class);
        }catch(IOException e){
            e.printStackTrace();
            mainConfig = new MainConfig();
        }

        return mainConfig;
    }
}
