package linkcollection.client.ui.frame;


import linkcollection.client.ui.bar.DefaultActionBar;
import linkcollection.client.ui.widgets.ImageButton;
import linkcollection.client.ui.widgets.ImageButtonListener;
import linkcollection.client.ui.widgets.WidgetConstant;
import linkcollection.common.constans.Constans;
import top.totoro.file.core.TFile;
import top.totoro.file.util.Disk;
import user.Info;

import javax.swing.*;

/**
 * 用户信息界面
 */
public class UserFrame extends JFrame {

    private JFrame parent;

    private static int startX = 10, startY = 100;
    private static JLabel lovesLabel = new JLabel("关注");
    private static JLabel lovesContentLabel = new JLabel();
    private static ImageButton changeLovesBtn;

    public UserFrame(JFrame parent) {
        this.parent = parent;
        TFile.builder().toDisk(Disk.TMP).toPath(Constans.INFO_PATH).toName(Constans.LOGIN_INFO_FILE_NAME).toFile();
        if (!TFile.getProperty().exists()) {
            new LoginFrame(parent);
            dispose();
            parent.setVisible(false);
            return;
        }
        setUndecorated(true);
        WidgetConstant.setVisibleSize(400, 300);
        setSize(WidgetConstant.VisibleWidth, WidgetConstant.VisibleHeight);
        setLocation(WidgetConstant.getCenterLocation());
        setLayout(null);
        DefaultActionBar actionBar = new DefaultActionBar(this);
        actionBar.setBounds(0, 0, WidgetConstant.VisibleWidth, 50);
        actionBar.setTitle("个人中心");
        add(actionBar);

        lovesLabel.setFont(WidgetConstant.TitleFont);
        lovesContentLabel.setFont(WidgetConstant.TitleFont);
        changeLovesBtn = new ImageButton(getClass(), "ui/img/change.png");
        lovesLabel.setBounds(startX, startY, 32, 30);
        String loves = LoveSelectFrame.getLovesInChinese(Info.getLoveInfo());
        refreshLoves(loves);

        add(lovesLabel);
        add(lovesContentLabel);
        add(changeLovesBtn);

        changeLovesBtn.addClickListener(new ImageButtonListener() {
            @Override
            public void click(String flag) {
                setVisible(false);
                LoveSelectFrame lsf = new LoveSelectFrame(UserFrame.this);
                lsf.init(Info.getLoveInfo());
            }
        });

        setVisible(true);
    }

    public static void refreshLoves(String loves) {
        if (changeLovesBtn == null) return;
        lovesContentLabel.setText(loves);
        lovesContentLabel.setBounds(startX + 40, startY, loves.length() * 16, 30);
        changeLovesBtn.setBounds(startX + 50 + loves.length() * 16, startY, 100, 30);
    }

    @Override
    public void dispose() {
        super.dispose();
        WidgetConstant.rollBackVisibleSize();
        parent.setVisible(true);
    }
}
