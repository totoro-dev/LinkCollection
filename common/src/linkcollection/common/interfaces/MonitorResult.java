package linkcollection.common.interfaces;

public interface MonitorResult {

    String getClipboardContent();

    void clipboardContentChanged();

    void spiderSuccess(String link, String title, String label_1, String label_2, String label_3);
}
