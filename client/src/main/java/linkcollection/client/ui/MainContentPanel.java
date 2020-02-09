package linkcollection.client.ui;

import linkcollection.client.ui.frame.MainFrame;
import linkcollection.client.ui.widgets.ContentBarItem;
import linkcollection.client.ui.widgets.ImageButton;
import linkcollection.client.ui.widgets.WidgetConstant;
import linkcollection.client.ui.widgets.adapter.ItemAdapter;
import linkcollection.client.ui.widgets.adapter.PushAdapter;
import linkcollection.client.ui.widgets.adapter.SearchAdapter;
import linkcollection.client.ui.widgets.view.RecyclerView;
import search.Search;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

@SuppressWarnings("ALL")
public class MainContentPanel extends JPanel {

    private JPanel contentBar = new JPanel(new GridLayout(1, 3));
    private ContentBarItem push = new ContentBarItem("推荐"), my = new ContentBarItem("我的"), other = new ContentBarItem("搜索");
    private JLabel selected = new JLabel();

    private static JPanel search = new JPanel(null);

    public static JPanel MyContentPanel = new JPanel(null);
    public static JPanel PushContentPanel = new JPanel(null);
    public static JPanel OtherContentPanel = new JPanel(null);

    public static JScrollPane PushScrollPanel = new JScrollPane(PushContentPanel);
    public static JScrollPane MyScrollPanel = new JScrollPane(MyContentPanel);
    public static JScrollPane OtherScrollPanel = new JScrollPane(OtherContentPanel);

    public static MainContentPanel instance;

    private static String key = "";
    private static String localKey = key;
    private static String serviceKey = key;

