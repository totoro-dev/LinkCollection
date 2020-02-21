package linkcollection.client.ui.frame;

import linkcollection.client.result.*;
import linkcollection.client.ui.MainContentPanel;
import linkcollection.client.ui.MyTray;
import linkcollection.client.ui.bar.LeftSelectBar;
import linkcollection.client.ui.bar.MainActionBar;
import linkcollection.client.ui.widgets.Toast;
import linkcollection.client.ui.widgets.WidgetConstant;
import user.Info;
import user.Login;
import utils.CommonUtil;

import javax.swing.*;

/**
 * 用户主界面
 * 最后同步{@see test.UITest}的代码即可
 */
public class MainFrame extends JFrame {

    public static MainFrame context;

    public MainFrame() {
        super("LinkCollection");
        setIconImage(new ImageIcon(getClass().getClassLoader().getResource("ui/img/app.png")).getImage());
        context = this;
        new MyTray(this);
        setUndecorated(true);
        WidgetConstant.setVisibleSize(800, 600);
        setSize(WidgetConstant.VisibleWidth, WidgetConstant.VisibleHeight);
        setLocation(WidgetConstant.getCenterLocation());
        setLayout(null);

        MainActionBar actionBar = new MainActionBar(this);
        actionBar.setBounds(0, 0, WidgetConstant.VisibleWidth, 50);
        add(actionBar);

        LeftSelectBar leftSelectBar = new LeftSelectBar();
        leftSelectBar.setBounds(0, 50, 200, WidgetConstant.VisibleHeight - 50);
        add(leftSelectBar);

        MainContentPanel mainContentPanel = new MainContentPanel();
        mainContentPanel.setBounds(200, 50, WidgetConstant.VisibleWidth - 200, WidgetConstant.VisibleHeight - 50);
        add(mainContentPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        CommonUtil.checkAppCommon(new MyUserResult(), new MyPublishResult(), new MyLinkResult(), new MyLoginResult(),
                new MyRegisterResult(), new MyCheckMailResult(), new MyMonitorResult(), new MyLocalSearch());

        if (Login.autoLogin()) {
            setVisible(true);
            Toast.makeText(MainFrame.context, "已登录").show(Toast.LONG);
        } else setVisible(false);
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        if (b) {
            WidgetConstant.setVisibleSize(800, 600);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        System.exit(1);
    }
}
