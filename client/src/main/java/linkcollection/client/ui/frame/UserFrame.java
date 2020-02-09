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
import java.awt.*;

/**
 * 用户信息界面
 */
public class UserFrame extends JFrame {

    private JFrame parent;

    private static int startX = 10, startY = 80;
    private static JLabel collectionLabel = new JLabel("收藏：");
    private static JLabel lovesLabel = new JLabel("关注：");
    private static JLabel vipLabel = new JLabel("会员：");
    private static ImageButton changeLovesBtn;
    private static ImageButton changeVipBtn;

    public UserFrame(JFrame parent) {
        this.parent = parent;
        changeLovesBtn = new ImageButton(getClass(), "ui/img/change.png");
        changeVipBtn = new ImageButton(getClass(), "ui/img/change.png");
        getContentPane().setBackground(Color.white);
        TFile.builder().toDisk(Disk.TMP).toPath(Constans.INFO_PATH).toName(Constans.LOGIN_INFO_FILE_NAME).toFile();
        if (!TFile.getProperty().exists()) {
            new LoginFrame(parent);
            dispose();
            parent.setVisible(false);
            return;
        }
        TFile.builder().recycle();
        setUndecorated(true);
        WidgetConstant.setVisibleSize(400, 300);
        setSize(WidgetConstant.VisibleWidth, WidgetConstant.VisibleHeight);
        setLocation(WidgetConstant.getCenterLocation());
        setLayout(null);
        DefaultActionBar actionBar = new DefaultActionBar(this);
        actionBar.setBounds(0, 0, WidgetConstant.VisibleWidth, 50);
        actionBar.setTitle("个人中心");
        add(actionBar);

        collectionLabel.setFont(WidgetConstant.TitleFont);
        lovesLabel.setFont(WidgetConstant.TitleFont);
        vipLabel.setFont(WidgetConstant.TitleFont);

        collectionLabel.setText("收藏：" + Info.getCollectionCount() + "链接");
        String loves = Info.getLovesInChinese(Info.getLoveInfo());
        refreshLoves(loves);
        vipLabel.setText("会员：" + Info.getVip());


        collectionLabel.setBounds(startX, startY, 290, 30);
        lovesLabel.setBounds(startX, startY + 40, 290, 30);
        changeLovesBtn.setBounds(startX + 290, startY + 40, 100, 30);
        vipLabel.setBounds(startX, startY + 80, 290, 30);

        add(collectionLabel);
        add(lovesLabel);
        add(changeLovesBtn);
        add(vipLabel);

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
        lovesLabel.setText("关注：" + loves);
    }

    @Override
    public void dispose() {
        super.dispose();
        WidgetConstant.rollBackVisibleSize();
        parent.setVisible(true);
    }

    public static void main(String[] args) {
        new UserFrame(MainFrame.context);
    }
}
