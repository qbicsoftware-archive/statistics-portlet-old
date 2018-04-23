package life.qbic.model.components;

/**
 * @author fhanssen
 */
public class GitHubLabels extends AComponent{


    private final String title;
    private final String description;
    private final int count;
    private final String url;

    public GitHubLabels(String title, String description, int count){
        super();
        this.title = title;
        this.description = description.equals("null") ? "" : description;
        this.count = count;
        this.url = "https://github.com/".concat(title);
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCount() {
        return count;
    }

    public String getUrl() {
        return url;
    }
}