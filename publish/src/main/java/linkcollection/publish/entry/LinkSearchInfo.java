package linkcollection.publish.entry;

import java.io.Serializable;
import java.util.Objects;

public class LinkSearchInfo implements Serializable {

    private long id;
    private String link;
    private String labels;
    private String title;
    private String content;
    private String summary;
    public String userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LinkSearchInfo that = (LinkSearchInfo) o;
        return id == that.id &&
                Objects.equals(link, that.link) &&
                Objects.equals(labels, that.labels) &&
                Objects.equals(title, that.title) &&
                Objects.equals(content, that.content) &&
                Objects.equals(summary, that.summary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, link, labels, title, content, summary);
    }

    public LinkSearchInfo() {
    }

    public LinkSearchInfo(int linkId, String link, String labels, String title, String content, String summary) {
        this.id = linkId;
        this.link = link;
        this.labels = labels;
        this.title = title;
        this.content = content;
        this.summary = summary;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
