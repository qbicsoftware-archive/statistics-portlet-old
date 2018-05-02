package life.qbic.presenter.utils;

public class DataSorter implements Comparable<DataSorter> {

    private final String name;
    private final Integer count;

    public DataSorter (String name, int count){
        this.name = name;
        this.count  = count;
    }

    public String getName() {
        return name;
    }

    public Integer getCount() {
        return count;
    }

    public int compareTo(DataSorter o){
        return this.count.compareTo(o.count);
    }


}