    public MainContentPanel() {
        instance = this;
        setLayout(null);
        contentBar.setBounds(0, 0, WidgetConstant.VisibleWidth - 200, 33);
        contentBar.add(push);
        contentBar.add(my);
        contentBar.add(other);
        contentBar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, WidgetConstant.BorderColor));
        contentBar.setBackground(Color.white);

        selected.setBounds(0, 33, WidgetConstant.VisibleWidth - 200, 7);
        select("推荐");

        MyContentPanel.setBackground(Color.white);
        PushContentPanel.setBackground(Color.white);
        OtherContentPanel.setBackground(Color.white);

        PushScrollPanel.setBounds(0, 40, WidgetConstant.VisibleWidth - 200, WidgetConstant.VisibleHeight - 90);
        MyScrollPanel.setBounds(0, 70, WidgetConstant.VisibleWidth - 200, WidgetConstant.VisibleHeight - 120);
        OtherScrollPanel.setBounds(0, 70, WidgetConstant.VisibleWidth - 200, WidgetConstant.VisibleHeight - 120);

        PushScrollPanel.setBorder(WidgetConstant.NoneBorder);
        MyScrollPanel.setBorder(WidgetConstant.NoneBorder);
        OtherScrollPanel.setBorder(WidgetConstant.NoneBorder);

        PushScrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        MyScrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        OtherScrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        PushContentPanel.setPreferredSize(new Dimension(WidgetConstant.VisibleWidth - 200, 0));
        MyContentPanel.setPreferredSize(new Dimension(WidgetConstant.VisibleWidth - 200, 0));
        OtherContentPanel.setPreferredSize(new Dimension(WidgetConstant.VisibleWidth - 200, 0));

        PushScrollPanel.getViewport().add(PushContentPanel);
        MyScrollPanel.getViewport().add(MyContentPanel);
        OtherScrollPanel.getViewport().add(OtherContentPanel);

        push.addListener((name) -> {
            showPushContent();
        });
        my.addListener((name) -> {
            showMyContent();
        });
        other.addListener((name) -> {
            showOtherContent();
        });

        add(contentBar);
        add(selected);
        add(PushScrollPanel);

        setBackground(Color.white);
    }

    public static void showPushContent() {
        closeView();
        instance.select("推荐");
        instance.add(PushScrollPanel);
        RecyclerView contentView = new RecyclerView(PushContentPanel);
        contentView.setAdapter(PushAdapter.getInstance());
        PushScrollPanel.getViewport().add(PushContentPanel);
    }

    public static void showMyContent() {
        closeView();
        instance.select("我的");
        instance.add(MyScrollPanel);
        RecyclerView contentView = new RecyclerView(MyContentPanel);
        contentView.setAdapter(ItemAdapter.getInstance());
        MyScrollPanel.getViewport().add(MyContentPanel);
    }

    public static void showOtherContent() {
        closeView();
        instance.select("搜索");
        instance.add(OtherScrollPanel);
        RecyclerView contentView = new RecyclerView(OtherContentPanel);
        contentView.setAdapter(SearchAdapter.getInstance());
        OtherScrollPanel.getViewport().add(OtherContentPanel);
    }

    private static void closeView() {
        instance.remove(MyScrollPanel);
        instance.remove(PushScrollPanel);
        instance.remove(OtherScrollPanel);
        instance.repaint();
    }

    public static void createSearchPanel(String hint, int type) {
        search.removeAll();
        search.setBackground(WidgetConstant.NormalColor);
        int width = 262;
        JLabel container = new JLabel() {
            @Override
            public void paint(Graphics g) {
                WidgetConstant.drawBorderFifteenRadius(g, Color.white, WidgetConstant.BorderColor, width);
                super.paint(g);
            }
        };
        JTextField input = new JTextField(hint);
        ImageButton submit = new ImageButton(MainFrame.context.getClass(), "ui/img/search-theme.png");
        container.setBounds((WidgetConstant.VisibleWidth - 200 - width) / 2, 0, width, 30);
        switch (type) {
            case 1:
                input.setText(localKey.equals("") ? hint : localKey);
                break;
            case 2:
                input.setText(serviceKey.equals("") ? hint : serviceKey);
                break;
        }
        input.setBackground(Color.white);
        input.setForeground(Color.GRAY);
        input.setFont(WidgetConstant.TitleFont);
        input.setBounds(16, 1, 200, 28);
        input.setBorder(WidgetConstant.NoneBorder);
        input.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                //获取焦点时，清空提示内容
                String temp = input.getText();
                if (temp.equals(hint)) {
                    input.setText("");
                }
                input.setForeground(Color.BLACK);
            }

            @Override
            public void focusLost(FocusEvent e) {
                //失去焦点时，没有输入内容，显示提示内容
                String temp = input.getText();
                if (temp.equals("")) {
                    input.setText(hint);
                }
                input.setForeground(Color.GRAY);
            }
        });

        input.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyChar() == (KeyEvent.VK_ENTER) && input.isFocusable()) {
                    search(input, type);
                }
            }
        });
        submit.setBounds(217, 0, 30, 30);
        container.add(input);
        container.add(submit);
        search.add(container);
        search.setBounds(0, 40, WidgetConstant.VisibleWidth - 200, 30);
        search.setBorder(WidgetConstant.BottomBorder);
        instance.add(search);
        submit.addClickListener(flag -> {
            search(input, type);
        });
        instance.repaint();
    }

    private static void search(JTextField input, int type) {
        key = input.getText();
        switch (type) {
            case 1:
                localKey = key;
                ItemAdapter.refreshInstance(Search.searchInLocal(key));
                showMyContent();
                break;
            case 2:
                serviceKey = key;
                SearchAdapter.refreshInstance(key);
                MainContentPanel.showOtherContent();
                break;
        }
    }

    public void select(String name) {
        ImageIcon selectIcon;
        switch (name) {
            case "推荐":
                setOrigin();
                push.setForeground(WidgetConstant.ThemeColor);
                selectIcon = new ImageIcon(getClass().getResource("/ui/img/select-push-600x7.png"));
                instance.remove(search);
                break;
            case "我的":
                setOrigin();
                my.setForeground(WidgetConstant.ThemeColor);
                selectIcon = new ImageIcon(getClass().getResource("/ui/img/select-my-600x7.png"));
                instance.remove(search);
                createSearchPanel("本地搜索", 1);
                break;
            case "搜索":
                setOrigin();
                other.setForeground(WidgetConstant.ThemeColor);
                selectIcon = new ImageIcon(getClass().getResource("/ui/img/select-other-600x7.png"));
                instance.remove(search);
                createSearchPanel("全网搜索", 2);
                break;
            default:
                setOrigin();
                selectIcon = new ImageIcon(getClass().getResource("/ui/img/select-origin.png"));
                break;
        }
        selected.setIcon(selectIcon);
    }

    private void setOrigin() {
        push.setForeground(Color.black);
        my.setForeground(Color.black);
        other.setForeground(Color.black);
    }

}
