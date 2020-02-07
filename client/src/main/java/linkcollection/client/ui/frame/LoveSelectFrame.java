package linkcollection.client.ui.frame;

import linkcollection.client.ui.MainContentPanel;
import linkcollection.client.ui.bar.DefaultActionBar;
import linkcollection.client.ui.widgets.ImageButton;
import linkcollection.client.ui.widgets.ImageButtonListener;
import linkcollection.client.ui.widgets.WidgetConstant;
import linkcollection.client.ui.widgets.adapter.PushAdapter;
import user.Info;
import user.Login;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 喜好选择界面
 */
public class LoveSelectFrame extends JFrame implements ImageButtonListener {

    private JFrame parent;

    private String[] loves;

    private ImageButton science;
    private ImageButton art;
    private ImageButton computer;
    private ImageButton healthy;
    private ImageButton economics;
    private ImageButton life;
    private ImageButton game;
    private ImageButton eat;
    private ImageButton tour;

    private ImageButton submit;

    public LoveSelectFrame(JFrame parent) {
        this.parent = parent;
        parent.setVisible(false);
        setUndecorated(true);
        WidgetConstant.setVisibleSize(400, 400);
        setSize(WidgetConstant.VisibleWidth, WidgetConstant.VisibleHeight);
        setLocation(WidgetConstant.getCenterLocation());
        setLayout(null);
        DefaultActionBar actionBar = new DefaultActionBar(this);
        actionBar.setBounds(0, 0, WidgetConstant.VisibleWidth, 50);
        actionBar.setTitle("喜好选择");
        add(actionBar);
        science = new ImageButton(getClass(), "ui/img/un-science.png");
        art = new ImageButton(getClass(), "ui/img/un-art.png");
        computer = new ImageButton(getClass(), "ui/img/un-computer.png");
        healthy = new ImageButton(getClass(), "ui/img/un-healthy.png");
        economics = new ImageButton(getClass(), "ui/img/un-economics.png");
        life = new ImageButton(getClass(), "ui/img/un-life.png");
        game = new ImageButton(getClass(), "ui/img/un-game.png");
        eat = new ImageButton(getClass(), "ui/img/un-eat.png");
        tour = new ImageButton(getClass(), "ui/img/un-tour.png");
        submit = new ImageButton(getClass(), "ui/img/sure.png");
        int startX = 50, startY = 80, width = 80, height = 80;
        science.setBounds(startX, startY, width, height);
        art.setBounds(startX + width + 30, startY, width, height);
        computer.setBounds(startX + 2 * width + 60, startY, width, height);
        healthy.setBounds(startX, startY + height + 10, width, height);
        economics.setBounds(startX + width + 30, startY + height + 10, width, height);
        life.setBounds(startX + 2 * width + 60, startY + height + 10, width, height);
        game.setBounds(startX, startY + 2 * height + 20, width, height);
        eat.setBounds(startX + width + 30, startY + 2 * height + 20, width, height);
        tour.setBounds(startX + 2 * width + 60, startY + 2 * height + 20, width, height);
        submit.setBounds(150, startY + 280, 100, 30);
        add(science);
        add(art);
        add(computer);
        add(healthy);
        add(economics);
        add(life);
        add(game);
        add(eat);
        add(tour);
        add(submit);
        getContentPane().setBackground(Color.white);
        setVisible(true);
        science.addClickListener(this::click);
        art.addClickListener(this::click);
        computer.addClickListener(this::click);
        healthy.addClickListener(this::click);
        economics.addClickListener(this::click);
        life.addClickListener(this::click);
        game.addClickListener(this::click);
        eat.addClickListener(this::click);
        tour.addClickListener(this::click);
        submit.addClickListener(this::click);
    }

    @Override
    public void dispose() {
        super.dispose();
        WidgetConstant.rollBackVisibleSize();
        parent.setVisible(true);
    }

