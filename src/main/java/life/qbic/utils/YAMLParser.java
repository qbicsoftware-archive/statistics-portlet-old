package life.qbic.utils;

import life.qbic.model.data.MainConfig;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.IOException;

public final class YAMLParser {

    public static MainConfig parseConfig(String inputFile){

        DumperOptions options = new DumperOptions();

        //this option needs to agree with the respective option set in the writer
        //TODO: may make sense to put the yaml io options in a mutual mainConfig, so changing it in one place doesn't lead to inconsistencies
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
