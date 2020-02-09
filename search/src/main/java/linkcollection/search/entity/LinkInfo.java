package linkcollection.search.entity;

import java.io.Serializable;

public class LinkInfo  implements Serializable {
    private long linkId;
    private String link;
    private String label_1;
    private String label_2;
    private String label_3;

    public LinkInfo() {
    }

    public LinkInfo(long linkId, String link, String label_1, String label_2, String label_3) {
        this.linkId = linkId;
        this.link = link;
        this.label_1 = label_1;
        this.label_2 = label_2;
        this.label_3 = label_3;
    }

    public long getLinkId() {
        return linkId;
    }

    public void setLinkId(long linkId) {
        this.linkId = linkId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLabel_1() {
        return label_1;
    }

    public void setLabel_1(String label_1) {
        this.label_1 = label_1;
    }

    public String getLabel_2() {
        return label_2;
    }

    public void setLabel_2(String label_2) {
        this.label_2 = label_2;
    }

    public String getLabel_3() {
        return label_3;
    }

    public void setLabel_3(String label_3) {
        this.label_3 = label_3;
    }

}
