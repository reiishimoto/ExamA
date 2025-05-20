package tool;

public class LinkData {

    private final String url;
    private final String text;

    public LinkData(String url, String text) {
        this.url = url;
        this.text = text;
    }

    public String getUrl() { return url; }
    public String getText() { return text; }
}
