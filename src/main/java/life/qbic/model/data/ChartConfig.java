package life.qbic.model.data;

import java.util.ArrayList;
import java.util.Map;

public class ChartConfig {

    private ChartSettings settings;
    private Map<String,ArrayList<Object>> data;

    public ChartSettings getSettings() {
        return settings;
    }

    public void setSettings(ChartSettings settings) {
        this.settings = settings;
    }

    public Map<String,ArrayList<Object>>  getData() {
        return data;
    }

    public void setData(Map<String,ArrayList<Object>> data) {
        this.data = data;
    }
}
