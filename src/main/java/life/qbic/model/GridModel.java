package life.qbic.model;

import life.qbic.model.components.GitHubLabels;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GridModel extends AModel {


    private final List<Object> list = new ArrayList<>();

    public GridModel(String title, String subtitle){
        super(title, subtitle);
    }

    public void addData(GitHubLabels label){
        list.add(label);
    }

    public List<Object> getData() {
        return list;
    }
}
