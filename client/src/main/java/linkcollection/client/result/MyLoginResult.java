package linkcollection.client.result;

import linkcollection.client.ui.MainContentPanel;
import linkcollection.client.ui.bar.LeftSelectBar;
import linkcollection.client.ui.bar.MainActionBar;
import linkcollection.client.ui.frame.CollectionFrame;
import linkcollection.client.ui.frame.LoginFrame;
import linkcollection.client.ui.frame.MainFrame;
import linkcollection.client.ui.widgets.Toast;
import linkcollection.client.ui.widgets.WidgetConstant;
import linkcollection.client.ui.widgets.adapter.LabelAdapter;
import linkcollection.client.ui.widgets.adapter.PushAdapter;
import linkcollection.client.ui.widgets.view.RecyclerView;
import linkcollection.common.interfaces.LoginResult;
import monitor.Background;
import user.Info;

public class MyLoginResult implements LoginResult {
    @Override
    public void loginError(String error) {
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
    public void loginSuccess(String s) {
        MainActionBar.setUserName(Info.getUserName());
        LabelAdapter.refreshInstance(Info.getCollectionInfos());
        new RecyclerView(LeftSelectBar.recycleLabelPanel).setAdapter(LabelAdapter.getInstance());
        LeftSelectBar.scrollPane.getViewport().add(LeftSelectBar.recycleLabelPanel);
        WidgetConstant.setVisibleSize(800, 600);
        PushAdapter.refreshInstance(Info.getLoveInfo());
        MainContentPanel.showPushContent();
        WidgetConstant.rollBackVisibleSize();
        new CollectionFrame(MainFrame.context);
        Background.startClipboardListener();
    }
}
