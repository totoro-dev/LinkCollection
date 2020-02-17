package entry;

public class SearchInfo {
    private String link;
    private String linkId;
    private String labels;
    private String title;

    public SearchInfo(String linkId, String link, String labels, String title) {
        this.link = link;
        this.linkId = linkId;
        this.labels = labels;
        this.title = title;
    }

    @Override
    public String toString() {
        return "CollectionInfo{" +
                "link='" + link + '\'' +
                ", linkId='" + linkId + '\'' +
                ", labels=" + labels +
                ", title='" + title + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

}