    @Override
    public void click(String flag) {
        if (flag.equals(submit.path)) {
            String loves = getLoves();
            UserFrame.refreshLoves(getLovesInChinese(loves));
            PushAdapter.refreshInstance(loves);
            MainContentPanel.showPushContent();
            Info.refreshLoves(loves);
            dispose();
            return;
        }
        String love = "";
        if (flag.equals(science.path)) {
            love = changeImg(science);
        } else if (flag.equals(art.path)) {
            love = changeImg(art);
        } else if (flag.equals(computer.path)) {
            love = changeImg(computer);
        } else if (flag.equals(healthy.path)) {
            love = changeImg(healthy);
        } else if (flag.equals(economics.path)) {
            love = changeImg(economics);
        } else if (flag.equals(life.path)) {
            love = changeImg(life);
        } else if (flag.equals(game.path)) {
            love = changeImg(game);
        } else if (flag.equals(eat.path)) {
            love = changeImg(eat);
        } else if (flag.equals(tour.path)) {
            love = changeImg(tour);
        }
        changeLoves(love);
    }

    /**
     * 将原本的爱好，翻译成中文，但存储还是英文
     *
     * @param origin 英文的爱好
     * @return 中文的爱好
     */
    public static String getLovesInChinese(String origin) {
        String[] ls = origin.split(",");
        if (origin == null || origin.length() == 0) return "";
        String[] loves = new String[ls.length];
        for (int i = 0; i < ls.length; i++) {
            String l = ls[i];
            switch (l) {
                case "science":
                    loves[i] = "科技";
                    break;
                case "art":
                    loves[i] = "艺术";
                case "computer":
                    loves[i] = "计算机";
                    break;
                case "healthy":
                    loves[i] = "健康";
                    break;
                case "economics":
                    loves[i] = "经济";
                    break;
                case "life":
                    loves[i] = "生活";
                    break;
                case "game":
                    loves[i] = "游戏";
                    break;
                case "eat":
                    loves[i] = "美食";
                    break;
                case "tour":
                    loves[i] = "旅游";
                    break;
            }
        }
        return Arrays.stream(loves).collect(Collectors.joining(","));
    }

    /**
     * 获取当前已选择的爱好
     *
     * @return
     */
    public String getLoves() {
        return Arrays.stream(loves).collect(Collectors.joining(","));
    }

    public void init(String loves) {
        String[] ls = loves.split(",");
        this.loves = ls;
        for (String l :
                ls) {
            switch (l) {
                case "science":
                    changeImg(science);
                    break;
                case "art":
                    changeImg(art);
                    break;
                case "computer":
                    changeImg(computer);
                    break;
                case "healthy":
                    changeImg(healthy);
                    break;
                case "economics":
                    changeImg(economics);
                    break;
                case "life":
                    changeImg(life);
                    break;
                case "game":
                    changeImg(game);
                    break;
                case "eat":
                    changeImg(eat);
                    break;
                case "tour":
                    changeImg(tour);
                    break;
            }
        }
    }

    private String changeImg(ImageButton btn) {
        String love = "";
        if (btn.path.contains("un-")) {
            love = setSelected(btn);
        } else {
            love = setUnSelected(btn);
        }
        return love;
    }

    private void changeLoves(String love) {
        String ls = Arrays.stream(loves).collect(Collectors.joining(","));
        if (ls.startsWith(love)) {
            if (ls.length() == love.length()) {
                ls = "";
            } else {
                ls = ls.substring(love.length() + 1);
            }
        } else if (ls.endsWith(love)) {
            if (ls.length() == love.length()) {
                ls = "";
            } else {
                ls = ls.substring(0, ls.length() - love.length() - 1);
            }
        } else if (ls.contains(love)) {
            ls = ls.substring(0, ls.lastIndexOf(love)) + ls.substring(ls.lastIndexOf(love) + love.length() + 1);
        } else if (ls.length() == 0) {
            ls = love;
        } else {
            ls += "," + love;
        }
        loves = ls.split(",");
    }

    public String setSelected(ImageButton btn) {
        btn.setIcon(getClass(), btn.path.replace("un-", ""));
        return btn.path.substring(btn.path.lastIndexOf("/") + 1, btn.path.lastIndexOf("."));
    }

    public String setUnSelected(ImageButton btn) {
        String name = btn.path.substring(btn.path.lastIndexOf("/") + 1, btn.path.lastIndexOf("."));
        btn.setIcon(getClass(), btn.path.substring(0, btn.path.lastIndexOf("/") + 1) + "un-" + btn.path.substring(btn.path.lastIndexOf("/") + 1));
        return name;
    }
}
