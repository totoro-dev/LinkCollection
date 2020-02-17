package linkcollection.client.result;

import linkcollection.client.ui.frame.CollectionFrame;
import linkcollection.client.ui.widgets.Toast;
import linkcollection.common.interfaces.MonitorResult;
import monitor.Background;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;

public class MyMonitorResult implements MonitorResult {

    private static String currContent = "";
    private static Clipboard clipboard;

    @Override
    public String getClipboardContent() {
        try {
            clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            if (clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor))
                currContent = (String) clipboard.getData(DataFlavor.stringFlavor);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return currContent;
    }

    @Override
    public void clipboardContentChanged() {
        Background.startService();
    }

    @Override
    public void spiderSuccess(String link, String title, String label_1, String label_2, String label_3) {
        if (link == null || "".equals(link)) {
            // 全网没有收藏过该链接
        } else if ("链接已收藏".equals(title)) {
            Toast.makeText(CollectionFrame.context, link).show(Toast.SHORT);
        } else {
            CollectionFrame.setLinkTitle(title);
            CollectionFrame.setLink(link);
            CollectionFrame.setLabel_1_Field(label_1 == null ? "" : label_1);
            CollectionFrame.setLabel_2_Field(label_2 == null ? "" : label_2);
            CollectionFrame.setLabel_3_Field(label_3 == null ? "" : label_3);
            CollectionFrame.context.setVisible(true);
        }
    }

}