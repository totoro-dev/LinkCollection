package linkcollection.search.entity;

public class LinkCheckInfo {

    private long headId;
    private long tailId;

    private String links;

    @Override
    public String toString() {
        return "LinkCheckInfo{" +
                "headId=" + headId +
                ", tailId=" + tailId +
                ", links='" + links + '\'' +
                '}';
    }

    public long getHeadId() {
        return headId;
    }

    public void setHeadId(long headId) {
        this.headId = headId;
    }

    public long getTailId() {
        return tailId;
    }

    public void setTailId(long tailId) {
        this.tailId = tailId;
    }

    public String getLinks() {
        return links;
    }

    public void setLinks(String links) {
        this.links = links;
    }

    public LinkCheckInfo() {
    }

    public LinkCheckInfo(long headId, long tailId, String links) {
        this.headId = headId;
        this.tailId = tailId;
        this.links = links;
    }
}
