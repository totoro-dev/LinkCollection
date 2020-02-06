package entry;

public class CollectionInfo {
    private String link;
    private String linkId;
    private String[] labels;
    private String title;

    public CollectionInfo(String link, String linkId, String[] labels, String title) {
        this.link = link;
        this.linkId = linkId;
        this.labels = labels;
        this.title = title;
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

    public String[] getLabels() {
        return labels;
    }

    public void setLabels(String[] labels) {
        this.labels = labels;
    }

    public String getLabelsJoin(){
        String all = "";
        for (String label :
                labels) {
            all+=label;
        }
        return all;
    }
}
