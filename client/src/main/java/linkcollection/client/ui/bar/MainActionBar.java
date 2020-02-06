package linkcollection.client.ui.bar;

import linkcollection.client.ui.frame.MainFrame;
import linkcollection.client.ui.frame.SettingFrame;
import linkcollection.client.ui.frame.UserFrame;
import linkcollection.client.ui.widgets.ActionBar;
import linkcollection.client.ui.widgets.ImageButton;
import linkcollection.client.ui.widgets.ImageButtonListener;
import linkcollection.client.ui.widgets.WidgetConstant;
import top.totoro.file.core.TFile;
import top.totoro.file.util.Disk;
import user.Info;

import javax.swing.*;
import java.awt.*;

public class MainActionBar extends ActionBar implements ImageButtonListener {

    private JFrame main;

    private JPanel user = new JPanel(null);
    private JPanel operation = new JPanel(null);
    private JPanel search = new JPanel(null);

    private static JLabel name;
    private ImageButton head_pic, setting, min, exit;

    public static final String USER_HEAD_PATH = "link-collection,user-info";

    public MainActionBar(JFrame main) {
        super(main);
        this.main = main;
        setLayout(new GridLayout(1, 2));
        user.setBackground(WidgetConstant.ThemeColor);
        search.setBackground(WidgetConstant.ThemeColor);
        operation.setBackground(WidgetConstant.ThemeColor);
        addUserPanel();
        addOptPanel();
        addListener();
    }

    private void addListener() {
        head_pic.addClickListener(this::click);
        setting.addClickListener(this::click);
        min.addClickListener(this::click);
        exit.addClickListener(this::click);
    }

    private void addUserPanel() {
        head_pic = new ImageButton(getClass(), "ui/img/user-36x36.png");
        name = new JLabel("name");
        name.setFont(WidgetConstant.TitleFont);
        TFile.builder().toDisk(Disk.TMP).toPath(USER_HEAD_PATH).toName("user.jpg").toFile();
        if (TFile.getProperty().exists()) {
            head_pic.setIcon(TFile.getProperty().getFile().getPath());
        } else {
            TFile.builder().toName("user.png").toFile();
            if (TFile.getProperty().exists()) {
                head_pic.setIcon(TFile.getProperty().getFile().getPath());
            }
        }
        TFile.builder().recycle();
        head_pic.setLocation(10, 7);
        head_pic.setSize(36, 36);
        name.setBounds(60, 15, 220, 20);
        user.add(head_pic);
        user.add(name);
        add(user);
    }

    @SuppressWarnings("Duplicates")
    private void addOptPanel() {
        int optWidth = 30;
        setting = new ImageButton(getClass(), "ui/img/setting-16x16.png");
        min = new ImageButton(getClass(), "ui/img/min-16x16.png");
        exit = new ImageButton(getClass(), "ui/img/exit-16x16.png");
        setting.setSize(16, 16);
        min.setSize(16, 16);
        exit.setSize(16, 16);
        setting.setLocation(WidgetConstant.VisibleWidth / 2 - 3 * optWidth - 10, 15);
        min.setLocation(WidgetConstant.VisibleWidth / 2 - 2 * optWidth - 10, 15);
        exit.setLocation(WidgetConstant.VisibleWidth / 2 - optWidth - 10, 15);
        operation.add(setting);
        operation.add(min);
        operation.add(exit);
        add(operation);
    }

    public void setUserHead(ImageIcon icon) {
        this.head_pic.setIcon(icon);
    }

    public static void setUserName(String name) {
        MainActionBar.name.setText(name);
    }

    public void toSetting() {
        new SettingFrame(MainFrame.context);
    }

    @Override
    public void click(String flag) {
        if (flag.equals(head_pic.path)) {
            main.setVisible(false);
            new UserFrame(main);
        } else if (flag.equals(setting.path)) {
            main.setVisible(false);
            toSetting(); // 打开设置
        } else if (flag.equals(min.path)) {
            main.setExtendedState(JFrame.ICONIFIED);
        } else if (flag.equals(exit.path)) {
            showCloseTip();
        }
    }

    private void showCloseTip() {
        if (Info.getKnowable()) {
            main.setVisible(false);
            return;
        }
        Point start = WidgetConstant.getStartLocation(main);
        System.out.println("width:"+WidgetConstant.VisibleWidth+",height:"+WidgetConstant.VisibleHeight+",start:"+start.toString());
        int x = start.x + WidgetConstant.VisibleWidth / 2 - 125;
        int y = start.y + WidgetConstant.VisibleHeight / 2 - 75;
        JWindow window = new JWindow();
        window.setLayout(null);
        window.setBounds(x, y, 250, 150);
        JLabel container = new JLabel();
        container.setLayout(null);
        container.setBounds(0, 0, 250, 150);
        container.setBorder(WidgetConstant.WindowBorder);
        window.add(container);
        JLabel titleLabel = new JLabel("提示");
        JLabel tipsLabel1 = new JLabel("应用需要后台运行以提供服务");
        JLabel tipsLabel2 = new JLabel("可在系统托盘退出应用");
        titleLabel.setFont(WidgetConstant.TitleFont);
        titleLabel.setForeground(WidgetConstant.ThemeColor);
        tipsLabel1.setFont(WidgetConstant.TitleFont);
        tipsLabel2.setFont(WidgetConstant.TitleFont);
        ImageButton submit = new ImageButton(getClass(), "ui/img/sure.png");
        window.getContentPane().setBackground(Color.white);
        titleLabel.setBounds(10, 10, 100, 30);
        tipsLabel1.setBounds(10, 50, 230, 30);
        tipsLabel2.setBounds(10, 80, 230, 30);
        submit.setBounds(75, 115, 100, 30);
        container.add(titleLabel);
        container.add(tipsLabel1);
        container.add(tipsLabel2);
        container.add(submit);
        window.setVisible(true);
        submit.addClickListener((f) -> {
            Info.refreshKnowable(true);
            window.setVisible(false);
            main.setVisible(false);
        });
    }
}
