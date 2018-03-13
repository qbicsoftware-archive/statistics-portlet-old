package life.qbic.model.data;

import java.util.Map;

public class MainConfig {

    private Map<String, ChartConfig> charts;

    public Map<String, ChartConfig> getCharts() {
        return charts;
    }

    public void setCharts(Map<String, ChartConfig> charts) {
        this.charts = charts;
    }

    @Override public String toString() {
        return "YamlConfig{" +
                "charts=" + charts +
                '}';
    }
}
