package linkcollection.client.result;

import linkcollection.client.ui.MainContentPanel;
import linkcollection.client.ui.bar.LeftSelectBar;
import linkcollection.client.ui.bar.MainActionBar;
import linkcollection.client.ui.frame.CollectionFrame;
import linkcollection.client.ui.frame.LoginFrame;
import linkcollection.client.ui.frame.MainFrame;
import linkcollection.client.ui.widgets.Toast;
import linkcollection.client.ui.widgets.WidgetConstant;
import linkcollection.client.ui.widgets.adapter.CollectLabelAdapter;
import linkcollection.client.ui.widgets.adapter.LoveLabelAdapter;
import linkcollection.client.ui.widgets.adapter.PushAdapter;
import linkcollection.client.ui.widgets.view.RecyclerView;
import linkcollection.common.interfaces.LoginResult;
import monitor.Background;
import user.Info;

public class MyLoginResult implements LoginResult {
    @Override
    public void autoLoginError(String error) {
        switch (error) {
            case "请重新登录":
                new LoginFrame(MainFrame.context);
            case "请注册或登录":
                new LoginFrame(MainFrame.context);
            default:
                Toast.makeText(LoginFrame.context, error).show(Toast.LONG);
                break;
        }
    }

    @Override
    public void loginError(String error) {
        switch (error) {
//            case "请重新登录":
//                new LoginFrame(MainFrame.context);
//            case "请注册或登录":
//                new LoginFrame(MainFrame.context);
            default:
                Toast.makeText(LoginFrame.context, error).show(Toast.LONG);
                break;
        }
    }

    @Override
    public void loginSuccess(String s) {
        MainActionBar.setUserName(Info.getUserName());
        Info.getCollectionInfo();
        CollectLabelAdapter.refreshInstance(Info.getCollectionInfos());
        LoveLabelAdapter.refreshInstance(Info.getLoveInfo());
        PushAdapter.refreshInstance(Info.getLoveInfo());
        new RecyclerView(LeftSelectBar.contentPanel).setAdapter(CollectLabelAdapter.getInstance());
        WidgetConstant.setVisibleSize(800, 600); // 确保更新显示时大小正确
        MainContentPanel.showPushContent();
        WidgetConstant.rollBackVisibleSize(); // 更新后回退布局大小为当前显示界面大小
        new CollectionFrame(MainFrame.context); // 只有登录成功时才会初始化收藏界面，但是否显示由MonitorResult决定
        Background.startClipboardListener(); // 开始监听剪贴板内容变化
    }
}
