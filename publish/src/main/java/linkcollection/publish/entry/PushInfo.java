package linkcollection.publish.entry;

import java.io.Serializable;

public class PushInfo implements Serializable {

    private String type;

    private long linkId;

    public PushInfo() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getLinkId() {
        return linkId;
    }

    public void setLinkId(long linkId) {
        this.linkId = linkId;
    }

    public PushInfo(String type, long linkId) {
        this.type = type;
        this.linkId = linkId;
    }
}
