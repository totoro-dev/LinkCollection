package monitor;

import com.alibaba.fastjson.JSONObject;
import linkcollection.common.AppCommon;
import linkcollection.common.constans.Constans;
import linkcollection.retrofit.LinkController;
import spider.LinkSpider;
import top.totoro.file.core.TFile;
import top.totoro.file.util.Disk;
import utils.ScheduledService;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Background {

    private static String oldContent = ",";
    private static String currContent = "";

    private static Runnable clipboardListenerTask;

    private static Runnable currTask;
    private static ScheduledFuture currFuture;

    public static void startClipboardListener() {
        if (clipboardListenerTask == null) {
            clipboardListenerTask = () -> {
                String content = AppCommon.getMonitorResult().getClipboardContent().trim();
                if (currContent != content) {
                    currContent = content;
                    if (!"".equals(currContent) && !oldContent.contains("," + currContent + ",")) {
                        oldContent += currContent + ",";
                        AppCommon.getMonitorResult().clipboardContentChanged();
                    }
                }
            };
            ScheduledService.LOCAL_SERVICE.scheduleWithFixedDelay(clipboardListenerTask, 0, 500, TimeUnit.MILLISECONDS);
        }
    }

    public static void startService() {
        currTask = createTask();
        currFuture = ScheduledService.LOCAL_SERVICE.schedule(currTask, 0, TimeUnit.MILLISECONDS);
    }

    public static void stopService() {
        if (currFuture == null) return;
        currFuture.cancel(true);
    }

    private static void restart() {
        if (currTask != null && currFuture.isCancelled()) {
            currFuture = ScheduledService.LOCAL_SERVICE.schedule(currTask, 1, TimeUnit.MILLISECONDS);
        }
    }

    private synchronized static Runnable createTask() {
        String content = AppCommon.getMonitorResult().getClipboardContent();
        if (!content.startsWith("http:") && !content.startsWith("https:")) {
            return () -> {
            };
        }
        return () -> {
            String link = currContent;
            String linkId;
            String title = null;
            String label_1 = null;
            String label_2 = null;
            String label_3 = null;
            link = link.replace("www.", "");
            boolean exist = LinkController.instance().exist(link);
            if (exist) {
                String info = LinkController.instance().selectAllByLink(link);
                if (info != null) {
                    JSONObject object = JSONObject.parseObject(info);
                    linkId = object.getString("linkId");
                    TFile.builder().recycle();
                    TFile.builder().toDisk(Disk.TMP).toPath(Constans.COLLECTION_PATH).toName(Constans.getCollectionFileName(linkId)).toFile();
                    if (!TFile.getProperty().exists()) {
                        TFile.builder().recycle();
                        label_1 = object.getString("label_1");
                        label_2 = object.getString("label_2");
                        label_3 = object.getString("label_3");
                    } else {
                        AppCommon.getMonitorResult().spiderSuccess(link, "链接已收藏", label_1, label_2, label_3);
                        return;
                    }
                }
            }
            try {
                title = new LinkSpider(link).getTitle();
            } catch (Exception e) {
                e.printStackTrace();
            }
            AppCommon.getMonitorResult().spiderSuccess(link, title, label_1, label_2, label_3);
        };
    }

}